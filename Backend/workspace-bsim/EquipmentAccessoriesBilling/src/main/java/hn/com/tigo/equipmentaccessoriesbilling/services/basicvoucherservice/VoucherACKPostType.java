
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para VoucherACK_PostType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="VoucherACK_PostType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDVoucher" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDTransaction" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Company" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Company_Type"/>
 *         &lt;element name="NumberDEI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CAI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeadLine" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DocumentTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Range" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Range_Type"/>
 *         &lt;element name="Literal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LiteralAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Test" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherACK_PostType", propOrder = {
    "idVoucher",
    "idReference",
    "idTransaction",
    "company",
    "numberDEI",
    "cai",
    "deadLine",
    "documentTitle",
    "range",
    "literal",
    "literalAmount",
    "test"
})
public class VoucherACKPostType {

    @XmlElement(name = "IDVoucher")
    protected long idVoucher;
    @XmlElement(name = "IDReference")
    protected long idReference;
    @XmlElement(name = "IDTransaction")
    protected long idTransaction;
    @XmlElement(name = "Company", required = true)
    protected CompanyType company;
    @XmlElement(name = "NumberDEI", required = true)
    protected String numberDEI;
    @XmlElement(name = "CAI", required = true)
    protected String cai;
    @XmlElement(name = "DeadLine", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar deadLine;
    @XmlElement(name = "DocumentTitle", required = true)
    protected String documentTitle;
    @XmlElement(name = "Range", required = true)
    protected RangeType range;
    @XmlElement(name = "Literal", required = true)
    protected String literal;
    @XmlElement(name = "LiteralAmount")
    protected BigDecimal literalAmount;
    @XmlElement(name = "Test")
    protected Boolean test;

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
     * Obtiene el valor de la propiedad idTransaction.
     * 
     */
    public long getIDTransaction() {
        return idTransaction;
    }

    /**
     * Define el valor de la propiedad idTransaction.
     * 
     */
    public void setIDTransaction(long value) {
        this.idTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad company.
     * 
     * @return
     *     possible object is
     *     {@link CompanyType }
     *     
     */
    public CompanyType getCompany() {
        return company;
    }

    /**
     * Define el valor de la propiedad company.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyType }
     *     
     */
    public void setCompany(CompanyType value) {
        this.company = value;
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
     * Obtiene el valor de la propiedad deadLine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeadLine() {
        return deadLine;
    }

    /**
     * Define el valor de la propiedad deadLine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeadLine(XMLGregorianCalendar value) {
        this.deadLine = value;
    }

    /**
     * Obtiene el valor de la propiedad documentTitle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentTitle() {
        return documentTitle;
    }

    /**
     * Define el valor de la propiedad documentTitle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentTitle(String value) {
        this.documentTitle = value;
    }

    /**
     * Obtiene el valor de la propiedad range.
     * 
     * @return
     *     possible object is
     *     {@link RangeType }
     *     
     */
    public RangeType getRange() {
        return range;
    }

    /**
     * Define el valor de la propiedad range.
     * 
     * @param value
     *     allowed object is
     *     {@link RangeType }
     *     
     */
    public void setRange(RangeType value) {
        this.range = value;
    }

    /**
     * Obtiene el valor de la propiedad literal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Define el valor de la propiedad literal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiteral(String value) {
        this.literal = value;
    }

    /**
     * Obtiene el valor de la propiedad literalAmount.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLiteralAmount() {
        return literalAmount;
    }

    /**
     * Define el valor de la propiedad literalAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLiteralAmount(BigDecimal value) {
        this.literalAmount = value;
    }

    /**
     * Obtiene el valor de la propiedad test.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTest() {
        return test;
    }

    /**
     * Define el valor de la propiedad test.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTest(Boolean value) {
        this.test = value;
    }

}
