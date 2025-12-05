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

export interface StatusNames {
    [key: number]: string;
  }

export interface DataCancel {
    idInvoice?:number;
    user?:string;
}

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
    idBranchOffices?:number;
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
    idVoucher?: number;
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

export interface ControlUserPermissions {
    id?:number;
    idUser?:number;
    userName?:string;
    permitStatus?:string;
    typeUser?:number;
    created?:Date;
}

export interface ControlCancellation {
    idPrefecture?:number;
    typeCancellation?:number;
    description?:string;
    userCreate?:string;
    warehouse?:string;
    inventoryType?:string;
    status?:number;
    cancelReason?:string;

}

/**
 * Se usa para las validaciones del usuario
 * 
 */
export interface TypeUserModel {
    id?:number;
    typeUser?:string;
    description?:string;
    status?: number;
    created?: Date;
}

/**
 * Interfaz que mapea los datos de la tabla de BSIM
 * 
 */
export interface InventoryTypeModel {
    id?:number;
    version?:number;
    type?:string;
    longDescription?:string;
    shortDescription?:string;
    externalCode?:string;
}

/**
 * Interfaz que mapea las bodegas
 * 
 */
export interface WareHouseModel {
    id?:number;
    version?:number;
    code?:string;
    name?:string;
    address?:string;
    responsible?:string;
    status?:string;
    warehouseTypeId?:string;
    immediateBoss?:string;
    created?:Date;
}

/**
 * Se usa para el control de los select
 * 
 */
export interface SelectModel {
    id?:number | string;
    itemName?: string;
}

/**
 * Interfaz que mapea los datos de la tabla cancel reason
 * 
 */
export interface CancelReasonModel {
    id?:number;
    reasonCode?:number;
    reasonDesc?:string;
    inventoryType?:string;
    status?:number;
    created?:Date;
}