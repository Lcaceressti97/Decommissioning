
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ItemAmounts_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ItemAmounts_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemAmount" type="{http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema}ItemAmount_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemAmounts_Type", propOrder = {
    "itemAmount"
})
public class ItemAmountsType {

    @XmlElement(name = "ItemAmount", required = true)
    protected List<ItemAmountType> itemAmount;

    /**
     * Gets the value of the itemAmount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemAmount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemAmount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemAmountType }
     * 
     * 
     */
    public List<ItemAmountType> getItemAmount() {
        if (itemAmount == null) {
            itemAmount = new ArrayList<ItemAmountType>();
        }
        return this.itemAmount;
    }

}
