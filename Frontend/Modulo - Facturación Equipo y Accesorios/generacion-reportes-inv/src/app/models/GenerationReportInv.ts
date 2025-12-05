export interface GenerationReportInv {
    id?: number;
    fileName: string;
    description: string;
    reportInv: string;
    status: number;
    created: string;
}

export interface ReportInvPagesResponse {
    data?: ReportInvPermissions;
}

interface ReportInvPermissions {
    content?: GenerationReportInv[];
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
