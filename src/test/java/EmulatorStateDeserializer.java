import com.google.gson.*;

public class EmulatorStateDeserializer implements JsonDeserializer<EmulatorState> {
    @Override
    public EmulatorState deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int a = parseHex(jsonObject.get("cpu").getAsJsonObject().get("a").getAsString());
        int b = parseHex(jsonObject.get("cpu").getAsJsonObject().get("b").getAsString());
        int c = parseHex(jsonObject.get("cpu").getAsJsonObject().get("c").getAsString());
        int d = parseHex(jsonObject.get("cpu").getAsJsonObject().get("d").getAsString());
        int e = parseHex(jsonObject.get("cpu").getAsJsonObject().get("e").getAsString());
        int f = parseHex(jsonObject.get("cpu").getAsJsonObject().get("f").getAsString());
        int h = parseHex(jsonObject.get("cpu").getAsJsonObject().get("h").getAsString());
        int l = parseHex(jsonObject.get("cpu").getAsJsonObject().get("l").getAsString());
        int pc = parseHex(jsonObject.get("cpu").getAsJsonObject().get("pc").getAsString());
        int sp = parseHex(jsonObject.get("cpu").getAsJsonObject().get("sp").getAsString());

        JsonArray ramJsonArray = jsonObject.getAsJsonArray("ram");
        int[][] ram = new int[ramJsonArray.size()][];
        for (int i = 0; i < ramJsonArray.size(); i++) {
            JsonArray innerArray = ramJsonArray.get(i).getAsJsonArray();
            ram[i] = new int[innerArray.size()];
            for (int j = 0; j < innerArray.size(); j++) {
                ram[i][j] = parseHex(innerArray.get(j).getAsString());
            }
        }

        return new EmulatorState(a, b, c, d, e, f, h, l, pc, sp, ram);
    }

    // Custom method to parse hexadecimal strings to integers
    private int parseHex(String hexString) {
        // Remove "0x" prefix if present
        if (hexString.startsWith("0x") || hexString.startsWith("0X")) {
            hexString = hexString.substring(2);
        }
        // Parse hexadecimal string to integer
        return Integer.parseInt(hexString, 16);
    }
}
