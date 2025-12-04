
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para VoucherReference_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="VoucherReference_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CAI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VoucherDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="NumberDEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherReference_Type", propOrder = {
    "cai",
    "voucherDate",
    "numberDEI"
})
public class VoucherReferenceType {

    @XmlElement(name = "CAI", required = true)
    protected String cai;
    @XmlElement(name = "VoucherDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar voucherDate;
    @XmlElement(name = "NumberDEI", required = true)
    protected String numberDEI;

    /**
     * Obtiene el valor de la propiedad cai.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAI() {
        return cai;
    }

    /**
     * Define el valor de la propiedad cai.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAI(String value) {
        this.cai = value;
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

}
