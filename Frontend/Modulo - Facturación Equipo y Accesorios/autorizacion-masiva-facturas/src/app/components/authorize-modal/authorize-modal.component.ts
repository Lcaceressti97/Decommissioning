import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { finalize, take } from 'rxjs/operators';

import { Billing, ConfigParameter, TypeUserModel } from 'src/app/models/InvoicesModel';
import { BulkAuthorizeService } from 'src/app/services/bulk-authorize.service';
import { UtilService } from 'src/app/services/util.service';
import { ControlUserPermissions } from 'src/app/models/UserControlPermissionsModel';

@Component({
  selector: 'app-authorize-modal',
  templateUrl: './authorize-modal.component.html',
  styleUrls: ['./authorize-modal.component.css']
})
export class AuthorizeModalComponent implements OnInit {
  @Input() selected: Billing[] = [];
  @Input() parameters: ConfigParameter[] = [];
  /** Lista de tipos de usuario que pueden autorizar (igual que en ApprovalsModal) */
  @Input() parametersUserAuth1002: string[] = [];

  @Output() messageEvent = new EventEmitter<boolean>();

  form!: FormGroup;
  totalSelected = 0;
  typeTransactionLabel = 'Autorización';
  previewCount = 5;

  // Permisos
  controlUserPermissions: ControlUserPermissions | null = null;
  typeUser: TypeUserModel = {} as any;

  // UI
  showAll = false;
  isSubmitting = false;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private bulkAuthorizeService: BulkAuthorizeService,
    private utilService: UtilService,
  ) { }

  async ngOnInit(): Promise<void> {
    this.totalSelected = this.selected?.length ?? 0;

    const typeCfg = this.parameters?.find(p => p.stateCode === 1);
    if (typeCfg?.parameterValue) this.typeTransactionLabel = typeCfg.parameterValue;

    this.form = this.fb.group({
      typeApproval: [{ value: this.typeTransactionLabel, disabled: true }],
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
      this.bulkAuthorizeService.getControlUserPermissons(username).pipe(take(1)).subscribe({
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
        this.bulkAuthorizeService.getTypeUser(this.controlUserPermissions!.typeUser).pipe(take(1)).subscribe({
          next: (resp) => {
            if (resp?.status === 200) this.typeUser = resp.body?.data || {};
            resolve();
          },
          error: () => resolve(),
        });
      });
    }
  }

  private canAuthorize(): { ok: boolean; msg?: string } {
    if (!this.controlUserPermissions) {
      return { ok: false, msg: 'No tiene permiso para autorizar facturas.' };
    }

    const userType = this.typeUser?.typeUser || '(desconocido)';

    if (this.parametersUserAuth1002?.length &&
      !this.parametersUserAuth1002.includes(userType)) {
      return {
        ok: false,
        msg: `El usuario ${this.controlUserPermissions.userName} es un ${userType}; no puede autorizar facturas.`,
      };
    }

    // Permiso específico de autorizar
    if (this.controlUserPermissions.authorizeInvoice !== 'Y') {
      return { ok: false, msg: 'No tiene permiso para autorizar facturas.' };
    }

    return { ok: true };
  }

  // ---------- Acción principal ----------

  async authorize() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }

    await this.ensurePermissionsLoaded();
    const perm = this.canAuthorize();
    if (!perm.ok) {
      await Swal.fire({ icon: 'error', title: 'Permisos Insuficientes', text: perm.msg || '' });
      return;
    }

    const ids = this.selected.map(s => s.id);
    if (!ids.length) {
      await Swal.fire({ icon: 'info', title: 'Sin selección', text: 'No hay facturas seleccionadas.' });
      return;
    }

    const desc = (this.descCtrl?.value || 'Autorización masiva').toString().trim();

    const confirm: any = await Swal.fire({
      title: 'Autorizar Facturas',
      text: `Se autorizarán ${ids.length} factura(s). ¿Esta seguro que desea ejecutar esta acción?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí',
      cancelButtonText: 'No'
    });
    if (!confirm.isConfirmed && !confirm.value) return;

    this.isSubmitting = true;

    (Swal.fire as any)({
      title: 'Autorizando...',
      allowOutsideClick: false,
      onBeforeOpen: () => { Swal.showLoading(); }
    });

    const req = {
      invoiceIds: ids,
      user: this.utilService?.getSystemUser?.()
        || (this.utilService as any)?.getUserName?.()
        || (this.utilService as any)?.getSystemUserName?.()
        || 'SYSTEM',
      description: desc,
    };

    this.bulkAuthorizeService.authorizeInvoices(req)
      .pipe(take(1))
      .subscribe({
        next: async (resp: any) => {
          Swal.close();

          const body = resp && resp.body ? resp.body : resp;
          const results = Array.isArray(body?.results) ? body.results : [];

          if (results.length) {
            const ok = results.filter((r: any) => r.success);
            const fail = results.filter((r: any) => !r.success);

            const failHtml = fail.slice(0, 20).map((r: any) =>
              `<li><b>#${this.escapeHtml(String(r.invoiceId))}</b>: ${this.escapeHtml(r.message || 'Error')}</li>`
            ).join('');
            const extra = fail.length > 20 ? `<div class="mt-1">… y ${fail.length - 20} más</div>` : '';

            await Swal.fire({
              icon: fail.length ? 'warning' : 'success',
              title: fail.length ? 'Autorización Parcial' : 'Autorización Completada',
              html: `
              <div style="text-align:left">
                <p><b>Total:</b> ${body.totalRequested} &nbsp; | &nbsp; 
                   <b>OK:</b> ${body.totalAuthorized} &nbsp; | &nbsp; 
                   <b>Fallidas:</b> ${body.totalFailed}</p>
                ${fail.length ? `<div style="max-height:220px; overflow:auto"><ul>${failHtml}</ul>${extra}</div>` : ''}
              </div>`
            });
          } else {
            await Swal.fire({ icon: 'success', title: 'Autorización Completada' });
          }

          this.isSubmitting = false;

          this.messageEvent.emit(true);
          this.activeModal.close();
        },
        error: async (err) => {
          Swal.close();

          if (typeof err === 'function') { try { err = err(); } catch { } }
          const msgs = await this.extractServerErrors(err);
          const html = msgs.map(m => `<li>${this.escapeHtml(m)}</li>`).join('');
          await Swal.fire({ icon: 'error', title: 'No se pudo autorizar', html: `<ul style="text-align:left">${html}</ul>` });

          this.isSubmitting = false;
        }
      });
  }


  private async extractServerErrors(err: any): Promise<string[]> {
    try {
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
    } catch {
    }
    return [err?.message || 'Ocurrió un error procesando la autorización.'];
  }

  private escapeHtml(s: string): string {
    return (s || '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
  }

}
