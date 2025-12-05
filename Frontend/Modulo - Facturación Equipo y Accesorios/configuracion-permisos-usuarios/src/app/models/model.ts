export interface UserModel {
    id?:number;
    usuario?:string;
    email?:string;
    nombre?:string;
    status?:string | number | boolean;

}

export interface User {
    id?:number;
    name?:string;
    userName?:string;
    email?:string;
    status?:number;
    created?:Date;
}

export interface TypeUser {
    id?:number;
    typeUser?:string;
    description?:string;
    status?:number;
    created?:Date;
}

export interface UserWithOutPermission {
    id?:number;
    name?:string;
    userName?:string;
    email?:string;
    status?:number;
    created?:Date;
    typeUser?:number;
}


export interface ControlUserPermissions {
    id?:number;
    idUser?:number;
    name?:string;
    userName?:string;
    email?:string;
    generateBill?:string | boolean;
    issueSellerInvoice?:string | boolean;
    authorizeInvoice?:string | boolean;
    assignDiscount?:string | boolean;
    typeUser?:number;
    created?:Date;
}

export interface ControlUserPermissionsCancellation {
    id?:number;
    idUser?:number;
    userName?:string;
    permitStatus?:string | boolean;
    created?:Date;
}


/**
 * Interfaces que contendrá todos los permisos del usuario
 * 
 */
export interface UserPermissions {
    controlUserPermissions?:ControlUserPermissions;
    userPermissionsWithFn?:ControlUserPermissionsCancellation;
    controlUserPermissionsWithOutFn?:ControlUserPermissionsCancellation;
    user?:User;
}