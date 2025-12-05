export interface ImeiControlFileEntity {
    id?: number;
    transactionId: string;
    phone: string;
    imei: string;
    imsi: string;
    status: number;
    createdDate: string;
}