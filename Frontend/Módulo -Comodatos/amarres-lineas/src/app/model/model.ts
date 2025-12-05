export interface MooringBillingModel {
    id?:number;
    correlativeCmd?:number;
    correlativeMooringCmd?:number;
    subscriberId?:string;
    monthsOfPermanence?:number;
    cmdStatus?: string;
    userName?:string;
    supervisorUser?:string;
    userMooring?:string;
    userCancelled?:string;
    dateOfAdmission?: Date;
    dueDate?:Date;
    invoiceLocation?:number;
    invoiceSubLocity?:number;
    invoiceType?:string;
    invoiceReading?: string;
    invoiceNumber?:string;
    inventoryType?:string;
    inventoryModel?:string;
    teamSeries?:string;
    phoneCost?:number;
    phoneDiscount?:number;
    vac?:number;
    mooring?:number | string;
    promotion?:string;
    transactionId?:string;
    customerAccount?:string;
    serviceAccount?:string;
    billingAccount?:string;
    observations?:string;
    createDate?: Date;
}

export interface ParametersModel{
    id?:number;
    idApplication?:number;
    parameterName?:string;
    description?:string;
    value?:string | number;
    created?:Date;
}

export interface MooringModel {
    id?:number;
    idMooringBilling?:number;
    customerAccount?:string;
    billingAccount?:string;
    subscriberId?:string;
    userName?:string;
    dateOfEntry?:Date | string;
    checkInTime?:string;
    unmooringUser?:string;
    unmooringDate?:Date | string;
    mooringStatus?:number;
    created?:Date;
}

export interface UserPermissionCancellation {
    id?:number;
    userName?:string;
    permitStatus?:string;
    created?:Date;
}

export interface ControlCancellation {
    id?:number;
    idReference?:number;
    description?:string;
    cancellationUser?:string;
    created?: Date;
}

export interface Billing {
    id?: string;
    invoiceNo?:string;
    invoiceType?:string;
    idReference?:number;
    acctCode?:string;
    primaryIdentity?:string;
    documentNo?:string;
    diplomaticCardNo?:string;
    correlativeOrdenExemptNo?:string;
    correlativeCertificateExoNo?:string;
    xml?:string;
    cai?:string;
    customer?:string
    seller?:string;
    cashierName?:string;
    warehouse?:string;
    agency?:string;
    idBranchOffices?:number | string;
    dueDate?:Date;
    initialRank?:string;
    finalRank?: string;
    subtotal?:number;
    exchangeRate?:number;
    discount?: number;
    discountPercentage?:number;
    taxPercentage?:number;
    amountTax?:number;
    amountExo?:number;
    amountTotal?:number;
    authorizingUser?:string;
    authorizationDate?:Date;
    userIssued?:string;
    dateOfIssue?:Date;
    exonerationStatus?:number;
    status?:number | string;
    statusCode?:number | string;
    created?: Date;
    idCompany?:number;
    idSystem?:number;
    numberDei?:string;
    deadLine?:string;
    idVoucher?:number;
    customerId?:string;
    sellerCode?:string;
    invoiceDetails?: InvoiceDetail[];
    inventoryType?:string;
    subWarehouse?:string;
    channel?:number;
    exemptAmount?:number;
    customerAddress?:string;
    customerRtnId?:string;
    sellerName?:string;
    buttonExo?:boolean;
}

interface InvoiceDetail {
    id?:number;
    model?:string;
    description?:string;
    quantity?:number;
    unitPrice?:number;
    amountTotal?:number;
    serieOrBoxNumber?:string;
    created?: Date;
    status?: number;
}