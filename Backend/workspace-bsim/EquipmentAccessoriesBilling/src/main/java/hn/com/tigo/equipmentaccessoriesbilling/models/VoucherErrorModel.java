package hn.com.tigo.equipmentaccessoriesbilling.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Errors")
public class VoucherErrorModel {
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    @XmlElement(name = "Code")
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement(name = "Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
