
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Company_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Company_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RTN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BusinessName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Locality" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Locality_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Company_Type", propOrder = {
    "rtn",
    "name",
    "businessName",
    "email",
    "locality"
})
public class CompanyType {

    @XmlElement(name = "RTN", required = true)
    protected String rtn;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "BusinessName", required = true)
    protected String businessName;
    @XmlElement(name = "Email", required = true)
    protected String email;
    @XmlElement(name = "Locality", required = true)
    protected LocalityType locality;

    /**
     * Obtiene el valor de la propiedad rtn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTN() {
        return rtn;
    }

    /**
     * Define el valor de la propiedad rtn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTN(String value) {
        this.rtn = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad businessName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Define el valor de la propiedad businessName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessName(String value) {
        this.businessName = value;
    }

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad locality.
     * 
     * @return
     *     possible object is
     *     {@link LocalityType }
     *     
     */
    public LocalityType getLocality() {
        return locality;
    }

    /**
     * Define el valor de la propiedad locality.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalityType }
     *     
     */
    public void setLocality(LocalityType value) {
        this.locality = value;
    }

}
