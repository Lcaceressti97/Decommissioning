export interface MooringBillingModel {
    id?:number;
    correlativeCmd?:number;
    correlativeMooringCmd?:number;
    subscriberId?:string;
    monthsOfPermanence?:number;
    cmdStatus?: string;
    userName?:string;
    supervisorUser?:string;
    userMooring?:string;
    userCancelled?:string;
    dateOfAdmission?: Date;
    dueDate?:Date;
    invoiceLocation?:number;
    invoiceSubLocity?:number;
    invoiceType?:string;
    invoiceReading?: string;
    invoiceNumber?:string;
    inventoryType?:string;
    inventoryModel?:string;
    teamSeries?:string;
    phoneCost?:number;
    phoneDiscount?:number;
    vac?:number;
    mooring?:number | string;
    promotion?:string;
    transactionId?:string;
    customerAccount?:string;
    serviceAccount?:string;
    billingAccount?:string;
    observations?:string;
    createDate?: Date;
}

export interface ParametersModel{
    id?:number;
    idApplication?:number;
    parameterName?:string;
    description?:string;
    value?:string | number;
    created?:Date;
}

export interface MooringModel {
    id?:number;
    idMooringBilling?:number;
    customerAccount?:string;
    billingAccount?:string;
    subscriberId?:string;
    userName?:string;
    dateOfEntry?:Date | string;
    checkInTime?:string;
    unmooringUser?:string;
    unmooringDate?:Date | string;
    mooringStatus?:number;
    created?:Date;
}
