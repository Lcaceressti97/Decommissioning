import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { InsuranceClaimModel } from '../models/model';

@Injectable({
  providedIn: 'root'
})
export class InsuranceClaimInquiryService {


  private readonly localUrl = "/apiEquipmentInsurance/";
  //private readonly servicesInvoice = "/apiEquipmentAccessoriesBilling/";
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
  getInsuranceClaim(page?: any, size?: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}insurance-claim`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de Reclamo de Seguros")
        )
      );
  }

  /**
* Método que consume un servicios rest para crear
* un nuevo reclamo
* 
* @param data 
* @returns 
*/
  postInsuranceClaim(data: InsuranceClaimModel): Observable<any> {
    return this.http.post(`${this.localUrl}insurance-claim/add`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido crear el reclamo de seguros"))
    );
  }

  /**
   * Método que consume un servicios rest para actualizar
   * el reclamo
   * 
   * @param data 
   * @returns 
   */
  putInsuranceClaim(id: any, data: InsuranceClaimModel): Observable<any> {
    return this.http.put(`${this.localUrl}insurance-claim/update/${id}`, data, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el reclamo de seguros"))
    );
  }

  /**
     * Método que consume un servicios rest para eliminar 
     * el reclamo
     * 
     * @param data 
     * @returns 
     */
  deleteInsuranceClaim(id: any): Observable<any> {
    return this.http.delete(`${this.localUrl}insurance-claim/${id}`, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido eliminar el reclamo de seguros"))
    );
  }

  /**
 * Método que consume un servicio REST para obtener la información del seguro por IMEI
 * 
 * @returns 
 */
  getInsuranceControlByEsn(esn: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}equipment-insurance-control/esn/${esn}`, {
        observe: "response",
        headers: this.headers,
      })
  }

  /**
* Método que consume un servicio REST para obtener la información del reclamo seguro por IMEI
* 
* @returns 
*/
  getInsuranceClaimByEsn(esn: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}insurance-claim/esn/${esn}`, {
        observe: "response",
        headers: this.headers,
      })
  }

  /**
* Método que consume un servicio REST para obtener la información del reclamo seguro por telefono
* 
* @returns 
*/
  getInsuranceClaimByPhone(phone: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}insurance-claim/phone/${phone}`, {
        observe: "response",
        headers: this.headers,
      })
  }



  /**
   * Método que consume un servicio REST para obtener la información de los motivos
   * 
   * @returns 
   */
  getReason(): Observable<any> {
    return this.http
      .get(`${this.localUrl}reasons`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de motivos")
        )
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
    });
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

  /**
* Método que consume un servicio REST para obtener los modelos
* 
* @param warehouseId 
* @param type 
* @param equipmentLine 
* @returns 
*/
  getExistencesViewByFilter(warehouseId: any, type: any, equipmentLine: any): Observable<any> {
    return this.http.get(`${this.localUrl}existences/view/${warehouseId}/${type}/${equipmentLine}`, {
      observe: "response",
      headers: this.headers,
    })
  }

  /**
 * Método que consume un servicio REST para obtener el precio por el modelo y el tipo de inventario
 * 
 * @param model 
 * @param inventoryType 
 * @returns 
 */
  getPriceMasterByModelAndInventoryType(inventoryType: any, model: any): Observable<any> {
    return this.http.get(`${this.localUrl}price-master/inventory/${inventoryType}/model/${model}`, {
      observe: "response",
      headers: this.headers,
    })
  }

  
  /*
  * Método encargado de realizar una petición http para traer la info
  * de los parametros
  * 
  * @param id 
  * @returns 
  */
  getInvoiceType(): Observable<any> {
    return this.http.get(`${this.localUrl}invoice-type`, {
      observe: "response",
      headers: this.headers
    });
  }


    /**
* Método que consume un servicio REST para crear una factura por reclamo de seguros
* 
* @returns 
*/
postAddBilling(payload: any): Observable<any> {
  return this.http.post(`${this.localUrl}billing/insurance-claim`, payload, {
    observe: "response",
    headers: this.headers,
  });
}


    /**
* Método que consume un servicio REST para actualizar el control
* 
* @returns 
*/
putEquipmentInsurance(id: any, data: any): Observable<any> {
  return this.http
    .put(`${this.localUrl}equipment-insurance-control/update/${id}`, data, {
      observe: "response",
      headers: this.headers,
    })
    .pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el seguro")
      )
    );
}
}
