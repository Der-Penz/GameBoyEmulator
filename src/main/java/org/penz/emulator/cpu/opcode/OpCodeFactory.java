package org.penz.emulator.cpu.opcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpCodeFactory {

    public static void createOpCodes(String dir, String content, int start, int step, String filename) throws IOException {
        //replac the Content setA with setB
        //write the content to the file

        //go through a lsit of all registers
        //replace the content with the register name
        //write the content to the file
        List registers = new ArrayList<>();
        registers.add("B");
        registers.add("C");
        registers.add("D");
        registers.add("E");
        registers.add("H");
        registers.add("L");

        for (int i = 0; i < registers.size(); i++) {
            String register = (String) registers.get(i);
            String newContent1 = content.replace("__REG__", register);
            String hex = String.format("0x%02X", start);
            String newContent2 = newContent1.replace("__OP__", hex);

            //write the content to a file
            String fileName = dir + "/" + filename.replace("__REG__", register);

            System.out.println(fileName);
            //write the content to the file
            File file = new File(fileName);
            if (file.createNewFile()) {
                //write the content to the file

                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(newContent2);
                fileWriter.close();
            }
//            write the content to the file

            start += step;
        }


    }

    public static void main(String[] args) throws IOException {
        String content = "package org.penz.emulator.cpu.opcode.instructions.logical;\n" +
                "\n" +
                "import org.penz.emulator.cpu.Alu;\n" +
                "import org.penz.emulator.cpu.Registers;\n" +
                "import org.penz.emulator.cpu.opcode.DataType;\n" +
                "import org.penz.emulator.cpu.opcode.OpCode;\n" +
                "import org.penz.emulator.memory.AddressSpace;\n" +
                "\n" +
                "/**\n" +
                " * AND accumulator with register __REG__\n" +
                " */\n" +
                "public class AND__REG__Instruction extends OpCode {\n" +
                "\n" +
                "    public AND__REG__Instruction() {\n" +
                "        super(__OP__, \"AND __REG__\", 4);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int execute(Registers registers, AddressSpace addressSpace, Alu alu, int[] args) {\n" +
                "\n" +
                "        var aluOperation = alu.getOperation(\"AND\", DataType.d8, DataType.d8);\n" +
                "\n" +
                "        registers.setA(aluOperation.apply(registers.getFlags(), registers.getA(), registers.get__REG__()));\n" +
                "\n" +
                "        return cycles;\n" +
                "    }\n" +
                "}";

        createOpCodes("src/main/java/org/penz/emulator/cpu/opcode/instructions/logical", content, 0xA0, 1, "AND__REG__Instruction.java");
    }
}
