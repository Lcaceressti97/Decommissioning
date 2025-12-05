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
* Método que consume un servicio rest el cual trae la
* información de los Comodatos según el filtro
* 
*/
  getComodatosByConsult(value: any, type: any): Observable<any> {
    return this.http.get(`${this.localUrl}morringbilling/findbyconsult/${value}/${type}`, {
      observe: "response",
      headers: this.headers,
    });
  }

}
