import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ExemptInvoiceApprovalsEntity } from '../entities/ExemptInvoiceApprovalsEntity';
import { InvoiceEquipmentAccesoriesEntity } from '../entities/InvoiceEquipmentAccesoriesEntity';
@Injectable({
  providedIn: 'root'
})
export class ApprovalControlService {


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

  getInvoiceEquipmentAccessoriesExo(): Observable<any> {
    return this.http
      .get(`${this.localUrl}invoiceequipmentaccessories/exonerated`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de Facturación Equipo y Accesorios Exonerados")
        )
      );
  }

  getInvoicesByUserAndPermissions(approvedUser = null): Observable<any> {
    return this.http
      .get<InvoiceEquipmentAccesoriesEntity[]>(`${this.localUrl}invoiceequipmentaccessories/invoiceApproval`, {
        observe: "response",
        headers: this.headers,
        params: {
          approvedUser: approvedUser ? approvedUser : ""
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener la información")
        )
      );
  }

  saveExemptInvoiceApprovals(exemptInvoiceApprovals: ExemptInvoiceApprovalsEntity) {
    return this.http
      .post(`${this.localUrl}exemptinvoiceapprovals/add`, exemptInvoiceApprovals, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido realizar la aprobación")
        )
      );
  }

  
  updateStatusExoTax(id: number): Observable<any> {

    return this.http.patch(`${this.localUrl}invoiceequipmentaccessories/updateStatusExoTax/${id}`, null, {
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar los campos de la factura")
      )
    );
  }

    /**
 * Método que consume un servicio REST para obtener la información de las sucursales
 * 
 * @returns 
 */
    getBranches(page:any, size:any): Observable<any> {
      return this.http.get(`${this.localUrl}branchoffices`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      }).pipe(
        catchError((err)=> this.handleError(err,"No se ha podido cargar los datos"))
      );
    }
}
