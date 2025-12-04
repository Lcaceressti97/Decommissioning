
package hn.com.tigo.equipmentinsurance.services.exchangerate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ExchangeACK_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ExchangeACK_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDExchange" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangeACK_Type", propOrder = {
    "idExchange"
})
public class ExchangeACKType {

    @XmlElement(name = "IDExchange")
    protected int idExchange;

    /**
     * Obtiene el valor de la propiedad idExchange.
     * 
     */
    public int getIDExchange() {
        return idExchange;
    }

    /**
     * Define el valor de la propiedad idExchange.
     * 
     */
    public void setIDExchange(int value) {
        this.idExchange = value;
    }

}
