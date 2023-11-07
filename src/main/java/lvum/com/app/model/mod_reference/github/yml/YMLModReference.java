package lvum.com.app.model.mod_reference.github.yml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.app.model.mod_reference.ModReference;

@JsonDeserialize(as = YMLModReferenceImpl.class)
public interface YMLModReference extends ModReference {
    String getModID();
    void setModID(String value);

    String getVersion();
    void setVersion(String value);
}
