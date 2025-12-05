import { PriceMasterModel } from "../model/model";

export interface PriceMasterResponse {
    code?:number;
    description?:string;
    data?: PriceMasterModel[];
    errors?: any[];
}

export interface PriceMasterPagesResponse {
    data?: ContentPriceMaster;
}

interface ContentPriceMaster {
    content?: PriceMasterModel[];
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