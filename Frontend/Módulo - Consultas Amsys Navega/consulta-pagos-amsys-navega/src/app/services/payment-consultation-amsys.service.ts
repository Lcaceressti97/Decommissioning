import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentConsultationAmsysService {

  private readonly localUrl = "/apiInquiriesAmsysNavega/";
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

  getAllPayments(): Observable<any> {
    return this.http
      .get(`${this.localUrl}navegapayments`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los bancos")
        )
      );
  }


  getPaymentsByDateRange(initDate: any, ednDate: any): Observable<any> {
    let params = new HttpParams();
    params = params.set('startDate', initDate);
    params = params.set('endDate', ednDate);

    return this.http.get(`${this.localUrl}navegapayments/daterange`, {
      observe: "body",
      params: params
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros de pagos")
      )
    );
  }


  cancelPayment(id: number): Observable<any> {
    const url = `${this.localUrl}navegapayments/cancellationpayments`;
    const body = { id: id };

    return this.http.post(url, body).pipe(
      catchError((err) => this.handleError(err, `Error al cancelar el pago`))
    );
  }

}
