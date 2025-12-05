import { EquipmentInsuranceModel } from "../models/EquipmentInsuranceModel";

export interface EquipmentInsuranceResponse {
    data?: ContentEquipmentInsurance;
}

interface ContentEquipmentInsurance {
    content?: EquipmentInsuranceModel[];
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

export interface EquipmentInsuranceControlResponse {
    code?: number;
    description?: string;
    data?: EquipmentInsuranceModel[];
    errors?: any[];
}