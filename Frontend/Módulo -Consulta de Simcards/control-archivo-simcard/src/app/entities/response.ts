import { ArtModel, CustomerModel, Files, GraphicModel, ModeloModel, OrderControlDetailModel, OrderControlModel, ParamSimcardModel, ProviderModel, TypeModel, VersionModel } from "../models/model";


export interface ArtResponse {
    code?:number;
    description?: string;
    data?: ArtModel[];
    errors?: any[];
}

export interface TypeResponse {
    code?:number;
    description?: string;
    data?: TypeModel[];
    errors?: any[];
}

export interface GraphicResponse {
    code?:number;
    description?: string;
    data?: GraphicModel[];
    errors?: any[];
}

export interface ModelResponse {
    code?:number;
    description?: string;
    data?: ModeloModel[];
    errors?: any[];
}

export interface ModelByIdResponse {
    code?:number;
    description?: string;
    data?: ModeloModel;
    errors?: any[];
}

export interface VersionResponse {
    code?:number;
    description?: string;
    data?: VersionModel[];
    errors?: any[];
}

export interface ProviderResponse {
    code?:number;
    description?:string;
    data?: ProviderModel[];
    errors?: any[];
}

export interface CustomerResponse {
    code?:number;
    description?:string;
    data?: CustomerModel[];
    errors?: any[];
}

export interface OderControlResponse {
    code?:number;
    description?:string;
    data?: OrderControlModel[];
    errors?: any[];
}

export interface NextImsiResponse {
    code?:number;
    description?:string;
    data?: OrderControlModel;
    errors?: any[];
}

export interface OderControlDetailResponse {
    code?:number;
    description?:string;
    data?: OrderControlDetailModel[];
    errors?: any[];
}

export interface OderControlFileResponse {
    code?:number;
    description?:string;
    data?: Files;
    errors?: any[];
}

export interface ParamSimcardResponse {
    code?:number;
    description?:string;
    data?: ParamSimcardModel;
    errors?: any[];
}

export interface OrdersControlResponse {
    data?: ContentOrders;
}

interface ContentOrders {
    content?: OrderControlModel[];
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
