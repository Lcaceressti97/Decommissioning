export interface InsuranceClaimModel {
    id?: number;
    customerAccount?: string;
    serviceAccount?: string;
    billingAccount?: string;
    phone?: string;
    phoneStatus?: string;
    clientType?: string;
    actualPrice?: number;
    actualEsn?: string;
    actualModel?: string;
    actualInvType?: string;
    reasonClaim?: string;
    newEsn?: string;
    newModel?: string;
    newModelDescription?: string;
    newInvType?: string;
    userCreate?: string;
    dateCreate?: string;
    userResolution?: string;
    dateResolution?: string;
    invoiceType?: string;
    invoiceLetter?: string;
    invoiceNumber?: number;
    branchAnnex?: number;
    statusClaim?: string;
    observations?: string;
    insurancePremium?: number;
    deductible?: number;
    bandit?: string;
    customerName?: string;
    warehouse?: string;
    workshopOrderNumber?: string;
    priceAdjustment?: number;
    adjustmentPremiums?: number;
}



export interface InsuranceControlModel {
    id?: number;
    transactionCode?: string;
    userAs?: string;
    dateConsultation?: string;
    customerAccount?: string;
    serviceAccount?: string;
    billingAccount?: string;
    phoneNumber?: string;
    equipmentModel?: string;
    esn?: string;
    originAs?: string;
    inventoryTypeAs?: string;
    originTypeAs?: string;
    dateContract?: string;
    startDate?: string;
    endDate?: string;
    dateInclusion?: string;
    insuranceRate?: number;
    period?: number;
    insuranceRate2?: number;
    period2?: number;
    insuranceRate3?: number;
    period3?: number;
    insuranceStatus?: number;
    subscriber?: string;
    trama?: string;

}

export interface ReasonModel {
    id?: number;
    reason?: string;
    description?: string;
    status?: number;
    createdDate?: string;
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

export interface ItemSelect {
    id?: string | number;
    code?: string;
    itemName?: string;
}

/**
 * Interfaz que representa la estructura de los datos 
 * SE_CONFIG_PARAMETERS
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
 * Interfaz que representa la estructura de los datos 
 * EXISTENCIAS
 * 
 */
export interface ExistencesModel {
    warehouseId?: number;
    warehouseCode?: string;
    inventoryType?: string;
    lineCode?: string;
    modelCode?: string;
    model?: string;
    description?: string;
    brand?: string;
    quantity?: number;
}

export interface PriceMasterModel {
    id?:number;
    inventoryType?:string;
    model?:string;
    baseCost?:number;
    factorCode?:number;
    price?:number;
    userCreated?:string;
    screen?:string;
    createdDate?:string;
    currency?:string;
    lempirasPrice?:number;
    temporaryCost?:number;
}

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