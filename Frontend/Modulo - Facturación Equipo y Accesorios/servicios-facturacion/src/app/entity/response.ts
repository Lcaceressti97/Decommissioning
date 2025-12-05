import { BillingServicesModel } from "../model/model";

export interface BillingServicesResponse {
    code?:number;
    description?:string;
    data?: ContentBillingServicesResponse;
}

interface ContentBillingServicesResponse {

    content?: BillingServicesModel[];

}

export interface BillingServicesPagesResponse {
    data?: ContentBillingServices;
}

interface ContentBillingServices {
    content?: BillingServicesModel[];
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