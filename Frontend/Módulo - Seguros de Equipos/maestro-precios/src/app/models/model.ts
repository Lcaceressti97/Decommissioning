/**
 * Modelo principal que se muestra en la tabla de la pantalla
 * y el cual se realizará el crud de la misma
 * 
 */
export interface PriceMasterModel {
    id?: number;
    inventoryType?: string;
    model?: string;
    baseCost?: number;
    factorCode?: number;
    price?: number;
    userCreated?: string;
    screen?: string;
    createdDate?: Date;
    currency?: string;
    lempirasPrice?: number;
    temporaryCost?: number;
}