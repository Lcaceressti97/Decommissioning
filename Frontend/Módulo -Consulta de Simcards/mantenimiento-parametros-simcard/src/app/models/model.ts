export interface CardPrepagoModel {
    id?:number;
    model?:string;
    lin?:string;
    description?:string;
    value?:number;
    act?:string;
    createdDate?: Date;
}


export interface CardPrepagoLoteModel {
    id?:number;
    serie?:number;
    rangeInitial?:string;
    quantity?:number;
    createdDate?: Date;
    dueDate?: Date;
    days?:number;
    value?:number;
}