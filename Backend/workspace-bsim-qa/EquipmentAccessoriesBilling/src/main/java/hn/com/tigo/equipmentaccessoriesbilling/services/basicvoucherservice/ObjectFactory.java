
package hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice package. 
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

    private final static QName _NoopResponse_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "NoopResponse");
    private final static QName _MessageFault_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "MessageFault");
    private final static QName _CancelRemission_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "cancelRemission");
    private final static QName _Noop_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "Noop");
    private final static QName _ModifyRemission_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "modifyRemission");
    private final static QName _AddVoucher_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "addVoucher");
    private final static QName _GetVoucher_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "getVoucher");
    private final static QName _ModifyVoucher_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "modifyVoucher");
    private final static QName _VoucherResponse_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "VoucherResponse");
    private final static QName _AddRemission_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "addRemission");
    private final static QName _RemissionDEIResponse_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "RemissionDEI_Response");
    private final static QName _CancelVoucher_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "cancelVoucher");
    private final static QName _RemissionACK_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "RemissionACK");
    private final static QName _RemissionResponse_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "RemissionResponse");
    private final static QName _VoucherACK_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "VoucherACK");
    private final static QName _GetRemission_QNAME = new QName("http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", "getRemission");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.Voucher }
     * 
     */
    public hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.Voucher createVoucher() {
        return new hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.Voucher();
    }

    /**
     * Create an instance of {@link UpdVoucherType }
     * 
     */
    public UpdVoucherType createUpdVoucherType() {
        return new UpdVoucherType();
    }

    /**
     * Create an instance of {@link UpdVoucherType.Voucher }
     * 
     */
    public UpdVoucherType.Voucher createUpdVoucherTypeVoucher() {
        return new UpdVoucherType.Voucher();
    }

    /**
     * Create an instance of {@link VoucherType }
     * 
     */
    public VoucherType createVoucherType() {
        return new VoucherType();
    }

    /**
     * Create an instance of {@link VoucherType.Voucher }
     * 
     */
    public VoucherType.Voucher createVoucherTypeVoucher() {
        return new VoucherType.Voucher();
    }

    /**
     * Create an instance of {@link NoopResponseType }
     * 
     */
    public NoopResponseType createNoopResponseType() {
        return new NoopResponseType();
    }

    /**
     * Create an instance of {@link ErrorsType }
     * 
     */
    public ErrorsType createErrorsType() {
        return new ErrorsType();
    }

    /**
     * Create an instance of {@link CancelVoucherType }
     * 
     */
    public CancelVoucherType createCancelVoucherType() {
        return new CancelVoucherType();
    }

    /**
     * Create an instance of {@link NoopType }
     * 
     */
    public NoopType createNoopType() {
        return new NoopType();
    }

    /**
     * Create an instance of {@link UpdRemissionType }
     * 
     */
    public UpdRemissionType createUpdRemissionType() {
        return new UpdRemissionType();
    }

    /**
     * Create an instance of {@link GetRemissionDEI }
     * 
     */
    public GetRemissionDEI createGetRemissionDEI() {
        return new GetRemissionDEI();
    }

    /**
     * Create an instance of {@link GetVoucherType }
     * 
     */
    public GetVoucherType createGetVoucherType() {
        return new GetVoucherType();
    }

    /**
     * Create an instance of {@link VoucherResponseType }
     * 
     */
    public VoucherResponseType createVoucherResponseType() {
        return new VoucherResponseType();
    }

    /**
     * Create an instance of {@link RemissionType }
     * 
     */
    public RemissionType createRemissionType() {
        return new RemissionType();
    }

    /**
     * Create an instance of {@link VoucherACKType }
     * 
     */
    public VoucherACKType createVoucherACKType() {
        return new VoucherACKType();
    }

    /**
     * Create an instance of {@link VoucherACKPostType }
     * 
     */
    public VoucherACKPostType createVoucherACKPostType() {
        return new VoucherACKPostType();
    }

    /**
     * Create an instance of {@link IDSType }
     * 
     */
    public IDSType createIDSType() {
        return new IDSType();
    }

    /**
     * Create an instance of {@link CompanyType }
     * 
     */
    public CompanyType createCompanyType() {
        return new CompanyType();
    }

    /**
     * Create an instance of {@link RouteLocationType }
     * 
     */
    public RouteLocationType createRouteLocationType() {
        return new RouteLocationType();
    }

    /**
     * Create an instance of {@link LocalityType }
     * 
     */
    public LocalityType createLocalityType() {
        return new LocalityType();
    }

    /**
     * Create an instance of {@link ItemAmountsType }
     * 
     */
    public ItemAmountsType createItemAmountsType() {
        return new ItemAmountsType();
    }

    /**
     * Create an instance of {@link TaxPayerCType }
     * 
     */
    public TaxPayerCType createTaxPayerCType() {
        return new TaxPayerCType();
    }

    /**
     * Create an instance of {@link AmountsType }
     * 
     */
    public AmountsType createAmountsType() {
        return new AmountsType();
    }

    /**
     * Create an instance of {@link LocalityRType }
     * 
     */
    public LocalityRType createLocalityRType() {
        return new LocalityRType();
    }

    /**
     * Create an instance of {@link VoucherACKPreType }
     * 
     */
    public VoucherACKPreType createVoucherACKPreType() {
        return new VoucherACKPreType();
    }

    /**
     * Create an instance of {@link ErrorType }
     * 
     */
    public ErrorType createErrorType() {
        return new ErrorType();
    }

    /**
     * Create an instance of {@link RouteType }
     * 
     */
    public RouteType createRouteType() {
        return new RouteType();
    }

    /**
     * Create an instance of {@link VoucherSYSType }
     * 
     */
    public VoucherSYSType createVoucherSYSType() {
        return new VoucherSYSType();
    }

    /**
     * Create an instance of {@link SupplierType }
     * 
     */
    public SupplierType createSupplierType() {
        return new SupplierType();
    }

    /**
     * Create an instance of {@link ItemsRType }
     * 
     */
    public ItemsRType createItemsRType() {
        return new ItemsRType();
    }

    /**
     * Create an instance of {@link BuyerType }
     * 
     */
    public BuyerType createBuyerType() {
        return new BuyerType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link VoucherReferenceType }
     * 
     */
    public VoucherReferenceType createVoucherReferenceType() {
        return new VoucherReferenceType();
    }

    /**
     * Create an instance of {@link ItemsType }
     * 
     */
    public ItemsType createItemsType() {
        return new ItemsType();
    }

    /**
     * Create an instance of {@link ReasonType }
     * 
     */
    public ReasonType createReasonType() {
        return new ReasonType();
    }

    /**
     * Create an instance of {@link RangeType }
     * 
     */
    public RangeType createRangeType() {
        return new RangeType();
    }

    /**
     * Create an instance of {@link AmountType }
     * 
     */
    public AmountType createAmountType() {
        return new AmountType();
    }

    /**
     * Create an instance of {@link FieldsType }
     * 
     */
    public FieldsType createFieldsType() {
        return new FieldsType();
    }

    /**
     * Create an instance of {@link ExternalType }
     * 
     */
    public ExternalType createExternalType() {
        return new ExternalType();
    }

    /**
     * Create an instance of {@link AddressesType }
     * 
     */
    public AddressesType createAddressesType() {
        return new AddressesType();
    }

    /**
     * Create an instance of {@link ItemAmountType }
     * 
     */
    public ItemAmountType createItemAmountType() {
        return new ItemAmountType();
    }

    /**
     * Create an instance of {@link FieldType }
     * 
     */
    public FieldType createFieldType() {
        return new FieldType();
    }

    /**
     * Create an instance of {@link ItemRType }
     * 
     */
    public ItemRType createItemRType() {
        return new ItemRType();
    }

    /**
     * Create an instance of {@link VoucherDEIType }
     * 
     */
    public VoucherDEIType createVoucherDEIType() {
        return new VoucherDEIType();
    }

    /**
     * Create an instance of {@link TaxPayerType }
     * 
     */
    public TaxPayerType createTaxPayerType() {
        return new TaxPayerType();
    }

    /**
     * Create an instance of {@link hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.Voucher.External }
     * 
     */
    public hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.Voucher.External createVoucherExternal() {
        return new hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.Voucher.External();
    }

    /**
     * Create an instance of {@link UpdVoucherType.Reference }
     * 
     */
    public UpdVoucherType.Reference createUpdVoucherTypeReference() {
        return new UpdVoucherType.Reference();
    }

    /**
     * Create an instance of {@link UpdVoucherType.Voucher.External }
     * 
     */
    public UpdVoucherType.Voucher.External createUpdVoucherTypeVoucherExternal() {
        return new UpdVoucherType.Voucher.External();
    }

    /**
     * Create an instance of {@link VoucherType.Reference }
     * 
     */
    public VoucherType.Reference createVoucherTypeReference() {
        return new VoucherType.Reference();
    }

    /**
     * Create an instance of {@link VoucherType.Voucher.External }
     * 
     */
    public VoucherType.Voucher.External createVoucherTypeVoucherExternal() {
        return new VoucherType.Voucher.External();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoopResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "NoopResponse")
    public JAXBElement<NoopResponseType> createNoopResponse(NoopResponseType value) {
        return new JAXBElement<NoopResponseType>(_NoopResponse_QNAME, NoopResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "MessageFault")
    public JAXBElement<ErrorsType> createMessageFault(ErrorsType value) {
        return new JAXBElement<ErrorsType>(_MessageFault_QNAME, ErrorsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelVoucherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "cancelRemission")
    public JAXBElement<CancelVoucherType> createCancelRemission(CancelVoucherType value) {
        return new JAXBElement<CancelVoucherType>(_CancelRemission_QNAME, CancelVoucherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoopType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "Noop")
    public JAXBElement<NoopType> createNoop(NoopType value) {
        return new JAXBElement<NoopType>(_Noop_QNAME, NoopType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdRemissionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "modifyRemission")
    public JAXBElement<UpdRemissionType> createModifyRemission(UpdRemissionType value) {
        return new JAXBElement<UpdRemissionType>(_ModifyRemission_QNAME, UpdRemissionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "addVoucher")
    public JAXBElement<VoucherType> createAddVoucher(VoucherType value) {
        return new JAXBElement<VoucherType>(_AddVoucher_QNAME, VoucherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoucherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "getVoucher")
    public JAXBElement<GetVoucherType> createGetVoucher(GetVoucherType value) {
        return new JAXBElement<GetVoucherType>(_GetVoucher_QNAME, GetVoucherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdVoucherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "modifyVoucher")
    public JAXBElement<UpdVoucherType> createModifyVoucher(UpdVoucherType value) {
        return new JAXBElement<UpdVoucherType>(_ModifyVoucher_QNAME, UpdVoucherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "VoucherResponse")
    public JAXBElement<VoucherResponseType> createVoucherResponse(VoucherResponseType value) {
        return new JAXBElement<VoucherResponseType>(_VoucherResponse_QNAME, VoucherResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemissionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "addRemission")
    public JAXBElement<RemissionType> createAddRemission(RemissionType value) {
        return new JAXBElement<RemissionType>(_AddRemission_QNAME, RemissionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemissionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "RemissionDEI_Response")
    public JAXBElement<RemissionType> createRemissionDEIResponse(RemissionType value) {
        return new JAXBElement<RemissionType>(_RemissionDEIResponse_QNAME, RemissionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelVoucherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "cancelVoucher")
    public JAXBElement<CancelVoucherType> createCancelVoucher(CancelVoucherType value) {
        return new JAXBElement<CancelVoucherType>(_CancelVoucher_QNAME, CancelVoucherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherACKType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "RemissionACK")
    public JAXBElement<VoucherACKType> createRemissionACK(VoucherACKType value) {
        return new JAXBElement<VoucherACKType>(_RemissionACK_QNAME, VoucherACKType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherACKPostType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "RemissionResponse")
    public JAXBElement<VoucherACKPostType> createRemissionResponse(VoucherACKPostType value) {
        return new JAXBElement<VoucherACKPostType>(_RemissionResponse_QNAME, VoucherACKPostType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherACKType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "VoucherACK")
    public JAXBElement<VoucherACKType> createVoucherACK(VoucherACKType value) {
        return new JAXBElement<VoucherACKType>(_VoucherACK_QNAME, VoucherACKType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoucherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tigo.com/ElectronicBilling/Service/Voucher/v1/schema", name = "getRemission")
    public JAXBElement<GetVoucherType> createGetRemission(GetVoucherType value) {
        return new JAXBElement<GetVoucherType>(_GetRemission_QNAME, GetVoucherType.class, null, value);
    }

}
