import { Billing, Branche, ChannelModel, ConfigParameter, TypeUserModel } from "../models/billing";
import { InvoiceDetail } from "../models/invoice-detail";
import { ControlUserPermissions } from "../models/user-control-permissons";

export interface ConfigParameterResponse {
    code?: number | string;
    description?: string;
    data?: ConfigParameter[];
    errors?: any[];
}

export interface TypeUserResponse {
    code?: number | string;
    description?: string;
    data?: TypeUserModel;
    errors?: any[];
}

export interface BranchResponse {
    data?: ContentBranche;
}

interface ContentBranche {
    content?: Branche[];
}

/**
 * Response para traer todas las facturas
 */
export interface BillingResponse {
    code?: number | string;
    description?: string;
    data?: Billing[];
    errors?: any[];
}

export interface BillingResponseOne {
    code?: number | string;
    description?: string;
    data?: Billing;
    errors?: any[];
}

export interface InvoiceDetailResponse {
    code?: number | string;
    description?: string;
    data?: InvoiceDetail[];
    errors?: any[];
}

export interface InvoicePagesResponse {
    data?: ContentBilling;
}

interface ContentBilling {
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
    code?: number | string;
    description?: string;
    data?: ControlUserPermissions;
    errors?: any[];
}

export interface ChannelResponse {
    code?: number;
    description?: string;
    data?: ChannelModel;
    errors?: any[];
}

export interface BrancheResponse {
    code?: number;
    description?: string;
    data?: Branche;
    errors?: any[];
}