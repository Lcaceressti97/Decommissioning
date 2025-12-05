import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserResponse } from 'src/app/entity/response';
import { Branche, User, UserBranche } from 'src/app/model/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/util/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";



@Component({
  selector: 'app-assign-branch',
  templateUrl: './assign-branch.component.html',
  styleUrls: ['./assign-branch.component.css']
})
export class AssignBranchComponent implements OnInit {

  // Props
  active = 1;

  // Inputs | Outputs
  @Input() branche: Branche;
  @Output() messageEvent = new EventEmitter<boolean>();
  messages = messages;

  // Form
  searchUser!: FormGroup;
  createUserForm!: FormGroup;
  reandOnlyForm: boolean = false;

  userSearch: User = {};

  // Payload
  body: any = {};
  statusCreate: number = 0;
  readOnlyStatus: boolean = false;

  // Table
  rowsUserActive: User[] = [];
  rowsSearch: any = null;
  loadingIndicator: boolean = true;
  resultsPerPage: number = 3;
  searchedValue: string = "";

  rowsUserInactive: User[] = [];
  rowsSearchTwo: any = null;
  loadingIndicatorTwo: boolean = true;
  resultsPerPageTwo: number = 3;
  searchedValueTwo: string = "";

  // Hide
  hideTables1: boolean = false;
  hideTables2: boolean = true;
  hideButton: boolean = false;
  hideForm: boolean = true;

  constructor(private invoiceService: InvoiceService, private activeModal: NgbActiveModal, public utilService: UtilService, private readonly fb: FormBuilder) { }

  ngOnInit(): void {

    this.searchUser = this.initForm();
    this.createUserForm = this.initFormCreate();
    //console.log(this.branche);
    this.getUser();

  }

  /**
   * Formulario para buscar por el usuario
   * 
   */
  initForm(): FormGroup {
    return this.fb.group({
      userName: ['', [Validators.required]]
    });
  }

  initFormCreate(): FormGroup {
    return this.fb.group({
      id: [0, [Validators.required]],
      name: ['', [Validators.required]],
      userName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      status: [this.statusCreate, [Validators.required]],
    });
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
   * Buscardor de la tabla Activos
   * 
   */
  search(): void {
    this.rowsUserActive = this.rowsSearch.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  getTotalText() {
    return this.rowsUserActive.length == 1 ? "Registro" : "Registros";
  }
  getTotalTextOne() {
    return this.rowsUserInactive.length == 1 ? "Registro" : "Registros";
  }


  /**
   * Buscador de la tabla Inactivos
   * 
   */
  searchTwo(): void {
    this.rowsUserInactive = this.rowsSearchTwo.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValueTwo.toString()
          .toLowerCase());
    });
  }


  /**
   * Método para ocultar el formulario y las tablas
   * 
   * @param option 
   */
  changeView(option: any) {


    this.searchUser = this.initForm();
    this.createUserForm = this.initFormCreate();
    /**
     * Si es 1 = Muestra el formulario de asignar
     * Si es 0 = Muestra las tablas
     * 
     */
    if (option === 1) {
      this.hideForm = false;
      this.hideTables1 = true;
      this.hideTables2 = true;
      this.hideButton = true;

      if (this.rowsUserActive.length > 0) {
        this.statusCreate = 0;
        this.readOnlyStatus = true;
      } else {
        this.readOnlyStatus = false;
        this.statusCreate = 1;

      }
      this.createUserForm = this.initFormCreate();

    } else {
      this.hideForm = true;
      this.hideTables1 = false;
      this.hideTables2 = true;
      this.hideButton = false;
      this.active = 1;

    }

  }

  changeTab(option: number) {

    if (option === 1) {
      this.hideTables1 = false;
      this.hideTables2 = true;
    } else {
      this.hideTables1 = true;
      this.hideTables2 = false;

    }


  }

  /**
   * Método encargado de obtener los usuario activos e inactivos
   * 
   */
  async getUser() {

    const validaUserActive = await this.getUserActive();
    const validaUserInactive = await this.getUserInactive();

    if (validaUserActive === false && validaUserInactive === false) {
      this.utilService.showNotification(1, "La sucursal no tiene usuarios asignados");
    }

  }


  /**
   * Método encargado de actualizar el estado y refrescar
   * los datos de las tablas
   * 
   * @param data 
   */
  updateStatus(data: User) {

    const titleAlert = data.status === 1 ? "desactivación" : "activación";

    Swal.fire({
      title: 'Advertencia',
      text: `¿Desea realizar la ${titleAlert} del usuario?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {

        // Condición para verificar si se quiere activar un usuario
        if (data.status === 0) {

          const VALIDATE_USER = await this.getValidateUserById(data.id);

          /**
           * Condición que valida si el usuario está activo en otra sucursal
           * 
           */
          if(VALIDATE_USER){
            const updateStatus = data.status === 0 ? 1 : 0;

            const validateUpdate = await this.patchRegisterOrCancelUser(data.id, updateStatus);

            if (validateUpdate) {
              if (data.status === 0) {
                this.utilService.showNotification(0, "Usuario activado")

              } else {
                this.utilService.showNotification(0, "Usuario inactivado");

              }

              this.closeModal();

            } else {
              this.utilService.showNotification(3, "No se pudo realizar la actualización. Contacte al administrador del sistema");
            }
          }else{
            this.utilService.showNotification(1, `El usuario ${data.userName} está activo en otra sucursal`);
          }


        } else {
          const updateStatus = data.status === 0 ? 1 : 0;

          const validateUpdate = await this.patchRegisterOrCancelUser(data.id, updateStatus);

          if (validateUpdate) {
            if (data.status === 0) {
              this.utilService.showNotification(0, "Usuario activado")

            } else {
              this.utilService.showNotification(0, "Usuario inactivado");

            }

            this.closeModal();

          } else {
            this.utilService.showNotification(3, "No se pudo realizar la actualización. Contacte al administrador del sistema");
          }
        }

      }

    });



  }


  /**
   * Método para encontrar el usuario y colocar los datos en el formulario
   * de asignación en caso que el usuario exista
   * 
   */
  async findUserByUserName() {
    this.userSearch = this.searchUser.value;

    const validateFinUser = await this.getUserByUserName(this.userSearch.userName);

    if (validateFinUser === false) {
      this.createUserForm.get('id').setValue(0);
      this.createUserForm.get('name').setValue("");
      this.createUserForm.get('userName').setValue("");
      this.createUserForm.get('email').setValue("");
      this.utilService.showNotification(1, "No se encontraron datos");
    }

  }


  /**
   * Método que se encarga de validar si el usuario no exista en la sucursal, además,
   * sino existe hace un insert a la tabla para que quede registrado en la sucursal
   * 
   */
  async assignUserToBranch() {
    const userCreate: User = this.createUserForm.value;
    console.log(userCreate);
    userCreate.status = Number(userCreate.status);

      // Buscamos en el array de los usuario activos si existe
      const index = this.rowsUserActive.findIndex(usuario => usuario.userName === userCreate.userName);
      const usuarioExiste = index >= 0;

      // Buscamos en el array de los usuario inactivos si existe
      const indexTwo = this.rowsUserInactive.findIndex(usuario => usuario.userName === userCreate.userName);
      const usuarioExisteTwo = indexTwo >= 0;


      /**
       * Está condición nos ayuda a validar si existe o no el usuario en la sucursal
       * 
       */
      if (usuarioExiste === false && usuarioExisteTwo === false) {


        let VALIDATE_USER = await this.getValidateUserById(userCreate.id);

/*         if(VALIDATE_USER==false && userCreate.status==0){
          VALIDATE_USER = true;
        }
 */
 /*        if(VALIDATE_USER) { */
        // Creamos el body para crear el usuario a la sucursal
        const userBranchOffice: UserBranche = {
          idUser: userCreate.id,
          idBranchOffices: this.branche.id,
          status: userCreate.status
        }

        
        await this.postUserBranche(userBranchOffice);
        this.hideForm = true;
        this.hideButton = false;
        this.hideTables1 = false;
        this.hideTables2 = true;
        this.active = 1;
        //this.getUser();
        this.closeModal();
        
/*         }else {
          this.utilService.showNotification(1, `El usuario ${userCreate.userName} está activo en otra sucursal, puedes asignarlo de manera inactiva`);
        } */


      } else {
        this.utilService.showNotification(1, `El usuario ${userCreate.userName} ya existe en la sucursal`);
      }
    



  }

  /**
   * Método encargado de consumir un servicio que retorna una promesa boolean indicando si se 
   * encontraron datos activos o no
   * 
   * @returns 
   */
  getUserInactive(): Promise<boolean> {
    // Vaciamos las 
    this.rowsUserInactive = [];
    this.rowsSearchTwo = [];
    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getUserByBranchesAndStatus(this.branche.id, 0).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {



          // Mapeamos el body del response
          let userResponse = response.body as UserResponse;

          // Agregamos los valores a los rows

          userResponse.data.map((resourceMap, configError) => {

            let dto: User = resourceMap;

            this.rowsUserInactive.push(dto);

          });

          this.loadingIndicatorTwo = false;
          this.rowsUserInactive = [...this.rowsUserInactive];
          this.rowsSearchTwo = [...this.rowsUserInactive];

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
   * Método encargado de consumir un servicio que retorna una promesa boolean indicando si se 
   * encontraron datos activos o no
   * 
   * @returns 
   */
  getUserActive(): Promise<boolean> {

    return new Promise((resolve, reject) => {

      // Vaciamos las 
      this.rowsUserActive = [];
      this.rowsSearch = [];
      // Se llama e método del servicio
      this.invoiceService.getUserByBranchesAndStatus(this.branche.id, 1).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {



          // Mapeamos el body del response
          let userResponse = response.body as UserResponse;

          // Agregamos los valores a los rows

          userResponse.data.map((resourceMap, configError) => {

            let dto: User = resourceMap;

            this.rowsUserActive.push(dto);

          });

          this.loadingIndicator = false;
          this.rowsUserActive = [...this.rowsUserActive];
          this.rowsSearch = [...this.rowsUserActive];

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
   * Método que consume un servicio para actulizar el estado del usuario en la
   * tabla MEA_USER_BRANCH_OFFICE
   * 
   * @param data 
   * @param status 
   * @returns 
   */
  patchRegisterOrCancelUser(id_user: any, status: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.patchRegisterOrCancelUser(id_user, this.branche.id, status).subscribe((response) => {

        resolve(true);

      }, (error) => {
        resolve(false);
      })

    });

  }


  /**
   * Método que consume un servicio para obtener los datos del usuario y de esa forma
   * actualizar en el formulario reactivo los valores
   * 
   */
  getUserByUserName(userName: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getUserByUserName(userName).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Mapeamos el body del response
          let userResponse = response.body as UserResponse;

          // Seteamos los nuevos valores al formulario reactivo
          this.createUserForm.get('id').setValue(userResponse.data[0].id);
          this.createUserForm.get('name').setValue(userResponse.data[0].name);
          this.createUserForm.get('userName').setValue(userResponse.data[0].userName);
          this.createUserForm.get('email').setValue(userResponse.data[0].email);

          this.utilService.showNotification(0, "Datos encontrados");

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
   * Método que consume un servicio para validar si el usuario está asignado a otra sucursal
   * 
   */
  getValidateUserById(id: any): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getValidateUserById(id).subscribe((response) => {

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

  postUserBranche(data: UserBranche): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.postUserBranche(data).subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          this.utilService.showNotification(0, "usuario asignado a la sucursal exitosamente");

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
