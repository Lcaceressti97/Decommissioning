export interface ProviderModel {
    id?:number;
    supplierNo?:number;
    supplierName?: string;
    address?:string;
    phone?:string;
    postalMail?:string;
    emailSupplier?: string;
    email?:string;
    baseFile?:string;
    subject?:string;
    textEmail?:string;
    initialIccd?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?:Date;
    key?:string;
}