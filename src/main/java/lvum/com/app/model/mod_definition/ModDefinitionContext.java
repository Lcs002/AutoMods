package lvum.com.app.model.mod_definition;

import java.io.IOException;

public enum ModDefinitionContext {
    client("client"), server("server");

    private final String value;

    ModDefinitionContext(String value) {
        this.value = value;
    }

    public static ModDefinitionContext forValue(String value) throws IOException {
        if (value.equals("client")) return client;
        if (value.equals("server")) return server;
        throw new IOException("Cannot deserialize ModDefinitionContext");
    }
}
