import { Billing, Branche, ConfigParameter } from "../models/billing";
import { InvoiceDetail } from "../models/invoice-detail";

export interface ConfigParameterResponse {
    code?:number | string;
    description?:string;
    data?: ConfigParameter[];
    errors?: any[];
}

export interface ResponseWithOutData {
    code?:number | string;
    description?:string;
    data?: any[];
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

export interface BrancheResponse {
    code?:number;
    description?:string;
    data?: Branche;
    errors?: any[];
}