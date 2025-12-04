package hn.com.tigo.simcardinquiry.services.emailservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for sendMessageResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="sendMessageResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="generalResponseMsg" type="{http://tigo.hn/resources/emailService}generalResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMessageResponse", propOrder = { "generalResponseMsg" })
public class SendMessageResponse {

	protected GeneralResponse generalResponseMsg;

	/**
	 * Gets the value of the generalResponseMsg property.
	 * 
	 * @return possible object is {@link GeneralResponse }
	 * 
	 */
	public GeneralResponse getGeneralResponseMsg() {
		return generalResponseMsg;
	}

	/**
	 * Sets the value of the generalResponseMsg property.
	 * 
	 * @param value allowed object is {@link GeneralResponse }
	 * 
	 */
	public void setGeneralResponseMsg(GeneralResponse value) {
		this.generalResponseMsg = value;
	}

}
