import { ReleaseSeriesLogModel } from "../models/model";

/**
 * BSIM_RELEASE_SERIAL_LOG
 * 
 * Esta interfaz sirve para mapear el response del servicio rest
 * 
 */
export interface ReleaseSeriesLogResponse {
    code: number;
    description: string;
    data: ReleaseSeriesLogModel[];
    errors: any[];
}

export interface ReleaseSeriesLogPagesResponse {
    data?:ContentModelPage
}

interface ContentModelPage {
    content?: ReleaseSeriesLogModel[];
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