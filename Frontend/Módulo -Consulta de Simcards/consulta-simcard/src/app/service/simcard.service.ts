import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class SimcardService {

  // Props
  private readonly localUrl = "/apiSimcardInquiry/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }


  /**
   * Método encargado de obtener la data ya sea por
   * imsi o iccd
   * 
   */
  getImsiOrIccd(value:any, type:any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcarddetail/searchbyimsioricc/${value}/${type}`, {
        observe: "response",
        headers: this.headers,
      });
  }


}
