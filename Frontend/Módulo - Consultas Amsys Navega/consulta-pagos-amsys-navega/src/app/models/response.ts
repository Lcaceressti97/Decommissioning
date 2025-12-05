import { NavegaPayment } from "./NavegaPayment";

export interface PaymentsNavegaResponse {
    code?:number;
    description?:string;
    data?: NavegaPayment[];
    errors?: any[];
}