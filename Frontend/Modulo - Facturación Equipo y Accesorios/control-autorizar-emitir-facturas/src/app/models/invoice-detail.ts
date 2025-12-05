export interface InvoiceDetail {
    id?:number;
    idPrefecture?:number;
    model?:string;
    description?:string;
    amount?:number;
    unitPrice?:number;
    total?:number;
    serieOrBoxNumber?:number;
    status?:number;
    created?: Date;
}