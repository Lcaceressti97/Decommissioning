export interface TypeErrorModel {
    id?:number;
    typeError?:number;
    description?:string;
    created?: Date;
}

export interface Logs {
    id?:number;
    created?: Date;
    typeError?:number | string;
    message?: string;
    reference?:string;
    srt?:string;
    url?: string;
    userCreate?: string;
    executionTime?: string;
}

export interface ItemSelect {
    id?:number;
    itemName?:string;
}