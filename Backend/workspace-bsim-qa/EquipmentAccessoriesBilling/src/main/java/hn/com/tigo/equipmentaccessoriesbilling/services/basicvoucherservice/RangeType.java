
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Range_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Range_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Stop" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="DeinumberPrefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeinumberStart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeinumberStop" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Range_Type", propOrder = {
    "start",
    "stop",
    "deinumberPrefix",
    "deinumberStart",
    "deinumberStop"
})
public class RangeType {

    @XmlElement(name = "Start")
    protected long start;
    @XmlElement(name = "Stop")
    protected long stop;
    @XmlElement(name = "DeinumberPrefix")
    protected String deinumberPrefix;
    @XmlElement(name = "DeinumberStart")
    protected String deinumberStart;
    @XmlElement(name = "DeinumberStop")
    protected String deinumberStop;

    /**
     * Obtiene el valor de la propiedad start.
     * 
     */
    public long getStart() {
        return start;
    }

    /**
     * Define el valor de la propiedad start.
     * 
     */
    public void setStart(long value) {
        this.start = value;
    }

    /**
     * Obtiene el valor de la propiedad stop.
     * 
     */
    public long getStop() {
        return stop;
    }

    /**
     * Define el valor de la propiedad stop.
     * 
     */
    public void setStop(long value) {
        this.stop = value;
    }

    /**
     * Obtiene el valor de la propiedad deinumberPrefix.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeinumberPrefix() {
        return deinumberPrefix;
    }

    /**
     * Define el valor de la propiedad deinumberPrefix.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeinumberPrefix(String value) {
        this.deinumberPrefix = value;
    }

    /**
     * Obtiene el valor de la propiedad deinumberStart.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeinumberStart() {
        return deinumberStart;
    }

    /**
     * Define el valor de la propiedad deinumberStart.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeinumberStart(String value) {
        this.deinumberStart = value;
    }

    /**
     * Obtiene el valor de la propiedad deinumberStop.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeinumberStop() {
        return deinumberStop;
    }

    /**
     * Define el valor de la propiedad deinumberStop.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeinumberStop(String value) {
        this.deinumberStop = value;
    }

}
