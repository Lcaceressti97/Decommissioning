import { ConfigParameter, ExistencesModel, InsuranceClaimModel, InsuranceControlModel, InventoryTypeModel, InvoiceTypesModel, PriceMasterModel, ReasonModel, WareHouseModel } from "../models/model";

export interface InsuranceClaimResponse {
    code?: number;
    description?: string;
    data?: InsuranceClaimModel[];
    errors?: any[];
}

/**
 * EQUIPMENT INSURANCE CONTROL
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface InsuranceControlResponse {
    code?: number;
    description?: string;
    data?: InsuranceControlModel[];
    errors?: any[];
}

/**
 * REASON
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface ReasonResponse {
    code?: number;
    description?: string;
    data?: ReasonModel[];
    errors?: any[];
}

/**
 * WAREHOUSE
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface WarehouseResponse {
    code?: number;
    description?: string;
    data?: WareHouseModel[];
    errors?: any[];
}

/**
 * INVENTORY-TYPE
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface InventoryTypeResponse {
    code?: number;
    description?: string;
    data?: InventoryTypeModel[];
    errors?: any[];
}

/**
 * PARAMETERS
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface ConfigParameterResponse {
    code?: number | string;
    description?: string;
    data?: ConfigParameter[];
    errors?: any[];
}

/**
 * EXISTENCES
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface ExistencesResponse {
    code?: number;
    description?: string;
    data?: ExistencesModel[];
    errors?: any[];
}

export interface PriceMasterResponse {
    code?: number;
    description?: string;
    data?: PriceMasterModel[];
    errors?: any[];
}

/**
 * INVOICE_TYPE
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface InvoiceTypeResponse {
    code?: number | string;
    description?: string;
    data?: InvoiceTypesModel[];
    errors?: any[];
}


export interface InsuranceClaimResponsePaginated {
    data?: ContentInsuranceClaim;
}

interface ContentInsuranceClaim {
    content?: InsuranceClaimModel[];
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
    first: boolean;
    last: boolean;
    empty: boolean;
    numberOfElements: number;
    pageable: {
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        unpaged: boolean;
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        };
    };
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
}