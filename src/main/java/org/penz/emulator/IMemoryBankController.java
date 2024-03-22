package org.penz.emulator;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;

public interface IMemoryBankController extends AddressSpace {

    public static final int RAM_MEMORY_START = 0xA000;
    public static final int RAM_MEMORY_END = 0xBFFF;

    public Ram[] flushRam();

    public void loadRam(Ram[] ram);
}
