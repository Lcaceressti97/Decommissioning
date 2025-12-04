
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Remission_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Remission_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCompany" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDSystem" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Voucher" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherSYS_Type"/>
 *         &lt;element name="From" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}TaxPayer_Type"/>
 *         &lt;element name="To" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}TaxPayer_Type"/>
 *         &lt;element name="Reason" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Reason_Type"/>
 *         &lt;element name="Items" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}ItemsR_Type"/>
 *         &lt;element name="Route" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Route_Type"/>
 *         &lt;element name="ReferenceSystem" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Fields_Type" minOccurs="0"/>
 *         &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Terminal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TestOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Remission_Type", propOrder = {
    "idCompany",
    "idSystem",
    "idReference",
    "voucher",
    "from",
    "to",
    "reason",
    "items",
    "route",
    "referenceSystem",
    "user",
    "terminal",
    "testOnly"
})
public class RemissionType {

    @XmlElement(name = "IDCompany")
    protected long idCompany;
    @XmlElement(name = "IDSystem")
    protected long idSystem;
    @XmlElement(name = "IDReference")
    protected long idReference;
    @XmlElement(name = "Voucher", required = true)
    protected VoucherSYSType voucher;
    @XmlElement(name = "From", required = true)
    protected TaxPayerType from;
    @XmlElement(name = "To", required = true)
    protected TaxPayerType to;
    @XmlElement(name = "Reason", required = true)
    protected ReasonType reason;
    @XmlElement(name = "Items", required = true)
    protected ItemsRType items;
    @XmlElement(name = "Route", required = true)
    protected RouteType route;
    @XmlElement(name = "ReferenceSystem")
    protected FieldsType referenceSystem;
    @XmlElement(name = "User", required = true)
    protected String user;
    @XmlElement(name = "Terminal", required = true)
    protected String terminal;
    @XmlElement(name = "TestOnly")
    protected Boolean testOnly;

    /**
     * Obtiene el valor de la propiedad idCompany.
     * 
     */
    public long getIDCompany() {
        return idCompany;
    }

    /**
     * Define el valor de la propiedad idCompany.
     * 
     */
    public void setIDCompany(long value) {
        this.idCompany = value;
    }

    /**
     * Obtiene el valor de la propiedad idSystem.
     * 
     */
    public long getIDSystem() {
        return idSystem;
    }

    /**
     * Define el valor de la propiedad idSystem.
     * 
     */
    public void setIDSystem(long value) {
        this.idSystem = value;
    }

    /**
     * Obtiene el valor de la propiedad idReference.
     * 
     */
    public long getIDReference() {
        return idReference;
    }

    /**
     * Define el valor de la propiedad idReference.
     * 
     */
    public void setIDReference(long value) {
        this.idReference = value;
    }

    /**
     * Obtiene el valor de la propiedad voucher.
     * 
     * @return
     *     possible object is
     *     {@link VoucherSYSType }
     *     
     */
    public VoucherSYSType getVoucher() {
        return voucher;
    }

    /**
     * Define el valor de la propiedad voucher.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherSYSType }
     *     
     */
    public void setVoucher(VoucherSYSType value) {
        this.voucher = value;
    }

    /**
     * Obtiene el valor de la propiedad from.
     * 
     * @return
     *     possible object is
     *     {@link TaxPayerType }
     *     
     */
    public TaxPayerType getFrom() {
        return from;
    }

    /**
     * Define el valor de la propiedad from.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPayerType }
     *     
     */
    public void setFrom(TaxPayerType value) {
        this.from = value;
    }

    /**
     * Obtiene el valor de la propiedad to.
     * 
     * @return
     *     possible object is
     *     {@link TaxPayerType }
     *     
     */
    public TaxPayerType getTo() {
        return to;
    }

    /**
     * Define el valor de la propiedad to.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPayerType }
     *     
     */
    public void setTo(TaxPayerType value) {
        this.to = value;
    }

    /**
     * Obtiene el valor de la propiedad reason.
     * 
     * @return
     *     possible object is
     *     {@link ReasonType }
     *     
     */
    public ReasonType getReason() {
        return reason;
    }

    /**
     * Define el valor de la propiedad reason.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonType }
     *     
     */
    public void setReason(ReasonType value) {
        this.reason = value;
    }

    /**
     * Obtiene el valor de la propiedad items.
     * 
     * @return
     *     possible object is
     *     {@link ItemsRType }
     *     
     */
    public ItemsRType getItems() {
        return items;
    }

    /**
     * Define el valor de la propiedad items.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemsRType }
     *     
     */
    public void setItems(ItemsRType value) {
        this.items = value;
    }

    /**
     * Obtiene el valor de la propiedad route.
     * 
     * @return
     *     possible object is
     *     {@link RouteType }
     *     
     */
    public RouteType getRoute() {
        return route;
    }

    /**
     * Define el valor de la propiedad route.
     * 
     * @param value
     *     allowed object is
     *     {@link RouteType }
     *     
     */
    public void setRoute(RouteType value) {
        this.route = value;
    }

    /**
     * Obtiene el valor de la propiedad referenceSystem.
     * 
     * @return
     *     possible object is
     *     {@link FieldsType }
     *     
     */
    public FieldsType getReferenceSystem() {
        return referenceSystem;
    }

    /**
     * Define el valor de la propiedad referenceSystem.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldsType }
     *     
     */
    public void setReferenceSystem(FieldsType value) {
        this.referenceSystem = value;
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

    /**
     * Obtiene el valor de la propiedad terminal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Define el valor de la propiedad terminal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminal(String value) {
        this.terminal = value;
    }

    /**
     * Obtiene el valor de la propiedad testOnly.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTestOnly() {
        return testOnly;
    }

    /**
     * Define el valor de la propiedad testOnly.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTestOnly(Boolean value) {
        this.testOnly = value;
    }

}
