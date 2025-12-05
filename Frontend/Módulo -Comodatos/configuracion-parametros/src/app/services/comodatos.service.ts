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
        error.status,message
      );
    }


    return throwError(error);
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de la factura por el id de la factura
* 
* @param id 
* @returns 
*/
  configparametersById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}configparameters/searchById`, {
      observe: "response",
      headers: this.headers,
      params: {
        idApplication: id
      }
    });
  }


  /**
   * Método que consume un servicio rest para actualizar el valor del 
   * parámetro
   * 
   */
  putConfigParametersById(id:any, request:any): Observable<any> {

    return this.http.put(`${this.localUrl}configparameters/update/${id}`,request, 
    {
      observe: "response",
      headers: this.headers
    }
    ).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la actualización del parámetro`))
    );

  }


}
