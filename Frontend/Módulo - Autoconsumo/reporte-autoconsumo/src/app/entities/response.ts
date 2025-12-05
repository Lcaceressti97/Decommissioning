import { ReportModel } from "../models/ReportModel";

export interface ReportResponse {
    code?: number;
    description?: string;
    data?: ReportModel[];
    errors?: any[];
}