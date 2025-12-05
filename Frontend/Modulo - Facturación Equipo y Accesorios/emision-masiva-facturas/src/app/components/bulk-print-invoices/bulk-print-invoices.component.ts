import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import * as printJS from 'print-js';
import { UtilService } from 'src/app/services/util.service';
import { Billing, BrancheResponse, InvoiceDetail } from 'src/app/models/InvoicesModel';
import { InvoiceService } from 'src/app/services/invoice.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-bulk-print-invoices',
  templateUrl: './bulk-print-invoices.component.html',
  styleUrls: ['./bulk-print-invoices.component.css']
})
export class BulkPrintInvoicesComponent implements OnInit, AfterViewInit {

  // Props
  @Input() billings: Billing[] = [];
  @Input() autoPrint = false;
  @Output() printedIds = new EventEmitter<string[]>();

  // BrancheOffice
  branches = new Map<string | number, any>();
  userSystem = '';

  // Variables calculadas
  private totalsLps = new Map<number, number>();
  private totalsLpsLetters = new Map<number, string>();
  private taxableValuesMap = new Map<number, number>();
  private creditCashMap = new Map<number, string>();

  constructor(
    private activeModal: NgbActiveModal,
    private invoiceService: InvoiceService,
    public utilService: UtilService
  ) { }

  async ngOnInit() {
    this.userSystem = this.utilService.getSystemUser?.() || 'SYSTEM';

    const uniqueBranchIds = Array.from(new Set(this.billings.map(b => b.idBranchOffices).filter(Boolean)));

    await Promise.all(
      uniqueBranchIds.map(id => new Promise<void>((resolve) => {
        this.invoiceService.getBrancheById(id).subscribe({
          next: (resp) => {
            if (resp?.status === 200) {
              const data = (resp.body as BrancheResponse)?.data;
              if (data) this.branches.set(id, data);
            }
            resolve();
          },
          error: () => resolve()
        });
      }))
    );

    // Calcular totales, letras, valores imponibles y crédito/efectivo por factura
    for (const b of this.billings) {
      const id = Number(b.id);

      if (b.fiscalProcess === 'Facturación por Reclamo de Seguros') {
        this.taxableValuesMap.set(id, (b.subtotal || 0) - (b.exemptAmount || 0));
        this.creditCashMap.set(id, 'CONTRA ENTREGA (SIN INT');
      } else {
        this.taxableValuesMap.set(id, (b.subtotal || 0) - (b.discount || 0));
        this.creditCashMap.set(id, '');
      }

      if (b.totalLps != null && b.totalLpsLetters) {
        this.totalsLps.set(id, Number(b.totalLps));
        this.totalsLpsLetters.set(id, b.totalLpsLetters);
      } else {
        const total = Number(((b.amountTotal || 0) * (b.exchangeRate || 0)).toFixed(2));
        this.totalsLps.set(id, total);
        this.totalsLpsLetters.set(id, await this.convertNumbersToLetters(total));
      }
    }
  }

  ngAfterViewInit(): void {
    if (this.autoPrint) {
      setTimeout(() => this.printAll(), 300);
    }
  }

  // ===== Helpers per invoice =====
  branchOf(b: Billing): any {
    return this.branches.get(b.idBranchOffices) || {};
  }

  totalOf(b: Billing): number {
    return this.totalsLps.get(Number(b.id)) ?? 0;
  }

  letterOf(b: Billing): string {
    return this.totalsLpsLetters.get(Number(b.id)) ?? '';
  }

  taxableValuesOf(b: Billing): number {
    return this.taxableValuesMap.get(Number(b.id)) ?? 0;
  }

  creditCashOf(b: Billing): string {
    return this.creditCashMap.get(Number(b.id)) ?? '';
  }

  calculateValue(b: Billing, inv: InvoiceDetail): number {
    if (b.fiscalProcess === 'Facturación por Reclamo de Seguros' && inv.serieOrBoxNumber != null) {
      return inv.amountTotal ?? (inv.unitPrice * inv.quantity);
    }
    return inv.unitPrice * inv.quantity;
  }

  formatDue(b: Billing): Date | null {
    return (b.invoiceType === 'FC1' || b.invoiceType === 'FC3' || b.invoiceType === 'FS1' ||
      b.invoiceType === 'FS3' || b.invoiceType === 'SHP' || b.invoiceType === 'SHO')
      ? (b.created ? new Date(b.created) : null)
      : (b.dueDate ? new Date(b.dueDate) : null);
  }

  formatDate(deadLine: string): string {
    const parts = (deadLine || '').split('-');
    if (parts.length < 3) return '';
    return `${parts[0]}-${parts[1]}-${parts[2]}T00:00:00`;
  }

  closeModal() {
    this.activeModal.close();
  }

  // ===== Print all invoices =====
  printAll() {
    const wrapper = document.getElementById('invoices-container');
    const html = wrapper ? wrapper.innerHTML : '';

    const configuration: printJS.Configuration = {
      printable: html,
      type: 'raw-html',
      style: `
  .container { width:100%; margin-right:-10px; margin-left:10px; }
  .row { display:flex; flex-wrap:wrap; }
  .col-12 { flex:0 0 100%; max-width:100%; }
  .col-9 { flex:0 0 75%; max-width:75%; }
  .col-8 { flex:0 0 66.66666666%; max-width:66.66666666%; }
  .col-6 { flex:0 0 50%; max-width:50%; }
  .col-4 { flex:0 0 33.33333333%; max-width:33.33333333%; }
  .col-3 { flex:0 0 25%; max-width:25%; }
  .col-2 { flex:0 0 16.66666667%; max-width:16.66666667%; }
  .test-font { font-family: Calibri, sans-serif; font-size:13px; font-weight:normal!important; }
  .without-padding-right { padding-right:0; }

  @media print {
    /* 1) cada factura en página nueva */
    .invoice-page { page-break-after: always; break-after: page; }
    .invoice-page:last-child { page-break-after: auto; break-after: auto; }

    /* 2) no partir el contenido interno si cabe */
    .invoice-root, .invoice-page, .invoice-page .container {
      page-break-inside: avoid; break-inside: avoid-page;
    }

    /* 3) ocultar separador */
    .screen-separator { display: none !important; }
  }
`
    };


    printJS(configuration);

    const ids = this.billings.map(b => String(b.id));
    this.printedIds.emit(ids);
  }

  /**
    * Conversión número → letras
    */
  convertNumbersToLetters(numbers: number): Promise<string> {
    return new Promise((resolve) => {
      const numberStr: string = numbers.toFixed(2);
      let intNumber: number = parseInt(numberStr, 10);
      let booleanTen = false;
      let decimalLetters = '00/100';

      if (numberStr.includes('.')) {
        const [_, dec] = numberStr.split('.');
        decimalLetters = `${dec}/100`;
      }

      const units = ['', 'UNO', 'DOS', 'TRES', 'CUATRO', 'CINCO', 'SEIS', 'SIETE', 'OCHO', 'NUEVE'];
      const tens = ['DIEZ', 'ONCE', 'DOCE', 'TRECE', 'CATORCE', 'QUINCE', 'DIECISEIS', 'DIECISIETE', 'DIECIOCHO', 'DIECINUEVE'];
      const tensHigh = ['VEINTI', 'TREINTA', 'CUARENTA', 'CINCUENTA', 'SESENTA', 'SETENTA', 'OCHENTA', 'NOVENTA'];
      const hundreds = ['CIENTO', 'DOSCIENTOS', 'TRESCIENTOS', 'CUATROCIENTOS', 'QUINIENTOS', 'SEISCIENTOS', 'SETESIENTOS', 'OCHOCIENTOS', 'NOVECIENTOS'];

      if (intNumber === 0) {
        resolve('CERO CON ' + decimalLetters + ' (LEMPIRAS)');
        return;
      }

      let letters = '';

      // Millones
      if (intNumber >= 1000000) {
        if (intNumber === 1000000) {
          letters += 'UN MILLON ';
        } else {
          const numberMillions = Math.floor(intNumber / 1000000).toString();
          if (!numberMillions.endsWith('00') && numberMillions.length === 3) {
            let n = Number(numberMillions);
            letters += hundreds[Math.floor(n / 100) - 1] + ' ';
            n %= 100;
            if (n.toString().endsWith('0')) {
              letters += tensHigh[Math.floor(n / 10) - 2] + ' ';
            } else {
              letters += tensHigh[Math.floor(n / 10) - 2] + ' Y ';
            }
            n %= 10;
            letters += units[n] + ' MILLONES ';
          } else if (!numberMillions.endsWith('0') && numberMillions.length === 2) {
            let n = Number(numberMillions);
            letters += tensHigh[Math.floor(n / 10) - 2] + ' Y ';
            n %= 10;
            letters += units[n] + ' MILLONES ';
          } else {
            if (numberMillions === '1') letters += 'UN MILLON ';
            else letters += units[Number(numberMillions)] + ' MILLONES ';
          }
        }
        intNumber %= 1000000;
      }

      // Miles
      if (intNumber >= 1000) {
        if (intNumber === 1000) {
          letters += 'UN MIL ';
        } else {
          const numberMil = Math.floor(intNumber / 1000).toString();
          if (numberMil.length === 3) {
            let n = Number(numberMil);
            if (numberMil.endsWith('00')) {
              letters += hundreds[Math.floor(n / 100) - 1] + ' MIL ';
            } else {
              const firstHundreds = numberMil.substring(0, 1) + '00';
              letters += hundreds[Math.floor(Number(firstHundreds) / 100) - 1] + ' ';
            }
            n %= 100;
            if (n.toString().length === 2) {
              if (n <= 19 && n >= 10) {
                letters += tens[Math.floor(n / 10) - 2] + ' ';
                n %= 10;
              } else if (n <= 29 && n >= 20) {
                letters += tensHigh[Math.floor(n / 10) - 2] + ' ';
                n %= 10;
                if (n > 0) letters += units[n] + ' ';
              } else {
                const d = Math.floor(n / 10).toString() + '0';
                letters += tensHigh[Math.floor(Number(d) / 10) - 2] + ' ';
                n %= 10;
                if (n > 0) letters = letters + 'Y ' + units[n] + ' MIL ';
                else letters = letters + 'MIL ';
              }
            } else {
              if (letters.includes('MIL')) letters += units[n] + ' MIL ';
              else {
                letters = letters + units[n];
                letters += ' MIL ';
              }
              n %= 10;
            }
          } else if (numberMil.length === 2) {
            let n = Number(numberMil);
            if (numberMil.endsWith('0')) {
              letters += tensHigh[Math.floor(n / 10) - 2] + ' MIL ';
              n %= 10;
            } else {
              if (n <= 19 && n >= 10) {
                const d = n.toString().substring(1, 2);
                letters += tens[Number(d)] + ' MIL ';
                n %= 10;
              } else {
                letters += tensHigh[Math.floor(n / 10) - 2] + ' Y ';
                n %= 10;
                letters += units[n] + ' MIL ';
              }
            }
          } else {
            letters += units[Math.floor(intNumber / 1000)] + ' MIL ';
          }
        }
        intNumber %= 1000;
      }

      // Centenas
      if (intNumber >= 100) {
        if (intNumber === 100) letters += 'CIEN ';
        else letters += hundreds[Math.floor(intNumber / 100) - 1] + ' ';
        intNumber %= 100;
      }

      // Decenas
      if (intNumber >= 20) {
        if (intNumber >= 21 && intNumber <= 29) {
          letters += tensHigh[Math.floor(intNumber / 10) - 2];
        } else {
          letters += tensHigh[Math.floor(intNumber / 10) - 2] + ' ';
          booleanTen = true;
        }
        intNumber %= 10;
      }

      if (intNumber >= 10) {
        letters += tens[intNumber - 10] + ' ';
        intNumber = 0;
      }

      if (intNumber > 0) {
        if (booleanTen) letters += 'Y ';
        letters += units[intNumber] + ' ';
      }

      letters += 'CON ' + decimalLetters + ' (LEMPIRAS)';
      resolve(letters.trim());
    });
  }
}
