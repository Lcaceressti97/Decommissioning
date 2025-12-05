import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilsService } from './utils.service';

@Injectable({
  providedIn: 'root'
})
export class BalanceInquiryAmsysService {

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

  getNavegaBalances(page: number, size: number): Observable<HttpResponse<any>> {
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());

    return this.http
      .get(`${this.localUrl}navegabalances`, {
        observe: 'response',
        headers: this.headers,
        params,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, 'No se ha podido obtener los registros de saldos navega')
        )
      );
  }


  getAmnetBalances(page: number, size: number): Observable<HttpResponse<any>> {
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());

    return this.http
      .get(`${this.localUrl}amnetbalances`, {
        observe: 'response',
        headers: this.headers,
        params,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, 'No se ha podido obtener los registros de saldos amnet')
        )
      );
  }



  getDetailOptions(): Observable<any> {
    const url = `${this.localUrl}navegabalances/tablenames`; // Se utiliza el URL para 'Navega' por defecto, ya que el tipo ya no se valida

    return this.http.get(url, { headers: this.headers })
      .pipe(
        catchError((err) =>
          this.handleError(err, 'No se ha podido obtener las opciones del detalle')
        )
      );
  }


  getBalanceDetail(tableName: string): Observable<any> {
    const url = `${this.localUrl}navegabalances/tabledata?tableName=${tableName}`;

    return this.http.get(url, { headers: this.headers })
      .pipe(
        catchError((err) =>
          this.handleError(err, 'No se ha podido obtener los detalles de saldos')
        )
      );
  }


}
