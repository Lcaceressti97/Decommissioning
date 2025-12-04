package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hn.com.tigo.core.cache.ObjectFactoryCache;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;



public abstract class AbstractDriver<T> {
	/**
	 * The {@link MessageFactory} instance, it's used for create the soap
	 * envelope
	 */
	protected static MessageFactory messageFactory = ObjectFactoryCache.getInstance().getMessageFactory();
	
	/**
	 * The {@link DocumentBuilderFactory} instance, it's used for create a
	 * {@link Document} objects from a specific XML string.
	 */
	protected static DocumentBuilderFactory documentBuilderFactory;


	/** The Constant contextResponse. */
	private static JAXBContext context;

	/** The object factory cache. */
	protected ObjectFactoryCache objectFactoryCache = ObjectFactoryCache.getInstance();

	/** The cbs bc. */
	protected HttpVoucher httpVoucher;

	public AbstractDriver() {
		documentBuilderFactory = objectFactoryCache.getDocumentBuilderFactory();
		try {
			context = objectFactoryCache.getJaxbContext("hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice");
		} catch (JAXBException e) {
			//LOGGER.error(e.getMessage());
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Gets the request from a driver type and operation specific.
	 *
	 * @param genericType
	 *            the generic type
	 * @param operationClass
	 *            the operation class
	 * @param nameMethod
	 *            the name method
	 * @return the request
	 */
	protected String getRequest(final T genericType, Class<T> operationClass, String nameMethod,final String user, final String pass)throws Exception {

		String result;

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {

			final SOAPMessage soapMessage = messageFactory.createMessage();
			final SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
			final SOAPElement security = soapEnvelope.getHeader().addChildElement("Security", "wsse",
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
			final SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
			usernameToken.addAttribute(new QName("xmlns:wsu"),
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
			final SOAPElement usernameElement = usernameToken.addChildElement("Username", "wsse");
			usernameElement.addTextNode(user);
			final SOAPElement passwordElement = usernameToken.addChildElement("Password", "wsse");
			passwordElement.setAttribute("Type",
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
			passwordElement.addTextNode(pass);
			final SOAPBody soapBody = soapEnvelope.getBody();
			final Document requestDoc = this.<T>getDocument(genericType, operationClass, nameMethod);
			final NodeList nodelist = requestDoc.getChildNodes();

			for (int i = 0; i < nodelist.getLength(); i++) {
				final Node node = soapBody.getOwnerDocument().importNode(nodelist.item(i), true);
				soapBody.appendChild(node);
			}

			soapMessage.writeTo(baos);
			result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		} catch (DOMException | SOAPException | IOException e) {
			throw new Exception("Error Creando Request",e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new Exception("Error Creando Request",e);
			}

		}
		return result;
	}
	
	/**
	 * Gets a document from a driver type and operation specific.
	 *
	 * @param <A>
	 *            the driver type class
	 * @param genericType
	 *            the driver type instance
	 * @param operationClass
	 *            the operation class type
	 * @return the document
	 * @throws AdapterException
	 *             the adapter exception
	 */
	private <A> Document getDocument(final A genericType, Class<A> operationClass, final String nameMethod) throws Exception {

		Document document;

		try {
			document = documentBuilderFactory.newDocumentBuilder().newDocument();
			final Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			final JAXBElement<A> jaxbElement = new JAXBElement<A>(
					new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", nameMethod),
					operationClass, null, genericType);
			marshaller.marshal(jaxbElement, document);
		} catch (JAXBException e) {
			throw new Exception("Error Creando Request",e);
		} catch (ParserConfigurationException e) {
			throw new Exception("Error Creando Request",e);
		}

		return document;
	}

	/**
	 * Gets the result.
	 *
	 * @param response
	 *            the response
	 * @param class1
	 *            the clazz
	 * @return the result
	 * @throws Exception 
	 */
	protected VoucherResponseType getResult(final String response, final Class<VoucherResponseType> class1) throws Exception {

		VoucherResponseType responseService = null;
		try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				response.getBytes(StandardCharsets.UTF_8))) {
			final SOAPMessage message = objectFactoryCache.getMessageFactory().createMessage(null,
					byteArrayInputStream);
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			responseService = class1.cast(unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument()));

		} catch (JAXBException | IOException | SOAPException e) {
			//LOGGER.error(e.getMessage());
			//throw new Exception(e.getMessage());
		}
		return responseService;
	}

	/**
	 * Gets the json response.
	 *
	 * @param response
	 *            the response
	 * @return the json response
	 * @throws AdapterException
	 *             the adapter exception
	 */
	protected JSONObject getJsonResponse(final String response) throws Exception {

		JSONObject xmlJSONObj = null;

		try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				response.getBytes(StandardCharsets.UTF_8))) {
			final SOAPMessage message = objectFactoryCache.getMessageFactory().createMessage(null,
					byteArrayInputStream);
			final Document responseDoc = message.getSOAPBody().extractContentAsDocument();
			xmlJSONObj = XML.toJSONObject(toString(responseDoc));
		} catch (IOException | SOAPException e) {
			//LOGGER.error(e.getMessage());
			throw new Exception(e.getMessage());
		}

		return xmlJSONObj;
	}


	/**
	 * Concatenates a list of values.
	 *
	 * @param args
	 *            the Objects args list separated by commas.
	 * @return the String with all concatenated values.
	 */
	protected String concatParam(final Object... args) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(String.valueOf(args[i]));
		}
		return buffer.toString();
	}

	/**
	 * To string.
	 *
	 * @param doc
	 *            the doc
	 * @return the string
	 * @throws AdapterException
	 *             the adapter exception
	 */
	private String toString(final Document doc) throws Exception {

		String output = null;

		try {
			final StringWriter writer = new StringWriter();
			final Transformer transformer = objectFactoryCache.getTransformerFactory().newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			output = writer.getBuffer().toString();
		} catch (TransformerException e) {
			//LOGGER.error(e.getMessage());
			throw new Exception(e.getMessage());
		}

		return output;
	}

	/**
	 * @return the httpVoucher
	 */
	public final HttpVoucher getHttpVoucher() {
		return httpVoucher;
	}

	/**
	 * @param httpVoucher the httpVoucher to set
	 */
	public final void setHttpVoucher(HttpVoucher httpVoucher) {
		this.httpVoucher = httpVoucher;
	}
}
