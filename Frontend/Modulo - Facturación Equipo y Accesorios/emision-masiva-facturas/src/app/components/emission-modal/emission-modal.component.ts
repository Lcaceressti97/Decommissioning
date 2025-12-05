import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { take } from 'rxjs/operators';
import { Billing, ConfigParameter, TypeUserModel } from 'src/app/models/InvoicesModel';
import { ControlUserPermissions } from 'src/app/models/UserControlPermissionsModel';
import { BulkEmissionService } from 'src/app/services/bulk-emission.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceService } from 'src/app/services/invoice.service';
import { BulkPrintInvoicesComponent } from 'src/app/components/bulk-print-invoices/bulk-print-invoices.component';

@Component({
  selector: 'app-emission-modal',
  templateUrl: './emission-modal.component.html',
  styleUrls: ['./emission-modal.component.css']
})
export class EmissionModalComponent implements OnInit {
  @Input() selected: Billing[] = [];
  @Input() parameters: ConfigParameter[] = [];
  @Input() parametersUserAuth1002: string[] = [];

  @Output() messageEvent = new EventEmitter<boolean>();
  form!: FormGroup;
  totalSelected = 0;
  typeTransactionLabel = 'Emisión';
  previewCount = 5;

  // Permisos
  controlUserPermissions: ControlUserPermissions | null = null;
  typeUser: TypeUserModel = {} as any;

  // UI
  showAll = false;
  isSubmitting = false;

  private emitTimerId: any = null;
  private emitStartedAt = 0;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private bulkEmissionService: BulkEmissionService,
    private utilService: UtilService,
    private modalService: NgbModal,
    private invoiceService: InvoiceService
  ) { }

  async ngOnInit(): Promise<void> {
    this.totalSelected = this.selected?.length ?? 0;

    const typeCfg = this.parameters?.find(p => p.stateCode === 2);
    if (typeCfg?.parameterValue) this.typeTransactionLabel = typeCfg.parameterValue;

    this.form = this.fb.group({
      typeApproval: [{ value: this.typeTransactionLabel, disabled: true }],
      paymentCode: [''],
      description: ['', [Validators.required]],
    });

    // Cargar permisos y tipo de usuario
    await this.ensurePermissionsLoaded();
  }

  // ---------- Getters UI ----------
  get descCtrl(): AbstractControl | null { return this.form.get('description'); }
  get displayedRows() { return this.showAll ? this.selected : this.selected.slice(0, this.previewCount); }
  get remainingCount(): number { return Math.max((this.selected?.length || 0) - this.previewCount, 0); }
  togglePreview() { this.showAll = !this.showAll; }
  close() { this.activeModal.dismiss(); }

  // ---------- Permisos ----------
  private async ensurePermissionsLoaded(): Promise<void> {
    if (this.controlUserPermissions) return;

    const username =
      this.utilService?.getSystemUser?.() ||
      (this.utilService as any)?.getUserName?.() ||
      (this.utilService as any)?.getSystemUserName?.() ||
      'SYSTEM';

    // 1) Cargar permisos del usuario
    await new Promise<void>((resolve) => {
      this.bulkEmissionService.getControlUserPermissons(username).pipe(take(1)).subscribe({
        next: (response) => {
          if (response?.status === 200) {
            this.controlUserPermissions = response.body?.data || null;
          }
          resolve();
        },
        error: () => resolve(),
      });
    });

    // 2) Cargar tipo de usuario (si tenemos permisos)
    if (this.controlUserPermissions?.typeUser != null) {
      await new Promise<void>((resolve) => {
        this.bulkEmissionService.getTypeUser(this.controlUserPermissions!.typeUser).pipe(take(1)).subscribe({
          next: (resp) => {
            if (resp?.status === 200) this.typeUser = resp.body?.data || {};
            resolve();
          },
          error: () => resolve(),
        });
      });
    }
  }


  private canIssue(): { ok: boolean; msg?: string } {
    if (!this.controlUserPermissions) {
      return { ok: false, msg: 'No tiene permiso para emitir facturas.' };
    }

    const userType = this.typeUser?.typeUser || '(desconocido)';

    if (this.parametersUserAuth1002?.length &&
      !this.parametersUserAuth1002.includes(userType)) {
      return { ok: false, msg: `El usuario ${this.controlUserPermissions.userName} es ${userType}; no puede emitir.` };
    }

    // Permiso específico para EMISIÓN
    if (this.controlUserPermissions.generateBill === 'N') {
      return { ok: false, msg: 'No tiene permiso para emitir facturas.' };
    }

    return { ok: true };
  }

  // ---------- Acción principal ----------
  /** Color suave por código de error (estilo inline) */
  private codeStyle(code: string): string {
    const k = String(code || '').toUpperCase();
    if (k === 'CONNECTION_REFUSED') return 'background:#fff3f0;border:1px solid #ffd6cc;color:#b23b25;';
    if (k === 'TIMEOUT') return 'background:#fff9e6;border:1px solid #ffe9a6;color:#a36b00;';
    if (k === 'BAD_REQUEST') return 'background:#f2f8ff;border:1px solid #cfe5ff;color:#124b9a;';
    if (k === 'NOT_FOUND') return 'background:#f0fff8;border:1px solid #c7f5e1;color:#0a7a55;';
    return 'background:#f5f5f5;border:1px solid #e6e6e6;color:#555;';
  }

  /** Presenta el resultado en tabla con estilos inline (robusto) */
  private async presentBatchResult(body: any) {
    const total = Number(body?.totalRequested ?? 0);
    const okCount = Number(body?.totalSuccess ?? 0);
    const failCount = Number(body?.totalFailed ?? 0);
    const items: any[] = Array.isArray(body?.items) ? body.items : [];

    const failed = items
      .filter(x => !x?.success)
      .map(x => ({
        id: x?.idPrefecture ?? x?.idPrefactura ?? x?.id ?? '?',
        service: String(x?.service ?? '—'),
        code: String(x?.errorCode ?? 'UNEXPECTED'),
        error: String(x?.message ?? 'Error'),
      }))
      .sort((a, b) => String(a.id).localeCompare(String(b.id)));

    const rowsHtml = failed.map(r => `
    <tr>
      <td style="padding:12px 14px;white-space:nowrap;border-bottom:1px solid #eef0f2;">
        <span style="display:inline-block;background:#eef3ff;color:#1643d4;border-radius:999px;padding:3px 10px;font-weight:700;">${this.escapeHtml(String(r.id))}</span>
      </td>
      <td style="padding:12px 14px;white-space:nowrap;border-bottom:1px solid #eef0f2;">
        <span style="display:inline-block;background:#f6f7f9;border:1px solid #eceff3;border-radius:8px;padding:3px 10px;">${this.escapeHtml(r.service)}</span>
      </td>
      <td style="padding:12px 14px;white-space:nowrap;border-bottom:1px solid #eef0f2;">
        <span style="display:inline-block;border-radius:8px;padding:3px 10px;${this.codeStyle(r.code)}">${this.escapeHtml(r.code)}</span>
      </td>
      <td style="padding:12px 14px;border-bottom:1px solid #eef0f2;color:#2b2b2b;word-break:break-word;">${this.escapeHtml(r.error)}</td>
    </tr>
  `).join('');

    const html = `
  <div style="font-size:14px; text-align:left;">
    <!-- Counters centrados y con el mismo tamaño que el header -->
    <div style="
      display:flex; justify-content:center; align-items:center; gap:12px;
      margin:2px 0 12px; width:100%; font-size:14px; font-weight:600;
    ">
      <span>
        <b style="display:inline-block;min-width:34px;text-align:center;padding:3px 12px;border-radius:999px;background:#eef3ff;color:#0b48b7;">${this.escapeHtml(String(total))}</b>
        <span style="margin-left:6px;">Total</span>
      </span>
      <span style="color:#c7c7c7;">|</span>
      <span>
        <b style="display:inline-block;min-width:34px;text-align:center;padding:3px 12px;border-radius:999px;background:#e9f7ef;color:#1e7e34;">${this.escapeHtml(String(okCount))}</b>
        <span style="margin-left:6px;">Emitidas</span>
      </span>
      <span style="color:#c7c7c7;">|</span>
      <span>
        <b style="display:inline-block;min-width:34px;text-align:center;padding:3px 12px;border-radius:999px;background:#fdecea;color:#c0392b;">${this.escapeHtml(String(failCount))}</b>
        <span style="margin-left:6px;">Fallidas</span>
      </span>
    </div>

    ${failCount ? `
      <details open>
       <summary style="cursor:pointer;font-weight:700;margin-bottom:10px;display:flex;align-items:center;justify-content:center;width:100%;text-align:center;">
  Ver detalles (${failed.length})
</summary>


        <!-- Caja con estilo del modal + scroll interno -->
        <div style="
          max-height:42vh; overflow:auto; background:#ffffff;
          border:1px solid #e7e9ee; border-radius:12px; box-shadow:0 6px 18px rgba(16,24,40,.06);
        ">
          <table style="width:100%; border-collapse:separate; border-spacing:0; font-size:14px;">
            <thead style="
              position:sticky; top:0; z-index:1;
              background:#fafbfc; border-bottom:1px solid #e7e9ee;
              box-shadow:inset 0 -1px 0 #e7e9ee; 
            ">
              <tr>
                <th style="text-align:center; padding:12px 14px; font-weight:700; color:#111827;">No. Prefactura</th>
                <th style="text-align:center; padding:12px 14px; font-weight:700; color:#111827;">Servicio</th>
                <th style="text-align:center; padding:12px 14px; font-weight:700; color:#111827;">Código</th>
                <th style="text-align:center; padding:12px 14px; font-weight:700; color:#111827;">Mensaje</th>
              </tr>
            </thead>
            <tbody>
              ${rowsHtml || `<tr><td colspan="4" style="text-align:center; padding:14px;color:#6b7280;">Sin errores que mostrar</td></tr>`}
            </tbody>
          </table>
        </div>

        ${failed.length > 500 ? `<div style="margin-top:8px;color:#6b7280;">… y ${failed.length - 500} más</div>` : ``}
      </details>
    ` : ``}
  </div>`.trim();

    await Swal.fire({
      icon: failCount ? 'warning' : 'success',
      title: failCount ? 'Emisión Parcial' : 'Emisión Completada',
      html,
      width: 860,
      confirmButtonText: 'OK',
    });
  }


  /** Presenta el resultado de la emisión con una vista más rica */

  async issue() {
    // 1) Validación básica del formulario
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // 2) Permisos
    await this.ensurePermissionsLoaded();
    const perm = this.canIssue();
    if (!perm.ok) {
      await Swal.fire({
        icon: 'error',
        title: 'Permisos insuficientes',
        text: perm.msg || 'No tiene permiso para emitir facturas.'
      });
      return;
    }

    // 3) Datos de la emisión
    const ids = (this.selected || []).map(s => s.id).filter(Boolean);
    const desc = String(this.form.get('description')?.value || 'Emisión masiva').trim();
    const paymentCode = (String(this.form.get('paymentCode')?.value || '').trim() || null);

    // 4) Confirmación
    const confirm: any = await Swal.fire({
      title: 'Emitir Facturas',
      text: `Se emitirán ${ids.length} factura(s). ¿Está seguro que desea ejecutar esta acción?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí',
      cancelButtonText: 'No'
    });
    if (!confirm.isConfirmed && !confirm.value) return;

    // 5) Ejecutar emisión
    this.isSubmitting = true;
    this.emitStartedAt = Date.now();

    // dentro de issue(), justo donde llamas Swal.fire(...)
    Swal.fire({
      title: 'Emitiendo…',
      html: `
    <div style="margin-top:8px;font-size:16px;color:#374151;">
      Tiempo transcurrido: <b id="emit-timer">00:00</b>
    </div>
  `,
      allowOutsideClick: false,
      showConfirmButton: false,

      // v11+
      didOpen: () => this.startEmitTimer(),
      willClose: () => this.stopEmitTimer(),

      // v10/v9 (compat)
      onOpen: () => this.startEmitTimer(),
      onClose: () => this.stopEmitTimer(),
    });



    const userCreate =
      this.utilService?.getSystemUser?.() ||
      (this.utilService as any)?.getUserName?.() ||
      (this.utilService as any)?.getSystemUserName?.() ||
      'SYSTEM';

    const req = this.bulkEmissionService.buildBulkEmissionRequest(
      ids,
      userCreate,
      desc,
      null,
      paymentCode
    );

    this.bulkEmissionService.emissionInvoices(req).pipe(take(1)).subscribe({
      next: async (resp: any) => {
        if (this.emitTimerId) { clearInterval(this.emitTimerId); this.emitTimerId = null; }
        Swal.close();

        // 6) Mostrar resumen (parcial/completo) de la emisión
        const body = resp?.body ?? resp ?? {};
        await this.presentBatchResult(body);

        // 7) Obtener IDs emitidos con éxito
        const successIds = this.extractSuccessIds(body);

        // 8) SI/NO imprimir
        if (successIds.length > 0) {
          const ask = await Swal.fire({
            title: '¿Desea imprimir las facturas?',
            html: `Se imprimirá(n) <b>${successIds.length}</b> factura(s) emitida(s) con éxito.`,
            iconHtml: '<i class="fa fa-print"></i>',
            customClass: { icon: 'swal2-icon-print' },
            showCancelButton: true,
            confirmButtonText: 'Ver detalle',
            cancelButtonText: 'No',
            confirmButtonColor: '#002e6e',
            cancelButtonColor: '#d33'
          });


          if (ask.isConfirmed || ask.value === true) {
            // 9) Traer billings completos y abrir modal de impresión masiva
            const bills = await this.fetchBillings(successIds);

            if (bills.length) {
              const ref = this.modalService.open(BulkPrintInvoicesComponent, {
                size: 'md',
                backdrop: 'static',
                keyboard: false
              });
              ref.componentInstance.billings = bills;
              ref.componentInstance.autoPrint = false;
              // 10) Al terminar la impresión: notificar éxito y refrescar padre
              ref.componentInstance.printedIds.subscribe((printed: string[]) => {
                // Opcional: si gestionas una lista local, puedes filtrarlas aquí.
                // this.selected = (this.selected || []).filter(s => !printed.includes(String(s.id)));

                // Éxito y refresh del padre
                Swal.fire({
                  icon: 'success',
                  title: 'La operación se realizó con éxito',
                  timer: 1400,
                  showConfirmButton: false
                });

                this.messageEvent.emit(true);
              });
            }
          }
        }

        // 11) Cerrar el modal de emisión masiva y liberar estado
        this.isSubmitting = false;
        this.messageEvent.emit(true);
        this.activeModal.close();
      },
      error: async (err) => {
        if (this.emitTimerId) { clearInterval(this.emitTimerId); this.emitTimerId = null; }
        Swal.close();
        const msgs = await this.extractServerErrors(err);
        const html = `
        <div class="e-batch">
          <div class="e-counters">
            <div class="e-chip e-fail"><span>${msgs.length}</span> Error(es)</div>
          </div>
          <ul class="e-list">
            ${msgs.slice(0, 10).map(m => `
              <li>
                <span class="e-pill">•</span>
                <span class="e-msg">${this.escapeHtml(m)}</span>
              </li>`).join('')}
          </ul>
          ${msgs.length > 10 ? `<div class="e-more">… y ${msgs.length - 10} más</div>` : ''}
        </div>`;

        await Swal.fire({
          icon: 'error',
          title: 'Error en la emisión',
          html,
          width: 760,
          confirmButtonText: 'Entendido',
          customClass: {
            popup: 'swal2-rounded',
            title: 'swal2-title-strong',
            confirmButton: 'swal2-confirm-strong'
          }
        });

        this.isSubmitting = false;
      }
    });
  }

  private async extractServerErrors(err: any): Promise<string[]> {
    try {
      if (err && err.status === 0 && err.message) {
        return [err.message];
      }

      if (err?.error && typeof err.error === 'object') {
        const arr = err.error.errors;
        if (Array.isArray(arr) && arr.length) {
          return arr.map((e: any) => e.userMessage || e.internalMessage || e.description || e.code || 'Error');
        }
        if (err.error.message) return [String(err.error.message)];
      }

      if (typeof err?.error === 'string') {
        const parsed = JSON.parse(err.error);
        const arr = parsed?.errors;
        if (Array.isArray(arr) && arr.length) {
          return arr.map((e: any) => e.userMessage || e.internalMessage || e.description || e.code || 'Error');
        }
        if (parsed?.message) return [String(parsed.message)];
      }

      if (err?.error instanceof Blob) {
        const text = await new Promise<string>((resolve) => {
          const fr = new FileReader();
          fr.onload = () => resolve(String(fr.result || ''));
          fr.readAsText(err.error);
        });
        if (text) {
          try {
            const parsed = JSON.parse(text);
            const arr = parsed?.errors;
            if (Array.isArray(arr) && arr.length) {
              return arr.map((e: any) => e.userMessage || e.internalMessage || e.description || e.code || 'Error');
            }
            if (parsed?.message) return [String(parsed.message)];
          } catch {
            return [text];
          }
        }
      }
    } catch {/* ignore */ }
    return [err?.message || 'Ocurrió un error procesando la emisión.'];
  }

  private escapeHtml(s: string): string {
    return (s || '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
  }

  // Devuelve los IDs de prefacturas/facturas emitidas con éxito desde el body del API
  private extractSuccessIds(body: any): number[] {
    const items = Array.isArray(body?.items) ? body.items : [];
    return items
      .filter((x: any) => x?.success)
      .map((x: any) => Number(x?.idPrefecture ?? x?.id ?? x?.invoiceId))
      .filter((n: number) => Number.isFinite(n));
  }

  // Pide al backend la info completa (Billing) para cada ID
  private async fetchBillings(successIds: number[]): Promise<Billing[]> {
    const calls = successIds.map(idNum =>
      new Promise<Billing>((resolve) => {
        this.invoiceService.getInvoiceById(idNum).pipe(take(1)).subscribe({
          next: (resp: any) => {
            if (resp?.status === 200 && resp.body?.data) resolve(resp.body.data as Billing);
            else resolve(null as any);
          },
          error: () => resolve(null as any)
        });
      })
    );

    const results = await Promise.all(calls);
    return results.filter(Boolean);
  }


  private formatElapsed(ms: number): string {
    const sec = Math.floor(ms / 1000);
    const h = Math.floor(sec / 3600);
    const m = Math.floor((sec % 3600) / 60);
    const s = sec % 60;
    const pad = (n: number) => String(n).padStart(2, '0');
    return h > 0 ? `${pad(h)}:${pad(m)}:${pad(s)}` : `${pad(m)}:${pad(s)}`;
  }

  private startEmitTimer() {
    const popup = (Swal as any).getPopup?.() as HTMLElement | undefined;
    const el = popup?.querySelector('#emit-timer') as HTMLElement | null;

    this.emitStartedAt = Date.now();

    setTimeout(() => {
      this.emitTimerId = window.setInterval(() => {
        if (!el || !(Swal as any).isVisible?.()) return;
        el.textContent = this.formatElapsed(Date.now() - this.emitStartedAt);
      }, 250);
    }, 0);
  }

  private stopEmitTimer() {
    if (this.emitTimerId) {
      clearInterval(this.emitTimerId);
      this.emitTimerId = null;
    }
  }

}

