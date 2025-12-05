export interface SafeControlEquipment {
    id?: number;
    subscriberId: string;
    esn: string;
    customerAccount: string;
    serviceAccount: string;
    billingAccount: string;
    phone: string;
    phoneModel: string;
    originPhone: string;
    inventoryType: string;
    originType: string;
    dateStartService: string;
    dateInit: string;
    dateEnd: string;
    dateInclusion: string;
    monthlyFee: number;
    currentPeriod: number;
    insuranceStatus: string;
    userAs400: string;
    dateTransaction: string;
    operationProgram: string;
    period2: number;
    monthlyFee2: number;
    period3: number;
    monthlyFee3: number;
}