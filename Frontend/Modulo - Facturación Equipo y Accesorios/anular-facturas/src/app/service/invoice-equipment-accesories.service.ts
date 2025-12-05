import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { ControlCancellation, DataCancel } from '../model/billing';
@Injectable({
  providedIn: 'root'
})
export class InvoiceEquipmentAccesoriesService {


  // Props
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  constructor(private http: HttpClient, private utilService: UtilService) { }


  handleError(error: HttpErrorResponse, message: string = "") {

    console.log(error.status >= 400);

    if (error.status >= 400 ) {
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
   * Método que trae como paginación los datos con status 1 y 4
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  getInvoicePagination(page: number = 0, size: number = 10, seller?: any, status?:any): Observable<any> {
    const params = {
      page: page.toString(),
      size: size.toString(),
      seller: seller,
      status: status
    };

    return this.http.get(`${this.localUrl}billing/invoicescancel`, {
      params,
      observe: "response",
      headers: this.headers
    });
  }

  /**
  * Método que consume un servicio rest el cual nos devuelve los detalles
  * de una factura.
  * 
  */
  getInvoiceDetailById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}invoicedetail/details/${id}`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los detalles de la factura"))
    );
  }


  /**
* Método que trae el permiso del usuario para anular la factura con status 1
* 
* @param page 
* @param size 
* @returns 
*/
  getPermissionWithFn(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}cancelinvoicewithfiscalno/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }


  /**
* Método que trae el permiso del usuario para anular la factura con status 4
* 
* @param page 
* @param size 
* @returns 
*/
  getPermissionWithOutFn(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}cancelinvoicewithothfiscalno/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }


  /**
 * Método encargado de actualizar el estado de la factura
 * 
 * @param page 
 * @param size 
 * @returns 
 */
  patchUpdateStatusInvoice(id: any, status: any, authorizingUser: any, authorizationDate: any, userIssued: any, dateOfIssue: any): Observable<any> {
    return this.http.patch(`${this.localUrl}billing/updatestatus/${id}?status=${status}&authorizingUser=${authorizingUser}&authorizationDate=${authorizationDate}&userIssued=${userIssued}&dateOfIssue=${dateOfIssue}`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la actualización de la factura`))
    );
  }


  /**
   * Método que ingresa las anulaciones
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  postControlCancellation(data: ControlCancellation): Observable<any> {
    return this.http.post(`${this.localUrl}controlcancellation/add`, data, {
      observe: "response",
      headers: this.headers,

    }).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la anulación`))
    );
  }


  /**
  * Método que realiza la consulta por id de factura
  * 
  * @param id 
  * @returns 
  */
  getInvoiceById(id: number): Observable<any> {
    return this.http
      .get(`${this.localUrl}billing/invoicescancel/findbyid/${id}`, {
        observe: "response",
        headers: this.headers,
      });
  }

  // Método para consumir el cancelVoucher
  postCancelVoucher(data: DataCancel): Observable<any> {
    return this.http.post(`${this.localUrl}voucher/cancelvoucher`, data, {
      observe: "response",
      headers: this.headers,

    }
    );
  }

  /**
 * Método que trae los permisos del usuario
 * 
 * @param page 
 * @param size 
 * @returns 
 */
  getControlUserPermissons(username: any): Observable<any> {
    return this.http.get(`${this.localUrl}controluserpermissions/searchbyusername`, {
      observe: "response",
      headers: this.headers,
      params: {
        username: username,
      }
    });
  }

  /**
 * Método que consume un servicio rest para traer el tipo de usuario
 * 
 */
  getTypeUser(id: any): Observable<any> {

    return this.http.get(`${this.localUrl}typeusers/${id}`, {
      observe: 'response'
    });

  }

  /**
* Método que consume un servicio REST para obtener la información de los
* tipos de inventarios
* que se utilizan en los procesos de facturación
* 
* @returns 
*/
  getInventoryType(): Observable<any> {
    return this.http.get(`${this.localUrl}typeinventory`, {
      observe: "response",
      headers: this.headers,
    });
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

    /**
* Método que consume un servicio REST para obtener la información de los motivos 
* 
* @returns 
*/
getCancelReason(): Observable<any> {
  return this.http.get(`${this.localUrl}cancel-reason`, {
    observe: "response",
    headers: this.headers,
  });
}

}
