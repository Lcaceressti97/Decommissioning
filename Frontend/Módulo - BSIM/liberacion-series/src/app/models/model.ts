export interface SerialNumberEntry {
    serialNumber: string;
}

export interface ReleaseSeriesRequest {
    releaseType: string;
    inventoryType: string;
    itemCode: string;
    warehouseCode: string;
    subWarehouseCode: string;
    serialNumberList: SerialNumberEntry[];
}

export interface ReleaseDetail {
    id: number;
    inventoryType: string;
    itemCode: string;
    warehouseCode: string;
    subWarehouseCode: string;
    serialNumber: string;
    resultMessage: string;
    status: string;
    createdAt: string;
}

export interface ReleaseSeriesLogModel {
    id: number;
    releaseType: string;
    totalSeries: number;
    successfulReleases: number;
    failedReleases: number;
    fileName: string | null;
    status: string;
    createdAt: string;
    details: ReleaseDetail[];
}