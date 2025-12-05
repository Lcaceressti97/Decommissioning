import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ReasonModel } from '../models/model';

@Injectable({
  providedIn: 'root'
})
export class ReasonsService {
  private readonly localUrl = "/apiEquipmentInsurance/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  // Handler que controla los codeHttp

  handleError(error: HttpErrorResponse, message: string = "") {

    //console.log(error);

    if (error.status === 500 || error.status === 504 || error.status === 400) {
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


  constructor(private http: HttpClient, private utilService: UtilService) { }


  // Methods Services Rest
  /**
  * Método que consume un servicio REST para obtener la información de las razones
  * 
  * @returns 
  */
  getReasons(): Observable<any> {
    return this.http.get(`${this.localUrl}reasons`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err) => this.handleError(err, "No se ha podido cargar los datos"))
    );
  }


  /**
 * Método que consume un servicios rest para crear
 * una razón
 * 
 * @param data 
 * @returns 
 */
  postReason(data: ReasonModel): Observable<any> {
    return this.http.post(`${this.localUrl}reasons/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el motivo"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * la razón
   * 
   * @param data 
   * @returns 
   */
  putReason(id: any, data: ReasonModel): Observable<any> {
    return this.http.put(`${this.localUrl}reasons/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el motivo"))
    );
  }


}
 