import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SimcardDetailEntity } from 'src/app/entities/SimcardDetailEntity';
import { ControlOrder } from 'src/app/models/ControlOrder';
import { SimcardControl } from 'src/app/models/SimcardControl';
import { SimcardDetail } from 'src/app/models/SimcardDetail';
import { SimcardPadre } from 'src/app/models/SimcardPadre';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-file-detail-modal',
  templateUrl: './file-detail-modal.component.html',
  styleUrls: ['./file-detail-modal.component.css']
})
export class FileDetailModalComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<SimcardControl>();
  @Input() rowsSimcardControl: SimcardControl;
  @Input() button: Buttons;


  simcardData: SimcardPadre;
  headerFileForm: FormGroup;
  simcardPadre: SimcardPadre;
  simcardDetailData: SimcardDetailEntity[];
  resultsPerPage: number = 10;
  searchedValue: string = "";
  typeValue: string = "";
  rows = [];
  loadingIndicator: boolean = true;
  
  constructor(private simcardService: SimcardControlFileService,
    private route: ActivatedRoute, private activeModal: NgbActiveModal, public utilService: UtilService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.getDataFromFile(this.rowsSimcardControl.id);
    console.log(this.rowsSimcardControl.id);
    this.initForm();
  }

  initForm(): void {
    this.headerFileForm = this.fb.group({
      fileName: [null, Validators.required],
      orderDate: [null, Validators.required],
      customerName: [null, Validators.required],
      quantity: [null, Validators.required],
      type: [null, Validators.required],
      artWork: [null, Validators.required],
      profile: [null, Validators.required],
      batch: [null, Validators.required],
      transportKey: [null, Validators.required],
      simReference: [null, Validators.required],
      graphRef: [null, Validators.required],
      imsi: [null, Validators.required],
      serNb: [null, Validators.required],
      suppliersId: [null, Validators.required],
      supplierName: [null, Validators.required],
    });
  }

  //* COMPONENTS
  getDataFromFile(id: number) {
    this.simcardService.getSimcardControlById(id).subscribe(
      (response) => {
        this.simcardData = this.extractSimcardData(response.body.data);
        this.simcardDetailData = this.mapVarOutData(response.body.data.simcardFile);
        this.headerFileForm.patchValue({
          fileName: this.simcardData?.fileName,
          orderDate: this.simcardData?.orderDate,
          customerName: this.simcardData?.customerName,
          quantity: this.simcardData?.quantity,
          type: this.simcardData?.type,
          artWork: this.simcardData?.artWork,
          profile: this.simcardData?.profile,
          batch: this.simcardData?.batch,
          transportKey: this.simcardData?.transportKey,
          simReference: this.simcardData?.simReference,
          graphRef: this.simcardData?.graphRef,
          imsi: this.simcardData?.imsi,
          serNb: this.simcardData?.serNb,
        });
        this.rows = [...this.simcardDetailData];
        console.log(this.simcardData);
        console.log(this.simcardDetailData);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  mapVarOutData(simcardFileContent: string): SimcardDetailEntity[] {
    const varOutDetails: SimcardDetailEntity[] = [];

    const varOutIndex = simcardFileContent.indexOf("Var out: ICC/IMSI/IMSIb/KI/PIN1/PUK1/PIN2/PUK2/ADM1/ADM2/ADM3/ACC");
    if (varOutIndex !== -1) {
      const varOutSection = simcardFileContent.substring(varOutIndex);

      const lines = varOutSection.split(/\r?\n/);

      let varOutStarted = false;
      for (const line of lines) {
        if (varOutStarted) {
          if (line.trim() !== '') {
            const values = line.trim().split(' ');
            const simcardDetail: SimcardDetailEntity = {
              id: null,
              idSimcardPadre: null,
              icc: values[0],
              imsi: values[1],
              imsib: values[2],
              ki: values[3],
              pin1: values[4],
              puk1: values[5],
              pin2: values[6],
              puk2: values[7],
              adm1: values[8],
              adm2: values[9],
              adm3: values[10],
              acc: values[11],
              status: null,
            };

            varOutDetails.push(simcardDetail);
          }
        } else if (line.trim() === 'Var out: ICC/IMSI/IMSIb/KI/PIN1/PUK1/PIN2/PUK2/ADM1/ADM2/ADM3/ACC') {
          varOutStarted = true;
        }
      }
    }

    return varOutDetails;
  }




  //* UTILS
  searchDetail() {
    this.simcardDetailData = this.simcardDetailData.filter((simcardDetailData) => {
      return JSON.stringify(simcardDetailData)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    this.getDataFromFile(this.rowsSimcardControl.id);
  }

  getTitle() {
    return "Ver Detalle:   " + this.rowsSimcardControl.nameFile;
  }

  closeModal() {
    this.activeModal.close();
  }

  private extractSimcardData(data: any): SimcardPadre {
    const headerLines = data.simcardFile.split('\r\n');
    const headerData: SimcardPadre = {
      id: data.id,
      fileName: this.extractHeaderValue(headerLines, 'File Name:'),
      orderDate: this.extractHeaderValue(headerLines, 'Order Date:'),
      customerName: this.extractHeaderValue(headerLines, 'Customer Name:'),
      quantity: this.extractHeaderValue(headerLines, 'Quantity:'),
      type: this.extractHeaderValue(headerLines, 'Type:'),
      artWork: this.extractHeaderValue(headerLines, 'ArtWork:'),
      profile: this.extractHeaderValue(headerLines, 'Profile:'),
      batch: this.extractHeaderValue(headerLines, 'Batch:'),
      transportKey: this.extractHeaderValue(headerLines, 'Transport Key:'),
      simReference: this.extractHeaderValue(headerLines, 'SIM-Reference:'),
      graphRef: this.extractHeaderValue(headerLines, 'Graph ref:'),
      imsi: this.extractVarInValue(headerLines, 'IMSI:'),
      serNb: this.extractVarInValue(headerLines, 'Ser NB:'),
      suppliersId: data.idSimcardPadre,
      supplierName: '',
    };

    return headerData;
  }

  private extractHeaderValue(lines: string[], key: string): string {
    const line = lines.find((l) => l.includes(key));
    return line ? line.split(':')[1].trim() : '';
  }

  private extractVarInValue(lines: string[], key: string): string {
    const varInLine = lines.find((l) => l.includes(key));
    const startIndex = varInLine ? varInLine.indexOf(key) : -1;
    return startIndex !== -1 ? varInLine.substring(startIndex + key.length).trim() : '';
  }

}
