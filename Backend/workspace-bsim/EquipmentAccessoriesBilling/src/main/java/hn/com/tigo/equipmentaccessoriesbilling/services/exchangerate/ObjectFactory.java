
package hn.com.tigo.equipmentaccessoriesbilling.services.exchangerate;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hn.com.tigo.equipmentaccessoriesbilling.services.exchangerate package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ModifyCurrency_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "modifyCurrency");
    private final static QName _CurrencyACK_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "CurrencyACK");
    private final static QName _ExchangeResponse_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "ExchangeResponse");
    private final static QName _ModifyExchange_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "modifyExchange");
    private final static QName _DeleteCurrency_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "deleteCurrency");
    private final static QName _DeleteExchange_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "deleteExchange");
    private final static QName _ExchangeACK_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "ExchangeACK");
    private final static QName _AddCurrency_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "addCurrency");
    private final static QName _GetExchange_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "getExchange");
    private final static QName _CurrencyResponse_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "CurrencyResponse");
    private final static QName _MessageFault_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "MessageFault");
    private final static QName _GetCurrency_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "getCurrency");
    private final static QName _AddExchange_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", "addExchange");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hn.com.tigo.equipmentaccessoriesbilling.services.exchangerate
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErrorsType }
     * 
     */
    public ErrorsType createErrorsType() {
        return new ErrorsType();
    }

    /**
     * Create an instance of {@link CurrencyTypeS }
     * 
     */
    public CurrencyTypeS createCurrencyTypeS() {
        return new CurrencyTypeS();
    }

    /**
     * Create an instance of {@link ExchangeType }
     * 
     */
    public ExchangeType createExchangeType() {
        return new ExchangeType();
    }

    /**
     * Create an instance of {@link ExchangeACKType }
     * 
     */
    public ExchangeACKType createExchangeACKType() {
        return new ExchangeACKType();
    }

    /**
     * Create an instance of {@link ExchangeTypeG }
     * 
     */
    public ExchangeTypeG createExchangeTypeG() {
        return new ExchangeTypeG();
    }

    /**
     * Create an instance of {@link CurrencyType }
     * 
     */
    public CurrencyType createCurrencyType() {
        return new CurrencyType();
    }

    /**
     * Create an instance of {@link ExchangeTypeD }
     * 
     */
    public ExchangeTypeD createExchangeTypeD() {
        return new ExchangeTypeD();
    }

    /**
     * Create an instance of {@link CurrencyACKType }
     * 
     */
    public CurrencyACKType createCurrencyACKType() {
        return new CurrencyACKType();
    }

    /**
     * Create an instance of {@link ExchangeResponseType }
     * 
     */
    public ExchangeResponseType createExchangeResponseType() {
        return new ExchangeResponseType();
    }

    /**
     * Create an instance of {@link ExchangeTypeM }
     * 
     */
    public ExchangeTypeM createExchangeTypeM() {
        return new ExchangeTypeM();
    }

    /**
     * Create an instance of {@link DatesType }
     * 
     */
    public DatesType createDatesType() {
        return new DatesType();
    }

    /**
     * Create an instance of {@link ExchangeTypeR }
     * 
     */
    public ExchangeTypeR createExchangeTypeR() {
        return new ExchangeTypeR();
    }

    /**
     * Create an instance of {@link ErrorType }
     * 
     */
    public ErrorType createErrorType() {
        return new ErrorType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "modifyCurrency")
    public JAXBElement<CurrencyType> createModifyCurrency(CurrencyType value) {
        return new JAXBElement<CurrencyType>(_ModifyCurrency_QNAME, CurrencyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyACKType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "CurrencyACK")
    public JAXBElement<CurrencyACKType> createCurrencyACK(CurrencyACKType value) {
        return new JAXBElement<CurrencyACKType>(_CurrencyACK_QNAME, CurrencyACKType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "ExchangeResponse")
    public JAXBElement<ExchangeResponseType> createExchangeResponse(ExchangeResponseType value) {
        return new JAXBElement<ExchangeResponseType>(_ExchangeResponse_QNAME, ExchangeResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeTypeM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "modifyExchange")
    public JAXBElement<ExchangeTypeM> createModifyExchange(ExchangeTypeM value) {
        return new JAXBElement<ExchangeTypeM>(_ModifyExchange_QNAME, ExchangeTypeM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyTypeS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "deleteCurrency")
    public JAXBElement<CurrencyTypeS> createDeleteCurrency(CurrencyTypeS value) {
        return new JAXBElement<CurrencyTypeS>(_DeleteCurrency_QNAME, CurrencyTypeS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeTypeD }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "deleteExchange")
    public JAXBElement<ExchangeTypeD> createDeleteExchange(ExchangeTypeD value) {
        return new JAXBElement<ExchangeTypeD>(_DeleteExchange_QNAME, ExchangeTypeD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeACKType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "ExchangeACK")
    public JAXBElement<ExchangeACKType> createExchangeACK(ExchangeACKType value) {
        return new JAXBElement<ExchangeACKType>(_ExchangeACK_QNAME, ExchangeACKType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "addCurrency")
    public JAXBElement<CurrencyType> createAddCurrency(CurrencyType value) {
        return new JAXBElement<CurrencyType>(_AddCurrency_QNAME, CurrencyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeTypeG }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "getExchange")
    public JAXBElement<ExchangeTypeG> createGetExchange(ExchangeTypeG value) {
        return new JAXBElement<ExchangeTypeG>(_GetExchange_QNAME, ExchangeTypeG.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "CurrencyResponse")
    public JAXBElement<CurrencyType> createCurrencyResponse(CurrencyType value) {
        return new JAXBElement<CurrencyType>(_CurrencyResponse_QNAME, CurrencyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "MessageFault")
    public JAXBElement<ErrorsType> createMessageFault(ErrorsType value) {
        return new JAXBElement<ErrorsType>(_MessageFault_QNAME, ErrorsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyTypeS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "getCurrency")
    public JAXBElement<CurrencyTypeS> createGetCurrency(CurrencyTypeS value) {
        return new JAXBElement<CurrencyTypeS>(_GetCurrency_QNAME, CurrencyTypeS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/ExchangeRate/v1/schema", name = "addExchange")
    public JAXBElement<ExchangeType> createAddExchange(ExchangeType value) {
        return new JAXBElement<ExchangeType>(_AddExchange_QNAME, ExchangeType.class, null, value);
    }

}
