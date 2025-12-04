package hn.com.tigo.simcardinquiry.services.emailservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for sendMessage complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="sendMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="from" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SendTo" type="{http://tigo.hn/resources/emailService}sentDTO"/>
 *         &lt;element name="CC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="body" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attachments" type="{http://tigo.hn/resources/emailService}attachmentsDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMessage", propOrder = { "from", "sendTo", "cc", "subject", "body", "attachments" })
public class SendMessage {

	@XmlElement(required = true)
	protected String from;
	@XmlElement(name = "SendTo", required = true)
	protected SentDTO sendTo;
	@XmlElementRef(name = "CC", type = JAXBElement.class, required = false)
	protected JAXBElement<String> cc;
	@XmlElement(required = true)
	protected String subject;
	@XmlElement(required = true)
	protected String body;
	@XmlElementRef(name = "attachments", type = JAXBElement.class, required = false)
	protected JAXBElement<AttachmentsDTO> attachments;

	/**
	 * Gets the value of the from property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the value of the from property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFrom(String value) {
		this.from = value;
	}

	/**
	 * Gets the value of the sendTo property.
	 * 
	 * @return possible object is {@link SentDTO }
	 * 
	 */
	public SentDTO getSendTo() {
		return sendTo;
	}

	/**
	 * Sets the value of the sendTo property.
	 * 
	 * @param value allowed object is {@link SentDTO }
	 * 
	 */
	public void setSendTo(SentDTO value) {
		this.sendTo = value;
	}

	/**
	 * Gets the value of the cc property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<String> getCC() {
		return cc;
	}

	/**
	 * Sets the value of the cc property.
	 * 
	 * @param value allowed object is {@link JAXBElement }{@code <}{@link String
	 *              }{@code >}
	 * 
	 */
	public void setCC(JAXBElement<String> value) {
		this.cc = value;
	}

	/**
	 * Gets the value of the subject property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the value of the subject property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSubject(String value) {
		this.subject = value;
	}

	/**
	 * Gets the value of the body property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the value of the body property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setBody(String value) {
		this.body = value;
	}

	/**
	 * Gets the value of the attachments property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link AttachmentsDTO
	 *         }{@code >}
	 * 
	 */
	public JAXBElement<AttachmentsDTO> getAttachments() {
		return attachments;
	}

	/**
	 * Sets the value of the attachments property.
	 * 
	 * @param value allowed object is {@link JAXBElement
	 *              }{@code <}{@link AttachmentsDTO }{@code >}
	 * 
	 */
	public void setAttachments(JAXBElement<AttachmentsDTO> value) {
		this.attachments = value;
	}

}
