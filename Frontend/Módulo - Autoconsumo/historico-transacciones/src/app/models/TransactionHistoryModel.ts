export interface TransactionHistoryModel {
    id?: number;
    created?: string;
    status?: number | string;
    cycle?: string;
    feeItemAmount?: string;
    discountedAmount?: string;
    extAttribute?: string;
    productId?: string;
    chargeCodeType?: string;
    priIdentOfSubsc?: string;
    message?: string;
    dataDate?: string;
    request?: string;
    response?: string;

    acctCode?: string;
    subscriberOrigin?: number | string;
    itemName?: string;
    item?: string;
    businessUnit?: string;

}