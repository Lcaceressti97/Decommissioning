package hn.com.tigo.equipmentaccessoriesbilling.services.emailservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for attachmentDto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="attachmentDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachContent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attachName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encodingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attachmentDto", propOrder = { "attachContent", "attachName", "mimeType", "encodingType" })
public class AttachmentDto {

	@XmlElement(required = true)
	protected String attachContent;
	@XmlElement(required = true)
	protected String attachName;
	@XmlElement(required = true)
	protected String mimeType;
	protected String encodingType;

	/**
	 * Gets the value of the attachContent property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAttachContent() {
		return attachContent;
	}

	/**
	 * Sets the value of the attachContent property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setAttachContent(String value) {
		this.attachContent = value;
	}

	/**
	 * Gets the value of the attachName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAttachName() {
		return attachName;
	}

	/**
	 * Sets the value of the attachName property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setAttachName(String value) {
		this.attachName = value;
	}

	/**
	 * Gets the value of the mimeType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the value of the mimeType property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setMimeType(String value) {
		this.mimeType = value;
	}

	/**
	 * Gets the value of the encodingType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEncodingType() {
		return encodingType;
	}

	/**
	 * Sets the value of the encodingType property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEncodingType(String value) {
		this.encodingType = value;
	}

}
