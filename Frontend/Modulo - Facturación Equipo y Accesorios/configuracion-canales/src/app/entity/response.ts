import { ChannelModel } from "../model/model";

/**
 * Interface encargada de mapear la respuesta
 * de la api si da un codeHttp = 200
 * 
 */
export interface ChannelResponse {
    code?:number;
    description?:string;
    data?: ChannelModel[];
    errors?: any[];
}