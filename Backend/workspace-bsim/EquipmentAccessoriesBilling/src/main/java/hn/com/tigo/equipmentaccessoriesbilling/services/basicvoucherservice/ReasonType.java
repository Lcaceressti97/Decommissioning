
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Reason_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Reason_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDReason" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reason_Type", propOrder = {
    "idReason",
    "description"
})
public class ReasonType {

    @XmlElement(name = "IDReason")
    protected Long idReason;
    @XmlElement(name = "Description")
    protected String description;

    /**
     * Obtiene el valor de la propiedad idReason.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIDReason() {
        return idReason;
    }

    /**
     * Define el valor de la propiedad idReason.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIDReason(Long value) {
        this.idReason = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
