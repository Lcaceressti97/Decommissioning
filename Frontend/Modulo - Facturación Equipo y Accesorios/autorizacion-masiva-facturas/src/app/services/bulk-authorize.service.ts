import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UtilService } from './util.service';
import { from, Observable, throwError } from 'rxjs';
import { catchError, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BulkAuthorizeService {

  private readonly localUrl = '/apiEquipmentAccessoriesBilling/';


  constructor(private http: HttpClient, private utilService: UtilService) { }

  /** Genera headers por solicitud. */
  private readonly headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': this.utilService.getSystemUser(),
  });


  /**
   * Manejo centralizado de errores HTTP.
   * Muestra notificación contextual y propaga el error al suscriptor.
   * @param error Error HTTP recibido
   * @param message Mensaje adicional a mostrar (opcional)
   */
  handleError(error: HttpErrorResponse, message: string = '') {
    if (error.status === 500 || error.status === 502 || error.status === 504) {
      this.utilService.showNotifyError(
        error.status,
        `${message}. Contacte al administrador del sistema.`
      );
    } else {
      this.utilService.showNotifyError(error.status);
    }
    return throwError(error);
  }

  // ========================================================================
  // CONFIGURACIÓN PARAMETROS
  // ========================================================================

  /*
  * Método encargado de obtener los parámetros de configuración.
  *
  * @param id Identificador de la aplicación (idApplication)
  * @returns Observable con la respuesta HTTP completa
  */
  configParametersById(id: any): Observable<any> {
    return this.http.get(`${this.localUrl}configparameters/searchById`, {
      observe: 'response',
      headers: this.headers,
      params: { idApplication: id }
    }).pipe(
      catchError(err => this.handleError(err, 'Error al obtener parámetros de configuración'))
    );
  }


  // ========================================================================
  // AUTORIZACIÓN MASIVA
  // ========================================================================

  /**
   * Obtiene el listado paginado base para autorización masiva.
   * Endpoint: GET /bulk-authorize
   *
   * @param page Número de página (0-index)
   * @param size Tamaño de página
   * @param seller Vendedor
   * @returns Observable con la respuesta HTTP completa (lista paginada)
   */
  getBulkAuthorize(page: any, size: any, seller: any): Observable<any> {
    return this.http.get(`${this.localUrl}bulk-authorize`, {
      observe: 'response',
      headers: this.headers,
      params: {
        page: page,
        size: size,
        seller: seller
      }
    }).pipe(
      catchError(err => this.handleError(err, 'Error al obtener listado para autorización masiva'))
    );
  }

  /**
   * Busca facturas por nombre de cliente o por código de cliente (paginado).
   * Endpoint: GET /bulk-authorize/search
   *
   * Nota: Ambos filtros son opcionales. El backend acepta cualquiera de los dos.
   *
   * @param page Número de página (0-index)
   * @param size Tamaño de página
   * @param customer Texto a buscar por nombre/identificador de cliente (opcional)
   * @param customerId Código de cliente a buscar (opcional)
   * @returns Observable con la respuesta HTTP completa (lista paginada filtrada)
   */
  searchBulkAuthorize(
    page: any,
    size: any,
    seller?: string,
    customer?: string,
    customerId?: string
  ): Observable<any> {
    return this.http.get(`${this.localUrl}bulk-authorize/search`, {
      observe: 'response',
      headers: this.headers,
      params: {
        page: page,
        size: size,
        seller: seller,
        ...(customer ? { customer } : {}),
        ...(customerId ? { customerId } : {}),
      }
    }).pipe(
      catchError(err => this.handleError(err, 'Error al filtrar facturas para autorización masiva'))
    );
  }

  /**
   * Autoriza en bloque un conjunto de facturas.
   * Endpoint: POST /bulk-authorize/authorize
   *
   * @param req Cuerpo de la solicitud (BulkAuthorizeRequest)
   * @returns Observable con la respuesta HTTP completa (resultado de la autorización)
   */
  authorizeInvoices(req: any): Observable<any> {
    return this.http.post(`${this.localUrl}bulk-authorize/authorize`, req, {
      observe: 'response',
      headers: this.headers
    }).pipe(
      catchError((err: HttpErrorResponse) => this.rethrowNormalizedError(err, 'Error al autorizar facturas en bloque'))
    );
  }

  // ========================================================================
  // HELPERS OPCIONALES (por si lo necesitas en tus componentes)
  // ========================================================================

  /**
   * Construye un request body típico para autorización masiva.
   * Útil para estandarizar el payload desde componentes.
   *
   * @param invoiceIds Arreglo de IDs de factura a autorizar
   * @param comment Comentario opcional
   * @returns Objeto listo para enviar a authorizeInvoices(...)
   */
  buildBulkAuthorizeRequest(invoiceIds: string[], comment?: string): any {
    return {
      invoices: invoiceIds,
      authorizedBy: this.utilService.getSystemUser?.() ?? 'SYSTEM',
      ...(comment ? { comment } : {}),
    };
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

  /** Convierte Blob o string a JSON y re-lanza el error normalizado */
  private rethrowNormalizedError(err: HttpErrorResponse, _msg?: string) {
    // Blob -> JSON
    if (err?.error instanceof Blob) {
      return from(new Promise<HttpErrorResponse>(resolve => {
        const fr = new FileReader();
        fr.onload = () => {
          let parsed: any;
          try { parsed = JSON.parse(String(fr.result || '')); }
          catch { parsed = { raw: String(fr.result || '') }; }
          resolve(new HttpErrorResponse({ ...err, error: parsed }));
        };
        fr.readAsText(err.error);
      })).pipe(
        mergeMap(normalized => throwError(normalized))
      );
    }

    // string -> JSON
    if (typeof err?.error === 'string') {
      let parsed: any;
      try { parsed = JSON.parse(err.error); }
      catch { parsed = { raw: err.error }; }
      const normalized = new HttpErrorResponse({ ...err, error: parsed });
      return throwError(normalized);
    }

    return throwError(err);
  }
}
