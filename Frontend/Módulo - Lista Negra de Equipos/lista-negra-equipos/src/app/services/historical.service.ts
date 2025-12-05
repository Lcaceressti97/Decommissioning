import { HttpHeaders, HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { HistoricalEntity } from '../entities/Historical';

@Injectable({
  providedIn: 'root'
})
export class HistoricalService {


  private readonly localUrl = "/api/";
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

  handleErrorSearch(error: HttpErrorResponse, message: string = "" ,value = null) {
    this.utilService.showNotification(1, `No se encontraron datos con el valor ${value}`);
    return throwError(error);
  }

  /*   getAllHistorical(): Observable<any> {
      return this.http
        .get(`${this.localUrl}historical`, {
          observe: "response",
          headers: this.headers,
        })
        .pipe(
          catchError((err) =>
            this.handleError(err, "No se ha podido obtener los registros de archivos de teléfonos defectuosos / robados")
          )
        );
    } */

    getAllHistorical(page: number = 0, size: number = 10): Observable<HttpResponse<any>> {
      const params = {
        page: page.toString(),
        size: size.toString()
      };
  
      return this.http.get<any>(`${this.localUrl}historical`, {
        params,
        observe: 'response',
        headers: this.headers
      }).pipe(
        catchError(err =>
          this.handleError(
            err,
            'No se ha podido obtener los registros de series bloqueadas'
          )
        )
      );
    }

  getHistoricalById(id: number): Observable<HistoricalEntity> {
    return this.http
      .get<HistoricalEntity>(`${this.localUrl}historical/${id}`)
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido el registro registros de series bloqueadas")
        )
      );
  }

  getHistoricalByType(type = null, value : string): Observable<any> {
    return this.http
      .get<HistoricalEntity[]>(`${this.localUrl}historical/searchhistorical`, {
        observe: "response",
        headers: this.headers,
        params: {
          type: type ? type : "",
          value: value ? value : ""
        }
      })
      .pipe(
        catchError((err) =>
          this.handleErrorSearch(err, err.error.errors[0].userMessage, value)
        )
      );
  }

  saveHistorical(historical: HistoricalEntity) {
    return this.http
      .post(`${this.localUrl}historical/add`, historical, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido agregar el archivo de teléfonos defectuosos / robados")
        )
      );
  }

  updateHistorical(historical: HistoricalEntity) {
    return this.http
      .put(`${this.localUrl}historical/update/${historical.id}`, historical, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(
            err,
            "No se ha podido actualizar la información del archivo de teléfonos defectuosos / robados"
          )
        )
      );
  }
}
