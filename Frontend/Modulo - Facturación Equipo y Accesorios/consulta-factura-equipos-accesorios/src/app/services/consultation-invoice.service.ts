import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { InvoiceEquipmentAccesoriesEntity } from '../entities/InvoiceEquipmentAccesoriesEntity';

@Injectable({
  providedIn: 'root'
})
export class ConsultationInvoiceService {

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

  getInvoiceEquipmentAccessories(): Observable<any> {
    return this.http
      .get(`${this.localUrl}invoiceequipmentaccessories`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de Facturación Equipo y Accesorios")
        )
      );
  }


  getBy(type = null, value = null): Observable<any> {
    return this.http
      .get<InvoiceEquipmentAccesoriesEntity[]>(`${this.localUrl}invoiceequipmentaccessories/see`, {
        observe: "response",
        headers: this.headers,
        params: {
          type: type ? type : "",
          value: value ? value : ""
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener la información")
        )
      );
  }

  sendEmail(emailRequest: any): Observable<any> {
    const url = `${this.localUrl}invoiceequipmentaccessories/sendemail`;
    return this.http.post(url, emailRequest, {
      headers: this.headers,
      responseType: 'text'  
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido enviar el correo")
      )
    );
  }
  
}
