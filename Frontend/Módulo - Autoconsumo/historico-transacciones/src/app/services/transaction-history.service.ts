import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable, of } from 'rxjs';
import { UtilsService } from './utils.service';
import { catchError, map } from "rxjs/operators";
import { TransactionHistoryResponse } from '../entities/response';

@Injectable({
  providedIn: 'root'
})
export class TransactionHistoryService {

  // Props
  private readonly localUrl = "/apiSelfconsumption/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilsService) { }

  handleError(error: HttpErrorResponse, message: string = "") {
    this.utilService.showNotification(
      2,
      `${message}. Contacte al administrador del sistema.`
    );
    return throwError(error);
  }

  // Methods

  /**
   * Método encargado de obtener todos la administracion de ofertas 
   * 
   * 
   */
  getTransactionHistory(): Observable<any> {
    return this.http
      .get(`${this.localUrl}autoconsumo-temp`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener el historico de autoconsumos")
        )
      );
  }

  /**
   * Método que trae los datos por un rango de fecha
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  getTransactionHistoryByDateRange(initDate: any, ednDate: any): Observable<any> {
    return this.http.get(`${this.localUrl}autoconsumo-temp/daterange`, {
      observe: "response",
      headers: this.headers,
      params: {
        startDate: initDate,
        endDate: ednDate
      }
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener el historico de autoconsumos")
      )
    );
  }

    /**
   * Método encargado de obtener la data ya sea por
   * Cauenta de facturacion, telefono o ciclo
   * 
   */
    getTransactionHistoryByFilter(value:any, type:any): Observable<any> {
      return this.http
        .get(`${this.localUrl}autoconsumo-temp/findbyfilter/${value}/${type}`, {
          observe: "response",
          headers: this.headers,
        });
    }
}