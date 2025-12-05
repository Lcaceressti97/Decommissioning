import { MooringBillingModel, MooringModel, ParametersModel } from "../model/model";

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