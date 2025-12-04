
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para VoucherDEI_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="VoucherDEI_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Local" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Point" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="DocumentType" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Correlative" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherDEI_Type", propOrder = {
    "local",
    "point",
    "documentType",
    "correlative"
})
public class VoucherDEIType {

    @XmlElement(name = "Local", required = true)
    protected BigInteger local;
    @XmlElement(name = "Point", required = true)
    protected BigInteger point;
    @XmlElement(name = "DocumentType", required = true)
    protected BigInteger documentType;
    @XmlElement(name = "Correlative")
    protected long correlative;

    /**
     * Obtiene el valor de la propiedad local.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLocal() {
        return local;
    }

    /**
     * Define el valor de la propiedad local.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLocal(BigInteger value) {
        this.local = value;
    }

    /**
     * Obtiene el valor de la propiedad point.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoint() {
        return point;
    }

    /**
     * Define el valor de la propiedad point.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoint(BigInteger value) {
        this.point = value;
    }

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
     * Obtiene el valor de la propiedad correlative.
     * 
     */
    public long getCorrelative() {
        return correlative;
    }

    /**
     * Define el valor de la propiedad correlative.
     * 
     */
    public void setCorrelative(long value) {
        this.correlative = value;
    }

}
