
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Locality_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Locality_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Phones" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Addresses" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}Addresses_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Locality_Type", propOrder = {
    "phones",
    "addresses"
})
public class LocalityType {

    @XmlElement(name = "Phones", required = true)
    protected String phones;
    @XmlElement(name = "Addresses", required = true)
    protected AddressesType addresses;

    /**
     * Obtiene el valor de la propiedad phones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhones() {
        return phones;
    }

    /**
     * Define el valor de la propiedad phones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhones(String value) {
        this.phones = value;
    }

    /**
     * Obtiene el valor de la propiedad addresses.
     * 
     * @return
     *     possible object is
     *     {@link AddressesType }
     *     
     */
    public AddressesType getAddresses() {
        return addresses;
    }

    /**
     * Define el valor de la propiedad addresses.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressesType }
     *     
     */
    public void setAddresses(AddressesType value) {
        this.addresses = value;
    }

}
