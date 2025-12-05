export interface BillingServicesModel {
    id?:number;
    serviceCode?:number;
    serviceName?:string;
    totalQuantity?:number;
    creationUser?:number;
    creationDate?: Date;
    modificationUser?: string;
    modificationDate?: Date;
    status?:number | string;
    statusCode?:number | string;
}