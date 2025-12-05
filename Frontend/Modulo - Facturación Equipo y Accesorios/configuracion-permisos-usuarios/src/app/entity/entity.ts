import { ControlUserPermissions, ControlUserPermissionsCancellation, TypeUser, UserWithOutPermission } from "../models/model";
// No está en uso
export interface UserPermissionsResponse {
    code?:number | string;
    description?:string;
    data?: ContentUser;
    errors?: any[];
}

export interface TypeUserResponse {
    code?:number | string;
    description?:string;
    data?: TypeUser[];
    errors?: any[];
}

interface ContentUser {
    content?:ControlUserPermissions[];
}

export interface UserPermissionsCancellationResponse {
    code?:number | string;
    description?:string;
    data?: ControlUserPermissionsCancellation[];
    errors?: any[];
}

// No esta en uso
export interface UserPermissionsResponse {
    content?:ControlUserPermissions[]
}

export interface UserWithOutPermissionsResponse {
    code?:number | string;
    description?:string;
    data?: UserWithOutPermission[];
    errors?: any[];
}

export interface UserControlPermissionsResponse {
    code?:number;
    description?:string;
    data?:ControlUserPermissions;
}

export interface ControlPermissionsPagesResponse {
    data?: ContentControlPermissions;
}

interface ContentControlPermissions {
    content?: ControlUserPermissions[];
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
    first: boolean;
    last: boolean;
    empty: boolean;
    numberOfElements: number;
    pageable: {
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        unpaged: boolean;
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        };
    };
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
}

export interface ControlUserPermissionsConsult {
    code?:number;
    description?:string;
    data?: ControlUserPermissions[];
    errors?: any[];
}