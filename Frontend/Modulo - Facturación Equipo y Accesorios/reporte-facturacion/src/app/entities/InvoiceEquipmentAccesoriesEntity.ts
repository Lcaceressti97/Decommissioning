import { Billing, ConfigParameter } from "../models/InvoiceEquipmentAccesories";

export interface BrancheOfficesResponse {
    data?: BrancheOffices[];
}

interface ContentBranches {
    content?: BrancheOffices[];
}

export interface BrancheOffices {
    id?: number;
    name?: string;
    address?: string;
    phone?: string;
    rtn?: string;
    fax?: string;
    pbx?: string;
    email?: string;
    acctCode?: string;
    fictitiousNumber?: string;
    idCompany?: number;
    idSystem?: number;
    wineryCode?: string;
    status?: number;
    created?: Date;
}


export interface WareHousesResponse {
    code?: number;
    description?: string;
    data?: WareHouseModel[];
    error?: any[];
}

export interface WareHouseModel {
    id?: number;
    version?: number;
    code?: string;
    name?: string;
    address?: string;
    responsible?: string;
    status?: string;
    warehouseTypeId?: string;
    immediateBoss?: string;
    created?: Date;
}

export interface IssuingUserResponse {
    code?: number;
    description?: string;
    data?: IssuingUser[];
    errors?: any[];
}



export interface IssuingUser {
    id?: number;
    idUser?: string;
    name?: string;
    userName?: string;
    email?: string;
    generateBill?: string;
    issueSellerInvoice?: string;
    authorizeInvoice?: string;
    created?: Date;
}


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
    code?: number | string;
    description?: string;
    data?: ConfigParameter[];
    errors?: any[];
}


export interface BillingResponse {
    code?: number;
    description?: string;
    data?: Billing[];
}

export interface BillingPagesResponse {
    data?: ContentModelPage
}

interface ContentModelPage {
    content?: Billing[];
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
    first: boolean;
    last: boolean;
    empty: boolean;
    numberOfElements: number;
    pageable: {
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        unpaged: boolean;
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        };
    };
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
}