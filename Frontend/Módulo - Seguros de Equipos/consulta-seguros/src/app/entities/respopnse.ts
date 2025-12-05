import { MonthlyFeesModel } from "../models/MonthlyFeesModel";

export interface InsuranceRatesResponse {
    code?: number;
    description?: string;
    data?: MonthlyFeesModel[];
    errors?: any[];
}