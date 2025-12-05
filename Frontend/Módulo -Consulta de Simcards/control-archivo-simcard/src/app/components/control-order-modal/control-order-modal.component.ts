import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { take } from 'rxjs/operators';
import { SimcardPadreEntity } from 'src/app/entities/SimcardPadreEntity';
import { ArtResponse, CustomerResponse, GraphicResponse, ModelResponse, NextImsiResponse, OderControlDetailResponse, ParamSimcardResponse, ProviderResponse, TypeResponse, VersionResponse } from 'src/app/entities/response';
import { ArtModel, CustomerModel, EmailModel, GraphicModel, ModeloModel, OrderControlDetailModel, OrderControlModel, ProviderModel, TypeModel, VersionModel } from 'src/app/models/model';
import { SimcardPadre } from 'src/app/models/SimcardPadre';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";
import Big from 'big.js';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-control-order-modal',
  templateUrl: './control-order-modal.component.html',
  styleUrls: ['./control-order-modal.component.css']
})
export class ControlOrderModalComponent implements OnInit {

  // Props
  active: number = 1;

  // Input

  // No Use
  @Input() idPadre: number = null;
  @Input() idSimcardControl: number = null;


  @Input() data: OrderControlModel = null;
  @Input() action: string = null;
  @Output() messegaEvent = new EventEmitter<boolean>();

  // Form
  form: FormGroup;
  nextOrderNumber: string;
  messages = messages;

  // Parameters que nos ayudan a actualizar en tiempo real los datos de la pantalla. además, se usan para el payload
  hlrCustomer: string = "0";
  client: string = "";
  nameProvider: string = "";
  idProvider: number = 0;
  nameType: string = "";
  nameGraphic: string = "";
  nameModel: string = "";
  versionSize: any = null;
  capacity: any = null;
  key: any = "";

  // Data
  providerModels: ProviderModel[] = [];
  clientModels: CustomerModel[] = [];
  artModels: ArtModel[] = [];
  typeModels: TypeModel[] = [];
  graphicModels: GraphicModel[] = [];
  modeloModels: ModeloModel[] = [];
  versionModels: VersionModel[] = [];

  // Readonly
  readonly: boolean = false;

  constructor(public utilService: UtilService, private simcardService: SimcardControlFileService, private activeModal: NgbActiveModal, private formBuilder: FormBuilder, private datePipe: DatePipe) { }

  async ngOnInit() {

    if (this.action === "add") {
      this.form = this.initForm();
      this.loadParameters();
    } else {
      this.readonly = true;
      this.form = this.initFormTwo();
    }

    if(this.action === "see"){
      const campoControl = this.form.get('createdDate') as FormControl;
      const date:string = this.datePipe.transform(this.data.createdDate, 'dd/MM/yyyy hh:mm:ss a');
      campoControl.setValue(date);
    }


  }



  // Methods
  initForm(): FormGroup {
    return this.form = this.formBuilder.group({
      idSupplier: [null],
      supplierName: [null, Validators.required],
      noOrder: [null],
      userName: [this.utilService.getSystemUser(), Validators.required],
      emailSend: ["", Validators.email],
      customerName: [null, Validators.required],
      initialImsi: [null, Validators.required],
      initialIccd: [null, Validators.required],
      orderQuantity: [null, [Validators.required, Validators.maxLength(20000)]],
      fileName: [null],
      createdDate: [null],
      customer: [null, Validators.required],
      hlr: [null, Validators.required],
      batch: [null],
      key: [null, Validators.required],
      type: [null, Validators.required],
      art: [null, Validators.required],
      graphic: [null, Validators.required],
      model: [null, Validators.required],
      versionSize: [null, Validators.required],
      email: [null, Validators.required],
    });
  }

  // Methods
  initFormTwo(): FormGroup {
    return this.form = this.formBuilder.group({
      idSupplier: [null],
      supplierName: [this.data.supplierName, Validators.required],
      noOrder: [this.data.noOrder],
      userName: [this.data.userName, Validators.required],
      customerName: [this.data.customerName, Validators.required],
      emailSend: ["", Validators.email],
      initialImsi: [this.data.initialImsi],
      initialIccd: [this.data.initialIccd],
      orderQuantity: [this.data.orderQuantity, [Validators.required, Validators.maxLength(20000)]],
      fileName: [this.data.fileName],
      createdDate: [this.data.createdDate],
      customer: [this.data.customer, Validators.required],
      hlr: [this.data.hlr, Validators.required],
      batch: [this.data.batch],
      key: [this.data.key, Validators.required],
      type: [this.data.type, Validators.required],
      art: [this.data.art, Validators.required],
      graphic: [this.data.graphic, Validators.required],
      model: [this.data.model, Validators.required],
      versionSize: [this.data.versionSize, Validators.required],
      email: [this.data.email, Validators.required],
    });
  }

  validateNumber(control) {
    const numeroDecimalRegExp = /^[0-9]+$/;
    if (control.value && !numeroDecimalRegExp.test(control.value)) {
      return { 'numeroDecimalInvalido': true };
    }
    return null;
  }

  closeModal() {
    this.activeModal.close();
  }

  changeProvider(event) {



    const dataProvider: string = event.target.value;
    //console.log(dataProvider);
    const spliceArray = dataProvider.split(",");
    //console.log(spliceArray);

    this.nameProvider = spliceArray[1];
    this.idProvider = Number(spliceArray[0]);

    // Actualiza el valor del campo directamente
    const campoControl = this.form.get('idSupplier') as FormControl;
    campoControl.setValue(Number(spliceArray[0]));

    // Iccd inicial
    const campoControlIccd = this.form.get('initialIccd') as FormControl;
    if (spliceArray[2] != 'null') {

      //const ICCD_PROVIDER:number = Number(spliceArray[3]) +1;
      let ICCD_PROVIDER: Big = new Big(spliceArray[2]);
      ICCD_PROVIDER = ICCD_PROVIDER.plus(1);
      campoControlIccd.setValue(ICCD_PROVIDER);

    } else {
      const VALUE: number = 1;
      campoControlIccd.setValue(VALUE);
      campoControlIccd.setValue(VALUE);
    }

    // Email del proveedor
    const campoControlEmail = this.form.get('email') as FormControl;
    campoControlEmail.setValue(spliceArray[3]);

    // Key del proveedor
    const campoKey = this.form.get('key') as FormControl;
    campoKey.setValue(spliceArray[4]);

  }

  changeCustomer(event) {
    const dataCustomer: string = event.target.value;
    //console.log(dataProvider);
    const spliceArray = dataCustomer.split(",");
    //console.log(spliceArray);

    this.client = spliceArray[0];
    this.hlrCustomer = spliceArray[1];
    const campoControlHrl = this.form.get('hlr') as FormControl;
    const campoControlCustomer = this.form.get('customer') as FormControl;

    // Actualiza el valor del campo directamente
    campoControlHrl.setValue(this.hlrCustomer);
    campoControlCustomer.setValue(this.client);
  }

  async changeModel(event) {
    //console.log(event.target.value);
    
    const dataModel: any = event.target.value;
    const spliceArray = dataModel.split(",");
    //console.log(dataModel);
    this.versionModels = [];
    this.versionModels = [...this.versionModels];

    this.nameModel = spliceArray[0];
    await this.getVersionsByIdModel(spliceArray[0]);
  }

  changeVersion(event) {
    //console.log(event.target.value);
    const dataModel: any = event.target.value;
    const ARRAY_VALUE = dataModel.split(",");
    //console.log(ARRAY_VALUE);
    this.versionSize = ARRAY_VALUE[0];
    this.capacity = ARRAY_VALUE[1];
  }

  // Parameters
  async loadParameters() {
    await this.getCustomer();
    await this.getArts();
    await this.getTypes();
    await this.getGraphics();
    await this.getModels();
    await this.getProvider();
    await this.getSimcardParam();
    //await this.getNextImsi();
  }

  /**
   * Método que nos ayuda a crear ordenes de pedidos
   * 
   */
  addOrderCOntrol() {

    const title: string = this.action === 'add' ? "crear" : "actualizar"

    Swal.fire({

      title: 'Advertencia',
      text: `¿Desea ${title} esta orden de pedido?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        Swal.fire({
          title: "Creando pedido...",
          allowOutsideClick: false,
          onBeforeOpen: () => {
            Swal.showLoading();
          }
        });

        let data: OrderControlModel = this.form.value;
        data.supplierName = this.nameProvider;
        //data.model = this.nameModel;
        data.customer = this.client;
        data.customerName = this.client;
        data.hlr = data.hlr.toString();
        data.versionSize = this.versionSize;
        //data.model = this.capacity;
        data.model = this.nameModel.toString();

        delete data.initialIccd;
        delete data.initialImsi;
        delete data.createdDate;
        delete data.batch;
        delete data.fileName;
        delete data.noOrder;

        //console.log(data);

        if (data.orderQuantity > 20000) {
          this.utilService.showNotification(1, "La cantidad máxima por pedido es de (20K) veinte mil unidades");
          Swal.close();
        } else {

          
          const VALIDATE_POST = await this.postOrdersControl(data);

          if (VALIDATE_POST) {
            Swal.close();
            this.utilService.showNotification(0, "Pedido creado con exito");
            this.messegaEvent.emit(true);
            this.closeModal();
          }
          Swal.close();
          


        }

      }
    })

  }

  sendEmail() {
    Swal.fire({
      title: 'Advertencia',
      text: `Si procesa este pedido se le enviará un correo al proveedor, ¿Quiere procesar el pedido?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {
        const data: EmailModel = {
          id: this.data.id
        }

        const VALIDATE_EMAIL = await this.postEmail(data);

        if (VALIDATE_EMAIL) {
          this.utilService.showNotification(0, "Pedido enviado con exito, estar al pendiente de la respuesta del proveedor");
          this.messegaEvent.emit(true);
          this.closeModal();
        }

      }

    });
  }

  // Rest

  /**
   * Método que nos ayuda a obtener el valor del siguiente pedido
   * 
   */
  getNextOrderNumber(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.simcardService.getNextOrderId().pipe(take(1)).subscribe(
        (response) => {
          this.nextOrderNumber = response?.body.data;
          //console.log('Next Order Number:', this.nextOrderNumber);
          resolve(true);
        },
        (error) => {
          //console.error('Error fetching next order ID:', error);
          resolve(false);
        }
      );
    });
  }


  /**
 * Método que consume un servicio para obtener los proveedores
 * 
 */
  getCustomer(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.simcardService.getCustomer().subscribe((response) => {

        if (response.status === 200) {

          this.clientModels = [];

          const providerResponse = response.body as CustomerResponse;

          providerResponse.data.map((dataOk) => {

            let dto: CustomerModel = dataOk;

            this.clientModels.push(dto);

          });

          this.clientModels = [...this.clientModels];
          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }



  /**
   * Método que consume un servicio para obtener los proveedores
   * 
   */
  getProvider(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.simcardService.getProvider().subscribe((response) => {

        if (response.status === 200) {

          this.providerModels = [];

          const providerResponse = response.body as ProviderResponse;

          providerResponse.data.map((dataOk) => {

            let dto: ProviderModel = dataOk;
            dto.statusCode = dto.status === 1 ? 'Activo' : 'Inactivo';

            this.providerModels.push(dto);

          });

          this.providerModels = [...this.providerModels];
          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }


  /**
   * Método que consume un servicio para obtener los artes
   * 
   */
  getArts(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.getArts().subscribe((response) => {

        if (response.status === 200) {

          this.artModels = [];

          const providerResponse = response.body as ArtResponse;

          providerResponse.data.map((dataOk) => {

            let dto: ArtModel = dataOk;

            this.artModels.push(dto);

          });

          this.artModels = [...this.artModels];
          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }
  /**
   * Método que consume un servicio para obtener los artes
   * 
   */
  getTypes(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.getTypes().subscribe((response) => {

        if (response.status === 200) {

          this.typeModels = [];

          const providerResponse = response.body as TypeResponse;

          providerResponse.data.map((dataOk) => {

            let dto: TypeModel = dataOk;

            this.typeModels.push(dto);

          });

          this.typeModels = [...this.typeModels];
          resolve(true);
        } else {
          resolve(true);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }
  /**
   * Método que consume un servicio para obtener los artes
   * 
   */
  getGraphics(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.getGraphics().subscribe((response) => {

        if (response.status === 200) {

          this.graphicModels = [];

          const providerResponse = response.body as GraphicResponse;

          providerResponse.data.map((dataOk) => {

            let dto: GraphicModel = dataOk;

            this.graphicModels.push(dto);

          });

          this.graphicModels = [...this.graphicModels];
          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }
  /**
   * Método que consume un servicio para obtener los artes
   * 
   */
  getModels(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.getModels().subscribe((response) => {

        if (response.status === 200) {

          this.modeloModels = [];

          const providerResponse = response.body as ModelResponse;

          providerResponse.data.map((dataOk) => {

            let dto: ModeloModel = dataOk;

            this.modeloModels.push(dto);

          });

          this.modeloModels = [...this.modeloModels];
          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }

  /**
   * Método que consume un servicio para obtener los artes
   * 
   */
  getVersionsByIdModel(idModal: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.getVersionsByIdModal(idModal).subscribe((response) => {

        if (response.status === 200) {

          this.versionModels = [];

          const providerResponse = response.body as VersionResponse;

          providerResponse.data.map((dataOk) => {

            let dto: VersionModel = dataOk;

            this.versionModels.push(dto);

          });

          this.versionModels = [...this.versionModels];
          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }

  /**
   * Método que obtiene el imsi de los parametros de simcard
   * 
   * @returns 
   */
  getSimcardParam(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.simcardService.getSimcardParam().subscribe((response) => {

        if (response.status === 200) {

          const providerResponse = response.body as ParamSimcardResponse;
          const campoInitialImsi = this.form.get('initialImsi') as FormControl;


          let IMSI_PROVIDER: Big = new Big(providerResponse.data.parameterValue);
          IMSI_PROVIDER = IMSI_PROVIDER.plus(1);
          // Actualiza el valor del campo directamente
          campoInitialImsi.setValue(IMSI_PROVIDER);
          
          resolve(true);
        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }

  /**
   * Método que consume un servicio para crear las orden del pedido
   * 
   */
  postOrdersControl(data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.postOrdersControl(data).subscribe((response) => {

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


  /**
   * Método que consume un servicio para procesar la orden del pedido
   * 
   */
  postEmail(data: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      this.simcardService.postEmail(data).subscribe((response) => {

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
