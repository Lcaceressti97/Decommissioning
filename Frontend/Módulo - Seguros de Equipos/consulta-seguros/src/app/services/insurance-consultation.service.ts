import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class InsuranceConsultationService {

  private readonly localUrl = "/apiEquipmentInsurance/";
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

  /**
* Método que realiza la consulta por modelo
* 
* @param id 
* @returns 
*/
  getMonthlyFeesByModel(model: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}insurance-rates/monthly-fees/${model}`, {
        observe: "response",
        headers: this.headers,
      })
  }
}
