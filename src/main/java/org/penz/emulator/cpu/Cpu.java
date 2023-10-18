package org.penz.emulator.cpu;

import org.apache.commons.io.FileUtils;
import org.penz.emulator.Constants;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.memory.AddressSpace;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class Cpu {

    /**
     * Cpu Registers and Flags
     */
    private final Registers registers;

    private final AddressSpace memory;

    private final Alu alu;

    private final Map<Integer, OpCode> chipset = new HashMap<>();

    private final Map<Integer, OpCode> bitChipset = new HashMap<>();

    {
        getOpcodesClasses().forEach(this::registerOpcodeInChipset);
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
                String modulePath = moduleFullPath.substring(moduleFullPath.indexOf("org."),  moduleFullPath.length() - 6);
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

    public Cpu(AddressSpace memory) {
        this.registers = new Registers();
        this.alu = new Alu();
        this.memory = memory;
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
                case d16 -> {
                    int low = memory.readByte(registers.getAndIncPC());
                    int high = memory.readByte(registers.getAndIncPC());

                    args[i] = BitUtil.toWord(high, low);
                }
                case r8 -> {
                    int offset = memory.readByte(registers.getAndIncPC());

                    args[i] = BitUtil.toSignedByte(offset);
                }
                default -> throw new IllegalArgumentException("Unknown data type: " + dataType);

            }
        }
        return opCode.execute(registers, memory, alu, args);
    }

    /**
     * Execute one CPU tick
     */
    public void tick() {
        int opcode = memory.readByte(registers.getAndIncPC());

        // CB prefix means bit instructions
        boolean isBitInstruction = opcode == 0xCB;
        if (isBitInstruction) {
            opcode = memory.readByte(registers.getAndIncPC());
        }
        OpCode instruction = getOpcode(opcode, isBitInstruction);
        int cycles = executeInstruction(instruction);
    }


    public Registers getRegisters() {
        return registers;
    }
}
