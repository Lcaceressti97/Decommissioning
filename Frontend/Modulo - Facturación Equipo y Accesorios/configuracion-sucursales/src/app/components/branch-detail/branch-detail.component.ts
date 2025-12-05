import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { WareHousesResponse } from 'src/app/entity/response';
import { Branche, ItemSelect, WareHouseModel } from 'src/app/model/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/util/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-branch-detail',
  templateUrl: './branch-detail.component.html',
  styleUrls: ['./branch-detail.component.css']
})
export class BranchDetailComponent implements OnInit {

  // Props

  // Inputs | Outputs
  @Input() branche: Branche;
  @Input() button: string;
  @Output() messageEvent = new EventEmitter<boolean>();
  messages = messages;

  // Form
  brancheForm!: FormGroup;
  reandOnlyForm: boolean = false;

  // WareHouses
  wareHouses: WareHouseModel [] = [];
  hiddenWareHouse: boolean = false;

  // PayLoad
  body: Branche = {};
  wareHouseCode: string = "";
  wareHouseManager: string = "";
  wareHouseName: string = "";

  // Select
  // Bodega
  public dropsownSettingsWareHouse = {};
  public dropDownListWareHouse: ItemSelect[] = [];
  
  public selectedItemWareHouse = [];



  constructor(private invoiceService: InvoiceService, private activeModal: NgbActiveModal, public utilService: UtilService, private readonly fb: FormBuilder) { }

  async ngOnInit() {
    console.log(this.branche);

    this.dropsownSettingsWareHouse = {
      singleSelection: true,
      text: "Seleccione un valor",
      selectAllText: "Seleccionar todos",
      unSelectAllText: "Deseleccionar todo",
      enableSearchFilter: true,
      classes: ""
    };
    
    if(this.button==="add"){
      this.brancheForm = this.initFormAdd();
      await this.getWareHouses();
    }else{
      this.brancheForm = this.initForm();
    }

    if(this.button==="edit"){
      await this.getWareHouses();
      const index = this.dropDownListWareHouse.findIndex(item => item.id === this.branche.wineryCode);
      //console.log(index);
      if (index !== -1) {
        // Manipular el elemento encontrado
        const itemSearched = this.dropDownListWareHouse[index]; // Por ejemplo, cambiar el nombre
        //console.log(itemSearched);
        this.wareHouseCode = itemSearched.id.toString();
        this.wareHouseName = itemSearched.itemName;
        this.selectedItemWareHouse.push(itemSearched);
        const wineryCodeControl = this.brancheForm.get('wineryCode') as FormControl;
        wineryCodeControl.setValue(this.selectedItemWareHouse);
        const wineryNameControl = this.brancheForm.get('wineryName') as FormControl;
        wineryNameControl.setValue(this.selectedItemWareHouse);
        //wineryCodeControl.updateValueAndValidity();
      } else {
        //console.log(`No se encontró ningún elemento con el id ${this.branche.wineryCode}`);
      }
      
    }

    if(this.button==="see") {
      this.reandOnlyForm = true;
      this.hiddenWareHouse = true;
    }
  }

  /**
* Método que contiene todas las validaciones de los input, en este caso sería la del username
* 
*/
  initForm(): FormGroup {
    return this.fb.group({
      idPoint: [this.branche.idPoint, [Validators.required]],
      name: [this.branche.name, [Validators.required, Validators.maxLength(100)]],
      address: [this.branche.address, [Validators.required, Validators.maxLength(500)]],
      phone: [this.branche.phone, [Validators.required, Validators.maxLength(12)]],
      rtn: [this.branche.rtn, [Validators.required, Validators.maxLength(20)]],
      fax: [this.branche.fax, [Validators.required, Validators.maxLength(200)]],
      pbx: [this.branche.pbx, [Validators.required, Validators.maxLength(50)]],
      email: [this.branche.email, [Validators.required, Validators.maxLength(500), Validators.email]],
      acctCode: [this.branche.acctCode, [Validators.required, Validators.maxLength(30)]],
      fictitiousNumber: [this.branche.fictitiousNumber, [Validators.required, Validators.maxLength(50)]],
      idCompany: [this.branche.idCompany, [Validators.required]],
      idSystem: [this.branche.idSystem, [Validators.required]],
      wineryCode: [this.branche.wineryCode, [Validators.required]],
      territory: [this.branche.territory, [Validators.required]],
      wareHouseManager: [this.branche.wareHouseManager, [Validators.required]],
      wineryName: [this.branche.wineryName, []],

    })
  }

  initFormAdd(): FormGroup {
    return this.fb.group({
      idPoint: ['', [Validators.required]],
      name: ['', [Validators.required, Validators.maxLength(100)]],
      address: ['', [Validators.required, Validators.maxLength(500)]],
      phone: ['', [Validators.required, Validators.maxLength(12)]],
      rtn: ['', [Validators.required, Validators.maxLength(20)]],
      fax: ['', [Validators.required, Validators.maxLength(200)]],
      pbx: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.maxLength(500), Validators.email]],
      acctCode: ['', [Validators.required, Validators.maxLength(30)]],
      fictitiousNumber: ['', [Validators.required, Validators.maxLength(50)]],
      idCompany: ['', [Validators.required]],
      idSystem: ['', [Validators.required]],
      wineryCode: ['', [Validators.required]],
      territory: ['', [Validators.required]],
      wareHouseManager: ['', [Validators.required]],
      wineryName: ['', []],
    })
  }

  closeModal() {
    this.activeModal.close();
  }

  getTitle() {
    if (this.button === "see") return "Detalles Sucursal:";
    if (this.button === "edit") return "Modificar Sucursal:";
    if (this.button === "add") return "Crear Nueva Sucursal:";
  }

  onItemSelectWareHouse(event:any) {
    console.log(event);
    this.wareHouseCode = event.id;
    this.wareHouseName = event.itemName;
    this.wareHouseManager = event.wareHouseManager;
    const wineryCodeControl = this.brancheForm.get('wareHouseManager') as FormControl;
    wineryCodeControl.setValue(this.wareHouseManager);
  }
  onItemDeSelectWareHouse(event:any) {
    this.wareHouseCode = null;
    this.wareHouseName = null;
    this.wareHouseManager = "";
    const wineryCodeControl = this.brancheForm.get('wareHouseManager') as FormControl;
    wineryCodeControl.setValue("");
  }


  /**
   * Método que controla el CRUD
   * 
   */
  async actionBranche() {

    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea realizar esta acción?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) =>{

      if(result.value){

        this.body = this.brancheForm.value;
        this.body.wineryCode = this.wareHouseCode.toString();
        this.body.wineryName = this.wareHouseName;
        this.body.wareHouseManager = this.wareHouseManager;
        this.body.fictitiousNumber = this.body.fictitiousNumber.toString();
        console.log(this.body);
        console.log(this.wareHouseCode);
        console.log(this.wareHouseName);
        console.log(this.wareHouseManager);

        
        if(this.button==="add"){

          const validatePost = await this.postBranchesAdd(this.body);

          if(validatePost){
            this.utilService.showNotification(0,"Sucursal creada exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }else{
            //this.utilService.showNotification(2,"No se pudo crear el registro. Contacte al Administrador del Sistema");
          }

        }

        if(this.button==="edit"){
          const validatePut = await this.putBranchesUpdate(this.branche.id,this.body);

          if(validatePut){
            this.utilService.showNotification(0,"Sucursal actualizada exitosamente");
            this.messageEvent.emit(true);
            this.closeModal();
          }else{
            //this.utilService.showNotification(2,"No se pudo actualizar el registro. Contacte al Administrador del Sistema");
          }

        }

        

      }

    });


  }


  // Métodos REST

  /**
   * Método que consume un servicio para crear una nueva sucursal
   * 
   * @param body 
   * @returns 
   */
  postBranchesAdd(body: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.postBranchesAdd(body).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        this.utilService.showNotification(2,`${error.error.errors[0].userMessage}`);
        resolve(false);
      })

    })
  }


  /**
   * Método encargado de actualizar los datos de una sucursal
   * 
   * @param id 
   * @param body 
   * @returns 
   */
  putBranchesUpdate(id:any, body: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.putBranchesUpdate(id,body).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        
        this.utilService.showNotification(2,`${error.error.errors[0].userMessage} `);

        resolve(false);
      })

    })
  }


  /**
   * Método encargado de obtener las bodegas
   * 
   * @returns 
   */
  getWareHouses(): Promise<boolean>{

    return new Promise((resolve,reject)=>{

      this.invoiceService.getWareHouses().subscribe((response)=>{

        if(response.status===200){
          this.wareHouses = [];
        
          let brancheOfficesResponse = response.body as WareHousesResponse;

          brancheOfficesResponse.data.map((resourceMap, configError)=>{

            let dto: WareHouseModel = resourceMap;
            let item: ItemSelect = {
              id: dto.code,
              itemName: `${dto.code} - ${dto.name}`,
              wareHouseManager: dto.responsible
            }

            this.dropDownListWareHouse.push(item);
            this.wareHouses.push(dto);

          });

          this.wareHouses = [...this.wareHouses];
          this.dropDownListWareHouse = [...this.dropDownListWareHouse];

         // console.log(responseInvoice);
          resolve(true);

        }else{
          resolve(false);
        }

      }, (error)=>{
        resolve(false);
      })

    });

  }


}
