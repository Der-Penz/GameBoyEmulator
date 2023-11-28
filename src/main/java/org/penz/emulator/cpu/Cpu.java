package org.penz.emulator.cpu;

import org.apache.commons.io.FileUtils;
import org.penz.emulator.Constants;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.interrupt.InterruptType;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cpu {

    /**
     * Cpu Registers and Flags
     */
    private final Registers registers;

    private final AddressSpace memory;

    private final Alu alu;

    private final InterruptManager interruptManager;

    private final Map<Integer, OpCode> chipset = new HashMap<>();

    private final Map<Integer, OpCode> bitChipset = new HashMap<>();

    private CpuState state = CpuState.OPCODE;

    private InterruptType requestedInterrupt;

    {
        getOpcodesClasses().forEach(this::registerOpcodeInChipset);
    }

    public Cpu(AddressSpace memory, InterruptManager interruptManager) {
        this.registers = new Registers();
        this.alu = new Alu();
        this.memory = memory;
        this.interruptManager = interruptManager;
    }

    private static List<Class<?>> getOpcodesClasses() {

        //TODO rewrite maybe put it in a separate class
        List<Class<?>> classes = new ArrayList<>();

        String moduleName = Constants.INSTRUCTIONS_MODULE_PATH;

        String path = moduleName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource != null) {
            File directory = new File(resource.getFile());

            if (!directory.exists()) {
                throw new RuntimeException("Directory does not exist: " + directory);
            }

            var allFiles = FileUtils.listFiles(directory, new String[]{"class"}, true);
            allFiles.forEach((file) -> {
                //full classname without .class extension
                String moduleFullPath = file.getPath().replace(File.separator, ".");
                String modulePath = moduleFullPath.substring(moduleFullPath.indexOf("org."), moduleFullPath.length() - 6);
                try {
                    Class<?> clazz = Class.forName(modulePath);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                }
            });
        }

        return classes;
    }

    /**
     * Load the opcode into the chipset
     *
     * @param instructionClass The instruction class
     */
    private void registerOpcodeInChipset(Class<?> instructionClass) {

        if (instructionClass == null)
            throw new IllegalArgumentException("Instruction class must not be null");
        if (!OpCode.class.isAssignableFrom(instructionClass))
            throw new IllegalArgumentException("Instruction class must extend OpCode");
        if (instructionClass.getDeclaredConstructors().length != 1)
            throw new IllegalArgumentException("Instruction class must have exactly one constructor");

        try {
            OpCode instruction = (OpCode) instructionClass.getDeclaredConstructors()[0].newInstance();

            if (instruction.isBitInstruction()) {
                bitChipset.put(instruction.getOpcode(), instruction);
            } else {
                chipset.put(instruction.getOpcode(), instruction);
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the opcode from the chipset
     *
     * @param opcode           The opcode as integer
     * @param isBitInstruction if the opcode is a bit instruction (CB prefix)
     * @return OpCode instance
     * @throws IllegalArgumentException if the opcode is not supported in this chipset
     */
    public OpCode getOpcode(int opcode, boolean isBitInstruction) {

        OpCode instruction = isBitInstruction ? bitChipset.get(opcode) : chipset.get(opcode);

        if (instruction == null) {
            throw new IllegalArgumentException(String.format("Unknown opcode:" + (isBitInstruction ? "CB " : " ") + "0x%02X - not supported in this chipset", opcode));
        }
        return instruction;
    }

    /**
     * Execute a single instruction
     *
     * @param opCode The opcode to execute
     * @return The number of cycles the instruction took to execute
     */
    public int executeInstruction(OpCode opCode) {
        int[] args = new int[opCode.getArgsTypeLength()];

        for (int i = 0; i < args.length; i++) {
            DataType dataType = opCode.getArgsType()[i];

            switch (dataType) {
                case d8 -> args[i] = memory.readByte(registers.getAndIncPC());
                case a16, d16 -> {
                    int low = memory.readByte(registers.getAndIncPC());
                    int high = memory.readByte(registers.getAndIncPC());

                    args[i] = BitUtil.toWord(high, low);
                }
                case r8 -> {
                    int data = memory.readByte(registers.getAndIncPC());

                    args[i] = BitUtil.toSignedByte(data);
                }
                default -> throw new IllegalArgumentException("Unknown data type: " + dataType);

            }
        }

        //special case for HALT and STOP instruction
        if (opCode.getOpcode() == 0x76) {
            state = CpuState.HALTED;
        } else if (opCode.getOpcode() == 0x10) {
            state = CpuState.STOPPED;
        }

        //TODO add timing to instructions to have accurate memory timings
        return opCode.execute(registers, memory, alu, args);
    }

    /**
     * Execute one CPU tick
     */
    public int tick() {

        if (state == CpuState.HALTED || state == CpuState.OPCODE || state == CpuState.STOPPED) {
            if (registers.interruptsEnabled() && interruptManager.anyInterruptsRequested()) {
                state = CpuState.IT_REQUESTED;
            }
        }

        // wake up from halt
        if (state == CpuState.HALTED && interruptManager.anyInterruptsRequested()) {
            state = CpuState.OPCODE;
        }

        if (CpuState.isInterruptState(state)) {
            return handleInterrupt();
        }

        if (state == CpuState.HALTED) {
            return 4;
        }

        int opcode = memory.readByte(registers.getAndIncPC());

        // CB prefix means bit instructions
        boolean isBitInstruction = opcode == 0xCB;
        if (isBitInstruction) {
            opcode = memory.readByte(registers.getAndIncPC());
        }
        OpCode instruction = getOpcode(opcode, isBitInstruction);

        return executeInstruction(instruction);
    }

    /**
     * Handle an interrupt.
     * Should be called when an interrupt is requested and IME is enabled.
     *
     * @return The number of cycles that passed
     */
    private int handleInterrupt() {

        switch (state) {
            case IT_REQUESTED:
                requestedInterrupt = interruptManager.getRequestedInterruptWithHighestPriority();

                if (requestedInterrupt == null) {
                    state = CpuState.OPCODE;
                } else {
                    registers.disableInterrupts();
                    interruptManager.clearInterrupt(requestedInterrupt);
                    state = CpuState.IT_PUSH_LOW;
                }

                //takes 2 cycles for reading IF and IE
                return 8;
            case IT_PUSH_HIGH:
                memory.writeByte(registers.decrementSP(), BitUtil.getMSByte(registers.getPC()));
                break;
            case IT_PUSH_LOW:
                memory.writeByte(registers.decrementSP(), BitUtil.getLSByte(registers.getPC()));
                break;
            case IH_JP_ADDRESS:
                registers.setPC(requestedInterrupt.getJumpAddress());
                state = CpuState.OPCODE;
                break;
            default:
                throw new IllegalStateException("Unexpected CPU state: " + state + " when handling interrupt");
        }

        return 4;
    }

    public Registers getRegisters() {
        return registers;
    }
}
