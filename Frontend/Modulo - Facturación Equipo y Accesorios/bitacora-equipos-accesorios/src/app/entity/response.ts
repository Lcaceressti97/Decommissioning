import { Logs, TypeErrorModel } from "../model/model";

export interface TypeErrorResponse {
    code?: number;
    description?: string;
    data?: TypeErrorModel[];
}

export interface LogsResponse {
    data: ContentLogsResponse;
}

interface ContentLogsResponse {
    content?: Logs[];
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


export interface LogsRangeDateResponse {
    code?: number;
    description?: string;
    data?: Logs[];
}