export interface CustomerModel {
    id?:number;
    code?:number;
    hlr?:number;
    clientName?:string;
    descriptionHrl?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}

