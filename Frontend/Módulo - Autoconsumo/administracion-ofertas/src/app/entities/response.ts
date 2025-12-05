import { AdministrationOffersModel } from "../models/AdministrationOffersModel";
import { ChangeCodeHistoricalModel } from "../models/ChangeCodeHistoricalModel";

export interface AdministrationOffersResponse {
    code?: number;
    description?: string;
    data?: AdministrationOffersModel[];
    errors?: any[];
}

export interface ChangeCodeHistoricalResponse {
    code?: number;
    description?: string;
    data?: ChangeCodeHistoricalModel[];
    errors?: any[];
}