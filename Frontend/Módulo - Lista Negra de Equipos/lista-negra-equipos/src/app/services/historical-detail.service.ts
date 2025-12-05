import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { HistoricalDetailEntity } from '../entities/HistoricalDetailEntity';

@Injectable({
  providedIn: 'root'
})
export class HistoricalDetailService {

  private readonly localUrl = "/api/";
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


  getHistoricalDetails(): Observable<any> {
    return this.http
      .get(`${this.localUrl}historicaldetail`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener el histórico")
        )
      );
  }


  getHistoricalDetailById(id: number): Observable<any> {
    return this.http
      .get<any>(`${this.localUrl}historicaldetail/${id}`, {
        observe: "response",
        headers: this.headers
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener el histórico")
        )
      );
  }

  getHistoricalDetailsByEsn(esnImei = null): Observable<any> {
    return this.http
      .get<HistoricalDetailEntity[]>(`${this.localUrl}historicaldetail/details`, {
        observe: "response",
        headers: this.headers,
        params: {
          esnImei
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener la información")
        )
      );
  }


}
