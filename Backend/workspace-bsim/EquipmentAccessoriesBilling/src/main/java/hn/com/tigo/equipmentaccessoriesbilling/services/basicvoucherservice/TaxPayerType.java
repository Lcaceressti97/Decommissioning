
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TaxPayer_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TaxPayer_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxPayerType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="DocumentID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxPayer_Type", propOrder = {
    "taxPayerType",
    "idType",
    "documentID",
    "name",
    "email"
})
public class TaxPayerType {

    @XmlElement(name = "TaxPayerType")
    protected long taxPayerType;
    @XmlElement(name = "IDType")
    protected long idType;
    @XmlElement(name = "DocumentID", required = true)
    protected String documentID;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Email")
    protected String email;

    /**
     * Obtiene el valor de la propiedad taxPayerType.
     * 
     */
    public long getTaxPayerType() {
        return taxPayerType;
    }

    /**
     * Define el valor de la propiedad taxPayerType.
     * 
     */
    public void setTaxPayerType(long value) {
        this.taxPayerType = value;
    }

    /**
     * Obtiene el valor de la propiedad idType.
     * 
     */
    public long getIDType() {
        return idType;
    }

    /**
     * Define el valor de la propiedad idType.
     * 
     */
    public void setIDType(long value) {
        this.idType = value;
    }

    /**
     * Obtiene el valor de la propiedad documentID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentID() {
        return documentID;
    }

    /**
     * Define el valor de la propiedad documentID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentID(String value) {
        this.documentID = value;
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

}
