import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map } from "rxjs/operators";
import { UtilService } from './util.service';
import { SuppliersEntity } from '../entities/SuppliersEntity';
import { ControlOrder } from '../models/ControlOrder';

@Injectable({
  providedIn: 'root'
})
export class SimcardControlFileService {
  private readonly localUrl = "/apiSimcardInquiry/";
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

  getSimcardControl(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardcontrol`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los archivos ")
        )
      );
  }


  processSimcardFile(file: File): Observable<any> {
    const headers = new HttpHeaders();

    return this.http.post<any>(`${this.localUrl}simcardcontrol/processSimcardFile`, file, { headers });
  }

  getSimcardControlById(id: number): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardcontrol/${id}`, {
        observe: 'response',
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, 'No se ha podido obtener los detalles de la SIMCARD_CONTROL')
        )
      );
  }

  updateSupplierSimcardControl(id: number, suppliersId: number): Observable<any> {
    const queryParams = new HttpParams().set('suppliersId', suppliersId.toString());

    return this.http.patch(`${this.localUrl}simcardcontrol/updateSupplier/${id}`, null, {
      headers: this.headers,
      params: queryParams
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el proveedor")
      )
    );
  }

  getSuppliers(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardsuppliers`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros de Proveedores")
        )
      );
  }


  getSuppliersById(id: number): Observable<any> {
    return this.http
      .get<SuppliersEntity[]>(`${this.localUrl}simcardsuppliers/${id}`, {
        observe: "response",
        headers: this.headers
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener el proveedor")
        )
      );
  }

  updateSuppliersSimcardPadre(id: number, suppliersId: number): Observable<any> {
    const queryParams = new HttpParams().set('suppliersId', suppliersId.toString());

    return this.http.patch(`${this.localUrl}simcardpadre/updateSuppliers/${id}`, null, {
      headers: this.headers,
      params: queryParams
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar el proveedor")
      )
    );
  }


  getOrderControl(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener las ordenes")
        )
      );
  }

  getSimcardPadreById(id: number): Observable<any> {
    return this.http
      .get<SuppliersEntity[]>(`${this.localUrl}simcardpadre/${id}`, {
        observe: "response",
        headers: this.headers
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros")
        )
      );
  }

  saveOrderControl(orderControl: ControlOrder) {
    return this.http
      .post(`${this.localUrl}simcardordercontrol/add`, orderControl, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido agregar la orden")
        )
      );
  }

  updateStatusOrder(id: number): Observable<any> {

    return this.http.patch(`${this.localUrl}simcardordercontrol/updateStatus/${id}`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido procesar la orden")
      )
    );
  }

  getNextOrderId(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol/getNextOrderId`, {
        observe: 'response',
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, 'No se ha podido obtener el próximo ID de la orden')
        )
      );
  }

  getOrderByIdPadre(idSimcardPadre: number): Observable<any> {
    return this.http
      .get<ControlOrder[]>(`${this.localUrl}simcardordercontrol/${idSimcardPadre}`, {
        observe: "response",
        headers: this.headers
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los registros")
        )
      );
  }

  updateStatusDetail(id: number): Observable<any> {

    return this.http.patch(`${this.localUrl}simcarddetail/updateStatus/${id}`, {
      observe: "response",
      headers: this.headers
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido procesar la orden")
      )
    );
  }

  updateOrderSimcardControl(id: number, idSimcardOrder: number): Observable<any> {
    const queryParams = new HttpParams().set('idSimcardOrder', idSimcardOrder.toString());

    return this.http.patch(`${this.localUrl}simcardcontrol/updateOrder/${id}`, null, {
      headers: this.headers,
      params: queryParams
    }).pipe(
      catchError((err) =>
        this.handleError(err, "No se ha podido actualizar la orden")
      )
    );
  }


  /**
   * Método que consume un servicio rest para obtener los clientes
   * 
   */
  getCustomer(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardcustomer`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los clientes")
        )
      );
  }

  /**
   * Método encargado de obtener todos los proveedores 
   * que existen
   * 
   */
  getProvider(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardsuppliers`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los proveedores")
        )
      );
  }


  // Parameters
  /**
   * Método encargado de obtener todos los artes 
   * que existen
   * 
   */
  getArts(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardart`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los artes")
        )
      );
  }

    // Type
  /**
   * Método encargado de obtener todos los tipos 
   * que existen
   * 
   */
  getTypes(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardtype`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los tipos")
        )
      );
  }

    /**
   * Método encargado de obtener todos los gráficos 
   * que existen
   * 
   */
    getGraphics(): Observable<any> {
      return this.http
        .get(`${this.localUrl}simcardgraphic`, {
          observe: "response",
          headers: this.headers,
        })
        .pipe(
          catchError((err) =>
            this.handleError(err, "No se ha podido obtener los gráficos")
          )
        );
    }

  /**
   * Método encargado de obtener todos los modelos 
   * que existen
   * 
   */
  getModels(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardmodel`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los modelos")
        )
      );
  }

  /**
   * Método encargado de obtener todos los versiones 
   * que existen
   * 
   */
  getVersionsByIdModal(id:any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardversion/getversionbymodel/${id}`, {
        observe: "response",
        headers: this.headers,
      });
  }


  // Order Control
  /**
   * Método encargado de obtener todos los pedidos 
   * que existen
   * 
   */
  getOrdersControl(page?: any, size?: any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol`, {
        observe: "response",
        headers: this.headers,
        params: {
          page: page,
          size: size
        }
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los pedidos")
        )
      );
  }


  /**
   * Método encargado de obtener todos los pedidos 
   * que existen por el imsi
   * 
   */
  getOrdersControlByImsi(imsi:any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol/getorderbyinitialimsi/${imsi}`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los pedidos")
        )
      );
  }

  /**
   * Método encargado de obtener todos los pedidos 
   * que existen por el iccd
   * 
   */
  getOrdersControlByIccd(iccd:any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol/getorderbyinitialiccd/${iccd}`, {
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido obtener los pedidos")
        )
      );
  }

  getNextImsi(): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol/getNextImsi`, {
        observe: "response",
        headers: this.headers,
      });
  }

  /**
   * Método encargado de obtener todos los detalles del pedido 
   * existente
   * 
   */
  getOrdersControlDetailById(id:any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcarddetail/${id}`, {
        observe: "response",
        headers: this.headers,
      });
  }

  postOrdersControl(data:any): Observable<any> {
    return this.http
      .post(`${this.localUrl}simcardordercontrol/add`, data,{
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido crear el pedido")
        )
      );
  }

  postEmail(data:any): Observable<any> {
    return this.http
      .post(`${this.localUrl}simcardemail/send`, data,{
        observe: "response",
        headers: this.headers,
      })
      .pipe(
        catchError((err) =>
          this.handleError(err, "No se ha podido procesar el pedido")
        )
      );
  }

  /**
   * Método encargado de subir el archivo de la respuesta del proveedor
   * 
   */
  processSimcardFileById(id:any ,file: File): Observable<any> {
    const headers = new HttpHeaders();

    return this.http.post<any>(`${this.localUrl}simcardordercontrol/processSimcardFile/${id}`, file, { headers });
  }

  /**
   * Método encargado de obtener los base64 de los archivos de
   * la orden por el id secuencial
   * 
   */
  getFilesFromOrdersControlById(id:any): Observable<any> {
    return this.http
      .get(`${this.localUrl}simcardordercontrol/orderfiles/${id}`, {
        observe: "response",
        headers: this.headers,
      });
  }

  /**
   * Método que consume un servicio rest para obtener el imsi de los
   * parametros de Simcard
   * 
   */
  getSimcardParam(): Observable<any> {
    return this.http
      .get(`${this.localUrl}parameters/parametername/MSIN`, {
        observe: 'response',
        headers: this.headers,
      });
  }


}
