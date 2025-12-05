export interface NavegaPaymentEntity {
    id?: number;
    navegaCode: string;
    productCode: number;
    currency: string;
    paymentAmount: number;
    bank: number;
    bankAuthorization: number;
    exchangeRate: number;
    paymentDate: string;
    idDeaOrganization: number;
    ebsAccount: string;
    transactionSts: string;
}