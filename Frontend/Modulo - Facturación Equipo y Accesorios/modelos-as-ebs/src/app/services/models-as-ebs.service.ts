import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ModelAsEbsModel } from '../models/model';

@Injectable({
  providedIn: 'root'
})
export class ModelsAsEbsService {

  // Url And Header
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  // Handler que controla los codeHttp
  handleError(error: HttpErrorResponse, message: string = "") {

    //console.log(error);

    if (error.status === 500 || error.status === 504 || error.status === 400) {
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

  constructor(private http: HttpClient, private utilService: UtilService) { }


  // Methods Services Rest
  /**
  * Método que consume un servicio REST para obtener la información de los modelos
  * 
  * @returns 
  */
  getModelsAsEbs(page: number = 0, size: number = 10): Observable<any> {
    return this.http.get(`${this.localUrl}model-as-ebs`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page.toString(),
        size: size.toString()
      }
    }).pipe(
      catchError((err) => this.handleError(err, "No se ha podido cargar los datos"))
    );
  }

  /**
 * Método que consume un servicio rest para obtener el modelo de as ebs
 * por el codigo ebs
 * 
 * @returns 
 */
  getModelAsEbsByCodEbs(codEbs: any): Observable<any> {
    return this.http.get(`${this.localUrl}/model-as-ebs/cod-ebs/${codEbs}`, {
      observe: 'response',
      headers: this.headers
    });
  }

  /**
 * Método que consume un servicios rest para crear
 * un nuevo modelo
 * 
 * @param data 
 * @returns 
 */
  postModelsAsEbs(data: ModelAsEbsModel): Observable<any> {
    return this.http.post(`${this.localUrl}model-as-ebs/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el modelo"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * el modelo
   * 
   * @param data 
   * @returns 
   */
  putModelsAsEbs(id: any, data: ModelAsEbsModel): Observable<any> {
    return this.http.put(`${this.localUrl}model-as-ebs/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el modelo"))
    );
  }


}

