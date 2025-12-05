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

export interface CorreoModel {
    idPrefecture?:number;
    email?:number;
    cashierName?: string;
}

export interface StatusNames {
    [key: number]: string;
  }

/**
 * Modelo principal
 * 
 */
export interface Billing {
    id?: number;
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
    paymentCode?:string;
    totalLps?:string;
    totalLpsLetters?:string;
    buttonExo?:boolean;
    fiscalProcess?:string;
}

export interface InvoiceDetail {
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

export interface ExoneradoCorportive {
    diplomaticCardNo?: string;
    correlativeOrdenExemptNo?: string;
    correlativeCertificateExoNo?: string;
}

export interface Branche {
    id?:number;
    name?:string;
    address?:string;
    phone?:string;
    rtn?:string;
    fax?:string;
    pbx?:string;
    email?:string;
    acctCode?:string;
    fictitiousNumber?:string;
    idCompany?:number;
    idSystem?:number;
    idWarehouse?:number;
    warehouseName?:string;
    status?:number;
    created?:Date;
    
}

