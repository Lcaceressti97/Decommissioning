import { DataModel } from "../model/model";

export interface DataResponse {
    code?:number;
    description?: string;
    data?: DataModel;
    errors?: any[];
}