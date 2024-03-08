package org.penz.emulator.cpu;

import org.penz.emulator.Constants;
import org.penz.emulator.cpu.opcode.DataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Alu {

    /**
     * One argument arithmetic or logical operations which can be performed by the ALU
     */
    private final Map<AluFunctionType, AluOperation> operations = new HashMap<>();

    /**
     * Two argument arithmetic or logical operations which can be performed by the ALU
     */
    private final Map<AluFunctionType, BiAluOperation> biOperations = new HashMap<>();

    /**
     * Get a one argument operation from the ALU by name and data type
     *
     * @param name     the name of the operation
     * @param dataType the data type of the argument
     * @return the operation
     * @throws IllegalArgumentException if the operation is not found
     */
    public AluOperation getOperation(String name, DataType dataType) {
        AluFunctionType functionType = new AluFunctionType(name, dataType);
        AluOperation operation = operations.get(functionType);
        if (operation == null) {
            throw new IllegalArgumentException("Unknown Alu operation: " + functionType);
        }
        return operation;
    }

    /**
     * Get a two argument operation from the ALU by name and data type
     *
     * @param name      the name of the operation
     * @param dataType  the data type of the first argument
     * @param dataType2 the data type of the second argument
     * @return the operation
     */
    public BiAluOperation getOperation(String name, DataType dataType, DataType dataType2) {
        AluFunctionType functionType = new AluFunctionType(name, dataType, dataType2);

        BiAluOperation operation = biOperations.get(functionType);
        if (operation == null) {
            throw new IllegalArgumentException("Unknown Alu operation: " + functionType);
        }
        return operation;
    }

    /**
     * Register a new function in the ALU with one argument
     *
     * @param name      the name of the function
     * @param dataType  the data type of the argument
     * @param operation the operation to perform
     */
    private void registerFunction(String name, DataType dataType, AluOperation operation) {
        operations.put(new AluFunctionType(name, dataType), operation);
    }

    /**
     * Register a new function in the ALU with two argument
     *
     * @param name      the name of the function
     * @param dataType  the data type of the first argument
     * @param operation the operation to perform
     */
    private void registerFunction(String name, DataType dataType, DataType dataType2, BiAluOperation operation) {
        biOperations.put(new AluFunctionType(name, dataType, dataType2), operation);
    }

    /*
      Initialize the ALU with all the functions it can perform
     */ {
//      increment an 8 bit argument by 1
        registerFunction("INC", DataType.d8, (flags, a) -> {
            a = (a + 1) & Constants.BYTE_MAX_VALUE;

            flags.setZ(a == 0);
            flags.setN(false);
            flags.setH(BitUtil.getLSByte(a) == 0x0F);
            return a;
        });

//      increment a 16 bit argument by 1
        registerFunction("INC", DataType.d16, (flags, a) -> {
            a = (a + 1) & Constants.WORD_MAX_VALUE;
            return a;
        });

//      decrement an 8 bit argument by 1
        registerFunction("DEC", DataType.d8, ((flags, a) -> {
            a = (a - 1) & Constants.BYTE_MAX_VALUE;

            flags.setZ(a == 0);
            flags.setN(true);
            flags.setH(BitUtil.getLSByte(a) == 0x0F);
            return a;
        }));

//      decrement a 16 bit argument by 1
        registerFunction("DEC", DataType.d16, ((flags, a) -> {
            a = (a - 1) & Constants.WORD_MAX_VALUE;
            return a;
        }));

//      add two 8 bit arguments
        registerFunction("ADD", DataType.d8, DataType.d8, ((flags, a, b) -> {
            int result = (a + b);

            flags.setC(result > Constants.BYTE_MAX_VALUE);
            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(false);
            flags.setH((BitUtil.getLSByte(a) + BitUtil.getLSByte(b)) > 0x0F);
            return result;
        }));

//      add two 16 bit arguments
        registerFunction("ADD", DataType.d16, DataType.d16, ((flags, a, b) -> {
            int result = (a + b);

            flags.setN(false);
            flags.setC(result > Constants.WORD_MAX_VALUE);
            flags.setH(((a & 0x0fff) + (b & 0x0fff) > 0x0fff));
            return result & Constants.WORD_MAX_VALUE;
        }));

//      add an 8 bit argument to the stack pointer
        registerFunction("ADD", DataType.d16, DataType.r8, ((flags, a, b) -> {
            int result = (a + b);
            flags.setZ(false);
            flags.setN(false);
            flags.setC(result > Constants.BYTE_MAX_VALUE);
            flags.setH((BitUtil.getLSByte(a) + BitUtil.getLSByte(b)) > 0x0f);
            return result & Constants.WORD_MAX_VALUE;
        }));

//      subtract two 8 bit arguments
        registerFunction("SUB", DataType.d8, DataType.d8, ((flags, a, b) -> {
            int result = (a - b);

            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(true);
            flags.setC(b > a);
            flags.setH(BitUtil.getLSByte(b) > BitUtil.getLSByte(a));

            return result;
        }));

//     bitwise and two 8 bit arguments
        registerFunction("AND", DataType.d8, DataType.d8, ((flags, a, b) -> {
            int result = (a & b);

            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(false);
            flags.setH(true);
            flags.setC(false);

            return result;
        }));

//      bitwise or two 8 bit arguments
        registerFunction("OR", DataType.d8, DataType.d8, ((flags, a, b) -> {
            int result = (a | b);

            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(false);
            flags.setH(false);
            flags.setC(false);

            return result;
        }));

//      bitwise xor two 8 bit arguments
        registerFunction("XOR", DataType.d8, DataType.d8, ((flags, a, b) -> {
            int result = (a ^ b);

            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(false);
            flags.setH(false);
            flags.setC(false);

            return result;
        }));

//      compare two 8 bit arguments
        registerFunction("CP", DataType.d8, DataType.d8, ((flags, a, b) -> {
            int result = (a - b);

            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(true);
            flags.setC(b > a);
            flags.setH(BitUtil.getLSByte(b) > BitUtil.getLSByte(a));

            return a;
        }));

//      add two 8 bit arguments and the carry
        registerFunction("ADC", DataType.d8, DataType.d8, (flags, a, b) -> {
            int result = (a + b + (flags.isC() ? 1 : 0));

            flags.setC(result > Constants.BYTE_MAX_VALUE);
            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(false);
            flags.setH((BitUtil.getLSByte(a) + BitUtil.getLSByte(b) + (flags.isC() ? 1 : 0)) > 0x0F);

            return result;
        });

//      subtract two 8 bit arguments and the carry
        registerFunction("SBC", DataType.d8, DataType.d8, (flags, a, b) -> {
            int result = (a - b - (flags.isC() ? 1 : 0));

            flags.setC(result < 0);
            result &= Constants.BYTE_MAX_VALUE;
            flags.setZ(result == 0);
            flags.setN(true);
            flags.setH((BitUtil.getLSByte(b) + (flags.isC() ? 1 : 0)) > BitUtil.getLSByte(a));

            return result;
        });

//      bcd adjust the accumulator
        registerFunction("DAA", DataType.d8, (flags, a) -> {

            if (flags.isN()) {
                //previous instruction was a subtraction
                if (flags.isH()) {
                    a = (a - 0x06) & Constants.BYTE_MAX_VALUE;
                }
                if (flags.isC()) {
                    a -= 0x60;
                }
            } else {
                //previous instruction was an addition
                if ((a & 0x0F) > 0x09 || flags.isH()) {
                    a += 0x06;
                }
                if (a > 0x9F || flags.isC()) {
                    a += 0x60;
                }
            }

            flags.setC(a > Constants.BYTE_MAX_VALUE);

            a &= Constants.BYTE_MAX_VALUE;
            flags.setZ(a == 0);
            flags.setH(false);

            return a;
        });

//      set carry flag
        registerFunction("SCF", DataType.d8, (flags, a) -> {
            flags.setN(false);
            flags.setH(false);
            flags.setC(true);

            return a;
        });

//      complement accumulator
        registerFunction("CPL", DataType.d8, (flags, a) -> {
            flags.setN(true);
            flags.setH(true);

            return ~a & Constants.BYTE_MAX_VALUE;
        });

//      complement carry flag
        registerFunction("CCF", DataType.d8, (flags, a) -> {
            flags.setN(false);
            flags.setH(false);
            flags.setC(!flags.isC());

            return a;
        });

    }

    public static class AluFunctionType {
        private final String name;

        private final DataType type1;

        private final DataType type2;

        public AluFunctionType(String name, DataType type1, DataType type2) {
            this.name = name;
            this.type1 = type1;
            this.type2 = type2;
        }

        public AluFunctionType(String name, DataType type1) {
            this(name, type1, null);
        }

        public String getName() {
            return name;
        }

        public DataType getType1() {
            return type1;
        }

        public DataType getType2() {
            return type2;
        }

        public String toString() {
            return String.format("AluFunctionType: %s-%s-%s", this.name, this.type1, this.type2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type1, type2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            if (!name.equals(((AluFunctionType) o).name)) return false;
            if (!type1.equals(((AluFunctionType) o).type1)) return false;
            return Objects.equals(type2, ((AluFunctionType) o).type2);
        }

    }

    /**
     * A one argument operation for the ALU
     */
    @FunctionalInterface
    public interface AluOperation {

        /**
         * Perform an operation on the ALU for a single argument
         *
         * @param flags to set the flags
         * @param a     the argument
         * @return the result of the operation
         */
        int apply(Flags flags, int a);
    }

    /**
     * A two argument operation for the ALU
     */
    @FunctionalInterface
    public interface BiAluOperation {

        /**
         * Perform an operation on the ALU for two arguments
         * @param flags to set the flags
         * @param a the first argument
         * @param b the second argument
         * @return the result of the operation
         */
        int apply(Flags flags, int a, int b);
    }
}
