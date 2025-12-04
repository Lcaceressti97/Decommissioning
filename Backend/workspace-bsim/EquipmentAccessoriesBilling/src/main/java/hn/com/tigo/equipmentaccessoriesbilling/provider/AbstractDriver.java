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

	/** SOAP utils */
	protected static MessageFactory messageFactory = ObjectFactoryCache.getInstance().getMessageFactory();
	protected static DocumentBuilderFactory documentBuilderFactory;

	/** Cache de factories/contexts del proyecto */
	protected ObjectFactoryCache objectFactoryCache = ObjectFactoryCache.getInstance();

	/** HTTP client del proveedor (si aplica) */
	protected HttpVoucher httpVoucher;

	public AbstractDriver() {
		documentBuilderFactory = objectFactoryCache.getDocumentBuilderFactory();
	}

	// -------------------------------------------------------
	// Helpers de JAXBContext por paquete/clase (NO estático)
	// -------------------------------------------------------
	private JAXBContext jaxbContextForPackage(String pkg) throws JAXBException {
		return objectFactoryCache.getJaxbContext(pkg);
	}

	private JAXBContext jaxbContextForClass(Class<?> clazz) throws JAXBException {
		// equivalente a JAXBContext.newInstance(clazz) pero usando tu cache
		return objectFactoryCache.getJaxbContext(clazz.getPackage().getName());
	}

	/**
	 * Construye el SOAP request con WS-Security + body a partir del objeto JAXB.
	 */
	protected String getRequest(final T genericType,
								Class<T> operationClass,
								String nameMethod,
								final String user,
								final String pass) throws Exception {

		String result;
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			final SOAPMessage soapMessage = messageFactory.createMessage();
			final SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();

			// Header WS-Security
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

			// Body
			final SOAPBody soapBody = soapEnvelope.getBody();
			final Document requestDoc = this.getDocument(genericType, operationClass, nameMethod);
			final NodeList nodelist = requestDoc.getChildNodes();
			for (int i = 0; i < nodelist.getLength(); i++) {
				final Node node = soapBody.getOwnerDocument().importNode(nodelist.item(i), true);
				soapBody.appendChild(node);
			}

			soapMessage.writeTo(baos);
			result = new String(baos.toByteArray(), StandardCharsets.UTF_8);

		} catch (DOMException | SOAPException | IOException e) {
			throw new Exception("Error Creando Request", e);
		} finally {
			try { baos.close(); } catch (IOException e) { /* noop */ }
		}
		return result;
	}

	/**
	 * Construye un Document (XML) marshaleando el objeto JAXB con el QName correcto.
	 * Usa JAXBContext del paquete de la clase de operación → evita “unknown to this context”.
	 */
	private <A> Document getDocument(final A genericType,
									 Class<A> operationClass,
									 final String nameMethod) throws Exception {

		try {
			Document document = documentBuilderFactory.newDocumentBuilder().newDocument();

			// Contexto del paquete correcto (p.ej. ...services.basicvoucherservice)
			JAXBContext ctx = jaxbContextForPackage(operationClass.getPackage().getName());
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			JAXBElement<A> jaxbElement = new JAXBElement<>(
					new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", nameMethod),
					operationClass,
					null,
					genericType
			);

			marshaller.marshal(jaxbElement, document);
			return document;

		} catch (JAXBException | ParserConfigurationException e) {
			throw new Exception("Error Creando Request", e);
		}
	}

	/**
	 * Unmarshal del SOAP response a VoucherResponseType usando el contexto del paquete correcto.
	 */
	protected VoucherResponseType getResult(final String response,
											final Class<VoucherResponseType> clazz) throws Exception {

		VoucherResponseType responseService = null;
		try (final ByteArrayInputStream bais =
					 new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8))) {

			final SOAPMessage message = objectFactoryCache.getMessageFactory().createMessage(null, bais);
			final JAXBContext ctx = jaxbContextForClass(clazz);
			final Unmarshaller unmarshaller = ctx.createUnmarshaller();

			responseService = clazz.cast(unmarshaller.unmarshal(
					message.getSOAPBody().extractContentAsDocument()));

		} catch (JAXBException | IOException | SOAPException e) {
			// si quieres, propaga como Exception controlada
			throw new Exception("Error parseando respuesta de Voucher", e);
		}
		return responseService;
	}

	/**
	 * Convierte el SOAP response a JSON (útil si necesitas mapear errores).
	 */
	protected JSONObject getJsonResponse(final String response) throws Exception {
		try (final ByteArrayInputStream bais =
					 new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8))) {

			final SOAPMessage message = objectFactoryCache.getMessageFactory().createMessage(null, bais);
			final Document responseDoc = message.getSOAPBody().extractContentAsDocument();
			return XML.toJSONObject(toString(responseDoc));

		} catch (IOException | SOAPException e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	protected String concatParam(final Object... args) {
		StringBuilder sb = new StringBuilder();
		for (Object a : args) sb.append(String.valueOf(a));
		return sb.toString();
	}

	private String toString(final Document doc) throws Exception {
		try {
			final StringWriter writer = new StringWriter();
			final Transformer transformer = objectFactoryCache.getTransformerFactory().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			return writer.getBuffer().toString();
		} catch (TransformerException e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	public final HttpVoucher getHttpVoucher() {
		return httpVoucher;
	}

	public final void setHttpVoucher(HttpVoucher httpVoucher) {
		this.httpVoucher = httpVoucher;
	}
}
