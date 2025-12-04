package hn.com.tigo.equipmentaccessoriesbilling.utils;

import java.util.Hashtable;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectFactoryCache {
	
	/**
	 * Attribute that determine the object factory object and itÂ´s loaded with the
	 * classloader.
	 */
	private static ObjectFactoryCache objectFactory = new ObjectFactoryCache();

	/** The tf. */
	private final TransformerFactory transformerFactory;

	/**
	 * Attribute that determine the instance of the {@link MessageFactory} object.
	 */
	private MessageFactory messageFactory;
	
	/**
	 * Attribute that determine a map of {@link MessageFactory} stored by SOAP protocol
	 */
	private Map<String, MessageFactory> messageFactoryProtocol;

	/**
	 * Attribute that determine the instance of the {@link JAXBContext} objects.
	 */
	private Map<String, JAXBContext> jaxbContexts;

	/**
	 * Attribute that determine the instance of the {@link DocumentBuilderFactory}
	 * object.
	 */
	private DocumentBuilderFactory documentBuilderFactory;
	
	/**
	 * Attribute that determine the instance of the {@link ObjectMapper}
	 * object.
	 */
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Instantiates a new object factory cache.
	 */
	private ObjectFactoryCache() {
		jaxbContexts = new Hashtable<>();
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		transformerFactory = TransformerFactory.newInstance();
		messageFactoryProtocol = new Hashtable<>();
	}

	/**
	 * Gets the single instance of ObjectFactoryCache.
	 *
	 * @return single instance of ObjectFactoryCache
	 */
	public static ObjectFactoryCache getInstance() {
		return objectFactory;
	}

	/**
	 * Gets the message factory.
	 *
	 * @return {@link MessageFactory} unique instance.
	 */
	public MessageFactory getMessageFactory() {

		if (this.messageFactory == null) {

			try {
				this.messageFactory = MessageFactory.newInstance();
			} catch (SOAPException e) {
				//LOGGER.error(e.getCause().getMessage(), e);
			}

		}

		return this.messageFactory;
	}

	/**
	 * Gets the message factory.
	 *
	 * @param protocol
	 *            the protocol
	 * @return the message factory
	 */
	public MessageFactory getMessageFactory(final String protocol) {

		if (!messageFactoryProtocol.containsKey(protocol)) {
			try {
				messageFactoryProtocol.put(protocol, MessageFactory.newInstance(protocol));
			} catch (SOAPException e) {
				//LOGGER.error(e.getCause().getMessage(), e);
			}
		}

		return messageFactoryProtocol.get(protocol);
	}

	/**
	 * Gets the jaxb context.
	 *
	 * @param contextName
	 *            the context name
	 * @return the jaxb context
	 * @throws JAXBException
	 *             the JAXB exception
	 */
	public JAXBContext getJaxbContext(final String contextName) throws JAXBException {

		if (!jaxbContexts.containsKey(contextName)) {
			final JAXBContext context = JAXBContext.newInstance(contextName);
			jaxbContexts.put(contextName, context);
		}

		return jaxbContexts.get(contextName);
	}

	/**
	 * Gets the document builder factory.
	 *
	 * @return the document builder factory
	 */
	public DocumentBuilderFactory getDocumentBuilderFactory() {
		return documentBuilderFactory;
	}

	/**
	 * Gets the transformer factory.
	 *
	 * @return the transformer factory
	 */
	public TransformerFactory getTransformerFactory() {
		return transformerFactory;
	}
	
	/**
	 * Gets the object mapper
	 * 
	 * @return the object mapper object
	 */
	public ObjectMapper getObjectMapper() {
		return mapper;
	}
}
