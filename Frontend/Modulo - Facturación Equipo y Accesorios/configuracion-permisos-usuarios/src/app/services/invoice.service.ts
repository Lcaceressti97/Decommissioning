import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ControlUserPermissions, ControlUserPermissionsCancellation, User, UserWithOutPermission } from '../models/model';

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


    if (error.status === 500 || error.status === 504 || error.status === 502 || error.status === 400) {
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
   * Método que trae como paginación los datos de conrol de permisos del usuario
   * 
   * @param page 
   * @param size 
   * @returns 
   */
    getControlUserPermissions(page: any, size: any): Observable<any> {
      return this.http.get(`${this.localUrl}controluserpermissions`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      }).pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de los usuarios"))
      );
    }

  /**
 * Método que trae los permisos del usuario
 * 
 * @param page 
 * @param size 
 * @returns 
 */
  getControlUserPermissonsByUser(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}controluserpermissions/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }

  /**
   * Método que realiza una solicitud a una api para traer los
   * tipos de usuario
   * 
   */
  getTypeUser(): Observable<any> {
    return this.http.get(`${this.localUrl}typeusers`, {
      observe: "response",
      headers: this.headers,
    });
  }

  getUserPermissonsWithFn(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}cancelinvoicewithfiscalno/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }

  getUserPermissonsWithOutFn(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}cancelinvoicewithothfiscalno/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }


  /**
   * Servicio que nos ayuda a validar si el usuario existe en la tabla MEA_USERS
   * 
   * @param username 
   * @returns 
   */
  existsUser(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}users/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }


  /**
   * Servicio que nos ayuda a validar si el usuario existe en la tabla MEA_CONTROL_USER_PERMISSIONS
   * 
   * @param username 
   * @returns 
   */
  existsUserPermissions(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}controluserpermissions/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }



  // Métodos para actualizar permisos

  /**
   * Servicio rest para actualizar los permisos de autorizar y emitir
   * 
   */
  putUpdatePermissionControlUser(body: ControlUserPermissions, id: any): Observable<any> {
    return this.http.put(`${this.localUrl}controluserpermissions/update/${id}`, body, {
      observe: "response",
      headers: this.headers,

    });
  }

  putUpdateUser(body: User, id: any): Observable<any> {
    return this.http.put(`${this.localUrl}users/update/${id}`, body, {
      observe: "response",
      headers: this.headers,

    });
  }

  putUpdatePermissionCancelInvoiceWithFiscalNo(body: ControlUserPermissionsCancellation, id: any): Observable<any> {
    return this.http.put(`${this.localUrl}cancelinvoicewithfiscalno/update/${id}`, body, {
      observe: "response",
      headers: this.headers,

    });
  }



  putUpdatePermissionCancelInvoiceWithOutFiscalNo(body: ControlUserPermissionsCancellation, id: any): Observable<any> {
    return this.http.put(`${this.localUrl}cancelinvoicewithothfiscalno/update/${id}`, body, {
      observe: "response",
      headers: this.headers,

    });
  }



  /**
   * Métodos rest para crear registros de permisos
   * 
   */

  postCreatePermissionControlUser(body: ControlUserPermissions): Observable<any> {
    return this.http.post(`${this.localUrl}controluserpermissions/add`, body, {
      observe: "response",
      headers: this.headers,

    });
  }


  postCreatePermissionCancelInvoiceWithFiscalNo(body: ControlUserPermissionsCancellation): Observable<any> {
    return this.http.post(`${this.localUrl}cancelinvoicewithfiscalno/add`, body, {
      observe: "response",
      headers: this.headers,

    });
  }

  postCreatePermissionCancelInvoiceWithOutFiscalNo(body: ControlUserPermissionsCancellation): Observable<any> {
    return this.http.post(`${this.localUrl}cancelinvoicewithothfiscalno/add`, body, {
      observe: "response",
      headers: this.headers,

    });
  }


  /**
   * Método que consume un servicio rest para traer a los usuarios sin permisos
   * 
   */
  getUserWithOutPermissions(): Observable<any> {
    return this.http.get(`${this.localUrl}users`,{
      observe: "response",
      headers: this.headers
    });
  }


  /**
   * Método encargado de actualizar el estado de la factura
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  patchUpdateStatusUser(id: any): Observable<any> {
    return this.http.patch(`${this.localUrl}users/updatestatus/${id}?status=1`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la actualización del estado del usuario`))
    );
  }


  /**
   * Método que consume un servicio rest para crear un usuario en la tabla
   * MEA_USERS
   * 
   */
  postCreateuser(body: UserWithOutPermission): Observable<any> {
    return this.http.post(`${this.localUrl}users/add`, body, {
      observe: "response",
      headers: this.headers,

    });
  }

}
