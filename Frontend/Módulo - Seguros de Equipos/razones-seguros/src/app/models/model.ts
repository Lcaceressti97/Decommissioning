/**
 * Modelo principal que se muestra en la tabla de la pantalla
 * y el cual se realizará el crud de la misma
 * 
 */
export interface ReasonModel {
    id?: number;
    reason?: string;
    description?: string;
    status?: string;
    createdDate?: Date;
}