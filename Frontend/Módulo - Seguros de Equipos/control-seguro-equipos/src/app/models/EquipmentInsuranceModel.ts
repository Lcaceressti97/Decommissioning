export interface EquipmentInsuranceModel {
    id?: number;
    transactionCode?: string;
    userAs?: string;
    dateConsultation?: string;
    customerAccount?: string;
    serviceAccount?: string;
    billingAccount?: string;
    phoneNumber?: string;
    equipmentModel?: string;
    esn?: string;
    originAs?: string;
    inventoryTypeAs?: string;
    originTypeAs?: string;
    dateContract?: string;
    startDate?: string;
    endDate?: string;
    dateInclusion?: string;
    insuranceRate?: number;
    period?: number;
    insuranceRate2?: number;
    period2?: number;
    insuranceRate3?: number;
    period3?: number;
    insuranceStatus?: number | string;
    subscriber?: string;
}