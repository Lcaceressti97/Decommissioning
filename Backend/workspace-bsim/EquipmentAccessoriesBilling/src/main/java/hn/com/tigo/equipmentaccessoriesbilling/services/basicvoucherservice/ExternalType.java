
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para External_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="External_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentType" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="VoucherDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="VoucherReference" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Fields_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "External_Type", propOrder = {
    "documentType",
    "voucherDate",
    "voucherReference"
})
public class ExternalType {

    @XmlElement(name = "DocumentType", required = true)
    protected BigInteger documentType;
    @XmlElement(name = "VoucherDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar voucherDate;
    @XmlElement(name = "VoucherReference", required = true)
    protected FieldsType voucherReference;

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
     * Obtiene el valor de la propiedad voucherReference.
     * 
     * @return
     *     possible object is
     *     {@link FieldsType }
     *     
     */
    public FieldsType getVoucherReference() {
        return voucherReference;
    }

    /**
     * Define el valor de la propiedad voucherReference.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldsType }
     *     
     */
    public void setVoucherReference(FieldsType value) {
        this.voucherReference = value;
    }

}
