import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReleaseSeriesRequest } from 'src/app/models/model';
import { ReleaseSerialNumbersService } from 'src/app/services/release-serial-numbers.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enums';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-release-serial-numbers-modal',
  templateUrl: './release-serial-numbers-modal.component.html',
  styleUrls: ['./release-serial-numbers-modal.component.css']
})
export class ReleaseSerialNumbersModalComponent implements OnInit {

  // Props

  // Input y Output
  @Input() button: string;
  @Input() data: ReleaseSeriesRequest;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  dataForm!: FormGroup;
  messages = messages;

  // Readonly
  inputReadOnly: boolean = false;

  // File handling
  selectedFile: File | null = null;


  constructor(public utilService: UtilService, private releaseSerialNumbersService: ReleaseSerialNumbersService, private datePipe: DatePipe, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    if (this.button === "see") {
      this.inputReadOnly = true;
    }

    this.dataForm = this.initForm();
  }

  // Methods Screen

  // Methods for initial Form

  /**
   * Se usa solo para la creación
   * 
   * @returns 
   */
  initForm(): FormGroup {
    const formGroup = this.formBuilder.group({
      releaseType: ['', [Validators.required]],
      inventoryType: ['', []], // No requerido inicialmente
      itemCode: ['', []], // No requerido inicialmente
      warehouseCode: ['', []], // No requerido inicialmente
      subWarehouseCode: ['', []], // No requerido inicialmente
      serialNumberList: this.formBuilder.array([]) // Inicializamos el FormArray para los números de serie
    });

    // Observamos cambios en el campo releaseType
    formGroup.get('releaseType')?.valueChanges.subscribe(value => {
      if (value === 'Unidad') {
        // Si es "Unidad", hacemos que los campos sean requeridos
        formGroup.get('inventoryType')?.setValidators([Validators.required]);
        formGroup.get('itemCode')?.setValidators([Validators.required]);
        formGroup.get('warehouseCode')?.setValidators([Validators.required]);
        formGroup.get('subWarehouseCode')?.setValidators([]);

      } else {
        // Si es "Masivo", removemos los validadores
        formGroup.get('inventoryType')?.clearValidators();
        formGroup.get('itemCode')?.clearValidators();
        formGroup.get('warehouseCode')?.clearValidators();
        formGroup.get('subWarehouseCode')?.clearValidators();
      }

      // Actualizamos el estado de validez del formulario
      formGroup.get('inventoryType')?.updateValueAndValidity();
      formGroup.get('itemCode')?.updateValueAndValidity();
      formGroup.get('warehouseCode')?.updateValueAndValidity();
      formGroup.get('subWarehouseCode')?.updateValueAndValidity();
    });

    return formGroup;
  }

  /**
   * Se usa solo para visualizar o modificar
   * 
   * @returns 
   */
  initFormTwo(): FormGroup {
    return this.formBuilder.group({
      releaseType: [this.data.releaseType, [Validators.required]],
      inventoryType: [this.data.inventoryType, [Validators.required]],
      itemCode: [this.data.itemCode, [Validators.required]],
      warehouseCode: [this.data.warehouseCode, [Validators.required]],
      subWarehouseCode: [this.data.subWarehouseCode, []]
    });
  }

  get serialNumberList(): FormArray {
    return this.dataForm.get('serialNumberList') as FormArray;
  }

  addSerialNumber() {
    this.serialNumberList.push(this.formBuilder.control('', Validators.required));
  }

  removeSerialNumber(index: number) {
    this.serialNumberList.removeAt(index);
  }

  onReleaseTypeChange() {
    this.serialNumberList.clear();
    this.selectedFile = null;
  }


  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      console.log(file);
    }
  }

  closeModal() {
    this.activeModal.close();
  }

  crudReleaseSerial() {
    let action: string = this.button === 'add' ? "crear" : "modificar";
  
    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea realizar la liberación?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
  
      if (result.value) {
        const data: ReleaseSeriesRequest = this.dataForm.value;
  
        if (this.button === 'add') {
          if (data.releaseType === 'Masivo') {
            // Mostrar el spinner de carga
            Swal.fire({
              title: 'Cargando...',
              text: 'Por favor, espere mientras se realiza la liberación masiva.',
              allowOutsideClick: false,
              onBeforeOpen: () => {
                Swal.showLoading();
              }
            });
  
            const VALIDATE_CREATION = await this.postReleaseSeriesFromFile(this.selectedFile, data.releaseType);
            Swal.close(); // Cerrar el spinner
  
            if (VALIDATE_CREATION) {
              this.utilService.showNotification(0, "La liberación masiva se realizó exitosamente");
              this.messageEvent.emit(true);
              this.closeModal();
            }
          } else {
            // Liberación individual
            const VALIDATE_CREATION = await this.postReleaseSingleSeries(data);
  
            if (VALIDATE_CREATION) {
              this.utilService.showNotification(0, "La liberación se realizó exitosamente");
              this.messageEvent.emit(true);
              this.closeModal();
            }
          }
        }
      }
    });
  }

  // Methods Services

  /**
 * Método que consume un servicio para realizar la liberacion
 * 
 * @param data 
 * @returns 
 */
  postReleaseSingleSeries(data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.releaseSerialNumbersService.postReleaseSingleSeries(data).subscribe((response) => {

        if (response.status === 200) {
          resolve(true);
        } else {
          resolve(false);
        }

      }, (erro) => {
        resolve(false);
      });

    });

  }

  postReleaseSeriesFromFile(file: File | null, releaseType: string): Promise<boolean> {
    return new Promise((resolve, reject) => {
      if (!file) {
        this.utilService.showNotification(1, "Por favor, seleccione un archivo para la liberación masiva.");
        resolve(false);
        return;
      }

      this.releaseSerialNumbersService.postReleaseSeriesFromFile(file, releaseType).subscribe((response) => {
        if (response.status === 200) {
          resolve(true);
        } else {
          resolve(false);
        }
      }, (error) => {
        resolve(false);
      });
    });
  }

}
