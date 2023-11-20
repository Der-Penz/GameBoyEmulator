package org.penz.emulator.memory;

public class EchoAddressSpace implements AddressSpace {

    private final AddressSpace echoSpace;

    private final int echoStart;
    private final int from;
    private final int to;

    public EchoAddressSpace(AddressSpace echoSpace, int echoStart, int from, int to) {
        this.echoSpace = echoSpace;
        this.echoStart = echoStart;
        this.from = from;
        this.to = to;
    }

    private int translateAddress(int address) {
        int relativeAddress = address - from;
        if (relativeAddress < 0 || relativeAddress > to - from) {
            throw new IllegalArgumentException("Echo AddressSpace translation out of bounds");
        }

        return relativeAddress + echoStart;

    }

    @Override
    public boolean accepts(int address) {
        return address >= from && address <= to;
    }

    @Override
    public void writeByte(int address, int value) {
        echoSpace.writeByte(translateAddress(address), value);
    }

    @Override
    public int readByte(int address) {
        return echoSpace.readByte(translateAddress(address));
    }
}
