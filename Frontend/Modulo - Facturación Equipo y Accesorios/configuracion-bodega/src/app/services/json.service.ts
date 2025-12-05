import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class JsonService {

  // Props
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }


  handleError(error: HttpErrorResponse, message: string = "") {


    if (error.status === 500) {
      this.utilService.showNotifyError(
        error.status,
        `${message}. Contacte al administrador del sistema.`
      );
    } else {
      this.utilService.showNotifyError(
        error.status
      );
    }


    return throwError(error);
  }


  /*
* Método encargado de realizar una petición http para traer la info
* de las bodegas
* 
* @param id 
* @returns 
*/
  getWareHouses(): Observable<any> {
    return this.http.get(`${this.localUrl}warehousemaintenance`, {
      observe: "response",
      headers: this.headers
    });
  }


}
