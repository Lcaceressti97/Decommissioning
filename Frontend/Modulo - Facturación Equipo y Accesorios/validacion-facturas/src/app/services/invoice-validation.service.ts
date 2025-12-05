import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class InvoiceValidationService {

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

  updateDocumentNo(id: number, documentNo: string): Observable<any> {
    const queryParams = new HttpParams().set('documentNo', documentNo);

    return this.http.patch(`${this.localUrl}invoiceequipmentaccessories/updateDocumentNo/${id}`, null, {
      headers: this.headers,
      params: queryParams
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar los campos de la factura")
      )
    );
  }

  updateSingleClient(id: number, diplomaticCardNo: string): Observable<any> {
    const queryParams = new HttpParams().set('diplomaticCardNo', diplomaticCardNo);

    return this.http.patch(`${this.localUrl}invoiceequipmentaccessories/updateSingleClient/${id}`, null, {
      headers: this.headers,
      params: queryParams
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar los campos de la factura")
      )
    );
  }

  updateCorporateClient(id: number, correlativeOrdenExemptNo: string, correlativeCertificateExoNo: string): Observable<any> {
    const queryParams = new HttpParams()
      .set('correlativeOrdenExemptNo', correlativeOrdenExemptNo)
      .set('correlativeCertificateExoNo', correlativeCertificateExoNo);

    return this.http.patch(`${this.localUrl}invoiceequipmentaccessories/updateCorporateClient/${id}`, null, {
      headers: this.headers,
      params: queryParams
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar los campos de la factura")
      )
    );
  }

}
