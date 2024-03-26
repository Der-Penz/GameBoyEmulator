import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
import org.penz.emulator.memory.AddressSpace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private AddressSpace createMemory() {
        return new AddressSpace() {

            final int[] memory = new int[0x20000];

            @Override
            public boolean accepts(int address) {
                return address < memory.length;
            }

            @Override
            public void writeByte(int address, int value) {
                memory[address] = value;
            }

            @Override
            public int readByte(int address) {
                return memory[address];
            }
        };
    }

    private void setByState(EmulatorState state, Registers registers, AddressSpace memory) {
        registers.setA(state.a());
        registers.setB(state.b());
        registers.setC(state.c());
        registers.setD(state.d());
        registers.setE(state.e());
        registers.setH(state.h());
        registers.setL(state.l());
        registers.setPC(state.pc());
        registers.setSP(state.sp());
        registers.getFlags().setFlags(state.f());

        for (int i = 0; i < state.ram().length; i++) {
            int[] ram = state.ram()[i];
            memory.writeByte(ram[0], ram[1]);
        }
    }

    static Stream<Arguments> opCodeNumbers() {
        Cpu cpu = new Cpu(null, new InterruptManager());

        List<Integer> excludeOpcodes = List.of(0xD3, 0xDB, 0xDD, 0xE3, 0xE4, 0xEB, 0xEC, 0xED, 0xF4, 0xFC, 0xFD);
        return IntStream.rangeClosed(0, 511).boxed().
                filter(i -> !excludeOpcodes.contains(i)).
                map(i -> Arguments.of(i, i / 255 > 1, cpu.getOpcode(i, false).getName() + " " + BitUtil.toHex(i)));
    }

    @ParameterizedTest(name = "Opcode {2}")
    @MethodSource("opCodeNumbers")
    @DisplayName("Test Opcode")
    public void opcodeTest(int testNumber, boolean bitInstruction, @SuppressWarnings("unused") String description) {
        String number = Integer.toHexString(testNumber);
        if (number.length() == 1) {
            number = "0" + number;
        }

        List<OpcodeTestData> testData = readJSONTestFile(TEST_RESOURCES + "\\" + number + ".json");

        AddressSpace memory = createMemory();
        Cpu cpu = new Cpu(memory, new InterruptManager());


        OpCode op = cpu.getOpcode(testNumber, bitInstruction);

        if (op == null) {
            fail("Opcode not registered");
            return;
        }

        for (OpcodeTestData data : testData) {
            Registers registers = new Registers();
            EmulatorState state = data.initialState();
            setByState(state, registers, memory);

            registers.getAndIncPC();
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

            registers.setPC(registers.getPC() & 0xFFFF);

            op.execute(registers, memory, alu, args);

            EmulatorState newState = toEmulatorState(registers, memory, Arrays.stream(data.finalState().ram()).mapToInt(ram -> ram[0]).toArray());

            assertEmulatorState(data.finalState(), newState);

            // Reset memory
            Arrays.stream(data.initialState().ram()).forEach(ram -> memory.writeByte(ram[0], 0));
            Arrays.stream(data.finalState().ram()).forEach(ram -> memory.writeByte(ram[0], 0));
        }
    }

    private EmulatorState toEmulatorState(Registers registers, AddressSpace memory, int[] ramAddresses) {
        int[][] ram = new int[ramAddresses.length][2];
        for (int i = 0; i < ramAddresses.length; i++) {
            ram[i] = new int[]{ramAddresses[i], memory.readByte(ramAddresses[i])};
        }

        return new EmulatorState(
                registers.getA(),
                registers.getB(),
                registers.getC(),
                registers.getD(),
                registers.getE(),
                registers.getFlags().getFlags(),
                registers.getH(),
                registers.getL(),
                registers.getPC(),
                registers.getSP(),
                ram
        );
    }

    private void assertEmulatorState(EmulatorState expected, EmulatorState actual) {
        assertEquals(expected.a(), actual.a());
        assertEquals(expected.b(), actual.b());
        assertEquals(expected.c(), actual.c());
        assertEquals(expected.d(), actual.d());
        assertEquals(expected.e(), actual.e());
        assertEquals(expected.h(), actual.h());
        assertEquals(expected.l(), actual.l());
        assertEquals(expected.pc(), actual.pc());
        assertEquals(expected.sp(), actual.sp());
        assertEquals(expected.f(), actual.f());

        for (int i = 0; i < expected.ram().length; i++) {
            int[] expectedRam = expected.ram()[i];
            int[] actualRam = actual.ram()[i];

            assertEquals(expectedRam[0], actualRam[0]);
            assertEquals(expectedRam[1], actualRam[1]);
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

