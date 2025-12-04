
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para NoopResponse_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="NoopResponse_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Alive" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoopResponse_Type", propOrder = {
    "alive"
})
public class NoopResponseType {

    @XmlElement(name = "Alive", required = true)
    protected String alive;

    /**
     * Obtiene el valor de la propiedad alive.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlive() {
        return alive;
    }

    /**
     * Define el valor de la propiedad alive.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlive(String value) {
        this.alive = value;
    }

}
