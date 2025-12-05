/**
 * Interfaz que representa la estructura de los datos
 * MEA_INVOICE_TYPE
 *
 */
export interface InvoiceTypesModel {
    id?: number;
    type?: string;
    description?: string;
    status?: number;
    created?: number;
}

/**
 * Interfaz que representa la estructura de los datos
 * MEA_BILLING_SERVICES
 *
 */
export interface BillingServicesModel {
    id?: number;
    serviceCode?: number;
    serviceName?: string;
    totalQuantity?: number;
    creationUser?: number;
    creationDate?: Date;
    modificationUser?: string;
    modificationDate?: Date;
    status?: number;
}

/**
 * Interfaz que representa la estructura de los datos
 * MEA_CONFIG_PARAMETERS
 *
 */
export interface ConfigParameter {
    id?: number;
    idApplication?: number;
    parameterType?: string;
    parameterName?: string;
    parameterDescription?: string;
    parameterValue?: string;
    stateCode?: number;
    created?: Date;

}

/**
 * Ayuda a mapear los datos que se reciben del servicio
 * de la tasa de cambio
 *
 */
export interface Exchangerate {
    idExchange?: number;
    salePrice?: number;
    purchasePrice?: number;

}


/**
 * Interfaz que representa la estructura de los datos
 * MEA_BRANCH_OFFICES
 *
 */
export interface Branche {
    id?: number;
    idPoint?: number;
    name?: string;
    address?: string;
    phone?: string;
    rtn?: string;
    fax?: string;
    pbx?: string;
    email?: string;
    acctCode?: string;
    fictitiousNumber?: string;
    idCompany?: number;
    idSystem?: number;
    wineryCode?: string;
    status?: number;
    created?: Date;
    territory?: number;
}

/**
 * No está en uso
 * Interfaz que representa la estructura de los datos
 * MEA_CHANNELS
 *
 */
export interface ChannelModel {
    id?: number;
    name?: string;
    description?: string;
    statusStr?: string;
    status?: number;
    created?: Date;
    payUpFrontNumber?: number;
    nonFiscalNote?: number;
    reserveSerialNumber?: number;
    releaseSerialNumber?: number;
}

/**
 * Interfaz para el detalle de la factura
 *
 */
export interface InvoiceDetail {
    id?: number;
    idFind?: string;
    model?: string;
    description?: string;
    quantity?: number;
    unitPrice?: number;
    subtotal?: number;
    discountPercentage?: number;
    discount?: number;
    taxPercentage?: number;
    tax?: number;
    amountTotal?: number;
    serieOrBoxNumber?: string;
    reserveKey?: string;
}

/**
 * Modelo principal
 *
 */
export interface Billing {
    id?: number;
    invoiceNo?: string;
    invoiceType?: string;
    idReference?: number;
    acctCode?: string;
    primaryIdentity?: string;
    documentNo?: string;
    diplomaticCardNo?: string;
    correlativeOrdenExemptNo?: string;
    correlativeCertificateExoNo?: string;
    xml?: string;
    cai?: string;
    customer?: string
    seller?: string;
    cashierName?: string;
    warehouse?: string;
    agency?: string;
    idBranchOffices?: number | string;
    dueDate?: Date;
    initialRank?: string;
    finalRank?: string;
    subtotal?: number;
    exchangeRate?: number;
    discount?: number;
    discountPercentage?: number;
    taxPercentage?: number;
    amountTax?: number;
    amountExo?: number;
    amountTotal?: number;
    authorizingUser?: string;
    authorizationDate?: Date;
    userIssued?: string;
    dateOfIssue?: Date;
    exonerationStatus?: number;
    status?: number | string;
    statusCode?: number | string;
    created?: Date;
    idCompany?: number;
    idSystem?: number;
    numberDei?: string;
    deadLine?: string;
    idVoucher?: number;
    customerId?: string;
    sellerCode?: string;
    invoiceDetails?: InvoiceDetail[];
    inventoryType?: string;
    subWarehouse?: string;
    channel?: number;
    exemptAmount?: number;
    customerAddress?: string;
    customerRtnId?: string;
    sellerName?: string;
    paymentCode?: string;
    totalLps?: string;
    totalLpsLetters?: string;
    buttonExo?: boolean;
    fiscalProcess?: string;
    idInsuranceClaim?: number;
    uti?: string;
    customerType?: string;
}


/**
 * Interfaz que mapea los datos de la tabla de BSIM
 *
 */
export interface InventoryTypeModel {
    id?: number;
    version?: number;
    type?: string;
    longDescription?: string;
    shortDescription?: string;
    externalCode?: string;
}

/**
 * Interfaz que mapea los datos de la tabla de BSIM
 *
 */
export interface CustomerInfoModel {
    customerName?: string;
    customerAddress?: string;
    customerRtn?: string;
    primaryIdentity?: string;
    customerId?: string;
}

/**
 * Interfaz que mapea los datos de la tabla de BSIM
 *
 */
export interface SubWarehouseModel {
    id?: number;
    version?: number;
    code?: string;
    name?: string;
}

/**
 * Interfaz que mapea los datos de la tabla de Del detalle de bloqueos
 *
 */
export interface HistoricalDetail {
    id?: number;
    esnImei: string;
    ivesn: string;
    operador: number;
    tipobloqueo: string;
    telefono: string;
    anexo: number;
    technologyType: string;
    simcard: string;
    nombreUsuarioTransaccion: string;
    identidadUsuarioTransaccion: string;
    direccionUsuarioTransaccion: string;
    telefonoUsuarioTransaccion: string;
    motivoBloqueo: string;
    descriptionReasonBlocking: string;
    createdDate: string;
    fechaBloqueo: string;
    nombreDesbloqueante: string;
    identidadDesbloqueante: string;
    direccionDesbloqueante: string;
    telefonoDesbloqueante: string;
    motivoDesbloqueo: string;
    descriptionReasonUnlock: string;
    fechaDesbloqueo: string;
    usuarioTransaccion: string;
    pantallaTransaccion: string;
    estadoBajaAlta: string;
    status: number;
    modelo: string;
}

export interface WareHouseModel {
    id?: number;
    version?: number;
    code?: string;
    name?: string;
    address?: string;
    responsible?: string;
    status?: string;
    warehouseTypeId?: string;
    immediateBoss?: string;
    created?: Date;
}

export interface ExistencesModel {
    id?: number;
    version?: number;
    typeInventoryId?: number;
    modelOrArticleId?: number;
    warehouseId?: number;
    quantityExistence?: number;
    primaryIdentification?: string;
    legacyCode?: string;
    model?: string;
    description?: string;
    code?: string;
    equipmentLineId?: number;
}


export interface StatusNames {
    [key: number]: string;
}

export interface ItemSelect {
    id?: string | number;
    code?: string;
    itemName?: string;
}

export interface PriceMasterModel {
    id?:number;
    inventoryType?:string;
    model?:string;
    description?:string;
    baseCost?:number;
    factorCode?:number;
    price?:number;
    userCreated?:string;
    screen?:string;
    created?:Date;
    currency?:string;
    convertLps?:string;
    priceLps?:number;
    lastCost?:number;
    costTemporary?:number;
    priceChangeEsn?:string;
    esn?:string;
    priceEsn?:number;
    priceLpsEsn?:number;
}

/**
 * Se usa para mapear los permisos del usuario
 *
 */
export interface ControlUserPermissions {
    id?:number;
    idUser?:number;
    name?:string;
    userName?:string;
    email?:string;
    generateBill?:string | boolean;
    issueSellerInvoice?:string | boolean;
    authorizeInvoice?:string | boolean;
    assignDiscount?:string | boolean;
    typeUser?:number;
    created?:Date;
}
