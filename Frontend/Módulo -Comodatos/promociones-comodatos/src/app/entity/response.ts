import { PromotionModel } from "../model/model";

export interface PromotionResponse {
    code?:number;
    description?:string;
    data?:PromotionModel[];
    errors?:any[];
}