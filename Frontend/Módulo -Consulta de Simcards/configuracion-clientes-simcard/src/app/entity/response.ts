import { CustomerModel } from "../models/model";

export interface CustomerResponse {
    code?:number;
    description?:string;
    data?: CustomerModel[];
    errors?: any[];
}