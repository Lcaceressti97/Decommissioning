import { Branche, User, WareHouseModel } from "../model/model";

export interface BranchResponse {
    data?: ContentBranche;
}

interface ContentBranche {
    content?: Branche[];
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


export interface UserResponse {
    code?:number;
    description?:string;
    data?: User[];
    errors?: any[];
}

export interface WareHousesResponse {
    code?:number;
    description?:string;
    data?: WareHouseModel[];
    error?: any[];
}