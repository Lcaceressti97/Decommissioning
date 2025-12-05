import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  // Props
  private readonly localUrl = "/apiEquipmentAccessoriesBilling/";
  private readonly headers = new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": this.utilService.getSystemUser(),
  });

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

  /**
   * Método encargado de traer las facturas del día
   *
   */
  getBilling(page: number = 0, size: number = 10,seller: any): Observable<any> {
    return this.http.get(`${this.localUrl}billing/creation`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page.toString(),
        size: size.toString(),
        seller: seller
      }
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido obtener los registros de las facturas"))
    );
  }


  /*
  * Método encargado de realizar una petición http para traer la info
  * de los parametros
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

  /*
  * Método encargado de realizar una petición http para traer la info
  * de los parametros
  *
  * @param id
  * @returns
  */
  getInvoiceType(): Observable<any> {
    return this.http.get(`${this.localUrl}invoicetype`, {
      observe: "response",
      headers: this.headers
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
    });
  }


  /**
 * Método que consume un servicio rest para obtener todos los
 * servicios de facturación
 *
 */
  getBillingServiceByPagination(page: any, size: any): Observable<any> {
    return this.http.get(`${this.localUrl}billingservices`, {
      observe: "response",
      headers: this.headers,
      params: {
        page: page,
        size: size
      }
    });
  }

  /**
* Método que consume un servicio REST para obtener la información de los canales
* que se utilizan en los procesos de facturación
*
* @returns
*/
  getChannels(): Observable<any> {
    return this.http.get(`${this.localUrl}channel`, {
      observe: "response",
      headers: this.headers,
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

  /**
* Método que consume un servicio REST para obtener la información del
* cliente por la cuenta de facturación
* que se utilizan en los procesos de facturación
*
* @returns
*/
  getCustomerInfo(acctCode: any): Observable<any> {
    return this.http.get(`${this.localUrl}customerinfo/${acctCode}`, {
      observe: "response",
      headers: this.headers,
    });
  }

  /**
   * Método que consume un servicio rest para obtener la tasa de
   * cambio del día
   *
   */
  getExchangerate(): Observable<any> {
    return this.http.get(`${this.localUrl}exchangerate`, {
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


  /**
   * Método que consume un servicio rest para obtener las sub-bodegas
   *
   */
  getSubWareHouse(): Observable<any> {
    return this.http.get(`${this.localUrl}subwarehouses`, {
      observe: "response",
      headers: this.headers,
    });
  }

  /**
   * Método que consume un servicio rest para obtener el nombre de la bodega
   *
   */
  getSubWareHouseByCode(namebycode: any): Observable<any> {
    return this.http.get(`${this.localUrl}warehousemaintenance/namebycode`, {
      observe: "response",
      headers: this.headers,
      params: {
        code: namebycode
      }
    });
  }

  /**
* Método que consume un servicio REST para crear una factura
*
* @returns
*/
  postAddBilling(payload: any): Observable<any> {
    return this.http.post(`${this.localUrl}billing/add`, payload, {
      observe: "response",
      headers: this.headers,
    });
  }


  getHistoricalDetailsByPhone(phone: any): Observable<any> {
    return this.http.get(`${this.localUrl}historicaldetail`, {
      observe: "response",
      headers: this.headers,
      params: {
        phone
      }
    });
  }

  /**
* Método para validar si ya existe un reclamo de seguros
*
* @returns
*/
  getBillingByInsuranceClaim(idInsuranceClaim: any): Observable<any> {
    return this.http.get(`${this.localUrl}billing/insuranceclaim/${idInsuranceClaim}`, {
      observe: "response",
      headers: this.headers,
    });
  }

  /**
 * Método que consume un servicio REST para obtener las existencias de un tipo de inventario en una bodega específica
 *
 * @param warehouseId
 * @param typeInventoryId
 * @returns
 */
  getExistencesByFilter(warehouseId: any, typeInventoryId: any): Observable<any> {
    return this.http.get(`${this.localUrl}existences/${warehouseId}/${typeInventoryId}`, {
      observe: "response",
      headers: this.headers,
    }).pipe(
      catchError((error: HttpErrorResponse) => this.handleError(error, "Error al obtener las existencias"))
    );
  }

  /**
 * Método que consume un servicio REST para obtener la información de las existencias de las series
 *
 * @param itemCode
 * @param warehouseCode
 * @param subWarehouseCode
 * @param inventoryType
 * @returns
 */
  getSerialNumbersQuery(itemCode: any, warehouseCode: any, subWarehouseCode: any, inventoryType: any): Observable<any> {
    const url = `${this.localUrl}serial-number-query`;
    const params = new HttpParams()
      .set('itemCode', itemCode)
      .set('warehouseCode', warehouseCode)
      .set('subWarehouseCode', subWarehouseCode)
      .set('inventoryType', inventoryType);

    return this.http.get(url, {
      observe: "response",
      headers: this.headers,
      params: params
    }).pipe(
      catchError((error: HttpErrorResponse) => this.handleError(error, "Error al obtener las existencias"))
    );
  }

  /**
* Método que consume un servicio REST para obtener el precio por el modelo y el tipo de inventario
*
* @param model
* @param inventoryType
* @returns
*/
  getPriceMasterByModelAndInventoryType(model: any, inventoryType: any): Observable<any> {
    return this.http.get(`${this.localUrl}pricemaster/${model}/${inventoryType}`, {
      observe: "response",
      headers: this.headers,
    })
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
 * Método que reserva las series
 *
 * @param page
 * @param size
 * @returns
 */
  getSerialNumbersReserveQuery(itemCode: any, warehouseCode: any, subWarehouseCode: any, inventoryType: any, rowLimit: number): Observable<any> {
    const requestBody = {
      warehouseCode,
      subWarehouseCode,
      inventoryType,
      itemCode,
      rowLimit
    }
    const url = `${this.localUrl}serial-number-query/reservation`;
    return this.http.post(url, requestBody, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((error: HttpErrorResponse) => this.handleError(error, "Error al obtener las existencias con reservacion"))
    );
  }
}

