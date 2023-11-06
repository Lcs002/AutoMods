package lvum.com.app.model.mod_definition.github.reference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.app.model.mod_definition.github.reference.yml.YMLModReference;

@JsonDeserialize(as = YMLModReference.class)
public interface ModReference {
    String getModID();
    void setModID(String value);

    String getVersion();
    void setVersion(String value);
}
