export interface InvoiceEquipmentAccesories {
    id?: number;
    invoiceNo: string;
    invoiceType: string;
    invoiceStatus: string;
    billingAccount: string;
    subscriber: string;
    custcode: string;
    emailaddr: string;
    company: string;
    cai: string;
    customerName: string;
    customerType: string;
    documentNo: string;
    diplomaticCardNo: string;
    correlativeOrdenExemptNo: string;
    correlativeCertificateExoNo: string;
    xml: string;
    path: string;
    address: string;
    warehouse: string;
    agency: string;
    transactionUser: string;
    chargeLocal: string;
    chargeUsd: string;
    exchangeRate: string;
    tax: string;
    discount: string;
    created: string;
    createdBy: string;
}

export interface ItemSelect {
    id?: string | number;
    itemName?: string;
}

export interface ConfigParameter {
    id?:number;
    idApplication?:number;
    parameterType?:string;
    parameterName?:string;
    parameterDescription?:string;
    parameterValue?:string;
    stateCode?:number;
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