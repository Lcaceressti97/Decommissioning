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