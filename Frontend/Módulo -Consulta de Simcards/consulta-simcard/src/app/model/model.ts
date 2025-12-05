export interface OrderControlModel {
    id?:number;
    idSupplier?:number;
    supplierName?:string;
    noOrder?:string;
    userName?:string;
    customerName?:string;
    initialImsi?:string;
    initialIccd?:string;
    orderQuantity?:number;
    fileName?:string;
    orderFile?:string;
    orderDetailFile?:string;
    createdDate?:Date;
    customer?:string;
    hlr?:string;
    batch?:string;
    key?:string;
    type?:string;
    art?:string;
    graphic?:string;
    model?:string;
    versionSize?:string;
    status?:string;
    statusCode?:string;
}

export interface OrderDetailModel {
    id?:number;
    idOrderControl?:number;
    icc?:string;
    imsi?:string;
    imsib?:string;
    ki?:string;
    pin1?:string;
    puk1?:string;
    pin2?: string;
    puk2?: string;
    adm1?: string;
    adm2?: string;
    adm3?: string;
    acc?: string;
    createdDate?: Date;
}

export interface DataModel {
    orderControl: OrderControlModel;
    details: OrderDetailModel;
}