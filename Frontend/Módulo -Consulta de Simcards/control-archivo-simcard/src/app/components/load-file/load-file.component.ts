import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderControlModel } from 'src/app/models/model';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';

import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-load-file',
  templateUrl: './load-file.component.html',
  styleUrls: ['./load-file.component.css']
})
export class LoadFileComponent implements OnInit {

  // Props
  active = 1;

  // Input | Output
  @Input() data: OrderControlModel;
  @Input() action: string;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  fileName: string = "Seleccionar";
  file: File = null;
  contentFile: string = "Seleccione un archivo";
  showContent: boolean = false;
  messages = messages;
  updateShow: boolean = true;

  constructor(public utilService: UtilService, private simcardService: SimcardControlFileService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.form = this.initForm();

  }

  closeModal() {
    this.activeModal.close();
  }

  initForm(): FormGroup {
    return this.formBuilder.group({
      baseFile: ["", [Validators.required]]
    });
  }

  /**
   * Para ver según sea la respuesta 
   * 
   * @param option 
   */
  changeTab(option: number) {
    if (option === 1) {
    }
    if (option === 2) {
    }


  }

  /**
* Método que nos ayuda a controlar el archivo que se va a cargar
* 
* @param event 
*/
  selectFile(event: any) {
    const fileName: string = event.target.files[0]?.name;
    this.fileName = fileName;

    this.file = event.target.files[0];
    //console.log(this.fileName);
    const reader = new FileReader();

    reader.onload = () => {
      this.contentFile = reader.result.toString(); // Almacenar el contenido del archivo
    };
  
    this.showContent = true;
    // Leer el archivo como texto
    reader.readAsText(this.file);


  }

  updateFile(option: number) {

    if (option === 1) {
      this.updateShow = false;
    } else {
      this.updateShow = true;
      this.form.patchValue({
        baseFile: ""
      });
      this.fileName = "Seleccionar";
    }

  }

  postFile(){

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea seguir con el proceso de subir el archivo?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any)=>{

      if (result.value){
        if(this.file!==null){
          const VALIDATE_FILE = await this.processSimcardFile(this.data.id,this.file);

          
            this.utilService.showNotification(0, "Archivo subido exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
         

        }
      }

    });
  }

  /**
 * Método que consume un servicio para procesar la orden del pedido
 * 
 */
  processSimcardFile(id: any, data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.processSimcardFileById(id,data).subscribe((response) => {

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
