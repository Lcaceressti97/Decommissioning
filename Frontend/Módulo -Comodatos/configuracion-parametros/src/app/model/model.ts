export interface ParametersModel{
    id?:number;
    idApplication?:number;
    parameterName?:string;
    description?:string;
    value?:string | number;
    created?:Date;
}