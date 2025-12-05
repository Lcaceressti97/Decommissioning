import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class ComodatosService {

  // Props
  private readonly localUrl = "/apiComodatos/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }


  handleError(error: HttpErrorResponse, message: string = "") {


    if (error.status === 500 || error.status === 502 || error.status === 504) {
      this.utilService.showNotifyError(
        error.status,
        `${message}. Contacte al administrador del sistema.`
      );
    } else {
      this.utilService.showNotifyError(
        error.status, message
      );
    }


    return throwError(error);
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de la factura de los comodatos
* 
* @param id 
* @returns 
*/
  getComodatosAllPagination(page: any, size): Observable<any> {
    return this.http.get(`${this.localUrl}morringbilling/bypagination`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    });
  }

  /**
   * Método que consume un servicio rest para obtener
   * los parámetros principales del módulo Comodatos
   * 
   */
  configparametersById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}configparameters/searchById`, {
      observe: "response",
      headers: this.headers,
      params: {
        idApplication: id
      }
    });
  }


  /**
   * Método que consume un servicio rest el cual trae la
   * información de los Comodatos según el filtro
   * 
   */
  getComodatosByFilter(value: any, type: any): Observable<any> {
    return this.http.get(`${this.localUrl}morringbilling/findbyfilter/${value}/${type}`, {
      observe: "response",
      headers: this.headers,
    });
  }

  /**
   * Método que consume un servicio rest para actualizar el
   * Comodato
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putUpdateComodatosById(id:any, data:any): Observable<any>{
    return this.http.put(`${this.localUrl}/morringbilling/update/${id}`,data,{
      observe: 'response',
      headers: this.headers
    });
  }

  /**
   * Método que consume un servicio rest para obtener las lineas que están ya sea activas o
   * inactivas en el amarre.
   * 
   * @param id 
   * @returns 
   */
  getMooringById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}mooring/findby/idmooringbilling/${id}`, {
      observe: "response",
      headers: this.headers,
    });
  }

  /**
   * Método que consume un servicio rest para obtener la
   * cuenta del cliente y cuenta facturación
   * 
   * @param subscribe 
   * @returns 
   */
  getInfoSubscribe(subscribe: any): Observable<any> {
    return this.http.get(`${this.localUrl}customer/info/subscriber/${subscribe}`, {
      observe: "response",
      headers: this.headers,
    });
  }


  /**
   * Método que consume un servicio rest para registrar los
   * amarres.
   * 
   * @param subscribe 
   * @returns 
   */
  postMooring(id: any, mooring: any): Observable<any> {
    return this.http.post(`${this.localUrl}mooring/create/idmooringbilling/${id}`, mooring,{
      observe: "response",
      headers: this.headers,
    });
  }

  /**
   * Método que consume un servicio para actualizar el amarre
   * de las lineas
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putUpdateMooringById(id:any, data:any): Observable<any>{
    return this.http.put(`${this.localUrl}/mooring/update/${id}`,data,{
      observe: 'response',
      headers: this.headers
    });
  }


  /**
   * Servicios de Control y Permisos de Cancelación
   * 
   */


  /**
   * Método que consume un servicio rest para obtener el
   * permiso de cancelar permisos
   * 
   */
  getPermissionCancelByUserName(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}cancellation/permission/username/${username}`, {
      observe: "response",
      headers: this.headers,
    });
  }


  /**
   * Método que consume un servicio rest para registrar el
   * control de las cancelaciones
   * 
   * @param subscribe 
   * @returns 
   */
  postControlCancellation(data: any): Observable<any> {
    return this.http.post(`${this.localUrl}control/cancellation/create`, data,{
      observe: "response",
      headers: this.headers,
    });
  }


    /**
   * Método que consume un servicio rest para validar si el suscriptor está amarrado
   * o está activo en la tabla padre
   * 
   * 
   */
    getValidateSubcriber(subcriber: any): Observable<any> {
      return this.http.get(`${this.localUrl}mooring/validate/subscriber/${subcriber}`, {
        observe: "response",
        headers: this.headers,
      });
    }

    /**
     * Método que consume un servicio rest para obtener los datos de la factura del comodato
     * si existe o no
     * 
     * @param numberDei 
     * @returns 
     */
    getValidateComodatoBilling(numberDei: any): Observable<any> {
      return this.http.get(`${this.localUrl}billing/invoicebynumberdei/${numberDei}`, {
        observe: "response",
        headers: this.headers,
      });
    }


}
