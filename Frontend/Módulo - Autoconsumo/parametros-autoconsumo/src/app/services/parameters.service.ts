import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { UtilsService } from './utils.service';
import { catchError, map } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ParametersService {


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
   * Método encargado de obtener todos los parametros
   * 
   * 
   */
  getParameters(): Observable<any> {
    return this.http
      .get(`${this.localUrl}parameter-autoconsumo`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los parámetros")
        )
      );
  }

  getParametersHistoricalById(idParameter: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}parameter-autoconsumo-hist/${idParameter}`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los parámetros")
        )
      );
  }

  postParameterHistorical(data: any): Observable<any> {
    return this.http
      .post(`${this.localUrl}parameter-autoconsumo-hist/add`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear el parámetro")
        )
      );
  }

  putParameters(id: any, data: any): Observable<any> {
    return this.http
      .put(`${this.localUrl}parameter-autoconsumo/update/${id}`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido actualizar el parámetro")
        )
      );
  }
}

