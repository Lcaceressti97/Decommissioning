import { Billing, CancelReasonModel, ConfigParameter, ControlCancellation, ControlUserPermissions, InventoryTypeModel, TypeUserModel, WareHouseModel } from "src/app/model/billing";
import { InvoiceDetail } from "src/app/model/invoice-detail";

export interface ConfigParameterResponse {
    code?:number | string;
    description?:string;
    data?: ConfigParameter[];
    errors?: any[];
}

/**
 * Response para traer todas las facturas
 * 
 */
export interface BillingResponse {
    code?:number | string;
    description?:string;
    data?: Billing[];
    errors?: any[];
}

export interface BillingOneResponse {
    code?:number | string;
    description?:string;
    data?: Billing;
    errors?: any[];
}

export interface InvoiceDetailResponse {
    code?:number | string;
    description?:string;
    data?: InvoiceDetail[];
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

export interface UserPermissionsResponse {
    code?:number | string;
    description?:string;
    data?: ControlUserPermissions[];
    errors?: any[];
}

export interface UserPermissionsTwoResponse {
    code?:number | string;
    description?:string;
    data?: ControlUserPermissions;
    errors?: any[];
}

export interface TypeUserResponse {
    code?:number | string;
    description?:string;
    data?: TypeUserModel;
    errors?: any[];
}

/**
 * INVENTORY-TYPE
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface InventoryTypeResponse {
    code?:number;
    description?:string;
    data?: InventoryTypeModel[];
    errors?: any[];
}

export interface WareHousesResponse {
    code?:number;
    description?:string;
    data?: WareHouseModel[];
    error?: any[];
}

export interface CancelReasonResponse {
    code?:number;
    description?:string;
    data?: CancelReasonModel[];
    error?: any[];
}