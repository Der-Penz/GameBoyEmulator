import com.google.gson.*;

public class CycleDeserializer implements JsonDeserializer<Cycle> {
    @Override
    public Cycle deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        int address = parseHex(jsonArray.get(0).getAsString());
        Integer value = jsonArray.get(1).isJsonNull() ? null : parseHex(jsonArray.get(1).getAsString());
        String operation = jsonArray.get(2).getAsString();
        return new Cycle(address, value, operation);
    }

    private int parseHex(String hexString) {
        // Remove "0x" prefix if present
        if (hexString.startsWith("0x") || hexString.startsWith("0X")) {
            hexString = hexString.substring(2);
        }
        // Parse hexadecimal string to integer
        return Integer.parseInt(hexString, 16);
    }
}
