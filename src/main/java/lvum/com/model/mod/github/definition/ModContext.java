package lvum.com.model.mod.github.definition;

import java.io.IOException;

public enum ModContext {
    client("client"), server("server");

    private final String value;

    ModContext(String value) {
        this.value = value;
    }

    public static ModContext forValue(String value) throws IOException {
        if (value.equals("client")) return client;
        if (value.equals("server")) return server;
        throw new IOException("Cannot deserialize ModContext");
    }
}
