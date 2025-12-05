import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { finalize } from 'rxjs/operators';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { SelectionType } from '@swimlane/ngx-datatable';

import { InvoicePagesResponse, ConfigParameterResponse } from 'src/app/entities/InvoicesEntity';
import { Billing, ConfigParameter, StatusNames } from 'src/app/models/InvoicesModel';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import { Buttons } from 'src/types';
import { DetailInvoiceModalComponent } from 'src/app/components/detail-invoice-modal/detail-invoice-modal.component';
import { BulkEmissionService } from 'src/app/services/bulk-emission.service';
import { EmissionModalComponent } from 'src/app/components/emission-modal/emission-modal.component';

@Component({
  selector: 'app-bulk-emission',
  templateUrl: './bulk-emission.component.html',
  styleUrls: ['./bulk-emission.component.css']
})
export class BulkEmissionComponent implements OnInit {


  // ==========================
  // FORMULARIO & FILTROS
  // ==========================
  searchInvoicesForm!: FormGroup;

  /** Último criterio de búsqueda de backend, se usa para paginar/refresh conservando filtros */
  private lastSearch: { customer?: string; customerId?: string } | null = null;

  /** Etiquetas y helpers visuales */
  labelClasses = 'col-form-label text-dark col-sm-2 col-md-2 col-lg-2';
  inputClasses = 'my-auto col-sm-2 col-md-4 col-lg-2';
  inputClassesTwo = 'my-auto col-sm-12 col-md-2 col-lg-2';

  /** Búsqueda local (barra “Buscar” de la tabla) */
  searchedValue = '';

  /** Select de tipo de consulta (angular2-multiselect) */
  queryTypeOptions = [
    { id: 'customer', itemName: 'Nombre de Cliente' },
    { id: 'acctCode', itemName: 'Código de cliente (RTN)' }
  ];
  selectedQueryType = [{ id: 'customer', itemName: 'Nombre de Cliente' }];
  queryTypeSettings = {
    singleSelection: true,
    text: 'Seleccione un valor',
    enableSearchFilter: false,
    badgeShowLimit: 1,
    labelKey: 'itemName',
    primaryKey: 'id',
  };

  // ==========================
  // TABLA & PAGINACIÓN
  // ==========================
  rows: Billing[] = [];
  /** Copia base para búsqueda local */
  rows2: Billing[] = [];
  loadingIndicator = true;

  currentPage = 0;
  resultsPerPage = 20; // vinculado al <select>
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  // ==========================
  // SELECCIÓN
  // ==========================
  SelectionType = SelectionType;

  /** Selección global persistente entre páginas */
  selectedIds = new Set<number>();

  /** Selección “visual” del datatable (página visible) */
  selectedRows: Billing[] = [];
  selectAllOnPage = false;

  // ==========================
  // PARÁMETROS & ESTADO
  // ==========================
  messages = messages;
  loading = false;
  showTable = false;
  infoLoaded = false;

  parametersInvoiceStatus: ConfigParameter[] = [];
  statusNames: StatusNames = {};
  parameters: ConfigParameter[] = [];
  parametersUserAuth1002: string[] = [];

  // ==========================
  // INYECCIONES
  // ==========================
  constructor(
    private bulkEmissionService: BulkEmissionService,
    private modalService: NgbModal,
    public utilService: UtilService,
    private readonly fb: FormBuilder
  ) { }

  // ==========================
  // CICLO DE VIDA
  // ==========================
  async ngOnInit() {
    this.searchInvoicesForm = this.initForm();
    await this.configParametersById(1000);
    await this.configParametersById(1002);
    await this.configParametersById(4439);
    this.loadPage(0, this.pageSize, 'Cargando Facturas...');
  }

  // ==========================
  // FORM: CREACIÓN & SINCRONÍA
  // ==========================

  /** Inicializa formulario: tipo de consulta y valor */
  private initForm(): FormGroup {
    return this.fb.group({
      queryType: ['customer', Validators.required],
      queryValue: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  /** Sincroniza el angular2-multiselect con el FormControl `queryType` */
  onQueryTypeChanged() {
    const sel = this.selectedQueryType?.[0]?.id || 'customer';
    this.searchInvoicesForm.get('queryType')?.setValue(sel);
  }

  /** Sanitiza texto para evitar inyección en notificaciones/HTML */
  private sanitize(text: string): string {
    return String(text).replace(/[<>&'"]/g, (s) =>
      ({ '<': '&lt;', '>': '&gt;', '&': '&amp;', '\'': '&#39;', '"': '&quot;' } as any)[s]
    );
  }

  /** Obtiene etiqueta legible según el tipo de consulta */
  private queryLabel(type: 'customer' | 'acctCode'): string {
    return type === 'customer' ? 'cliente' : 'código de cliente';
  }

  /**
   * Enviar búsqueda al backend (reinicia a página 0 y conserva el criterio en `lastSearch`)
   * - Si no hay resultados, muestra mensaje con tipo + valor sanitizado.
   */
  async onSearch() {

    if (this.searchInvoicesForm.invalid) {
      this.searchInvoicesForm.markAllAsTouched();
      this.utilService.showNotification(1, 'Complete los campos de búsqueda');
      return;
    }

    this.selectedIds.clear();
    this.selectedRows = [];
    this.selectAllOnPage = false;

    const { queryType, queryValue } = this.searchInvoicesForm.value as {
      queryType: 'customer' | 'acctCode'; queryValue: string;
    };

    const cleanValue = (queryValue ?? '').toString().trim();
    if (cleanValue.length < 2) {
      this.utilService.showNotification(1, 'Ingrese al menos 2 caracteres.');
      return;
    }

    // Criterio de backend para paginación y refresh
    this.lastSearch = queryType === 'customer'
      ? { customer: cleanValue }
      : { customerId: cleanValue };

    // Reiniciar en página 0
    this.loadPage(0, this.pageSize, 'Buscando Facturas...');
  }

  /**
   * Refresh TOTAL:
   * - Limpia formulario y multiselect (tipo y valor)
   * - Limpia búsqueda local (barra "Buscar")
   * - Limpia selección (global y visual)
   * - Reinicia paginación y tamaño de página al default
   * - Elimina criterios de búsqueda (lastSearch = null)
   * - Recarga desde backend getBulkEmission()
   */
  async onRefresh() {
    // Form & multiselect
    this.searchInvoicesForm.reset({ queryType: 'customer', queryValue: '' });
    this.selectedQueryType = [{ id: 'customer', itemName: 'Nombre de Cliente' }];
    this.onQueryTypeChanged();

    this.searchedValue = '';

    this.selectedIds.clear();
    this.selectedRows = [];
    this.selectAllOnPage = false;

    // Filtros backend y paginación
    this.lastSearch = null;
    this.currentPage = 0;
    this.resultsPerPage = 20;
    this.pageSize = 20;

    // Recargar sin filtros
    this.loadPage(0, this.pageSize, 'Cargando Facturas...');
  }

  // ==========================
  // TABLA: BÚSQUEDA LOCAL
  // ==========================

  /**
   * Filtro local (no llama backend).
   * Limpia la selección visual de la página visible para evitar inconsistencias.
   */
  searchInvoices() {
    this.rows = this.rows2.filter((row) =>
      JSON.stringify(row).toLowerCase().includes(this.searchedValue.toString().toLowerCase())
    );
    this.selectedRows = [];
    this.selectAllOnPage = false;
  }

  // ==========================
  // SELECCIÓN: HELPERS
  // ==========================

  /** ¿La fila está seleccionada globalmente? */
  isRowChecked(row: Billing): boolean {
    return this.selectedIds.has(row.id as unknown as number);
  }

  /** Toggle de una fila (actualiza Set global y la selección visual) */
  toggleRowChecked(row: Billing, checked: boolean) {
    const id = row.id as unknown as number;
    if (checked) this.selectedIds.add(id);
    else this.selectedIds.delete(id);

    this.selectedRows = this.rows2.filter(r => this.selectedIds.has(r.id as unknown as number));
    this.selectAllOnPage = this.allOnPageSelected();
  }

  /** ¿Todas las filas visibles están seleccionadas? */
  allOnPageSelected(): boolean {
    return this.rows.length > 0 && this.rows.every(r => this.selectedIds.has(r.id as unknown as number));
  }

  /** Seleccionar/deseleccionar todas las filas visibles */
  toggleAllOnPage(checked: boolean) {
    if (checked) {
      this.rows.forEach(r => this.selectedIds.add(r.id as unknown as number));
    } else {
      this.rows.forEach(r => this.selectedIds.delete(r.id as unknown as number));
    }
    this.selectedRows = this.rows2.filter(r => this.selectedIds.has(r.id as unknown as number));
    this.selectAllOnPage = this.allOnPageSelected();
  }

  /** Limpia sólo la selección de la página visible (persiste el resto) */
  clearSelectionOnPage() {
    this.rows.forEach(r => this.selectedIds.delete(r.id as unknown as number));
    this.selectedRows = this.rows2.filter(r => this.selectedIds.has(r.id as unknown as number));
    this.selectAllOnPage = this.allOnPageSelected();
  }

  /** Cambio de página (conserva filtros si existen) */
  onPageChange(event: any) {
    this.loadPage(event.offset, this.pageSize, 'Cargando Facturas...');
  }

  // ==========================
  // ACCIONES: EMISION
  // ==========================

  /** Cambio de tamaño de página (reinicia a 0 y respeta filtros activos) */
  onResultsPerPageChange(size: number) {
    this.resultsPerPage = size;
    this.loadPage(0, size, 'Cargando Facturas...');
  }

  // ==========================
  // MODALES
  // ==========================

  /**
   * Abre modal de detalle de factura
   */
  openDetailModal(button: Buttons, row: Billing | null = null) {
    const modalRef = this.modalService.open(DetailInvoiceModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billing = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.messageEvent.subscribe((_billing: Billing) => { });
  }

  /**
   * Abre modal de autorización con las seleccionadas
   */
  openEmissionModal() {
    const selected = this.rows2.filter(r => this.selectedIds.has(r.id as unknown as number));
    if (!selected.length) return;

    const modalRef = this.modalService.open(EmissionModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.selected = selected;
    modalRef.componentInstance.parameters = this.parameters;

    modalRef.componentInstance.messageEvent.subscribe(async (ok: boolean) => {
      if (ok) {
        this.selectedIds.clear();
        this.selectedRows = [];
        this.selectAllOnPage = false;
        this.loadPage(this.currentPage, this.pageSize, 'Actualizando...');
      }
    });
  }

  // ==========================
  // PARÁMETROS (CONFIG)
  // ==========================

  /**
   * Carga parámetros de la pantalla/estados/roles por id de aplicación
   */
  private configParametersById(id: any): Promise<boolean> {
    return new Promise((resolve) => {
      this.bulkEmissionService.configParametersById(id).subscribe((response) => {
        if (response.status === 200) {
          if (id === 1000) this.parametersInvoiceStatus = [];
          else this.parameters = [];

          const configParameterResponse = response.body as ConfigParameterResponse;
          configParameterResponse.data.forEach((dto: ConfigParameter) => {

            // Estados de factura
            if (id === 1000) {
              this.parametersInvoiceStatus.push(dto);
              if (dto.parameterType === 'INVOICE_STATUS') {
                this.statusNames[dto.stateCode] = dto.parameterValue;
              }
            }

            // Tipos de usuario que pueden autorizar
            if (id === 1002) {
              if (dto.parameterName === 'TYPE_USER_EMIT') {
                this.parametersUserAuth1002.push(dto.parameterValue);
              }
            }

            // Parámetros UI de pantalla
            if (id === 4439) {
              this.parameters.push(dto);
            }
          });

          if (id === 4439) this.parameters = [...this.parameters];
          if (id === 1000) this.parametersInvoiceStatus = [...this.parametersInvoiceStatus];
          if (id === 1002) this.parametersUserAuth1002 = [...this.parametersUserAuth1002];

          resolve(true);
        } else {
          resolve(false);
        }
      }, _ => resolve(false));
    });
  }

  // ==========================
  // CARGA DE DATOS (API)
  // ==========================

  /** Loader SweetAlert (abre) */
  private openLoading(title = 'Cargando Facturas...') {
    return Swal.fire({
      title,
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
  }

  /** Loader SweetAlert (cierra) */
  private closeLoading() { Swal.close(); }

  /**
   * Aplica la respuesta a la tabla y rehidrata selección según `selectedIds`
   */
  private applyResponse(billingResponse: InvoicePagesResponse) {
    // Paginación
    this.totalElements = billingResponse.data.totalElements;
    this.totalPages = billingResponse.data.totalPages;
    this.currentPage = billingResponse.data.number;

    // Mapeo de filas
    this.rows = billingResponse.data.content.map((dto: Billing) => {
      dto.buttonAuth = false;
      dto.buttonEmit = false;
      dto.statusCode = dto.status;
      dto.status = this.statusNames[dto.status] || 'Sin estado';
      return dto;
    });

    // Copia base para filtro local
    this.rows2 = [...this.rows];

    // Rehidratar selección visual en página actual (según selectedIds)
    this.selectedRows = this.rows2.filter(r => this.selectedIds.has(r.id as unknown as number));
    this.selectAllOnPage = this.allOnPageSelected();

    this.loadingIndicator = false;
  }

  /**
   * Decide el endpoint según haya búsqueda activa (`lastSearch`)
   */
  private fetchPage(page = this.currentPage, size = this.pageSize) {
    const seller = this.utilService.getSystemUser();
    if (this.lastSearch) {
      return this.bulkEmissionService.searchBulkEmission(
        page, size, seller, this.lastSearch.customer, this.lastSearch.customerId
      );
    }
    return this.bulkEmissionService.getBulkEmission(page, size, seller);
  }

  /**
 * Carga una página (con o sin filtros activos) y muestra mensajes adecuados:
 * - “Datos cargados” si hay filas
 * - “No se encontraron resultados…” si vienes de una búsqueda sin filas (usa queryLabel + sanitize)
 * - “No hay facturas disponibles” si no hay filtros ni datos
 */
  loadPage(page = 0, size = this.pageSize, loadingTitle = 'Cargando Facturas...') {
    this.openLoading(loadingTitle);
    this.currentPage = page;
    this.pageSize = size;

    this.fetchPage(page, size)
      .pipe(finalize(() => this.closeLoading()))
      .subscribe({
        next: (resp) => {
          if (resp.status === 200) {
            const body = resp.body as InvoicePagesResponse;
            this.applyResponse(body);

            if (this.rows.length > 0) {
              this.utilService.showNotification(0, 'Datos cargados');
            } else {
              if (this.lastSearch) {
                const isCustomer = this.lastSearch.customer !== undefined;
                const type = (isCustomer ? 'customer' : 'acctCode') as 'customer' | 'acctCode';
                const label = this.queryLabel(type);
                const value = this.sanitize(this.lastSearch.customer ?? this.lastSearch.customerId ?? '');
                this.utilService.showNotification(1, `No se encontraron resultados para ${label} "${value}".`);
              } else {
                this.utilService.showNotification(1, 'No hay facturas disponibles.');
              }
            }
          }
        },
        error: () => {
          this.utilService.showNotification(3, 'Ocurrió un error al cargar facturas');
        }
      });
  }
}
