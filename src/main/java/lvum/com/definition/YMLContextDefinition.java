package lvum.com.definition;

import java.io.IOException;

public enum YMLContextDefinition {
    client("client"), server("server");

    private final String value;

    YMLContextDefinition(String value) {
        this.value = value;
    }

    public static YMLContextDefinition forValue(String value) throws IOException {
        if (value.equals("client")) return client;
        if (value.equals("server")) return server;
        throw new IOException("Cannot deserialize Context");
    }
}
