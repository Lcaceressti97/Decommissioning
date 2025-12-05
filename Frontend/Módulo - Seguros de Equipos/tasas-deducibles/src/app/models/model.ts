/**
 * Modelo principal que se muestra en la tabla de la pantalla
 * y el cual se realizará el crud de la misma
 * 
 */
export interface DeductibleRateModel {
    id?: number;
    model?: string;
    description?: string;
    firstClaim?: string;
    secondClaim?: string;
    thirdClaim?: string;
    reason?: number;
    reasonDescription?: number;
    status?: string;
    created?: Date;
}

export interface ReasonModel {
    id?: number;
    reason?: string;
    description?: string;
    status?: number;
    createdDate?: string;
}
