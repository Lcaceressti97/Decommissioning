
package hn.com.tigo.equipmentinsurance.services.exchangerate;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Exchange_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Exchange_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDExchange" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *         &lt;element name="IDSource" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *         &lt;element name="IDTarget" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}ID"/>
 *         &lt;element name="StartDate" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Date"/>
 *         &lt;element name="SalePrice" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Factor"/>
 *         &lt;element name="PurchasePrice" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Factor"/>
 *         &lt;element name="User" type="{http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema}Name"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Exchange_Type", propOrder = {
    "idExchange",
    "idSource",
    "idTarget",
    "startDate",
    "salePrice",
    "purchasePrice",
    "user"
})
public class ExchangeType {

    @XmlElement(name = "IDExchange")
    protected int idExchange;
    @XmlElement(name = "IDSource")
    protected int idSource;
    @XmlElement(name = "IDTarget")
    protected int idTarget;
    @XmlElement(name = "StartDate", required = true)
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "SalePrice", required = true)
    protected BigDecimal salePrice;
    @XmlElement(name = "PurchasePrice", required = true)
    protected BigDecimal purchasePrice;
    @XmlElement(name = "User", required = true)
    protected String user;

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
     * Obtiene el valor de la propiedad startDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Define el valor de la propiedad startDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Obtiene el valor de la propiedad salePrice.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * Define el valor de la propiedad salePrice.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSalePrice(BigDecimal value) {
        this.salePrice = value;
    }

    /**
     * Obtiene el valor de la propiedad purchasePrice.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Define el valor de la propiedad purchasePrice.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPurchasePrice(BigDecimal value) {
        this.purchasePrice = value;
    }

    /**
     * Obtiene el valor de la propiedad user.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

}
