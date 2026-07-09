/**
 * Representa la estructura de un parámetro de configuración
 * proveniente de MEA_CONFIG_PARAMETERS
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