
export interface ArtModel {
    id?:string;
    artNumber?:number;
    artDescription?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}

export interface EmailModel {
    id?:number;
}

export interface OrderControlModel {
    id?:number;
    idSupplier?:number;
    supplierName?:string;
    noOrder?:number;
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
    email?:string;
}

export interface TypeModel {
    id?:number;
    typeNumber?:string;
    typeDescription?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}

export interface GraphicModel {
    id?:number;
    graphicRef?:string;
    graphicDescription?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}

export interface ModeloModel {
    id?:number;
    simModel?:number;
    modelDescription?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}


export interface VersionModel {
    id?:number;
    idModel?:number | string;
    modelo?:string;
    version?:string;
    versionSize?:string;
    versionDescription?:string;
    capacity?:string;
    versionDetail?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}

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
    initialIccd?:number;
    status?: number | string;
    statusCode?: number | string;
    createdDate?:Date;
    key?: string;
}

export interface CustomerModel {
    id?:number;
    code?:number;
    hlr?:string;
    clientName?:string;
    descriptionHrl?:string;
    status?: number | string;
    statusCode?: number | string;
    createdDate?: Date;
}

export interface OrderControlDetailModel {
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

export interface Files {
    orderFile?: string;
    orderDetailFile?: string;
}

export interface ParamSimcardModel {
    id?:number;
    idApplication?:number;
    parameterType?:string;
    parameterName?:string;
    parameterDescription?:string;
    parameterValue?:string;
    stateCode?:number;
    created?:Date;
}