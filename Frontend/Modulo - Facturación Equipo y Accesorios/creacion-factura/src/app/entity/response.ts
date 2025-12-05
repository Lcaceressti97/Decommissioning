import { Billing, BillingServicesModel, Branche, ChannelModel, ConfigParameter, ControlUserPermissions, CustomerInfoModel, Exchangerate, ExistencesModel, HistoricalDetail, InventoryTypeModel, InvoiceTypesModel, PriceMasterModel, SubWarehouseModel, WareHouseModel } from "../models/model";

/**
 * SERVICES
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface BillingServicesResponse {
    code?: number;
    description?: string;
    data?: ContentBillingServicesResponse;
}

interface ContentBillingServicesResponse {

    content?: BillingServicesModel[];

}


/**
 * PARAMETERS - INVOICE_TYPE
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
 * Interfaz que mapea la tasa de cambio del servicio 
 * getExchangerate()
 */
export interface ExchangerateResponse {
    code?: number | string;
    description?: string;
    data?: Exchangerate;
    errors?: any[];
}

/**
 * BRANCH OFFICES
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface BranchResponse {
    data?: ContentBranche;
}

interface ContentBranche {
    content?: Branche[];
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

/**
 * CHANNEL
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface ChannelResponse {
    code?: number;
    description?: string;
    data?: ChannelModel[];
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
 * SUB-WAREHOUSE
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface SubWarehouseResponse {
    code?: number;
    description?: string;
    data?: SubWarehouseModel[];
    errors?: any[];
}

export interface SubWarehouseNameResponse {
    code?: number;
    description?: string;
    data?: string;
    errors?: any[];
}

/**
 * CUSTOMER-INFO
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface CustomerInfoResponse {
    code?: number;
    description?: string;
    data?: CustomerInfoModel;
    errors?: any[];
}

/**
 * MEA_BILLING
 * 
 * Interfaz que mapea el response del servicio rest
 * 
 */
export interface BillingResponse {
    code?: number;
    description?: string;
    data?: Billing;
    errors?: any[];
}

/**
 * HISTORICAL DETAIL
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface HistoricalDetailResponse {
    code?: number;
    description?: string;
    data?: string;
    errors?: any[];
}

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

export interface UserPermissionsResponse {
    code?:number | string;
    description?:string;
    data?: ControlUserPermissions;
    errors?: any[];
}

export interface InvoicePagesResponse {
    data?:ContentModelPage
}

interface ContentModelPage {
    content?: Billing[];
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