
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RouteLocation_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RouteLocation_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Departure" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}LocalityR_Type"/>
 *         &lt;element name="Arrival" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}LocalityR_Type"/>
 *         &lt;element name="IDS" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}IDS_Type"/>
 *         &lt;element name="Transport" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Fields_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteLocation_Type", propOrder = {
    "departure",
    "arrival",
    "ids",
    "transport"
})
public class RouteLocationType {

    @XmlElement(name = "Departure", required = true)
    protected LocalityRType departure;
    @XmlElement(name = "Arrival", required = true)
    protected LocalityRType arrival;
    @XmlElement(name = "IDS", required = true)
    protected IDSType ids;
    @XmlElement(name = "Transport", required = true)
    protected FieldsType transport;

    /**
     * Obtiene el valor de la propiedad departure.
     * 
     * @return
     *     possible object is
     *     {@link LocalityRType }
     *     
     */
    public LocalityRType getDeparture() {
        return departure;
    }

    /**
     * Define el valor de la propiedad departure.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalityRType }
     *     
     */
    public void setDeparture(LocalityRType value) {
        this.departure = value;
    }

    /**
     * Obtiene el valor de la propiedad arrival.
     * 
     * @return
     *     possible object is
     *     {@link LocalityRType }
     *     
     */
    public LocalityRType getArrival() {
        return arrival;
    }

    /**
     * Define el valor de la propiedad arrival.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalityRType }
     *     
     */
    public void setArrival(LocalityRType value) {
        this.arrival = value;
    }

    /**
     * Obtiene el valor de la propiedad ids.
     * 
     * @return
     *     possible object is
     *     {@link IDSType }
     *     
     */
    public IDSType getIDS() {
        return ids;
    }

    /**
     * Define el valor de la propiedad ids.
     * 
     * @param value
     *     allowed object is
     *     {@link IDSType }
     *     
     */
    public void setIDS(IDSType value) {
        this.ids = value;
    }

    /**
     * Obtiene el valor de la propiedad transport.
     * 
     * @return
     *     possible object is
     *     {@link FieldsType }
     *     
     */
    public FieldsType getTransport() {
        return transport;
    }

    /**
     * Define el valor de la propiedad transport.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldsType }
     *     
     */
    public void setTransport(FieldsType value) {
        this.transport = value;
    }

}
