export interface InvoiceStatistics {
    unauthorizedInvoices: number;
    authorizedInvoices: number;
    issuedInvoices: number;
    invoicesWithTaxNumber: number;
    canceledInvoicesWithoutIssued: number;
    canceledInvoicesWithTaxNumber: number;
    errorInvoice: number;
    invoicesByType: Map<string, number>;
    invoicesByTypeAndStatus: InvoicesByTypeAndStatus [];
    invoicesByBranchOfficeAndStatus: InvoicesByBranchOfficeAndStatus [];
    invoicesPerSeller: Map<string, number>;
}

export interface ItemBranche {
    id:number;
    itemName:string;
}

export interface ItemSelect {
    id?: string | number;
    itemName?: string;
}

export interface InvoicesByBranchOfficeAndStatus {
    branchOfficeName?:string;
    status?:string;
    count?:number;
}



export interface TablePdf {
    text?:string;
    fontSize?:number;
    bold?: boolean;
    alignment?:string;
    fillColor?:string;
    color?:string;
}

export interface InvoicesByTypeAndStatus {

    invoiceType?:string;
    status?:string;
    count?:number;

}

export interface BrancheOffices {
    id?:number;
    name?:string;
    address?:string;
    phone?:string;
    rtn?:string;
    fax?:string;
    pbx?:string;
    email?:string;
    acctCode?:string;
    fictitiousNumber?:string;
    idCompany?:number;
    idSystem?:number;
    wineryCode?:string;
    status?:number;
    created?:Date;
}

export interface BrancheOfficesResponse {
    data?: BrancheOffices [];
}

interface ContentBranches {
    content?: BrancheOffices [];
}

export interface DataGraph {
    label?: any;
    backgroundColor?: any;
    data?: any;
}

export interface ContentBar {
    pendiente?: number;
    autorizada?: number;
    emitida?: number;
    conNumeroFiscal?: number;
    anuladaSinEmitir?: number;
    anuldaConNumeroFiscal?: number;
    error?: number;
}

export interface ContentResponse {
    unauthorizedInvoices?: number;
    authorizedInvoices?: number;
    issuedInvoices?: number;
    invoicesWithTaxNumber?: number;
    canceledInvoicesWithoutIssued?: number;
    canceledInvoicesWithTaxNumber?: number;
    errorInvoice?: number;
}


/**
 * Interfaces que estructura la información que se va a visualizar en la data
 * 
 */
export interface DataGraph {

    invoiceGeneral?: InvoiceGeneral;
    invoiceType?: InvoiceType;
    invoiceTypeAndStatus?:InvoiceTypeAndStatus[];
    invoiceBrancheOfficesAndStatus?:InvoiceBrancheOfficesAndStatus[];
    invoicesPerSeller?: InvoicesPerSeller[];
}

interface InvoiceGeneral {
    unauthorizedInvoices?: number;
    authorizedInvoices?:number;
    issuedInvoices?:number;
    invoicesWithTaxNumber?:number;
    canceledInvoicesWithoutIssued?:number;
    canceledInvoicesWithTaxNumber?:number;
    errorInvoice?:number;
}

export interface InvoiceType {
    FC2?: number;
    FC1?: number;
    FC4?: number;
    FC3?: number;
}

// Para el tipo y estado
export interface InvoiceByTypeAndStatus {
    invoiceType?: string;
    status?: string;
    count?: number;
}

export interface InvoiceTypeAndStatus {
    type?: string;
    invoiceGeneral?: InvoiceGeneral;
}

// Para las Sucursales
export interface InvoicesByBranchOfficeAndStatus {
    branchOfficeName?: string;
    status?:string;
    count?:number;
}

export interface InvoiceBrancheOfficesAndStatus {
    brancheOffice?: string;
    invoiceGeneral?: InvoiceGeneral;
}

// Para vendedor
export interface InvoicesPerSeller {
    nameSeller?:string;
    quantity?:number;
}

// Bodega

export interface WareHousesResponse {
    code?:number;
    description?:string;
    data?: WareHouseModel[];
    error?: any[];
}

export interface WareHouseModel {
    id?:number;
    version?:number;
    code?:string;
    name?:string;
    address?:string;
    responsible?:string;
    status?:string;
    warehouseTypeId?:string;
    immediateBoss?:string;
    created?:Date;
}

/**
 * Está interfaces nos ayudará a 
 * contruir el payload de la petición
 * 
 */
export interface FilterModel {
    status?: number[];
    invoiceType?: string[];
    agencies?: string[];
    territories?: string[];
    startDate?:string;
    endDate?:string;
    seller?:string;
}