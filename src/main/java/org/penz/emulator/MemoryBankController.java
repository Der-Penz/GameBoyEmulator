package org.penz.emulator;

import org.penz.emulator.memory.AddressSpace;

import java.io.FileNotFoundException;

public abstract class MemoryBankController implements AddressSpace {

    public abstract void save();
}
