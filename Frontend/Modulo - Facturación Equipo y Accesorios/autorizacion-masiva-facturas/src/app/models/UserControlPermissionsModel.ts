/**
 * Se usa para mapear los permisos del usuario
 * 
 */
export interface ControlUserPermissions {
    id?:number;
    idUser?:number;
    userName?:string;
    generateBill?:string;
    issueSellerInvoice?:string;
    authorizeInvoice?:string;
    typeUser?:number;
    created?:Date;
}

export interface ControlAuthEmission {
    idPrefecture?:number;
    typeApproval?:number | string;
    description?:string;
    userCreate?:string;
    status?:number;
    idBranchOffices?:number;
}

export interface ControlCancellation {
    idPrefecture?:number;
    typeCancellation?:number | string;
    description?:string;
    userCreate?:string;
    status?:number;
}