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
 * Se usa para mapear los parametros de la pantalla
 * 
 */
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

/**
 * Se usa para el control de los select
 * 
 */
export interface SelectMultiModel {
    id?:number;
    itemName?: string;
}

/**
 * Se usa para mapear las sucursales
 * 
 */
export interface Branche {
    id?:number;
    idPoint?:number;
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
    wineryCode?:string;
    status?:number;
    created?:Date;
}

/**
 * Se usa para la emisión de la factura
 * 
 */
export interface DataVoucherService {
    id?:number;
    idInvoice?: number;
    userName?:string;
    user?:string;
    idBranchOffices?:number;
}

export interface StatusNames {
    [key: number]: string;
  }

/**
 * Se usa para mapear los datos de las facturas
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
    sellerCode?:string;
    invoiceDetails?: InvoiceDetail[];
    inventoryType?:string;
    subWarehouse?:string;
    channel?:number;
    exemptAmount?:number;
    customerAddress?:string;
    customerRtnId?:string;
    sellerName?:string;
    buttonAuth?:boolean;
    buttonEmit?:boolean;
    totalLps?:string;
    totalLpsLetters?:string;
    numberDei?:string;
    deadLine?:string;
    isEmitDisabled?: boolean;
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

/**
 * Se usa para el envio del correo
 * 
 */
export interface CorreoModel {
    idPrefecture?:number;
    email?:string;
    cashierName?: string;

}

// Bsim

/**
 * Para la autenticación
 * 
 */
export interface AuthBSimModel {
    access_token?:string;
    token_type?:string;
    refresh_token?:string;
    expires_in?:number;
    scope?:string;
}

/**
 * Para la liberación
 * 
 */
export interface RealeaseSerialNumberModel {
    inventory_type?: string;
    item_code?:string;
    warehouse_code?: string;
    sub_warehouse_code?: string;
    serial_number_list?: SerialNumber[];
}

export interface SerialNumber {
    serial_number?: string;
}

/**
 * Sirve para mapear el servicio de código de respuesta 
 * de la liberación de los números de series
 * 
 */
export interface RealeaseSerialResponseModel {
    result_message?:string;
    result_code?:string;
    count?:number;
    reservation_result_list?:SerialNumberRealase;
}

export interface SerialNumberRealase {
    serial_number?: string;
    reservation_result?:string;
}


export interface ChannelModel {
    id?:number;
    name?:string;
    description?:string;
    payUpFrontNumber?:number;
    nonFiscalNote?:number;
    reserveSerialNumber?:number;
    releaseSerialNumber?:number;
    status?:number;
    created?:number;
}







