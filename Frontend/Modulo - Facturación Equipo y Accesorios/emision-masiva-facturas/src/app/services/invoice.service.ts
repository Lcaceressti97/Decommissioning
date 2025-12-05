import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';


@Injectable({
  providedIn: 'root'
})
export class InvoiceService {


  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

  private readonly headersBSIM = new HttpHeaders({
    "Accept": "*/*",
    "Accept-Encoding": "gzip, deflate, br",
  });

  // BSIM
  private baseUrl = '/api/auth-server/auth-server/oauth/token';
  private baseUrlRealeaseSerialNumber = '/api/bsim-operations-service/v1/operations/releaseSerialNumbers';
  private clientId = 'client';
  private clientSecret = 'secret';
  private username = 'BSIM';
  private password = 'imt6HkszyVOtBgqPo4DIt9wc54LdZD1Ul0wiKGIvKY7VMNZTZ9/VFbvITFV%2BLptn%2B/F8YX3AFsWppV0DSigfw2Y%2Bch5p0fbWgchatNPI41/rz5xmWSgXipmzZVdZClj%2B%2BYfICJjRbgOE0pFqUj4x4%2B8/ONg/RDtQ6TuSoFSKqr1BBjeepczP9J3UoK6qrzz4TKSU/S8EPXrW6rxDrC/Tbz4gtfep1iUwuNaPyaOGYKhgdS6ZteyBhW232et%2BLuis0DogJLg0uo2GeKTf5KEDbAj8zqep%2BhA037%2BMdoWvSNZl4ydvUUOPdEgJt5BmNZ5Bui3ngR6DU7sXkX0yFvAgjQ==';


  constructor(private http: HttpClient, private utilService: UtilService) { }

  handleError(error: HttpErrorResponse, message: string = "") {


    if (error.status === 500 || error.status === 502 || error.status === 504) {
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
 * Método que consume un servicio rest para obtener la infomarción
 * de todas las facturas
 * 
 */
  getInvoiceAll(): Observable<any> {
    return this.http.get(`${this.localUrl}billing`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros de Facturación"))
    );
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
   * Método que trae como paginación los datos con status 0 y 1
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  getInvoicePagination(page?: any, size?: any, seller?: any, status?: any): Observable<any> {
    return this.http.get(`${this.localUrl}billing/invoicesauthorizeissue`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size,
        seller: seller,
        status: status
      }
    });
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
   * Método encargado de actualizar el estado de la factura
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  patchUpdateStatusInvoiceAuth(id: any, status: any, authorizingUser: any, dateAuth: any): Observable<any> {
    return this.http.patch(`${this.localUrl}billing/updatestatus/${id}?status=${status}&authorizingUser=${authorizingUser}&authorizationDate=${dateAuth}`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la actualización de la factura`))
    );
  }

  /**
   * Método encargado de actualizar el estado de la factura a emitida
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  patchUpdateStatusInvoiceEmit(id: any, status: any, authorizingUser: any, authorizationDate: any, userIssued: any, dateOfIssue: any): Observable<any> {
    return this.http.patch(`${this.localUrl}billing/updatestatus/${id}?status=${status}&authorizingUser=${authorizingUser}&authorizationDate=${authorizationDate}&userIssued=${userIssued}&dateOfIssue=${dateOfIssue}`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la actualización de la factura`))
    );
  }


  /**
   * Método encargado de actualizar el estado de la factura
   * 
   * @param page 
   * @param size 
   * @returns 
   */
  putUpdateInvoice(id: any, data: any): Observable<any> {
    return this.http.put(`${this.localUrl}billing/update/${id}`, data, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, `No se ha podido realizar la actualización de la factura`))
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
      .get(`${this.localUrl}billing/${id}`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha encontrado la factura")
        )
      );
  }

    /**
  * Método que realiza la consulta por id de factura solo para pendientes y autorizadas
  * 
  * @param id 
  * @returns 
  */
    getBillsByIdAuthorizeIssue(id: number): Observable<any> {
      return this.http
        .get(`${this.localUrl}billing/invoicesauthorizeissue/findbyid/${id}`, {
          observe: "response",
          headers: this.headers,
        });
    }


  /**
 * Método que consume un servicio REST para obtener la información de las sucursales
 * 
 * @returns 
 */
  getBranches(page: any, size: any): Observable<any> {
    return this.http.get(`${this.localUrl}branchoffices`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    }).pipe(
      catchError((err) => this.handleError(err, "No se ha podido cargar los datos"))
    );
  }

  /**
   * Método que consume un servicio rest para obtener
   * la información del canal
   * 
   * @param id 
   * @returns 
   */
  getChannelById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}channel/${id}`, {
      observe: "response",
      headers: this.headers
    });
  }

  /**
 * Método que consume una api para enviar la factura por correo
 * que se ingreso
 * 
 * @param id 
 * @param data 
 * @returns 
 */
  postSendEmail(data: any): Observable<any> {

    return this.http.post(`${this.localUrl}sendemail`, data, {
      observe: 'response'
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se puedo enviar el correo"))
    );

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

  // Servicios BSIM
  /**
   * Método que consume un servicio rest para autenticarse en los servicios de 
   * BSIM
   * 
   */
  postAuthBSIM(parameter30410: Map<string, string>): Observable<any> {

    const password: any = "imt6HkszyVOtBgqPo4DIt9wc54LdZD1Ul0wiKGIvKY7VMNZTZ9/VFbvITFV%2BLptn%2B/F8YX3AFsWppV0DSigfw2Y%2Bch5p0fbWgchatNPI41/rz5xmWSgXipmzZVdZClj%2B%2BYfICJjRbgOE0pFqUj4x4%2B8/ONg/RDtQ6TuSoFSKqr1BBjeepczP9J3UoK6qrzz4TKSU/S8EPXrW6rxDrC/Tbz4gtfep1iUwuNaPyaOGYKhgdS6ZteyBhW232et%2BLuis0DogJLg0uo2GeKTf5KEDbAj8zqep%2BhA037%2BMdoWvSNZl4ydvUUOPdEgJt5BmNZ5Bui3ngR6DU7sXkX0yFvAgjQ==";

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = new HttpParams()
      .set('grant_type', parameter30410.get('GRANT_TYPE'))
      .set('client_id', parameter30410.get('CLIENT_ID'))
      .set('client_secret', parameter30410.get('CLIENT_SECRET'))
      .set('username', parameter30410.get('USERNAME'))
      .set('password', parameter30410.get('PASSWORD'));

    //const url = '/apiBSIM/api/auth-server/auth-server/oauth/token' +
    //`?grant_type=password&client_id=client&client_secret=secret&username=BSIM&password=${password}`;


    /*
    return this.http.post(`${this.localUrlBSIM}api/auth-server/auth-server/oauth/token`,null,{
      observe: 'response',
      headers: this.headersBSIM,
      params: {
        grant_type: 'password',
        client_id: 'client',
        client_secret: 'secret',
        username: 'BSIM',
        password: ''
      }
      */
    return this.http.post(`${parameter30410.get('ENDPOINT_PROXY_AUTH')}`, body, {
      observe: 'response',
      headers: headers,
    })
  }

  postRealeaseSerialNumberBSIM(token: any, payload: any, parameter30410: Map<string, string>): Observable<any> {


    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post(`${parameter30410.get('ENDPOINT_PROXY_REALEASE_SERIAL_NUMBER')}`, payload, {
      observe: 'response',
      headers: headers,
    })

  }

  /**
 * Método que consume un servicio rest para obtener la
 * información de la sucursal según su identificador
 * 
 * @param id 
 * @returns 
 */
  getBrancheById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}branchoffices/${id}`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los detalles de las facturas"))
    );
  }

}
