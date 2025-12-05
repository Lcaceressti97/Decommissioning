import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { UserBranche } from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {

    //console.log(error);

    if (error.status === 500 || error.status===504) {
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



  /**
 * Método que consume un servicio REST para obtener la información de las sucursales
 * 
 * @returns 
 */
  getBranches(page:any, size:any): Observable<any> {
    return this.http.get(`${this.localUrl}branchoffices`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    }).pipe(
      catchError((err)=> this.handleError(err,"No se ha podido cargar los datos"))
    );
  }


  /**
 * Método que consume un servicio REST para crear un nuevo registro
 * 
 * @returns 
 */
  postBranchesAdd(payload:any): Observable<any> {
    return this.http.post(`${this.localUrl}branchoffices/add`,payload, {
      observe: "response",
      headers: this.headers,
    });
  }


  /**
 * Método que consume un servicio REST para actualizar los datos de una sucursal
 * 
 * @returns 
 */
  putBranchesUpdate(id:any, payload:any): Observable<any> {
    return this.http.put(`${this.localUrl}branchoffices/update/${id}`,payload, {
      observe: "response",
      headers: this.headers,
    });
  }


  // Metodos para más de un usuarios

  /**
   * Método que consume un servicio rest para obtener los usuarios activos
   * e inactivos de la sucursal
   * 
   * @param id_branches 
   * @param status 
   * @returns 
   */
  getUserByBranchesAndStatus(id_branches:any, status:any): Observable<any> {
    return this.http.get(`${this.localUrl}users/${id_branches}/${status}`, {
      observe: "response",
      headers: this.headers,
    });
  }

  /**
   * Método que consume un servicio rest para validar si el usuario está activo ó
   * inactivos en otra sucursal
   * 
   * @param id_branches 
   * @param status 
   * @returns 
   */
  getValidateUserById(iduser:any): Observable<any> {
    return this.http.get(`${this.localUrl}userbranchoffices/validate/iduser/${iduser}`, {
      observe: "response",
      headers: this.headers,
    });
  }


  /**
   * Método encargado de actualizar el estado de los usuarios de la sucursal
   * 
   * @param id_user 
   * @param id_branches 
   * @param status 
   * @returns 
   */
  patchRegisterOrCancelUser(id_user:any,id_branches:any, status:any): Observable<any> {
    return this.http.patch(`${this.localUrl}userbranchoffices/updatestatus?idUser=${id_user}&idBranchOffices=${id_branches}&status=${status}`, {
      observe: "response",
      headers: this.headers,
    });
  }


  /**
   * Método que consume un servicio rest para crear un nuevo registro en la tabla MEA_USERS_BRANCH_OFFICES
   * 
   * @param data 
   * @returns 
   */
  postUserBranche(data:UserBranche): Observable<any> {
    return this.http.post(`${this.localUrl}userbranchoffices/add`, data ,{
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err)=> this.handleError(err,"No se pudo asignar el usuario a la sucursal"))
    );
  }

  /**
   * Método que consume un servicio rest para obtener el usuario por userName de la tabla
   * MEA_USERS
   * 
   * @param userName 
   * @returns 
   */
  getUserByUserName(userName:any): Observable<any> {
    return this.http.get(`${this.localUrl}users/searchbyusername?username=${userName}`, {
      observe: "response",
      headers: this.headers,
    });
  }


    /*
* Método encargado de realizar una petición http para traer la info
* de las bodegas
* 
* @returns 
*/
getWareHouses(): Observable<any> {
  return this.http.get(`${this.localUrl}warehousemaintenance`, {
    observe: "response",
    headers: this.headers
  });
}



}
