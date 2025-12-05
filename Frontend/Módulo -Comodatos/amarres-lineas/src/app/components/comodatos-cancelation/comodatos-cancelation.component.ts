import { DatePipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ControlCancellation, MooringBillingModel, MooringModel, UserPermissionCancellation } from 'src/app/model/model';
import { MooringResponse, UserPermissionCancellationResponse } from 'src/app/rentity/response';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-comodatos-cancelation',
  templateUrl: './comodatos-cancelation.component.html',
  styleUrls: ['./comodatos-cancelation.component.css']
})
export class ComodatosCancelationComponent implements OnInit {

  // Props
  // Input y Output
  @Input() data: MooringBillingModel;
  @Input() parameter: Map<string, string>;
  @Input() parametersMap4462 = new Map<string, string>();;
  @Output() messageEvent = new EventEmitter<boolean>();

  // Amarres Activos
  mooringModelActive: MooringModel[] = [];

  // Form
  deleteForm!: FormGroup;
  messages = messages;

  // Permiso
  permissionCancel: number = 0;

  permissionCancellation: UserPermissionCancellation = null;

  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService, private readonly fb: FormBuilder, private datePipe: DatePipe) { }

  async ngOnInit() {
    console.log(this.parametersMap4462);

    this.deleteForm = this.initForm();

    await this.getPermissionCancelByUserName(this.utilService.getSystemUser());
    await this.dataMooringById(this.data.id);
    this.permissionCancel = Number(this.parametersMap4462.get("APVTC"));

  }

  closeModal() {
    this.activeModal.close();
  }

  /**
* Formulario para buscar por el usuario
* 
*/
  initForm(): FormGroup {
    return this.fb.group({
      description: ['', [Validators.required, Validators.minLength(50)]]
    });
  }

  // Update

  /**
   * Método encargado de llevar toda la lógica para el proceso de cancelación
   * 
   */
  async cancellationComodato() {
    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea cancelar el comodato?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
      if (result.value) {
          const dateCurrent: Date = new Date();
          const dateCurrentStr: string = this.datePipe.transform(dateCurrent,"yyyy-MM-ddThh:mm:ss");

        /**
         * Validamos si se van a autorizar el permiso de cancelación a través del parámetro
         * 
         */
        if (this.permissionCancel === 1) {

          // Si no existe el usuario
          if (this.permissionCancellation === null) {
            this.utilService.showNotification(1, "No tienes permisos para cancelar este registro");
          } else {

            // Si su permiso es No = N
            if (this.permissionCancellation.permitStatus === "N") {
              this.utilService.showNotification(1, "No tienes permisos para cancelar este registro");
            } else {

              let dataCancellation: ControlCancellation = this.deleteForm.value;
              dataCancellation.idReference = this.data.id;
              dataCancellation.cancellationUser = this.utilService.getSystemUser();

              this.data.cmdStatus = "C";
              this.data.mooring = 0;
              this.data.userCancelled = this.utilService.getSystemUser();


              const VALIDATE_UPDATE = await this.putUpdateComodatosById(this.data.id, this.data);

              if (VALIDATE_UPDATE) {
                await this.postControlCancellation(dataCancellation);

                for (let i = 0; i <= this.mooringModelActive.length - 1; i++) {

                  if(this.mooringModelActive[i]!==undefined){
                    this.mooringModelActive[i].mooringStatus = 0;
                    this.mooringModelActive[i].unmooringDate = dateCurrentStr;
                    this.mooringModelActive[i].unmooringUser = this.utilService.getSystemUser();
                    await this.updateMooring(this.mooringModelActive[i].id, this.mooringModelActive[i]);
                  }

                }

                this.messageEvent.emit(true);
                this.closeModal();
              }

            }

          }
        } else {

          
          let dataCancellation: ControlCancellation = this.deleteForm.value;
          dataCancellation.idReference = this.data.id;
          dataCancellation.cancellationUser = this.utilService.getSystemUser();

          this.data.cmdStatus = "C";
          this.data.mooring = 0;
          this.data.userCancelled = this.utilService.getSystemUser();
          
          const VALIDATE_UPDATE = await this.putUpdateComodatosById(this.data.id, this.data);


          if(VALIDATE_UPDATE){
            await this.postControlCancellation(dataCancellation);

            for(let i=0; i<=this.mooringModelActive.length-1;i++){

              if(this.mooringModelActive[i]!==undefined){
                this.mooringModelActive[i].mooringStatus = 0;
                this.mooringModelActive[i].unmooringDate = dateCurrentStr;
                this.mooringModelActive[i].unmooringUser = this.utilService.getSystemUser();
                await this.updateMooring(this.mooringModelActive[i].id, this.mooringModelActive[i]);
              }
            }

            this.messageEvent.emit(true);
            this.closeModal();
          }
          
        }




      }
    })
  }


  // Métodos de Servicios

  /**
   * Método que consume un serivicio para obtener el permiso de cancelación del usuario
   * que está queriendo realizar esta acción
   * 
   * @param username 
   * @returns 
   */
  getPermissionCancelByUserName(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.getPermissionCancelByUserName(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionCancellationResponse = response.body as UserPermissionCancellationResponse;

          this.permissionCancellation = userPermissionCancellationResponse.data;

          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    });
  }


  /**
   * Método que consume un servicio para registrar el comodato que fue cancelado.
   * 
   * @param data 
   * @returns 
   */
  postControlCancellation(data: ControlCancellation): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.postControlCancellation(data).subscribe((response) => {

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


  /**
   * Método que consume un servicio para actualizar el comodato
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putUpdateComodatosById(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.putUpdateComodatosById(id, data).subscribe((response) => {

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



  /**
  * Método encargado de obtener los datos de las lineas amarradas
  * 
  */
  dataMooringById(id: any, option?: number): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.comodatosService.getMooringById(id).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.mooringModelActive = [];

          // Mapeamos el body del response
          let mooringResponse = response.body as MooringResponse;

          // Agregamos los valores a los rows

          mooringResponse.data.map((resourceMap, configError) => {

            let dto: MooringModel = resourceMap;

            if (dto.mooringStatus === 1) {
              this.mooringModelActive.push(dto);
            }

          });

          this.mooringModelActive = [...this.mooringModelActive];

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })
    });


  }


  /**
 * Método asyncrono que consume un servicio para crear el registro de o los amarres
 * 
 * @param id 
 * @param mooring 
 * @returns 
 */
  updateMooring(id: any, mooring: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.putUpdateMooringById(id, mooring).subscribe((response) => {

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
