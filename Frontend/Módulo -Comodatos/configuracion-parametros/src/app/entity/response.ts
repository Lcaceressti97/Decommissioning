import { ParametersModel } from "../model/model";

export interface ParametersResponse {
    code?:number;
    description?:string;
    data?: ParametersModel[];
}