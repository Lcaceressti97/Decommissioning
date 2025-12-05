import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilsService } from './utils.service';

@Injectable({
  providedIn: 'root'
})
export class BankConfigurationService {

  // Props
  private readonly localUrl = "/apiInquiriesAmsysNavega/";
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
   * Método encargado de obtener todos los bancos 
   * 
   * 
   */
  getBank(): Observable<any> {
    return this.http
      .get(`${this.localUrl}navegabank`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los bancos")
        )
      );
  }

  postBank(data: any): Observable<any> {
    return this.http
      .post(`${this.localUrl}navegabank/add`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear el banco")
        )
      );
  }

  putBank(id: any, data: any): Observable<any> {
    return this.http
      .put(`${this.localUrl}navegabank/update/${id}`, data, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido actualizar el banco")
        )
      );
  }
}
