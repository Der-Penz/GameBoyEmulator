package org.penz.emulator;

import org.penz.emulator.memory.AddressSpace;
import org.penz.emulator.memory.Ram;

public interface IMemoryBankController extends AddressSpace {

    public Ram[] flushRam();

    public void loadRam(Ram[] ram);
}
