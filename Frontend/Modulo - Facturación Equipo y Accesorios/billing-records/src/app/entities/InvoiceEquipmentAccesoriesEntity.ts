import { Billing, ConfigParameter } from "../models/InvoiceEquipmentAccesories";

export interface InvoiceEquipmentAccesoriesEntity {
    id?: number;
    invoiceNo: string;
    invoiceType: string;
    invoiceStatus: string;
    billingAccount: string;
    subscriber: string;
    custcode: string;
    emailaddr: string;
    company: string;
    cai: string;
    customerName: string;
    customerType: string;
    documentNo: string;
    diplomaticCardNo: string;
    correlativeOrdenExemptNo: string;
    correlativeCertificateExoNo: string;
    xml: string;
    path: string;
    address: string;
    warehouse: string;
    agency: string;
    transactionUser: string;
    chargeLocal: string;
    chargeUsd: string;
    exchangeRate: string;
    tax: string;
    discount: string;
    created: string;
    createdBy: string;
}

export interface ConfigParameterResponse {
    code?:number | string;
    description?:string;
    data?: ConfigParameter[];
    errors?: any[];
}


export interface BillingResponse {
    code?:number;
    description?:string;
    data?: Billing[];
}
