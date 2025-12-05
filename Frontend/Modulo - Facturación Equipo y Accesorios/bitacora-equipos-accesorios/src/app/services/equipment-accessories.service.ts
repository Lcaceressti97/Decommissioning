import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class EquipmentAccessoriesService {

  // Props
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
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
        error.status
      );
    }


    return throwError(error);
  }



  // Service Rest TABLE: MEA_TYPE_ERROR
  /*
* Método encargado de realizar una petición http para traer la info
* de todos los tipos de error
* 
* @param id 
* @returns 
*/
  getErrorTypeControl(): Observable<any> {
    return this.http.get(`${this.localUrl}errortypecontrol`, {
      observe: "response",
      headers: this.headers,
    });
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de todos los log un máximo de 2000 registros más recientes
* 
* @param id 
* @returns 
*/
  getLogsPagination(page: number = 0, size: number = 10,): Observable<any> {
    return this.http.get(`${this.localUrl}logs`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page.toString(),
        size: size.toString(),
      }
    });
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de todos los log por un rango de fecha
* 
* @param id 
* @returns 
*/
  getLogsRangeDate(page: number = 0, size: number = 10, startDate: any, endDate: any): Observable<any> {
    return this.http.get(`${this.localUrl}logs/daterange`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page.toString(),
        size: size.toString(),
        startDate: startDate,
        endDate: endDate
      }
    });
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de todos los log por el tipo de error
* 
* @param id 
* @returns 
*/
  getLogsByTypeError(page: number = 0, size: number = 10,typeError: any): Observable<any> {
    return this.http.get(`${this.localUrl}logs/searchbytypeerror`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page.toString(),
        size: size.toString(),
        typeError: typeError,
      }
    });
  }

}
