
package hn.com.tigo.equipmentaccessoriesbilling.services.exchangerate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Exchange_TypeG complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Exchange_TypeG">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDSource" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *         &lt;element name="IDTarget" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *         &lt;element name="Dates" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Dates_type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Exchange_TypeG", propOrder = {
    "idSource",
    "idTarget",
    "dates"
})
public class ExchangeTypeG {

    @XmlElement(name = "IDSource")
    protected int idSource;
    @XmlElement(name = "IDTarget")
    protected int idTarget;
    @XmlElement(name = "Dates")
    protected DatesType dates;

    /**
     * Obtiene el valor de la propiedad idSource.
     * 
     */
    public int getIDSource() {
        return idSource;
    }

    /**
     * Define el valor de la propiedad idSource.
     * 
     */
    public void setIDSource(int value) {
        this.idSource = value;
    }

    /**
     * Obtiene el valor de la propiedad idTarget.
     * 
     */
    public int getIDTarget() {
        return idTarget;
    }

    /**
     * Define el valor de la propiedad idTarget.
     * 
     */
    public void setIDTarget(int value) {
        this.idTarget = value;
    }

    /**
     * Obtiene el valor de la propiedad dates.
     * 
     * @return
     *     possible object is
     *     {@link DatesType }
     *     
     */
    public DatesType getDates() {
        return dates;
    }

    /**
     * Define el valor de la propiedad dates.
     * 
     * @param value
     *     allowed object is
     *     {@link DatesType }
     *     
     */
    public void setDates(DatesType value) {
        this.dates = value;
    }

}
