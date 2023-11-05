package lvum.com.model.mod.github.reference;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.model.mod.github.reference.yml.YMLModReference;

@JsonDeserialize(as = YMLModReference.class)
public interface ModReference {
    String getModID();
    void setModID(String value);

    String getVersion();
    void setVersion(String value);
}
