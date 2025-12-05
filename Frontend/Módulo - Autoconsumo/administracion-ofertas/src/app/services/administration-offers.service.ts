import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { UtilsService } from './utils.service';
import { catchError, map } from "rxjs/operators";
import { AdministrationOffersResponse } from '../entities/response';
import { MOCK_ADMINISTRATION_OFFERS_RESPONSE } from '../utils/mock-administration-offers';

@Injectable({
  providedIn: 'root'
})
export class AdministrationOffersService {

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
  getAdministrationOffers(): Observable<any> {
    return this.http
      .get(`${this.localUrl}charge-code-autoconsumo`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener las ofertas")
        )
      );
  }

  getChangeCodeHistoricalByChargeId(chargeCodeId: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}charge-code-historical/${chargeCodeId}`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener las ofertas")
        )
      );
  }

  getOfferingInfo(offeringId: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}offering-info/${offeringId}`, {
        observe: "response",
        headers: this.headers,
      })
  }


  postAdministrationOffers(data: any): Observable<any> {
    return this.http
      .post(`${this.localUrl}charge-code-autoconsumo/add`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear la ofertas")
        )
      );
  }

  putAdministrationOffers(id: any, data: any): Observable<any> {
    return this.http
      .put(`${this.localUrl}charge-code-autoconsumo/update/${id}`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido actualizar la ofertas")
        )
      );
  }

  postChangeCodeHistorical(data: any): Observable<any> {
    return this.http
      .post(`${this.localUrl}charge-code-historical/add`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear el historico de la oferta")
        )
      );
  }

}
