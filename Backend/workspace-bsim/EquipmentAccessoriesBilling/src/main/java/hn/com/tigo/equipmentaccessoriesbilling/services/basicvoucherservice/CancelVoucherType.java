
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cancelVoucher_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cancelVoucher_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCompany" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDSystem" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDVoucher" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "cancelVoucher_Type", propOrder = {
    "idCompany",
    "idSystem",
    "idVoucher",
    "idReference",
    "user",
    "terminal",
    "testOnly"
})
public class CancelVoucherType {

    @XmlElement(name = "IDCompany")
    protected long idCompany;
    @XmlElement(name = "IDSystem")
    protected long idSystem;
    @XmlElement(name = "IDVoucher")
    protected long idVoucher;
    @XmlElement(name = "IDReference")
    protected long idReference;
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
     * Obtiene el valor de la propiedad idVoucher.
     * 
     */
    public long getIDVoucher() {
        return idVoucher;
    }

    /**
     * Define el valor de la propiedad idVoucher.
     * 
     */
    public void setIDVoucher(long value) {
        this.idVoucher = value;
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
