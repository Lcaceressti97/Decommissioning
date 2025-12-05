import { HttpHeaders, HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { DeductibleRateModel } from '../models/model';
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class DeductibleRatesService {

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
  * Método que consume un servicio REST para obtener la información de las tasas deducibles
  * 
  * @returns 
  */
  getDeductibleRates(): Observable<any> {
    return this.http.get(`${this.localUrl}deductible-rates`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err) => this.handleError(err, "No se ha podido cargar los datos"))
    );
  }


  /**
 * Método que consume un servicios rest para crear
 * la tasa deducible
 * 
 * @param data 
 * @returns 
 */
  postDeductibleRate(data: DeductibleRateModel): Observable<any> {
    return this.http.post(`${this.localUrl}deductible-rates/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear la tasa deducible"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * la tasas deducible
   * 
   * @param data 
   * @returns 
   */
  putDeductibleRate(id: any, data: DeductibleRateModel): Observable<any> {
    return this.http.put(`${this.localUrl}deductible-rates/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar la tasas deducible"))
    );
  }

  /**
 * Método que consume un servicio REST para obtener la información de los motivos
 * 
 * @returns 
 */
  getReason(): Observable<any> {
    return this.http
      .get(`${this.localUrl}reasons`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de motivos")
        )
      );
  }
}
