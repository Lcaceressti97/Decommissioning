import { ConfigParameter, Billing, ControlInvoice, ControlCancellation } from "../models/model";


export interface ConfigParameterResponse {
    code?:number;
    description?:string;
    data?: ConfigParameter[];
    errors: any[];
}

export interface BillingResponse {
    code?:number;
    description?:string;
    data?: Billing;
    errors: any[];
}




export interface ControlInvoiceResponse {
    code?:number;
    description?:string;
    data?: ControlInvoice[];
    errors: any[];
}


export interface ControlCancellationResponse {
    code?:number;
    description?:string;
    data?: ControlCancellation[];
    errors: any[];
}

export interface ControlInvoicePagesResponse {
    data?: ContentControlInvoice;
}

interface ContentControlInvoice {
    content?: ControlInvoice[];
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

export interface ControlCancellationPagesResponse {
    data?: ContentControlCancellation;
}

interface ContentControlCancellation {
    content?: ControlCancellation[];
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
