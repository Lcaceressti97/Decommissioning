import { BankModel } from "../models/BankModel";

export interface BankNavegaResponse {
    code?:number;
    description?:string;
    data?: BankModel[];
    errors?: any[];
}