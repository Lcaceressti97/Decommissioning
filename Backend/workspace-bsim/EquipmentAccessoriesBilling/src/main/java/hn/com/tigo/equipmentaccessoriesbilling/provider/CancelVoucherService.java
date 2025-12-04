package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherACKType;
import org.springframework.stereotype.Service;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.CancelVoucherType;

@Service
public class CancelVoucherService extends AbstractDriver<CancelVoucherType> {

    public CancelVoucherService() throws MalformedURLException, SOAPException {
        super();
    }

    public String getRequest(CancelVoucherType cancelVoucherType, Map<String, String> parameters) {
        String request = null;
        try {
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" ")
                    .append("xmlns:sch=\"http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema\">")
                    .append("<soap:Header>")
                    .append("<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">")
                    .append("<wsse:UsernameToken wsu:Id=\"UsernameToken-13\" ")
                    .append("xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">")
                    .append("<wsse:Username>").append(parameters.get("USER_VOUCHER_SERVICE")).append("</wsse:Username>")
                    .append("<wsse:Password>").append(parameters.get("PASS_VOUCHER_SERVICE")).append("</wsse:Password>")
                    .append("</wsse:UsernameToken>")
                    .append("</wsse:Security>")
                    .append("</soap:Header>")
                    .append("<soap:Body>")
                    .append("<sch:cancelVoucher>")
                    .append("<sch:IDCompany>").append(cancelVoucherType.getIDCompany()).append("</sch:IDCompany>")
                    .append("<sch:IDSystem>").append(cancelVoucherType.getIDSystem()).append("</sch:IDSystem>")
                    .append("<sch:IDVoucher>").append(cancelVoucherType.getIDVoucher()).append("</sch:IDVoucher>")
                    .append("<sch:IDReference>").append(cancelVoucherType.getIDReference()).append("</sch:IDReference>")
                    .append("<sch:User>").append(cancelVoucherType.getUser()).append("</sch:User>")
                    .append("<sch:Terminal>").append(cancelVoucherType.getTerminal()).append("</sch:Terminal>")
                    .append("</sch:cancelVoucher>")
                    .append("</soap:Body>")
                    .append("</soap:Envelope>");

            request = requestBuilder.toString();
            System.out.println("Final SOAP Request: " + request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public String consumeCancelVoucher(String request, Map<String, String> parameters) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(parameters.get("URL_VOUCHER_SERVICE"));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            conn.setRequestProperty("SOAPAction", "");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = request.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream(),
                            "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            System.out.println("Raw SOAP Response: " + response.toString());
            return response.toString();

        } catch (Exception e) {
            System.err.println("Error in consumeCancelVoucher: " + e.getMessage());
            throw new Exception("Error consuming cancelVoucher service: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public VoucherACKType xmlToVoucherACKType(String xml) throws Exception {
        try {
            // Normalize the XML by removing whitespace between tags
            xml = xml.replaceAll(">\\s+<", "><").trim();

            // Create DOM parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML string
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            // Check for SOAP Fault
            NodeList faultList = doc.getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Fault");
            if (faultList.getLength() > 0) {
                Element faultElement = (Element) faultList.item(0);

                // Get the error details
                NodeList errorsList = faultElement.getElementsByTagName("Errors");
                if (errorsList.getLength() > 0) {
                    Element errorsElement = (Element) errorsList.item(0);
                    String code = getElementTextContent(errorsElement, "Code");
                    String description = getElementTextContent(errorsElement, "Description");
                    throw new Exception(String.format("Error Code: %s, Description: %s", code, description));
                } else {
                    // If no specific error details, get the reason
                    NodeList reasonList = faultElement.getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Text");
                    if (reasonList.getLength() > 0) {
                        throw new Exception(reasonList.item(0).getTextContent());
                    }
                    throw new Exception("Unknown SOAP Fault");
                }
            }

            // Find the VoucherACK element
            NodeList voucherAckList = doc.getElementsByTagName("VoucherACK");
            if (voucherAckList.getLength() == 0) {
                throw new IllegalArgumentException("VoucherACK not found in response");
            }

            Element voucherAckElement = (Element) voucherAckList.item(0);
            VoucherACKType voucherACK = new VoucherACKType();

            // Extract values
            voucherACK.setIDVoucher(Long.parseLong(getElementTextContent(voucherAckElement, "IDVoucher")));
            voucherACK.setIDReference(Long.parseLong(getElementTextContent(voucherAckElement, "IDReference")));
            voucherACK.setIDTransaction(Long.parseLong(getElementTextContent(voucherAckElement, "IDTransaction")));

            String testValue = getElementTextContent(voucherAckElement, "Test");
            if (testValue != null) {
                voucherACK.setTest(Boolean.parseBoolean(testValue));
            }

            return voucherACK;

        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            throw e;
        }
    }

    private String getElementTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}