package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Document;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.VoucherException;
import hn.com.tigo.equipmentaccessoriesbilling.models.VoucherErrorModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherType;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ObjectFactoryCache;

public class VoucherUtil {

	/**
	 * The {@link MessageFactory} instance, it's used for create the soap envelope
	 */
	protected static MessageFactory messageFactory = ObjectFactoryCache.getInstance().getMessageFactory();

	/**
	 * The {@link DocumentBuilderFactory} instance, it's used for create a
	 * {@link Document} objects from a specific XML string.
	 */
	protected static DocumentBuilderFactory documentBuilderFactory;

	/** The Constant contextResponse. */
	// private static JAXBContext context;

	/** The object factory cache. */

	protected String getRequestAddVoucher(final String user, final String pass, final VoucherType voucherType) {
		String result = null;

		String request = null;

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
			System.out.println(soapBody);

			// final SOAPElement changeElement = soapBody.addChildElement("Change");

			soapMessage.writeTo(baos);
			// result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			System.out.println(result);

			// Serializar objeto a XML

			JAXBContext jaxbContext = JAXBContext.newInstance(VoucherType.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter sw = new StringWriter();
			marshaller.marshal(voucherType, sw);
			String xmlObject = sw.toString();
			System.out.println(xmlObject);

			String elementAddVoucher = xmlObject
					.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");

			String deleteBadAttributed = elementAddVoucher.replace("SOAP-ENV", "soap");
			System.out.println(deleteBadAttributed + "Request");

			String changeAttributedHttp = deleteBadAttributed.replace("http://schemas.xmlsoap.org/soap/envelope/",
					"http://www.w3.org/2003/05/soap-envelope");

			String addVoucher = changeAttributedHttp.replace("<Change/>", elementAddVoucher);

			request = addVoucher.replace("voucherType", "addVocuher");
			System.out.println(request);

		} catch (Exception e) {
			// System.out.println("Fallo");
			// throw new Exception("Error Creando Request",e);
		} finally {

			try {
				baos.close();
			} catch (IOException e) {
				// throw new Exception("Error Creando Request",e);
			}

		}
		return request;
	}

	public String corregirRequest(String requestBorrado) {
		// String request = null;

		String requestOne = requestBorrado.replace(
				"<addVoucher xmlns=\"http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema\">",
				"<addVoucher>");

		String q = requestOne.replace("<addVoucher>", "<sch:addVoucher>");
		q = q.replace("</addVoucher>", "</sch:addVoucher>");

		String w = q.replace("<IDCompany>", "<sch:IDCompany>");
		w = w.replace("</IDCompany>", "</sch:IDCompany>");

		String e = w.replace("<IDSystem>", "<sch:IDSystem>");
		e = e.replace("</IDSystem>", "</sch:IDSystem>");

		String r = e.replace("<Period>", "<sch:Period>");
		r = r.replace("</Period>", "</sch:Period>");

		String t = r.replace("<Voucher>", "<sch:Voucher>");
		t = t.replace("</Voucher>", "</sch:Voucher>");

		String y = t.replace("<Original>", "<sch:Original>");
		y = y.replace("</Original>", "</sch:Original>");

		String u = y.replace("<VoucherDate>", "<sch:VoucherDate>");
		u = u.replace("</VoucherDate>", "</sch:VoucherDate>");

		String i = u.replace("<IDPoint>", "<sch:IDPoint>");
		i = i.replace("</IDPoint>", "</sch:IDPoint>");

		String o = i.replace("<DocumentType>", "<sch:DocumentType>");
		o = o.replace("</DocumentType>", "</sch:DocumentType>");

		String p = o.replace("<CustomerId>", "<sch:CustomerId>");
		p = p.replace("</CustomerId>", "</sch:CustomerId>");

		String a = p.replace("<AccountId>", "<sch:AccountId>");
		a = a.replace("</AccountId>", "</sch:AccountId>");

		String s = a.replace("<TaxPayer>", "<sch:TaxPayer>");
		s = s.replace("</TaxPayer>", "</sch:TaxPayer>");

		String d = s.replace("<TaxPayerType>", "<sch:TaxPayerType>");
		d = d.replace("</TaxPayerType>", "</sch:TaxPayerType>");

		String f = d.replace("<IDType>", "<sch:IDType>");
		f = f.replace("</IDType>", "</sch:IDType>");

		String g = f.replace("<DocumentID>", "<sch:DocumentID>");
		g = g.replace("</DocumentID>", "</sch:DocumentID>");

		String h = f.replace("<Name>", "<sch:Name>");
		h = h.replace("</Name>", "</sch:Name>");

		String j = h.replace("<ReferenceSystem>", "<sch:ReferenceSystem>");
		j = j.replace("</ReferenceSystem>", "</sch:ReferenceSystem>");

		String k = j.replace("<Field>", "<sch:Field>");
		k = k.replace("</Field>", "</sch:Field>");

		String l = k.replace("<IDField>", "<sch:IDField>");
		l = l.replace("</IDField>", "</sch:IDField>");

		String ll = l.replace("<Value>", "<sch:Value>");
		ll = ll.replace("</Value>", "</sch:Value>");

		String m = ll.replace("<Items>", "<sch:Items>");
		m = m.replace("</Items>", "</sch:Items>");

		String n = m.replace("<Item>", "<sch:Item>");
		n = n.replace("</Item>", "</sch:Item>");

		String z = n.replace("<ItemCode>", "<sch:ItemCode>");
		z = z.replace("</ItemCode>", "</sch:ItemCode>");

		String x = z.replace("<Description>", "<sch:Description>");
		x = x.replace("</Description>", "</sch:Description>");

		String c = x.replace("<Quantity>", "<sch:Quantity>");
		c = c.replace("</Quantity>", "</sch:Quantity>");

		String v = c.replace("<ItemsAmounts>", "<sch:ItemsAmounts>");
		v = v.replace("</ItemsAmounts>", "</sch:ItemsAmounts>");

		String b = v.replace("<ItemAmount>", "<sch:ItemAmount>");
		b = b.replace("</ItemAmount>", "</sch:ItemAmount>");

		String qq = b.replace("<IDCurrency>", "<sch:IDCurrency>");
		qq = qq.replace("</IDCurrency>", "</sch:IDCurrency>");

		String ww = qq.replace("<UnitPrice>", "<sch:UnitPrice>");
		ww = ww.replace("</UnitPrice>", "</sch:UnitPrice>");

		String ee = ww.replace("<Total>", "<sch:Total>");
		ee = ee.replace("</Total>", "</sch:Total>");

		String rr = ee.replace("<Amounts>", "<sch:Amounts>");
		rr = rr.replace("</Amounts>", "</sch:Amounts>");

		String tt = rr.replace("<Amount>", "<sch:Amount>");
		tt = tt.replace("</Amount>", "</sch:Amount>");

		String yy = tt.replace("<IDAmount>", "<sch:IDAmount>");
		yy = yy.replace("</IDAmount>", "</sch:IDAmount>");

		String uu = yy.replace("<Percentage>", "<sch:Percentage>");
		uu = uu.replace("</Percentage>", "</sch:Percentage>");

		String ii = uu.replace("<Tax>", "<sch:Tax>");
		ii = ii.replace("</Tax>", "</sch:Tax>");

		String oo = ii.replace("<User>", "<sch:User>");
		oo = oo.replace("</User>", "</sch:User>");

		String fin = oo.replace("<Terminal>", "<sch:Terminal>");
		fin = fin.replace("</Terminal>", "</sch:Terminal>");

		return fin;
	}

	public String consumeAddVoucher(final String request, Map<String, String> parameters) {

		// Creamos la variable de retorno
		String retorno = "";
		String responseString = "";
		String outputString = "";

		try {
			// URL urlService = new
			// URL("http://192.168.78.25:7016/VouchersWs/BasicVoucherService");

			URL urlService = new URL(parameters.get("URL_VOUCHER_SERVICE"));

			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			byte[] buffer = new byte[request.length()];
			buffer = request.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();

			// Abrir una conexión HTTP
			HttpURLConnection connection = (HttpURLConnection) urlService.openConnection();

			// Configurar la conexión
			String soapAction = "";

			// Configurar la conexión
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
			connection.setRequestProperty("Connection", "Keep-Alive");
			// connection.setRequestProperty("Content-Length", String.valueOf(b.length));
			connection.setRequestProperty("SOAPAction", soapAction);
			connection.setDoOutput(true);
			connection.setDoInput(true);

			// Enviar el mensaje SOAP

			try (OutputStream out = connection.getOutputStream()) {
				out.write(b);
			}

			// System.out.println("Antes de establecer la conexión");
			InputStreamReader isr;

			if (connection.getResponseCode() >= 400) {
				isr = new InputStreamReader(connection.getErrorStream());
			} else {
				isr = new InputStreamReader(connection.getInputStream());
			}

			// System.out.println("Después de establecer la conexión");
			BufferedReader in = new BufferedReader(isr);

			while ((responseString = in.readLine()) != null) {
				outputString = String.valueOf(outputString) + responseString;
			}

			System.out.println(outputString);
			retorno = outputString;
			// System.out.println(retorno);

			connection.disconnect();
			return retorno;
		} catch (IOException ex) {
			throw new VoucherException(" " + ex.getMessage());
		}

	}

	/**
	 * Método encargado de mapear el response del addVocuher cuando es un OK
	 * 
	 * @param response
	 * @return
	 */
	public VoucherResponseType xmlToVoucherResponseType(String response) {

		int start = response.indexOf("<S:Body>") + "<S:Body>".length();
		int end = response.indexOf("</S:Body>");
		String xmlBody = response.substring(start, end);

		String responseMap = xmlBody.replace("VoucherResponse", "VoucherResponseType");

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(VoucherResponseType.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(responseMap);
			VoucherResponseType voucherResponse = (VoucherResponseType) unmarshaller.unmarshal(reader);

			return voucherResponse;
		} catch (JAXBException e) {

		}

		return null;
	}

	/**
	 * Método encargado de mapear el response del addVocuher cuando es un ERROR
	 * 
	 * @param response
	 * @return
	 */
	public VoucherErrorModel xmlToVoucherErrorModel(String response) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(VoucherErrorModel.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(response);
			VoucherErrorModel voucherResponse = (VoucherErrorModel) unmarshaller.unmarshal(reader);

			return voucherResponse;
		} catch (JAXBException e) {

		}

		return null;
	}

}
