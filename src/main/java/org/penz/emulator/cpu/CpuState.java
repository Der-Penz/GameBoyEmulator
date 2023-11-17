package org.penz.emulator.cpu;

/**
 * Enum representing the different states the CPU can be in.
 */
public enum CpuState {
    /**
     * State indicating that the CPU is halted.
     */
    HALTED,
    /**
     * State indicating that a new opcode will be fetched.
     */
    OPCODE,

    /**
     * State indicating that any interrupt is requested.
     */
    IT_REQUESTED,

    /**
     * State indicating that the low byte of the PC will be pushed to the stack.
     */
    IT_PUSH_LOW,
    /**
     * State indicating that the high byte of the PC will be pushed to the stack.
     */
    IT_PUSH_HIGH,
    /**
     * State indicating that the jump address will be written to the PC.
     */
    IH_JP_ADDRESS,
    /**
     * State indicating that the CPU is in stop mode.
     */
    STOPPED;

    /**
     * Returns true if the given state is an interrupt state.
     *
     * @param state The state to check
     * @return True if the given state is an interrupt state
     */
    public static boolean isInterruptState(CpuState state) {
        return state == IT_REQUESTED || state == IT_PUSH_LOW || state == IT_PUSH_HIGH || state == IH_JP_ADDRESS;
    }
}
