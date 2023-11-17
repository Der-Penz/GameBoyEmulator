package org.penz.emulator.memory;

import org.penz.emulator.Constants;

import java.util.ArrayList;
import java.util.List;

public class Mmu implements AddressSpace {

    /**
     * All memory banks available in the Game Boy
     */
    private final List<AddressSpace> memoryBanks = new ArrayList<>();

    /**
     * Maps a memory address to the memory bank that accepts it
     * This is used to speed up memory access
     */
    private AddressSpace[] addressToBank;

    /**
     * Void memory bank that does not accept any address
     * Used to fill the addressToBank array with a default value
     */
    private final AddressSpace VOID_BANK = new AddressSpace() {
        @Override
        public boolean accepts(int address) {
            throw new UnsupportedOperationException("Trying to access void memory bank. No memory mapped to address " + address);
        }

        @Override
        public void writeByte(int address, int value) {
        }

        @Override
        public int readByte(int address) {
            return 0;
        }
    };

    /**
     * Add a memory bank to the MMU
     *
     * @param memoryBank to add
     */
    public void addMemoryBank(AddressSpace memoryBank) {
        memoryBanks.add(memoryBank);
    }

    /**
     * Index all memory banks to speed up memory access
     * This method should be called after all memory banks have been added to the MMU
     */
    public void indexBanks() {
//        TODO: Memory should be indexed differently in the future to not need a extra array
        addressToBank = new AddressSpace[0x10000];
        for (int i = 0; i < addressToBank.length; i++) {
            addressToBank[i] = VOID_BANK;
            for (AddressSpace space : memoryBanks) {
                if (space.accepts(i)) {
                    addressToBank[i] = space;
                    break;
                }
            }
        }
    }

    /**
     * Get the memory bank that accepts the given address
     *
     * @param address address to check
     * @return memory bank that accepts the given address
     * @throws IllegalArgumentException if the address is out of MMU range
     * @throws IllegalStateException    if the address is not accepted by any memory bank (this should never happen, it means the MMU is not properly indexed)
     */
    public AddressSpace getMemoryBank(int address) {
        if (addressToBank == null) {
            throw new IllegalStateException("Memory is not indexed, make sure to call indexBanks() after adding all memory banks");
        }

        if (!accepts(address)) {
            throw new IllegalArgumentException(String.format("Address %04X is not accepted by mmu", address));
        }

        AddressSpace bank = addressToBank[address];
        if (!bank.accepts(address)) {
            throw new IllegalStateException(String.format("Address %04X is not accepted by the memory bank that was indexed to accept it", address));
        }

        return bank;
    }

    @Override
    public boolean accepts(int address) {
        return address >= 0x0 && address <= Constants.WORD_MAX_VALUE;
    }

    @Override
    public void writeByte(int address, int value) {
        getMemoryBank(address).writeByte(address, value);
    }

    @Override
    public int readByte(int address) {
        return getMemoryBank(address).readByte(address);
    }
}
