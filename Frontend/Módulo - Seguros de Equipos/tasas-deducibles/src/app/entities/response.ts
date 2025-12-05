import { DeductibleRateModel, ReasonModel } from "../models/model";

/**
 * Interface encargada de mapear la respuesta
 * de la api si da un codeHttp = 200
 * 
 */
export interface DeductibleRateResponse {
    code?: number;
    description?: string;
    data?: DeductibleRateModel[];
    errors?: any[];
}

/**
 * REASON
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface ReasonResponse {
    code?: number;
    description?: string;
    data?: ReasonModel[];
    errors?: any[];
}