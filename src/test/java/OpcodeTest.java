import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.penz.emulator.cpu.Alu;
import org.penz.emulator.cpu.BitUtil;
import org.penz.emulator.cpu.Cpu;
import org.penz.emulator.cpu.Registers;
import org.penz.emulator.cpu.interrupt.InterruptManager;
import org.penz.emulator.cpu.opcode.DataType;
import org.penz.emulator.cpu.opcode.OpCode;
import org.penz.emulator.cpu.opcode.instructions.misc.NopInstruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Opcode Tests")
class OpcodeTest {

    private static String TEST_RESOURCES = "src/test/resources/";
    private final Alu alu = new Alu();

    @BeforeAll
    public static void setupResources() {

        String path = System.getenv().getOrDefault("TEST_PATH", "-");
        if (path.equals("-")) {
            fail("Provide a path to the JSON Test files");
            System.exit(1);
        } else {
            System.out.println("PATH: " + path);
        }
        OpcodeTest.TEST_RESOURCES = path;
    }

    static Stream<Arguments> opCodeNumbers() {
        Cpu cpu = new Cpu(null, new InterruptManager());

        List<Integer> excludeOpcodes = new ArrayList<>(List.of(0xCB, 0xD3, 0xDB, 0xDD, 0xE3, 0xE4, 0xEB, 0xEC, 0xED, 0xF4, 0xFC, 0xFD));
        excludeOpcodes.add(0xFF); // No test for RST 38H available
        return IntStream.rangeClosed(0, 255).boxed().
                filter(i -> !excludeOpcodes.contains(i)).
                map(i -> Arguments.of(i, i == 0xCB ? "CB prefixed (all)" : cpu.getOpcode(i, false).getName() + " " + BitUtil.toHex(i)));
    }

    @Test()
    @DisplayName("CB Prefix Opcode Set")
    public void cbPrefixOpcodeTest() {
        runTest(0xCB);
    }

    @ParameterizedTest(name = "Opcode {1}")
    @MethodSource("opCodeNumbers")
    @DisplayName("Basic Opcode Set")
    public void opcodeTest(int testNumber, @SuppressWarnings("unused") String description) {
        runTest(testNumber);
    }

    public void runTest(int testNumber) {
        String number = Integer.toHexString(testNumber);
        if (number.length() == 1) {
            number = "0" + number;
        }

        List<OpcodeTestData> testData = readJSONTestFile(TEST_RESOURCES + "\\" + number + ".json");

        ResettableMemory memory = new ResettableMemory();
        Cpu cpu = new Cpu(memory, new InterruptManager());


        OpCode op;
        boolean bitInstruction = false;
        if (testNumber == 0xcb) {
            System.out.println("CB prefix detected");
            bitInstruction = true;
            op = new NopInstruction();
        } else {
            op = cpu.getOpcode(testNumber, false);
        }

        if (op == null) {
            fail("Opcode not registered");
            return;
        }

        int passed = 0;
        for (OpcodeTestData data : testData) {
            Registers registers = new Registers();
            EmulatorState initialState = data.initialState();

            EmulatorState.setByState(initialState, registers, memory);

            registers.getAndIncPC();

            if (bitInstruction) {
                op = cpu.getOpcode(memory.readByte(registers.getAndIncPC()), bitInstruction);
            }

            int[] args = new int[op.getArgsTypeLength()];

            for (int i = 0; i < args.length; i++) {
                DataType dataType = op.getArgsType()[i];

                switch (dataType) {
                    case d8 -> args[i] = memory.readByte(registers.getAndIncPC());
                    case a16, d16 -> {
                        int low = memory.readByte(registers.getAndIncPC());
                        int high = memory.readByte(registers.getAndIncPC());

                        args[i] = BitUtil.toWord(high, low);
                    }
                    case r8 -> {
                        int d = memory.readByte(registers.getAndIncPC());

                        args[i] = BitUtil.toSignedByte(d);
                    }
                    default -> throw new IllegalArgumentException("Unknown data type: " + dataType);
                }
            }

            op.execute(registers, memory, alu, args);

            EmulatorState newState = EmulatorState.toEmulatorState(registers, memory, Arrays.stream(data.finalState().ram()).mapToInt(ram -> ram[0]).toArray());

            try {
                data.finalState().assertTo(newState);
            } catch (AssertionError e) {
                System.err.println("Failed on test: " + data.name());
                System.err.println("With opcode: " + op);
                System.err.println("Passed subtests: " + passed + "/" + testData.size());
                throw e;
            }

            memory.resetMemoryAccess();
            passed++;
        }
    }

    private List<OpcodeTestData> readJSONTestFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(EmulatorState.class, new EmulatorStateDeserializer())
                    .registerTypeAdapter(Cycle.class, new CycleDeserializer())
                    .create();

            return gson.fromJson(sb.toString(), new TypeToken<List<OpcodeTestData>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

}