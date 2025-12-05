import { Billing, MooringBillingModel, MooringModel, ParametersModel, UserPermissionCancellation } from "../model/model";

export interface MooringBillingPaginationResponse {
    content?: MooringBillingModel[];
}

export interface MooringBillingResponse {
    code?:number;
    description?:string;
    data?: MooringBillingModel[];
    errors?: any[];
}


export interface ParametersResponse {
    code?:number;
    description?:string;
    data?: ParametersModel[];
}

export interface MooringResponse {
    code?:number;
    description?:string;
    data?: MooringModel[];
}

export interface MooringValidateResponse {
    code?:number;
    description?:string;
    data?: MooringModel;
}

export interface UserPermissionCancellationResponse{
    code?:number;
    description?:string;
    data?: UserPermissionCancellation;
}

export interface BillingResponse {
    code?:number | string;
    description?:string;
    data?: Billing;
    errors?: any[];
}