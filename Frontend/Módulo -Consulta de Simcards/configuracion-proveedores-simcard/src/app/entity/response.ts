import { ProviderModel } from "../model/model";

export interface ProviderResponse {
    code?:number;
    description?:string;
    data?: ProviderModel[];
    errors?: any[];
}