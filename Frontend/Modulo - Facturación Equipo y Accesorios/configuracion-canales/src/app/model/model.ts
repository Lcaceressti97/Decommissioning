/**
 * Modelo principal que se muestra en la tabla de la pantalla
 * y el cual se realizará el crud de la misma
 * 
 */
export interface ChannelModel {
    id?:number;
    name?:string;
    description?: string;
    statusStr?: string;
    status?:number;
    created?: Date;
    payUpFrontNumber?:number;
    nonFiscalNote?:number;
    reserveSerialNumber?:number;
    releaseSerialNumber?:number;
    inventoryDownload?:number;
    generateTrama?:number;
    logs?:number;
}