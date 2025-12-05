import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class ReportGenerationService {

  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
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

  getReportGenerationInv(page: any, size: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}generationreportinv`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los reportes INV")
        )
      );
  }
}
