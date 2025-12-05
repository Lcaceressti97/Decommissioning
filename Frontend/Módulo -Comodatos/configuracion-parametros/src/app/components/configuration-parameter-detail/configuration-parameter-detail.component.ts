import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ParametersModel } from 'src/app/model/model';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/emun';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-configuration-parameter-detail',
  templateUrl: './configuration-parameter-detail.component.html',
  styleUrls: ['./configuration-parameter-detail.component.css']
})
export class ConfigurationParameterDetailComponent implements OnInit {

  // Props
  // Input y Output
  @Input() parameter: ParametersModel;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Form
  updateForm!: FormGroup;
  messages = messages;
  inputNumber = false;

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    if(this.isNumber(this.parameter.value.toString())){
      this.inputNumber = true;
      this.updateForm = this.initForm();
    }else{
      this.updateForm = this.initForm2();
      this.inputNumber = false;
    }
  }

  isNumber(valor: string): boolean {
    // Intenta convertir el valor a un número
    const numero = parseFloat(valor);
    
    // Verifica si el valor es un número finito y si el string es igual al número convertido a string.
    // Esto asegura que el string sea un número y que no contenga caracteres adicionales.
    return isFinite(numero) && valor === numero.toString();
  }

  /**
* Método encargado de construir el formulario reactivo
* 
*/
  initForm(): FormGroup {
    return this.formBuilder.group({
      parameterName: [this.parameter.parameterName, [Validators.required, Validators.maxLength(2000)]],
      description: [this.parameter.description, [Validators.required, Validators.maxLength(2000)]],
      value: [this.parameter.value, [Validators.required, this.valorDecimalMayorQueCero]]
    });
  }

  initForm2(): FormGroup {
    return this.formBuilder.group({
      parameterName: [this.parameter.parameterName, [Validators.required, Validators.maxLength(2000)]],
      description: [this.parameter.description, [Validators.required, Validators.maxLength(2000)]],
      value: [this.parameter.value, [Validators.required]]
    });
  }

    // Validador personalizado para verificar que el valor sea decimal y mayor que cero
    valorDecimalMayorQueCero(control) {
      const valor = control.value;
      if (valor < 0) {
        return { 'valorInvalido': true };
      }
      return null;
    }

  closeModal() {
    this.activeModal.close();
  }


  // Métodos de actualización
  async updateParameter(){

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea actualizar el parámetro?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any)=> {

      if(result.value){
        const BODY: ParametersModel = this.updateForm.value;
        
        const VALIDATE_UPDATE = await this.putConfigParametersById(this.parameter.id, BODY);

        if(VALIDATE_UPDATE){
          this.utilService.showNotification(0, "Parámetro actualizado exitosamente");
          this.messageEvent.emit(true);
          this.closeModal();
        }

      }

    })
  }


  // Métodos asyncronos

    /**
* Método encargado de obtener los parámetros
* 
*/
putConfigParametersById(id: any, body:any): Promise<boolean> {

  return new Promise((resolve, reject) => {
    // Se llama e método del servicio
    this.comodatosService.putConfigParametersById(id,body).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        resolve(true);

      } else {
        resolve(false);
      }

    }, (error) => {
      resolve(false);
    })
  });

}


}
