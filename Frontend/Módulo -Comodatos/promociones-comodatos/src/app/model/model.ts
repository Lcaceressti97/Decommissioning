export interface PromotionModel {
    id?:number;
    promotionCode?:string;
    modelCode?:string;
    description?:string;
    corporate?:string;
    permanentValidity?:string;
    startDate?:Date;
    endDate?:Date;
    promotionsDetail?:PromotionDetailModel[];
    status?:number;
    statusName?:string;
}

export interface PromotionDetailModel {
    id?:number;
    idOptional?:number;
    promotionCode?:string;
    modelCode?:string;
    planValue?:number;
    monthsPermanence?:number;
    subsidyFund?:number;
    additionalSubsidy?:number;
    institutionalFunds?:number;
    coopsFund?:number;
    status?:number;
}