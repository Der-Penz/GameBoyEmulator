import com.google.gson.annotations.SerializedName;

public record OpcodeTestData(
        String name,
        @SerializedName("initial")
        EmulatorState initialState,
        @SerializedName("final")
        EmulatorState finalState,
        Cycle[] cycles
) {
}

