import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable, of } from 'rxjs';
import { UtilsService } from './utils.service';
import { ReportModel } from '../models/ReportModel';
import { MOCK_REPORT_RESPONSE } from '../utils/mock-report';
import { catchError, map } from "rxjs/operators";
import { ReportResponse } from '../entities/response';

@Injectable({
  providedIn: 'root'
})
export class SelfControlReportService {


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
   * Método encargado de obtener todos la administracion de ofertas 
   * 
   * 
   */
  getReport1(): Observable<any> {
    return this.http
      .get(`${this.localUrl}navegabank`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener las ofertas")
        )
      );
  }

  getReport(): Observable<ReportResponse> {
    return of(MOCK_REPORT_RESPONSE);
  }


}
