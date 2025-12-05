import { WareHouseModel } from "../model/model";

export interface WareHousesResponse {
    code?:number;
    description?:string;
    data?: WareHouseModel[];
    error?: any[];
}