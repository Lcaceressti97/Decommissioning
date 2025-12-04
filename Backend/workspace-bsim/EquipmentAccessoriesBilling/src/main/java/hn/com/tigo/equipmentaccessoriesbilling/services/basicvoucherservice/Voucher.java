
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Voucher complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Voucher">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Original" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherSYS_Type" minOccurs="0"/>
 *         &lt;element name="External" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Pre" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}External_Type" minOccurs="0"/>
 *                   &lt;element name="Post" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherReference_Type" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Voucher", propOrder = {
    "original",
    "external"
})
public class Voucher {

    @XmlElement(name = "Original")
    protected VoucherSYSType original;
    @XmlElement(name = "External")
    protected Voucher.External external;

    /**
     * Obtiene el valor de la propiedad original.
     * 
     * @return
     *     possible object is
     *     {@link VoucherSYSType }
     *     
     */
    public VoucherSYSType getOriginal() {
        return original;
    }

    /**
     * Define el valor de la propiedad original.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherSYSType }
     *     
     */
    public void setOriginal(VoucherSYSType value) {
        this.original = value;
    }

    /**
     * Obtiene el valor de la propiedad external.
     * 
     * @return
     *     possible object is
     *     {@link Voucher.External }
     *     
     */
    public Voucher.External getExternal() {
        return external;
    }

    /**
     * Define el valor de la propiedad external.
     * 
     * @param value
     *     allowed object is
     *     {@link Voucher.External }
     *     
     */
    public void setExternal(Voucher.External value) {
        this.external = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Pre" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}External_Type" minOccurs="0"/>
     *         &lt;element name="Post" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherReference_Type" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "pre",
        "post"
    })
    public static class External {

        @XmlElement(name = "Pre")
        protected ExternalType pre;
        @XmlElement(name = "Post")
        protected VoucherReferenceType post;

        /**
         * Obtiene el valor de la propiedad pre.
         * 
         * @return
         *     possible object is
         *     {@link ExternalType }
         *     
         */
        public ExternalType getPre() {
            return pre;
        }

        /**
         * Define el valor de la propiedad pre.
         * 
         * @param value
         *     allowed object is
         *     {@link ExternalType }
         *     
         */
        public void setPre(ExternalType value) {
            this.pre = value;
        }

        /**
         * Obtiene el valor de la propiedad post.
         * 
         * @return
         *     possible object is
         *     {@link VoucherReferenceType }
         *     
         */
        public VoucherReferenceType getPost() {
            return post;
        }

        /**
         * Define el valor de la propiedad post.
         * 
         * @param value
         *     allowed object is
         *     {@link VoucherReferenceType }
         *     
         */
        public void setPost(VoucherReferenceType value) {
            this.post = value;
        }

    }

}
