import { ModelAsEbsModel } from "../models/model";

/**
 * Interface encargada de mapear la respuesta
 * de la api si da un codeHttp = 200
 * 
 */
export interface ModelAsEbsResponse {
    code?:number;
    description?:string;
    data?: ModelAsEbsModel[];
    errors?: any[];
}

export interface ModelAsEbsPagesResponse {
    data?:ContentModelPage
}

interface ContentModelPage {
    content?: ModelAsEbsModel[];
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