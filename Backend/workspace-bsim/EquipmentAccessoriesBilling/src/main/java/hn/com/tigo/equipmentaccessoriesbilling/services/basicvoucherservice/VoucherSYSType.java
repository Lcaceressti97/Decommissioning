
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para VoucherSYS_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="VoucherSYS_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VoucherDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="IDPoint" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DocumentType" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="IDCAI" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="NumberDEI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="BillingCycle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherSYS_Type", propOrder = {
    "voucherDate",
    "idPoint",
    "documentType",
    "idcai",
    "numberDEI",
    "state",
    "billingCycle",
    "customerId",
    "accountId"
})
public class VoucherSYSType {

    @XmlElement(name = "VoucherDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar voucherDate;
    @XmlElement(name = "IDPoint", required = true)
    protected BigInteger idPoint;
    @XmlElement(name = "DocumentType", required = true)
    protected BigInteger documentType;
    @XmlElement(name = "IDCAI")
    protected BigInteger idcai;
    @XmlElement(name = "NumberDEI")
    protected String numberDEI;
    @XmlElement(name = "State")
    protected BigInteger state;
    @XmlElement(name = "BillingCycle")
    protected String billingCycle;
    @XmlElement(name = "CustomerId")
    protected String customerId;
    @XmlElement(name = "AccountId")
    protected String accountId;

    /**
     * Obtiene el valor de la propiedad voucherDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVoucherDate() {
        return voucherDate;
    }

    /**
     * Define el valor de la propiedad voucherDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVoucherDate(XMLGregorianCalendar value) {
        this.voucherDate = value;
    }

    /**
     * Obtiene el valor de la propiedad idPoint.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDPoint() {
        return idPoint;
    }

    /**
     * Define el valor de la propiedad idPoint.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDPoint(BigInteger value) {
        this.idPoint = value;
    }

    /**
     * Obtiene el valor de la propiedad documentType.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDocumentType() {
        return documentType;
    }

    /**
     * Define el valor de la propiedad documentType.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDocumentType(BigInteger value) {
        this.documentType = value;
    }

    /**
     * Obtiene el valor de la propiedad idcai.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIDCAI() {
        return idcai;
    }

    /**
     * Define el valor de la propiedad idcai.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIDCAI(BigInteger value) {
        this.idcai = value;
    }

    /**
     * Obtiene el valor de la propiedad numberDEI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberDEI() {
        return numberDEI;
    }

    /**
     * Define el valor de la propiedad numberDEI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberDEI(String value) {
        this.numberDEI = value;
    }

    /**
     * Obtiene el valor de la propiedad state.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setState(BigInteger value) {
        this.state = value;
    }

    /**
     * Obtiene el valor de la propiedad billingCycle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingCycle() {
        return billingCycle;
    }

    /**
     * Define el valor de la propiedad billingCycle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingCycle(String value) {
        this.billingCycle = value;
    }

    /**
     * Obtiene el valor de la propiedad customerId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Define el valor de la propiedad customerId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerId(String value) {
        this.customerId = value;
    }

    /**
     * Obtiene el valor de la propiedad accountId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Define el valor de la propiedad accountId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountId(String value) {
        this.accountId = value;
    }

}
