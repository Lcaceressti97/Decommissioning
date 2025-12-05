import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class ComodatosService {

  // Props
  private readonly localUrl = "/apiComodatos/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {


    if (error.status === 500 || error.status === 502 || error.status === 504) {
      this.utilService.showNotifyError(
        error.status,
        `${message}. Contacte al administrador del sistema.`
      );
    } else {
      this.utilService.showNotifyError(
        error.status, message
      );
    }


    return throwError(error);
  }


  /**
   * Servicio Rest que obtiene todas las promociones
   * 
   */
  getPromotions(): Observable<any> {
    return this.http.get(`${this.localUrl}promotions`, {
      observe: "response",
      headers: this.headers
    });
  }

  /**
   * Método que consume un servicio rest
   * para poder crear una nueva promoción
   * 
   * @param data 
   * @returns 
   */
  postPromotion(data:any): Observable<any> {
    return this.http
      .post(`${this.localUrl}promotions/add`, data,{
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear la promoción")
        )
      );
  }

  /**
   * Método que consume un servicio rest
   * para poder actualizar una nueva promoción
   * 
   * @param data 
   * @returns 
   */
  putPromotion(id:any, data:any): Observable<any> {
    return this.http
      .put(`${this.localUrl}promotions/update/${id}`, data,{
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido actualizar la promoción")
        )
      );
  }


}
