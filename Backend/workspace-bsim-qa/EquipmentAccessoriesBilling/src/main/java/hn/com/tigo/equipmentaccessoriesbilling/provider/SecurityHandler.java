package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {
	private String user;
	private String password;
	
	public SecurityHandler(String user, String password) {
		this.setUser(user);
		this.setPassword(password);
	}
	
	@Override
    public boolean handleMessage(final SOAPMessageContext msgCtx) {

        // Indicatodor de direccion del mensaje
        final Boolean outInd = (Boolean)  msgCtx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // Solo mensajes de seguridad a encabezados outbound
        if (outInd.booleanValue()) {
            try {
                // Envoltorio SOAP
                final SOAPEnvelope envelope =  msgCtx.getMessage().getSOAPPart().getEnvelope();

                // Encabezado SOAP, puede no estar creado
                SOAPHeader header = envelope.getHeader();
                if (header == null)
                    header = envelope.addHeader();

                //Agrego seguridad wsse
                final SOAPElement security =  header.addChildElement("Security", "wsse",              
"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                SOAPElement usernameToken =
                        security.addChildElement("UsernameToken", "wsse");
                usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

                usernameToken.addChildElement("Username",
"wsse").addTextNode(getUser());
                usernameToken.addChildElement("Password",
"wsse").addTextNode(getPassword());

            } catch (final Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}
