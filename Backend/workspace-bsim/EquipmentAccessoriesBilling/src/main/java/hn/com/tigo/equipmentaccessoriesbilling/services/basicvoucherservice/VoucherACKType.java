
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para VoucherACK_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="VoucherACK_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDVoucher" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDTransaction" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "VoucherACK_Type", propOrder = {
    "idVoucher",
    "idReference",
    "idTransaction",
    "test"
})
@XmlSeeAlso({
    VoucherACKPreType.class
})
public class VoucherACKType {

    @XmlElement(name = "IDVoucher")
    protected long idVoucher;
    @XmlElement(name = "IDReference")
    protected long idReference;
    @XmlElement(name = "IDTransaction")
    protected long idTransaction;
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
