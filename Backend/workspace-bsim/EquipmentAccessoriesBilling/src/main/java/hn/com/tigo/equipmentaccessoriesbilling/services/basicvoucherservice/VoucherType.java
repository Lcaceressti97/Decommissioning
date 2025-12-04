
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Voucher_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Voucher_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCompany" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDSystem" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Period" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Voucher">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Original" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherSYS_Type" minOccurs="0"/>
 *                   &lt;element name="External" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Pre" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}External_Type" minOccurs="0"/>
 *                             &lt;element name="Post" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}VoucherReference_Type" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Reference" minOccurs="0">
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
 *         &lt;element name="TaxPayer" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}TaxPayer_Type" minOccurs="0"/>
 *         &lt;element name="ReferenceSystem" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Fields_Type" minOccurs="0"/>
 *         &lt;element name="Items" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Items_Type" minOccurs="0"/>
 *         &lt;element name="Amounts" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Amounts_Type"/>
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Voucher_Type", propOrder = {
    "idCompany",
    "idSystem",
    "idReference",
    "period",
    "voucher",
    "reference",
    "taxPayer",
    "referenceSystem",
    "items",
    "amounts",
    "user",
    "terminal",
    "testOnly"
})
public class VoucherType {

    @XmlElement(name = "IDCompany")
    protected long idCompany;
    @XmlElement(name = "IDSystem")
    protected long idSystem;
    @XmlElement(name = "IDReference")
    protected long idReference;
    @XmlElement(name = "Period", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar period;
    @XmlElement(name = "Voucher", required = true)
    protected VoucherType.Voucher voucher;
    @XmlElement(name = "Reference")
    protected VoucherType.Reference reference;
    @XmlElement(name = "TaxPayer")
    protected TaxPayerType taxPayer;
    @XmlElement(name = "ReferenceSystem")
    protected FieldsType referenceSystem;
    @XmlElement(name = "Items")
    protected ItemsType items;
    @XmlElement(name = "Amounts", required = true)
    protected AmountsType amounts;
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
     * Obtiene el valor de la propiedad period.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPeriod() {
        return period;
    }

    /**
     * Define el valor de la propiedad period.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPeriod(XMLGregorianCalendar value) {
        this.period = value;
    }

    /**
     * Obtiene el valor de la propiedad voucher.
     * 
     * @return
     *     possible object is
     *     {@link VoucherType.Voucher }
     *     
     */
    public VoucherType.Voucher getVoucher() {
        return voucher;
    }

    /**
     * Define el valor de la propiedad voucher.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherType.Voucher }
     *     
     */
    public void setVoucher(VoucherType.Voucher value) {
        this.voucher = value;
    }

    /**
     * Obtiene el valor de la propiedad reference.
     * 
     * @return
     *     possible object is
     *     {@link VoucherType.Reference }
     *     
     */
    public VoucherType.Reference getReference() {
        return reference;
    }

    /**
     * Define el valor de la propiedad reference.
     * 
     * @param value
     *     allowed object is
     *     {@link VoucherType.Reference }
     *     
     */
    public void setReference(VoucherType.Reference value) {
        this.reference = value;
    }

    /**
     * Obtiene el valor de la propiedad taxPayer.
     * 
     * @return
     *     possible object is
     *     {@link TaxPayerType }
     *     
     */
    public TaxPayerType getTaxPayer() {
        return taxPayer;
    }

    /**
     * Define el valor de la propiedad taxPayer.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPayerType }
     *     
     */
    public void setTaxPayer(TaxPayerType value) {
        this.taxPayer = value;
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
     * Obtiene el valor de la propiedad items.
     * 
     * @return
     *     possible object is
     *     {@link ItemsType }
     *     
     */
    public ItemsType getItems() {
        return items;
    }

    /**
     * Define el valor de la propiedad items.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemsType }
     *     
     */
    public void setItems(ItemsType value) {
        this.items = value;
    }

    /**
     * Obtiene el valor de la propiedad amounts.
     * 
     * @return
     *     possible object is
     *     {@link AmountsType }
     *     
     */
    public AmountsType getAmounts() {
        return amounts;
    }

    /**
     * Define el valor de la propiedad amounts.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountsType }
     *     
     */
    public void setAmounts(AmountsType value) {
        this.amounts = value;
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
    public static class Reference {

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
    @XmlType(name = "", propOrder = {
        "original",
        "external"
    })
    public static class Voucher {

        @XmlElement(name = "Original")
        protected VoucherSYSType original;
        @XmlElement(name = "External")
        protected VoucherType.Voucher.External external;

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
         *     {@link VoucherType.Voucher.External }
         *     
         */
        public VoucherType.Voucher.External getExternal() {
            return external;
        }

        /**
         * Define el valor de la propiedad external.
         * 
         * @param value
         *     allowed object is
         *     {@link VoucherType.Voucher.External }
         *     
         */
        public void setExternal(VoucherType.Voucher.External value) {
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

}
