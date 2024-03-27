import org.penz.emulator.memory.AddressSpace;

import java.util.ArrayList;
import java.util.List;

class ResettableMemory implements AddressSpace {

    private final int[] memory = new int[0x10000];
    private final List<Integer> memoryAddressAccess = new ArrayList<>();

    @Override
    public boolean accepts(int address) {
        return address < memory.length;
    }

    @Override
    public void writeByte(int address, int value) {
        memoryAddressAccess.add(address);
        memory[address] = value;
    }

    @Override
    public int readByte(int address) {
        return memory[address];
    }

    public void resetMemoryAccess() {
        for (int address : memoryAddressAccess) {
            memory[address] = 0;
        }
        memoryAddressAccess.clear();
    }
}
