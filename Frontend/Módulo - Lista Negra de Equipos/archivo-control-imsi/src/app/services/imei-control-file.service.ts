import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ImeiControlFileEntity } from '../entities/ImeiControlFileEntity';

@Injectable({
  providedIn: 'root'
})
export class ImsiControlFileService {


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

  getImeiControlFile(): Observable<any> {
    return this.http
      .get(`${this.localUrl}imeicontrolfile`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de archivo control IMEI")
        )
      );
  }


  getByPhoneOrImei(type = null, value : string): Observable<any> {
    return this.http
      .get<ImeiControlFileEntity[]>(`${this.localUrl}imeicontrolfile/searchbyphoneorimei/${value}/${type}`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleErrorSearch(err, err.error.errors[0].userMessage, value)
        )
      );
  }


  getByPhone(phone = null): Observable<any> {
    return this.http
      .get<ImeiControlFileEntity[]>(`${this.localUrl}imeicontrolfile/searchbyphone`, {
        observe: "response",
        headers: this.headers,
        params: {
          phone: phone ? phone : ""
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, err.error.errors[0].userMessage)
        )
      );
  }
}
