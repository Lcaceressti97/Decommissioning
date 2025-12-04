
package hn.com.tigo.equipmentinsurance.services.exchangerate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CurrencyACK_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CurrencyACK_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCurrency" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyACK_Type", propOrder = {
    "idCurrency"
})
public class CurrencyACKType {

    @XmlElement(name = "IDCurrency")
    protected int idCurrency;

    /**
     * Obtiene el valor de la propiedad idCurrency.
     * 
     */
    public int getIDCurrency() {
        return idCurrency;
    }

    /**
     * Define el valor de la propiedad idCurrency.
     * 
     */
    public void setIDCurrency(int value) {
        this.idCurrency = value;
    }

}
