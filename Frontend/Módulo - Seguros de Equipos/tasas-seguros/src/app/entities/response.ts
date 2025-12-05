import { InsuranceRatesModel } from "../models/model";

export interface InsuranceRatesResponse {
    code?:number;
    description?:string;
    data?: InsuranceRatesModel[];
    errors?: any[];
}