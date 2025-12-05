import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ReleaseSeriesRequest } from '../models/model';

@Injectable({
  providedIn: 'root'
})
export class ReleaseSerialNumbersService {

  private readonly localUrl = "/apiBsim/";
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
  * Método que consume un servicio REST para obtener la información de los reclamos de seguro
  * 
  * @returns 
  */
  getAllReleaseSerialLog(page: number = 0, size: number = 10): Observable<any> {
    return this.http
      .get(`${this.localUrl}release-serial-log`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page.toString(),
          size: size.toString()
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de logs")
        )
      );
  }

  /**
* Método que consume un servicio rest para obtener el log por el numero de serie
* 
* @returns 
*/
  getLogBySerialNumber(serialNumber: any): Observable<any> {
    return this.http.get(`${this.localUrl}release-serial-log/by-serial/${serialNumber}`, {
      observe: 'response',
      headers: this.headers
    });
  }
  /**
* Método que consume un servicios rest para liberar las series
* 
* @param data 
* @returns 
*/
  postReleaseSingleSeries(data: ReleaseSeriesRequest): Observable<any> {
    return this.http.post(`${this.localUrl}/release-serial/single`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido realizar la liberacion de las series"))
    );
  }

  // Método que consume un servicio REST para liberar las series desde un archivo
  postReleaseSeriesFromFile(file: File, releaseType: string): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('releaseType', releaseType);

    return this.http.post(`${this.localUrl}/release-serial/massive`, formData, {
      observe: 'response',
      headers: {
        // No se puede establecer Content-Type a application/json cuando se envía FormData
        "Authorization": this.utilService.getSystemUser(),
      }
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido realizar la liberación masiva de las series"))
    );
  }
}
