import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class SafeControlEquipmentService {


  private readonly localUrl = "/apiEquipmentInsurance/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {
    this.utilService.showNotification(
      2,
      `${message}. Contacte al administrador del sistema.`
    );
    return throwError(error);
  }


  // Methods

  /**
   * Método encargado de obtener todos los seguros de equipos
   * 
   * 
   */
  getEquipmentInsurance(page?: any, size?: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}equipment-insurance-control`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los seguros")
        )
      );
  }


  postEquipmentInsurance(data: any): Observable<any> {
    return this.http
      .post(`${this.localUrl}equipment-insurance-control/add`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear el seguro")
        )
      );
  }

  putEquipmentInsurance(id: any, data: any): Observable<any> {
    return this.http
      .put(`${this.localUrl}equipment-insurance-control/update/${id}`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido actualizar el seguro")
        )
      );
  }

  /**
* Método que realiza la consulta por imei
* 
* @param id 
* @returns 
*/
  getEquipmentInsuranceByEsn(esn: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}equipment-insurance-control/esn/${esn}`, {
        observe: "response",
        headers: this.headers,
      })
  }
}
