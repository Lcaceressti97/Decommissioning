import { ParametersHistoricalModel } from "../models/ParametersHistoricalModel";
import { ParametersModel } from "../models/ParametersModel";

export interface ParametersResponse {
    code?: number;
    description?: string;
    data?: ParametersModel[];
    errors?: any[];
}

export interface ParametersHistoricalResponse {
    code?: number;
    description?: string;
    data?: ParametersHistoricalModel[];
    errors?: any[];
}