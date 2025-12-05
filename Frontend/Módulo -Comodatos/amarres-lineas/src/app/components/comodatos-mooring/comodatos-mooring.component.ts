import { DatePipe } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MooringBillingModel, MooringModel } from 'src/app/model/model';
import { BillingResponse, MooringResponse, MooringValidateResponse } from 'src/app/rentity/response';
import { ComodatosService } from 'src/app/services/comodatos.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-comodatos-mooring',
  templateUrl: './comodatos-mooring.component.html',
  styleUrls: ['./comodatos-mooring.component.css']
})
export class ComodatosMooringComponent implements OnInit {

  // Props
  // Input y Output
  @Input() data: MooringBillingModel;
  @Input() parameter: Map<string, string>;
  @Input() parameter4462: Map<string, string>;
  @Output() messageEvent = new EventEmitter<boolean>();
  maxComodatos: number = 0;

  // Sirve para indicar si se muestra el proceso de amarre
  disabledButton: boolean = true;

  // Tab
  active: number = 1;

  // Form
  searchData!: FormGroup;
  messages = messages;

  // Tables
  mooringModel: MooringModel[] = [];
  mooringModelSearch: MooringModel[] = [];
  mooringModelDelete: MooringModel[] = [];
  loadingIndicator: boolean = true;
  resultsPerPage: number = 3;
  searchedValue: string = "";

  mooringModelActive: MooringModel[] = [];
  mooringModelActiveSearch: MooringModel[] = [];
  loadingIndicatorTwo: boolean = true;
  resultsPerPageTwo: number = 3;
  searchedValueTwo: string = "";

  mooringModelInactive: MooringModel[] = [];
  mooringModelInactiveSearch: MooringModel[] = [];
  loadingIndicatorThree: boolean = true;
  resultsPerPageThree: number = 3;
  searchedValueThree: string = "";

  // Hidden
  hideActive: boolean = false;
  hideInactive: boolean = false;

  hideCancel: boolean = true;


  constructor(private activeModal: NgbActiveModal, public utilService: UtilService, private comodatosService: ComodatosService, private readonly fb: FormBuilder, private datePipe: DatePipe) { }

  async ngOnInit() {

    // Inicializamos el formulario de busqueda, validamos el estado y obtenemos los valores que forman parte del amarre.
    this.searchData = this.initForm();
    this.maxComodatos = Number(this.parameter.get("MAXLNVAC"));
    this.disabledButton = this.data.cmdStatus === "Activo" ? true : false;
    this.hideCancel = this.data.cmdStatus !== "Activo" ? true : false;
    await this.dataMooringById(this.data.id, 1);

   // if (this.data.cmdStatus === "Activo") {
   //   await this.getValidateComodatoBilling(this.data.invoiceNumber);
   // }


    setTimeout(() => {
      this.hideInactive = true;
    }, 500);

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
      subscriberId: ['', [Validators.required]]
    });
  }

  /**
  * Buscardor de la tabla Amarre
  * 
  */
  search(): void {
    this.mooringModel = this.mooringModelSearch.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  getTotalText() {
    return this.mooringModel.length == 1 ? "Registro" : "Registros";
  }

  /**
  * Buscardor de la tabla Activos
  * 
  */
  searchActive(): void {
    this.mooringModelActive = this.mooringModelActiveSearch.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValueTwo.toString()
          .toLowerCase());
    });
  }

  getTotalTextActive() {
    return this.mooringModelActive.length == 1 ? "Registro" : "Registros";
  }


  /**
  * Buscardor de la tabla Activos
  * 
  */
  searchInactive(): void {
    this.mooringModelInactive = this.mooringModelInactiveSearch.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValueThree.toString()
          .toLowerCase());
    });
  }

  getTotalTextInactive() {
    return this.mooringModelInactive.length == 1 ? "Registro" : "Registros";
  }

  /**
   * Método que controla la visualización de las tablas 
   * 
   * @param option 
   */
  changeTab(option: number) {

    if (option === 1) {
      this.hideInactive = true;
      this.hideActive = false;
    } else {
      this.hideInactive = false;
      this.hideActive = true;
    }

  }

  /**
   * Método para validar si el número ingresado existe 
   * y si tiene las cuentas requeridas para el amarre
   * 
   */
  async findDataBySubscriber() {
    const SUBSCRIBER = this.searchData.value;

    /**
     * Validamos la cantidad máxima a agregar a la tabla de amarre
     * 
     */
    if (this.mooringModel.length >= Number(this.parameter.get("MAXLNVAC"))) {
      this.utilService.showNotification(1, `Solo puedes agregar un máximo de ${this.parameter.get("MAXLNVAC")} líneas`);
    } else {

      const EXISTS_SUBSCRIBER = await this.existsSubscriber(SUBSCRIBER.subscriberId);
      //console.log(EXISTS_SUBSCRIBER);

      /**
       * Validamos si existe en las tablas
       * 
       */
      if (EXISTS_SUBSCRIBER) {
        this.utilService.showNotification(1, `El Suscriptor ${SUBSCRIBER.subscriberId} ya existe`);
      } else {

        Swal.fire({
          title: 'Buscando registro...',
          allowOutsideClick: false,
          onBeforeOpen: () => {
            Swal.showLoading();
          }
        });

        const VALIDATE_SUBSCRIBER = await this.getInfoSubscribe(SUBSCRIBER.subscriberId);
        //console.log(VALIDATE_SUBSCRIBER);
        Swal.close();

        /**
         * Validamos si existe el número
         * 
         */
        if (VALIDATE_SUBSCRIBER !== null) {
          //console.log(VALIDATE_SUBSCRIBER);

          const VALIDATE_SUBSCRIBER_ACTIVE = await this.getValidateSubcriber(SUBSCRIBER.subscriberId);

          /**
           * Validamos si el comodato está activo a otro registro padre o hijo
           * 
           */
          if (VALIDATE_SUBSCRIBER_ACTIVE) {

            /**
             * Validamos si es igual la cuenta facturación del comodato padre de la linea que se
             * desea amarrar
             * 
             */
            if (this.data.customerAccount == VALIDATE_SUBSCRIBER.customerAccount) {
              this.mooringModel.push(VALIDATE_SUBSCRIBER)

              this.mooringModel = [...this.mooringModel];
              this.mooringModelSearch = [...this.mooringModel];
            } else {
              this.utilService.showNotification(1, `La cuenta del suscriptor ${SUBSCRIBER.subscriberId} no pertenece a la misma cuenta del comodato`);
            }

          } else {
            this.utilService.showNotification(1, `El suscriptor ${SUBSCRIBER.subscriberId} ya está amarrado en otro comodato`);
          }


        } else {
          this.utilService.showNotification(1, "No se encontraron datos del número ingresado");
        }
      }



    }

  }


  insertMooring() {

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea realizar el amarre de las líneas que ingreso?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
      if (result.value) {

        const cantInsert: number = this.mooringModel.length;
        const cantActive: number = this.mooringModelActive.length;

        const cantDisponible: number = this.maxComodatos - cantActive;


        /**
         * Validamos si no hay ningún registro activo, puede
         * insertar la cantidad que está en la tabla de proceso de amarre
         * 
         */
        if (cantActive == 0) {

          //console.log("Ingresa la cantidad que desea");

          // for (let i = 0; i <= cantInsert - 1; i++) {
          //console.log(i);
          //console.log(this.mooringModel[i]);

          await this.postMooring(this.data.id, this.mooringModel);

          //}

          this.mooringModelDelete = [];
          this.mooringModel = [];
          this.mooringModelSearch = [];

          this.mooringModel = [...this.mooringModel];
          this.mooringModelSearch = [...this.mooringModel];
          this.mooringModelDelete = [...this.mooringModel]
          await this.dataMooringById(this.data.id, 2);

          this.active = 1;
          this.hideActive = false;
          this.hideInactive = false;

          setTimeout(() => {
            this.hideInactive = true;
          }, 500);

          this.searchData.get('subscriberId').setValue('');

          this.utilService.showNotification(0, "Se realizó el amarre exitosamente");

        } else {

          /**
           * Validamos si la cantidad de activos es igual a la cantidad máxima,
           * si es así ya no se puede amarrar más datos
           * 
           */
          if (cantActive == this.maxComodatos) {

            this.utilService.showNotification(1, `Ya no se puede amarrar más líneas porque el máximo es de ${this.maxComodatos}`);
          } else {

            /**
             * Si la cantidad activa es mayor a la cantidad máxima de lineas que se pueden amarrar; tampoco
             * se puede amarrar más líneas.
             * 
             * Ejemplo del escenario: 
             *  Esto es en caso que al principio establecen el valor máximo de líneas que se pueden amarrar es 3 
             * y en un futuro lo reducen a 2, y, hay 3 líneas amarradas, la diferencia sería negativa por lo tanto
             * no se puede agregar más líneas al amarre.
             * 
             */
            if (cantActive > this.maxComodatos) {
              this.utilService.showNotification(1, `Ya no se puede amarrar más líneas porque el máximo es de ${this.maxComodatos}`);
            } else {

              // Lógica para saber la cantidad máxima que se puede amarrar

              /**
               * Se valida si la cantidad a insertar coincida con la cantidad disponible para amarrar
               * 
               */
              if (cantDisponible == cantInsert || cantDisponible > cantInsert) {

                // Se insertan los amarres
                //console.log("Se ingreso la cantidad de: " + cantInsert);

                await this.postMooring(this.data.id, this.mooringModel);

                this.mooringModelDelete = [];
                this.mooringModel = [];
                this.mooringModelSearch = [];

                this.mooringModel = [...this.mooringModel];
                this.mooringModelSearch = [...this.mooringModel];
                this.mooringModelDelete = [...this.mooringModel]
                await this.dataMooringById(this.data.id, 2);

                this.active = 1;
                this.hideActive = false;
                this.hideInactive = false;

                setTimeout(() => {
                  this.hideInactive = true;
                }, 500);
                this.searchData.get('subscriberId').setValue('');
                this.utilService.showNotification(0, "Se realizó el amarre exitosamente");

              } else {

                if (cantDisponible == 1) {
                  this.utilService.showNotification(1, `Solo puedes amarrar la cantidad disponible de ${cantDisponible} línea, puedes remover algún registro para seguir con el proceso de amarre`);
                } else {
                  this.utilService.showNotification(1, `Solo puedes amarrar la cantidad disponible de ${cantDisponible} líneas, puedes remover algún registro para seguir con el proceso de amarre`);
                }


              }

            }

          }
        }


        /**
  * Validamos sino hay registros activos el campo amarre se actualiza
  * a 0
  * 
  */
        if (this.mooringModelActive.length > 0) {
          //console.log(this.data, "Si hay datos activos");
          if (this.data.mooring === "No" || this.data.mooring === 0) {
            this.data.mooring = 1;


            if (this.data.cmdStatus.length > 1) {
              this.data.cmdStatus = this.data.cmdStatus === "Activo" ? "A" : this.data.cmdStatus === "Vencido" ? "V" : this.data.cmdStatus === "Cancelado" ? "C" : "N";
            }

            await this.putUpdateComodatosById(this.data.id, this.data);

            this.messageEvent.emit(true);
          }
        }

      }
    })



  }

  /**
   * Método encargado de llevar acabo el proceso de activar e inactivar lineas
   * del amarre
   * 
   * @param data 
   */
  updateStatus(option: number, data: MooringModel) {

    const ACTION = option == 1 ? "activar" : "desactivar"
    const ACTION_TWO = option == 1 ? "activado" : "desactivado"

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea ${ACTION} el registro?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        /**
         * Validamos que opción es: si desactivar o activar linea
         * 
         */
        if (option === 0) {

          const DATE: Date = new Date();

          data.unmooringDate = this.datePipe.transform(DATE, "yyyy-MM-ddTHH:mm:ss");
          data.unmooringUser = this.utilService.getSystemUser();
          data.mooringStatus = 0;

          await this.putUpdateMooringById(data.id, data);
          this.utilService.showNotification(0, `El reigstro fue ${ACTION_TWO} exitosamenten`);

          /*
          this.mooringModelDelete = [];
          this.mooringModel = [];
          this.mooringModelSearch = [];

          this.mooringModel = [...this.mooringModel];
          this.mooringModelSearch = [...this.mooringModel];
          this.mooringModelDelete = [...this.mooringModel]
          */
          await this.dataMooringById(this.data.id, 2);

          this.active = 1;
          this.hideActive = false;
          this.hideInactive = false;

          setTimeout(() => {
            this.hideInactive = true;
          }, 500);

          //console.log(data);

        } else {
          const cantActive: number = this.mooringModelActive.length;

          const cantDisponible: number = this.maxComodatos - cantActive;

          //console.log(cantActive);
          //console.log(cantDisponible);

          /**
           * Validamos si se puede amarrar o no "Activar"
           * 
           */
          if (cantActive == 0) {

            const VALIDATE_SUBSCRIBER = await this.getValidateSubcriber(data.subscriberId);

            if (VALIDATE_SUBSCRIBER) {
              data.mooringStatus = 1;

              await this.putUpdateMooringById(data.id, data);
              this.utilService.showNotification(0, `El reigstro fue ${ACTION_TWO} exitosamenten`);

              /*
              this.mooringModelDelete = [];
              this.mooringModel = [];
              this.mooringModelSearch = [];
  
              this.mooringModel = [...this.mooringModel];
              this.mooringModelSearch = [...this.mooringModel];
              this.mooringModelDelete = [...this.mooringModel]
              */
              await this.dataMooringById(this.data.id, 2);

              this.active = 1;
              this.hideActive = false;
              this.hideInactive = false;

              setTimeout(() => {
                this.hideInactive = true;
              }, 500);
            } else {
              this.utilService.showNotification(1, "El suscriptor ya está amarrado en otro comodato");
            }



          } else {

            // 
            if (cantActive > this.maxComodatos) {
              this.utilService.showNotification(1, `No se puede ${ACTION} el registro porque solo se permiten ${this.maxComodatos} lineas como máximo`);

            } else {

              // Validamos si hay cantidad disponible para activar
              if (cantDisponible >= 1) {

                const VALIDATE_SUBSCRIBER = await this.getValidateSubcriber(data.subscriberId);

                /**
                 * Validamos si el suscriptor está disponible para
                 * amarrar
                 * 
                 */
                if (VALIDATE_SUBSCRIBER) {

                  data.mooringStatus = 1;

                  await this.putUpdateMooringById(data.id, data);

                  /*
                  this.mooringModelDelete = [];
                  this.mooringModel = [];
                  this.mooringModelSearch = [];
  
                  this.mooringModel = [...this.mooringModel];
                  this.mooringModelSearch = [...this.mooringModel];
                  this.mooringModelDelete = [...this.mooringModel]
                  */
                  await this.dataMooringById(this.data.id, 2);

                  this.utilService.showNotification(0, `El reigstro fue ${ACTION_TWO} exitosamenten`);


                  this.active = 1;
                  this.hideActive = false;
                  this.hideInactive = false;

                  setTimeout(() => {
                    this.hideInactive = true;
                  }, 500);
                } else {
                  this.utilService.showNotification(1, "El suscriptor ya está amarrado en otro comodato");
                }



              } else {
                this.utilService.showNotification(1, `No se puede ${ACTION} el registro porque solo se permiten ${this.maxComodatos} lineas como máximo`);
              }

            }

          }
        }



        /**
         * Validamos sino hay registros activos el campo amarre se actualiza
         * a 0
         * 
         */
        if (this.mooringModelActive.length == 0) {
          //console.log(this.data, "No hay datos Activos");
          if (this.data.mooring === "Sí" || this.data.mooring === 1) {
            this.data.mooring = 0;


            if (this.data.cmdStatus.length > 1) {
              this.data.cmdStatus = this.data.cmdStatus === "Activo" ? "A" : this.data.cmdStatus === "Vencido" ? "V" : this.data.cmdStatus === "Cancelado" ? "C" : "N";
            }

            await this.putUpdateComodatosById(this.data.id, this.data);
            this.messageEvent.emit(true);
          }



        }

        /**
         * Validamos si hay datos activos, si hay se actualiza unos campos
         * de la tabla padre, pero este se ejecuta si solo hay un dato activo
         * para evitar redundancia de actualizar estos valores
         * 
         */
        if (this.mooringModelActive.length == 1) {
          //console.log(this.data, "Si hay datos activos");
          if (this.data.mooring === "No" || this.data.mooring === 0) {
            this.data.mooring = 1;


            if (this.data.cmdStatus.length > 1) {
              this.data.cmdStatus = this.data.cmdStatus === "Activo" ? "A" : this.data.cmdStatus === "Vencido" ? "V" : this.data.cmdStatus === "Cancelado" ? "C" : "N";
            }

            await this.putUpdateComodatosById(this.data.id, this.data);

            this.messageEvent.emit(true);
          }
        }

      }

    })

  }

  /**
   * Método encacrgado de remover en la tabla de Proceso de Amarre el registro
   * 
   * @param data 
   */
  remove(index: any) {
    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea remover este registro de la tabla?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
      if (result.value) {


        this.mooringModelDelete = [];

        this.mooringModel.splice(index, 1);

        this.mooringModelDelete = [...this.mooringModel];
        this.mooringModel = [];
        this.mooringModelSearch = [];

        this.mooringModel = [...this.mooringModelDelete];
        this.mooringModelSearch = [...this.mooringModelDelete];

      }
    })
  }

  /**
   * Promesa que nos ayuda a validar si la fecha actual, junto con la hora de amarre
   * se compará con la fecha actual
   * 
   */
  validateTimeForMooring(dateEmit: Date): Promise<boolean> {

    return new Promise((resolve, reject) => {

      if (dateEmit) {

        const hourDispo: number = Number(this.parameter4462.get("MOORING_HOURS"));

        // Valor Fecha Actual
        const dateCurrent: Date = new Date();


        const dateCurrentStr: string = this.datePipe.transform(dateCurrent, "yyyy-MM-dd");

        const dateTimeCurrentStr: string = this.datePipe.transform(dateCurrent, "yyyy-MM-dd hh:mm:ss");
        const dateTimeCurrentValidate: Date = new Date(dateTimeCurrentStr); // Fecha actual con tiempo

      
        // Valor Final
        const dateEmitWithTimeStr: string = this.datePipe.transform(dateEmit, "yyyy-MM-dd");

        const dateEmitStr: string = this.datePipe.transform(dateEmit, "yyyy-MM-dd hh:mm:ss");
        const dateEmitValidate: Date = new Date(dateEmitStr); // Fecha emitida con tiempo
        
        // Aumentar las horas utilizando setHours()
        dateEmitValidate.setHours(dateEmitValidate.getHours() + hourDispo);

        /**
         * Validamos si pertenece al mismo año, mes y día
         * 
         */
        if(dateCurrentStr==dateEmitWithTimeStr){
          //console.log("Son iguales");

          if(dateTimeCurrentValidate<=dateEmitValidate){
            //console.log("No ha pasado el tiempo");
            resolve(true);
            
          }else{
            //console.log("Ya paso el tiempo");
            resolve(false);
          }

        }else{
          //console.log("No son iguales");
          resolve(false);
        }

        

      } else {
        resolve(false);
      }

    });

  }

  // Función para comparar objetos
  compararObjetos(objeto1: MooringModel, objeto2: MooringModel) {

    return objeto1.subscriberId === objeto2.subscriberId;
  }

  /**
   * Método que valida si el número ingresado existe o no en la tabla de amarre,
   * activos e inactivos.
   * 
   * @param subscriber 
   * @returns 
   */
  existsSubscriber(subscriber: string): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.mooringModel.forEach((element) => {
        //console.log("Entro");
        if (element.subscriberId === subscriber) {
          //console.log("Existe");
          resolve(true);
        }

      });

      this.mooringModelActive.forEach((element) => {
        // console.log("Entro");
        if (element.subscriberId == subscriber) {
          // console.log("Existe");
          resolve(true);
        }
      });

      this.mooringModelInactive.forEach((element) => {
        // console.log("Entro");
        if (element.subscriberId == subscriber) {
          //  console.log("Existe");
          resolve(true);
        }
      });


      resolve(false);

    });
  }


  // Métodos Asyncronos
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
          this.mooringModelInactive = [];

          // Mapeamos el body del response
          let mooringResponse = response.body as MooringResponse;

          // Agregamos los valores a los rows

          mooringResponse.data.map((resourceMap, configError) => {

            let dto: MooringModel = resourceMap;

            if (dto.mooringStatus === 0) {
              this.mooringModelInactive.push(dto);
            } else {
              this.mooringModelActive.push(dto);
            }

          });


          this.mooringModelActive = [...this.mooringModelActive];
          this.mooringModelActiveSearch = [...this.mooringModelActive];

          this.mooringModelInactive = [...this.mooringModelInactive];
          this.mooringModelInactiveSearch = [...this.mooringModelInactive];

          this.loadingIndicator = false;
          this.loadingIndicatorTwo = false;
          this.loadingIndicatorThree = false;

          if (this.mooringModelActive.length > 0 || this.mooringModelInactive.length > 0) {
            if (option === 1) {
              this.utilService.showNotification(0, "Datos cargados");
            }

            if (option === 2) {
              this.utilService.showNotification(0, "Se refrescaron los datos en la tablas Activo e Inactivo");
            }
          }

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
   * Método que consume un servicio para obtener la información
   * del suscriptor y mostrarlo en la tabla del
   * "Proceso de Amarre"
   * 
   */
  getInfoSubscribe(subscribe: string): Promise<MooringModel> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.comodatosService.getInfoSubscribe(subscribe).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let mooringResponse = response.body as MooringValidateResponse;

          let dto: MooringModel = mooringResponse.data;

          let date = new Date();


          dto.idMooringBilling = this.data.id;
          dto.dateOfEntry = this.datePipe.transform(date, 'yyyy-MM-ddTHH:mm:ss');
          //console.log(dto.dateOfEntry);
          dto.userName = this.utilService.getSystemUser();

          dto.subscriberId = subscribe;
          //dto.checkInTime = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
          dto.checkInTime = this.datePipe.transform(date, 'HH:mm:ss');
          dto.mooringStatus = 1;

          //console.log(dto);

          resolve(dto);

        } else {
          resolve(null);
        }

      }, (error) => {
        resolve(null);
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
  postMooring(id: any, mooring: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.postMooring(id, mooring).subscribe((response) => {

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
   * Método que consume un servicio para actualizar
   * el estado de la linea amarrada o desamarrada
   * 
   * @param id 
   * @param data 
   * @returns 
   */
  putUpdateMooringById(id: any, data: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.putUpdateMooringById(id, data).subscribe((response) => {

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
   * Método que consume un servicio para validar si el suscriptor está
   * activo o no
   * 
   * @param subcriber 
   * @returns 
   */
  getValidateSubcriber(subcriber: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.getValidateSubcriber(subcriber).subscribe((response) => {

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
   * Método que consume un servicio para validar si la factura del comodato existe o no
   * si existe se valida que el estado esté
   * 
   */
  getValidateComodatoBilling(numberDei: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      this.comodatosService.getValidateComodatoBilling(numberDei).subscribe(async (response) => {

        if (response.status === 200) {
          let billingResponse = response.body as BillingResponse;

          if (billingResponse.data.status == 2) {

            this.disabledButton = await this.validateTimeForMooring(billingResponse.data.dateOfIssue);

            //this.disabledButton = true;
          } else {
            this.disabledButton = false;
          }

          resolve(true);
        } else {
          this.disabledButton = false;
          resolve(false);
        }

      }, (error) => {
        this.disabledButton = false;
        resolve(false);
      })

    });
  }


}
