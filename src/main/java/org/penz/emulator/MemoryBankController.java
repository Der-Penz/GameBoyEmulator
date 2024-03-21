package org.penz.emulator;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;

public interface MemoryBankController extends AddressSpace {

    public Ram[] flushRam();
}
