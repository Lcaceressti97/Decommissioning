import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class SimCardService {

  // Props
  private readonly localUrl = "/apiSimcardInquiry/";
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

  // Methods

  getCustomer(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardcustomer`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los clientes")
        )
      );
  }

  postClient(data:any): Observable<any> {
    return this.http
      .post(`${this.localUrl}simcardcustomer/add`, data,{
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear el cliente")
        )
      );
  }

  putClient(id:any,data:any): Observable<any> {
    return this.http
      .put(`${this.localUrl}simcardcustomer/update/${id}`, data,{
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido actualizar el cliente")
        )
      );
  }


}
