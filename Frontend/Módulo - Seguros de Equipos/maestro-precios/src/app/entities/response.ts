import { PriceMasterModel } from "../models/model";

/**
 * Interface encargada de mapear la respuesta
 * de la api si da un codeHttp = 200
 * 
 */
export interface PriceMasterResponse {
    code?:number;
    description?:string;
    data?: PriceMasterModel[];
    errors?: any[];
}