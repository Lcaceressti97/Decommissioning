
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para VoucherResponse_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="VoucherResponse_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Internal" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherACK_PostType" minOccurs="0"/>
 *         &lt;element name="External" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherACK_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "VoucherResponseType", namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherResponse_Type", propOrder = {
    "internal",
    "external"
})
public class VoucherResponseType {

    @XmlElement(name = "Internal")
    protected VoucherACKPostType internal;
    @XmlElement(name = "External")
    protected VoucherACKType external;

    /**
     * Obtiene el valor de la propiedad internal.
     * 
     * @return
     *     possible object is
     *     {@link VoucherACKPostType }
     *     
     */
    public VoucherACKPostType getInternal() {
        return internal;
    }

    /**
     * Define el valor de la propiedad internal.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherACKPostType }
     *     
     */
    public void setInternal(VoucherACKPostType value) {
        this.internal = value;
    }

    /**
     * Obtiene el valor de la propiedad external.
     * 
     * @return
     *     possible object is
     *     {@link VoucherACKType }
     *     
     */
    public VoucherACKType getExternal() {
        return external;
    }

    /**
     * Define el valor de la propiedad external.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherACKType }
     *     
     */
    public void setExternal(VoucherACKType value) {
        this.external = value;
    }

}
