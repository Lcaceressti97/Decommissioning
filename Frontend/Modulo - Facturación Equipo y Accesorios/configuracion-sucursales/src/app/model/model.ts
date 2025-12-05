export interface Branche {
    id?:number;
    idPoint?:number;
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
    territory?:number;
    wareHouseManager?:string;
    wineryName?:string;

}

export interface UserBranche {
    idUser?:number;
    idBranchOffices?:number;
    status?:number;
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

export interface User {
    id?:number;
    name?:string;
    userName?:string;
    email?:string;
    status?:number;
    created?: Date;
}

export interface ItemSelect {
    id?: string | number;
    itemName?: string;
    wareHouseManager?: string;
}