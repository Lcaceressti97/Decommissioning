import { TransactionHistoryModel } from "../models/TransactionHistoryModel";

export interface TransactionHistoryResponse {
    code?: number;
    description?: string;
    data?: TransactionHistoryModel[];
    errors?: any[];
}