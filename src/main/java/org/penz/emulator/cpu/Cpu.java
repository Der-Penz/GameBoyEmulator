package org.penz.emulator.cpu;

import org.penz.emulator.cpu.instructions.DataType;
import org.penz.emulator.cpu.instructions.OpCode;
import org.penz.emulator.cpu.instructions.NopInstruction;
import org.penz.emulator.cpu.instructions.load.LoadSPInstruction;
import org.penz.emulator.memory.AddressSpace;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Cpu {

    /**
     * Cpu Registers and Flags
     */
    private final Registers registers;

    private final AddressSpace memory;

    private final Alu alu;

    private final Map<Integer, OpCode> chipset = new HashMap<>();
    private IllegalArgumentException illegalArgumentException;

    {
        registerOpcodeInChipset(NopInstruction.class);
        registerOpcodeInChipset(LoadSPInstruction.class);
    }

    /**
     * Load the opcode into the chipset
     *
     * @param instructionClass The instruction class
     */
    private void registerOpcodeInChipset(Class<? extends OpCode> instructionClass) {
        if (instructionClass == null) throw new IllegalArgumentException("Instruction class must not be null");
        if (instructionClass.getDeclaredConstructors().length != 1)
            throw new IllegalArgumentException("Instruction class must have exactly one constructor");

        try {
            OpCode instruction = (OpCode) instructionClass.getDeclaredConstructors()[0].newInstance();
            chipset.put(instruction.getOpcode(), instruction);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the opcode from the chipset
     *
     * @param opcode The opcode as integer
     * @return OpCode instance
     * @throws IllegalArgumentException if the opcode is not supported in this chipset
     */
    public OpCode getOpcode(int opcode) {
        OpCode instruction = chipset.get(opcode);

        if (instruction == null) {
            throw new IllegalArgumentException(String.format("Unknown opcode: 0x%02X - not supported in this chipset", opcode));
        }
        return chipset.get(opcode);
    }

    public Cpu(AddressSpace memory) {
        this.registers = new Registers();
        this.alu = new Alu();
        this.memory = memory;
    }

    /**
     * Execute a single instruction
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
        OpCode instruction = getOpcode(opcode);
        int cycles = executeInstruction(instruction);
    }


    public Registers getRegisters() {
        return registers;
    }
}
