
package hn.com.tigo.equipmentaccessoriesbilling.services.exchangerate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Currency_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Currency_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCurrency" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *         &lt;element name="Description" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Name"/>
 *         &lt;element name="Simbol" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Simbol"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Currency_Type", propOrder = {
    "idCurrency",
    "description",
    "simbol"
})
public class CurrencyType {

    @XmlElement(name = "IDCurrency")
    protected int idCurrency;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Simbol", required = true)
    protected String simbol;

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

    /**
     * Obtiene el valor de la propiedad simbol.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimbol() {
        return simbol;
    }

    /**
     * Define el valor de la propiedad simbol.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimbol(String value) {
        this.simbol = value;
    }

}
