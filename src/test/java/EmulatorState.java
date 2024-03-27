import org.penz.emulator.cpu.Registers;
import org.penz.emulator.memory.AddressSpace;

import static org.junit.jupiter.api.Assertions.assertEquals;

public record EmulatorState(
        int a, int b, int c, int d, int e, int f, int h, int l,
        int pc, int sp, int[][] ram
) {
    static void setByState(EmulatorState state, Registers registers, AddressSpace memory) {
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

    static EmulatorState toEmulatorState(Registers registers, AddressSpace memory, int[] ramAddresses) {
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

    void assertTo(EmulatorState testTo) {
        assertEquals(a(), testTo.a(), "Failed at register A");
        assertEquals(b(), testTo.b(), "Failed at register B");
        assertEquals(c(), testTo.c(), "Failed at register C");
        assertEquals(d(), testTo.d(), "Failed at register D");
        assertEquals(e(), testTo.e(), "Failed at register E");
        assertEquals(h(), testTo.h(), "Failed at register H");
        assertEquals(l(), testTo.l(), "Failed at register L");
        assertEquals(pc(), testTo.pc(), "Failed at register PC");
        assertEquals(sp(), testTo.sp(), "Failed at register SP");
        assertEquals(f(), testTo.f(), "Failed at register F");

        for (int i = 0; i < ram().length; i++) {
            int[] expectedRam = ram()[i];
            int[] actualRam = testTo.ram()[i];

            assertEquals(expectedRam[0], actualRam[0], "Failed at RAM address " + i);
            assertEquals(expectedRam[1], actualRam[1], "Failed at RAM value " + i);
        }
    }
}

