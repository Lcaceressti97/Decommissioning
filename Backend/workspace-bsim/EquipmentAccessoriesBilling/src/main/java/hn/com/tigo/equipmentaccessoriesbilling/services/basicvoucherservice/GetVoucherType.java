
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para getVoucher_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="getVoucher_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCompany" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDSystem" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IDReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getVoucher_Type", propOrder = {
    "idCompany",
    "idSystem",
    "idReference"
})
public class GetVoucherType {

    @XmlElement(name = "IDCompany")
    protected long idCompany;
    @XmlElement(name = "IDSystem")
    protected long idSystem;
    @XmlElement(name = "IDReference")
    protected long idReference;

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

}
