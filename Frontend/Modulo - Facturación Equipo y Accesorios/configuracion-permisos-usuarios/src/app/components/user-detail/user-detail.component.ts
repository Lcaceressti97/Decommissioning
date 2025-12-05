import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserControlPermissionsResponse, UserPermissionsCancellationResponse, UserPermissionsResponse, UserWithOutPermissionsResponse } from 'src/app/entity/entity';
import { ControlUserPermissions, ControlUserPermissionsCancellation, TypeUser, User, UserModel, UserPermissions, UserWithOutPermission } from 'src/app/models/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { messages } from 'src/app/utils/enum';
import Swal from "sweetalert2/dist/sweetalert2.js";


@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  // Props

  // Inputs
  @Input() userModel: ControlUserPermissions;
  @Input() button: string;
  @Input() typesUser: TypeUser[];
  @Output() messageEvent = new EventEmitter<boolean>();
  messages = messages;

  // Form
  searchInvoiceForm!: FormGroup;

  // Permissions
  userPermissions: UserPermissions = {
    controlUserPermissions: null,
    controlUserPermissionsWithOutFn: null,
    userPermissionsWithFn: null,
    user: null
  }

  // Permios individuales
  authorize: boolean = false;
  generalBill: boolean = false;
  emitAll: boolean = false;
  cancellationWithFn: boolean = false;
  cancellationWithOutFn: boolean = false;
  assignDiscount: boolean = false;

  // Readonly checkbox
  disabled: boolean = false;
  readonlyDataUser: boolean = true;
  readonlyUser: boolean = false;
  readonlyEmail: boolean = false;

  // Array of user without permissions
  arrayUserWithOutPermission: UserWithOutPermission[] = [];
  findUser: UserWithOutPermission = null;
  idUser: number = 0;
  disabledAddUser: boolean = true;

  // Alert
  hiddenAlert: boolean = true;
  hiddenNewUser: boolean = false;
  hiddenUserExiste: boolean = false;

  // Create New User
  newUserBoolean: boolean = false;

  // Button
  disabledButtonSearch: boolean = false;
  disabledAsignar: boolean = false;


  constructor(private invoiceService: InvoiceService, private activeModal: NgbActiveModal, public utilService: UtilService, private readonly fb: FormBuilder) { }

  async ngOnInit() {


    if (this.button !== "add") {
      this.searchInvoiceForm = this.initForm();
      //this.readonlyDataUser = false;
    } else {
      this.searchInvoiceForm = this.initFormCreate();
    }

    if (this.button === "see") {
      this.disabled = true;
      this.readonlyUser = true;
      this.readonlyEmail = true;
      const index: number = this.typesUser.findIndex(item => item.id == this.userModel.typeUser);
      if (index !== -1) {
        const TYPE_CONTROL = this.searchInvoiceForm.get('typeUser') as FormControl;
        TYPE_CONTROL.setValue(this.typesUser[index].typeUser);
        TYPE_CONTROL.updateValueAndValidity();
      }
    } else {
      this.disabled = false;

    }

    if (this.button === "edit") {
      this.readonlyUser = true;
    }



    if (this.button === "see" || this.button === "edit") {
      await this.getControlUserPermissions(this.userModel.userName);
      await this.getUserPermissonsWithFn(this.userModel.userName);
      await this.getUserPermissonsWithOutFn(this.userModel.userName);
      this.hiddenNewUser = true;

      // Seteamos los valores de forma individual para trabajarlo con el [(ngModel)]
      this.setPermissions();
    }



  }

  initFormCreate(): FormGroup {
    return this.fb.group({
      name: ['', []],
      userName: ['', [Validators.required, Validators.maxLength(100)]],
      typeUser: ['', []],
      email: ['', []]
    })
  }

  // Validators.required, Validators.maxLength(100), Validators.email

  initForm(): FormGroup {
    return this.fb.group({
      name: [this.userModel.name, [Validators.required, Validators.maxLength(100)]],
      userName: [this.userModel.userName, [Validators.required, Validators.maxLength(100)]],
      typeUser: [this.userModel.typeUser, [Validators.required]],
      email: [this.userModel.email, [Validators.required, Validators.maxLength(100), Validators.email]]
    })
  }

  closeModal() {
    this.activeModal.close();
  }

  getTitle() {
    if (this.button === "see") return "Permisos del Usuario:";
    if (this.button === "edit") return "Modificar Permisos:";
    if (this.button === "add") return "Asignar Permisos:";
  }


  setPermissions() {

    this.authorize = this.userPermissions.controlUserPermissions.authorizeInvoice === "Y" ? true : false;
    this.generalBill = this.userPermissions.controlUserPermissions.generateBill === "Y" ? true : false;
    this.emitAll = this.userPermissions.controlUserPermissions.issueSellerInvoice === "Y" ? true : false;
    this.cancellationWithFn = this.userPermissions.userPermissionsWithFn.permitStatus === "Y" ? true : false;
    this.cancellationWithOutFn = this.userPermissions.controlUserPermissionsWithOutFn.permitStatus === "Y" ? true : false;
    this.assignDiscount = this.userPermissions.controlUserPermissions.assignDiscount === "Y" ? true : false;
  }


  async validateUser() {
    const validateUser: ControlUserPermissions = this.searchInvoiceForm.value;


    // Verificamos si el campo 'userName' está vacío
    if (!validateUser.userName || validateUser.userName.trim() === "") {
      this.utilService.showNotification(1, "Debe de ingresar un usuario para su búsqueda");
      this.hiddenNewUser = false; // Mantener ocultos los campos adicionales
      return; // Salimos del método
    }
    this.disabledAsignar = true;
    const VALIDATE_FIND_USER = await this.existsUser(validateUser.userName);

    /**
     * Si el usuario existe se cargan sus datos a l
     * 
     */
    if (VALIDATE_FIND_USER) {
      this.hiddenNewUser = true;

      const VALIDATE_FIND_USER_PERMISSIONS = await this.existsUserPermissions(validateUser.userName);

      /**
       * Validamos si el usuario que existe tiene permisos o no
       * 
       */
      if (VALIDATE_FIND_USER_PERMISSIONS) {
        this.utilService.showNotification(1, `El usuario ${validateUser.userName} ya tiene permisos asignados`);
        //this.hiddenNewUser = false;
        //this.readonlyDataUser = true;
        this.hiddenNewUser = false;
        this.disabledAsignar = false;
      } else {
        this.hiddenUserExiste = true;
      }

    } else {
      this.hiddenNewUser = true;
      this.readonlyDataUser = false;
      this.newUserBoolean = true;

      // Agregar las validaciones de los nuevos campos
      const NAME_CONTROL = this.searchInvoiceForm.get('name') as FormControl;
      NAME_CONTROL.setValidators([Validators.required, Validators.maxLength(100)]);
      NAME_CONTROL.setValue('');

      const EMAIL_CONTROL = this.searchInvoiceForm.get('email') as FormControl;
      EMAIL_CONTROL.setValidators([Validators.required, Validators.maxLength(100), Validators.email]);
      EMAIL_CONTROL.setValue('');

      const TYPE_CONTROL = this.searchInvoiceForm.get('typeUser') as FormControl;
      TYPE_CONTROL.setValidators([Validators.required]);
      //TYPE_CONTROL.setValue('');


      NAME_CONTROL.updateValueAndValidity();
      EMAIL_CONTROL.updateValueAndValidity();
      TYPE_CONTROL.updateValueAndValidity();

      this.utilService.showNotification(1, "El usuario ingresado no existe, por favor, ingrese los nuevo valores que se habilitaron para proseguir con la asignación de permisos");

    }




  }


  async updatePermissions() {
    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea actualizar los permisos?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {
      if (result.value) {
        const data: ControlUserPermissions = this.searchInvoiceForm.value;
  
        // Variables de id
        let idUserPermission: number = this.userPermissions.controlUserPermissions?.id;
        let idUserPermissionWithFn: number = this.userPermissions.userPermissionsWithFn?.id;
        let idUserPermissionWithOutFn: number = this.userPermissions.controlUserPermissionsWithOutFn?.id;
  
        // Preparar datos base para permisos
        let userControl: ControlUserPermissions = {
          name: data.name,
          email: data.email,
          userName: data.userName,
          generateBill: this.generalBill === true ? "Y" : "N",
          authorizeInvoice: this.authorize === true ? "Y" : "N",
          issueSellerInvoice: this.emitAll === true ? "Y" : "N",
          typeUser: data.typeUser,
          assignDiscount: this.assignDiscount === true ? "Y" : "N",
          idUser: this.userPermissions.controlUserPermissions?.idUser
        };
  
        let userControlWithFn: ControlUserPermissionsCancellation = {
          userName: data.userName,
          permitStatus: this.cancellationWithFn === true ? "Y" : "N",
          idUser: this.userPermissions.controlUserPermissions?.idUser
        };
  
        let userControlWithOutFn: ControlUserPermissionsCancellation = {
          userName: data.userName,
          permitStatus: this.cancellationWithOutFn === true ? "Y" : "N",
          idUser: this.userPermissions.controlUserPermissions?.idUser
        };
  
        let success = true;
  
        // Control User Permissions
        if (idUserPermission) {
          success = success && await this.putUpdatePermissionControlUser(userControl, idUserPermission);
        } else {
          success = success && await this.postCreatePermissionControlUser(userControl);
        }
  
        // User Permissions With Fiscal Number
        if (idUserPermissionWithFn) {
          success = success && await this.putUpdatePermissionCancelInvoiceWithFiscalNo(userControlWithFn, idUserPermissionWithFn);
        } else {
          success = success && await this.postCreatePermissionCancelInvoiceWithFiscalNo(userControlWithFn);
        }
  
        // User Permissions Without Fiscal Number
        if (idUserPermissionWithOutFn) {
          success = success && await this.putUpdatePermissionCancelInvoiceWithOutFiscalNo(userControlWithOutFn, idUserPermissionWithOutFn);
        } else {
          success = success && await this.postCreatePermissionCancelInvoiceWithOutFiscalNo(userControlWithOutFn);
        }
  
        // Actualizar usuario - Usamos el mismo objeto userControl que ya tiene todos los campos necesarios
        if (this.userPermissions.controlUserPermissions?.idUser) {
          success = success && await this.putUpdateUser(userControl, this.userPermissions.controlUserPermissions.idUser);
        }
  
        if (success) {
          this.utilService.showNotification(0, 'Permisos actualizados correctamente');
        } else {
          this.utilService.showNotification(1, 'No se pudieron actualizar los permisos');
        }
  
        this.messageEvent.emit(true);
        this.closeModal();
      }
    });
  }
  async setPermissonsToUser() {
    Swal.fire({
      title: 'Advertencia',
      text: "¿Desea asignar estos permisos al usuario ingresado?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#002e6e',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar'
    }).then(async (result: any) => {

      if (result.value) {


        let userSelected: UserWithOutPermission = this.searchInvoiceForm.value;

        //const validateFindUser = await this.existsUser(userSelected.userName);

        /**
         * Validamos si existe o no el usuario en la tabla MEA_USERS
         * Si es false existe
         * 
         */
        if (this.newUserBoolean == false) {

          //const VALIDATE_FIND_USER_PERMISSIONS = await this.existsUserPermissions(userSelected.userName);


          /**
           * Validamos si el usuario existe pero no tiene permiso
           * 
           */
          if (this.hiddenUserExiste) {
            // Estructuración para crear los registros



            // Para la tabla CONTROL_USER_PERMISSIONS
            let postControlUserPermisions: ControlUserPermissions = {};
            delete postControlUserPermisions.id;
            delete postControlUserPermisions.created;
            postControlUserPermisions.idUser = this.findUser.id;
            postControlUserPermisions.name = this.findUser.name;
            postControlUserPermisions.email = userSelected.email;
            postControlUserPermisions.userName = this.findUser.userName;
            postControlUserPermisions.generateBill = this.generalBill === true ? "Y" : "N";
            postControlUserPermisions.authorizeInvoice = this.authorize === true ? "Y" : "N";
            postControlUserPermisions.issueSellerInvoice = this.emitAll === true ? "Y" : "N";
            postControlUserPermisions.typeUser = userSelected.typeUser;
            postControlUserPermisions.assignDiscount = this.assignDiscount === true ? "Y" : "N";
            //console.log(postControlUserPermisions);

            // Para la tabla CANCEL_INVOICE_WITH_FN
            let postCancelInvoiceWithFn: ControlUserPermissionsCancellation = {};
            delete postCancelInvoiceWithFn.id;
            delete postCancelInvoiceWithFn.created;
            postCancelInvoiceWithFn.idUser = this.findUser.id;
            postCancelInvoiceWithFn.userName = this.findUser.userName;
            postCancelInvoiceWithFn.permitStatus = this.cancellationWithFn === true ? "Y" : "N";
            //console.log(postCancelInvoiceWithFn);


            // Para la tabla CANCEL_INVOICE_WITH_OUT_FN
            let postCancelInvoiceWithOutFn: ControlUserPermissionsCancellation = {};
            delete postCancelInvoiceWithOutFn.id;
            delete postCancelInvoiceWithOutFn.created;
            postCancelInvoiceWithOutFn.idUser = this.findUser.id;
            postCancelInvoiceWithOutFn.userName = this.findUser.userName;
            postCancelInvoiceWithOutFn.permitStatus = this.cancellationWithOutFn === true ? "Y" : "N";
            //console.log(postCancelInvoiceWithOutFn);

            // Realizamos la inserción de los permisos
            await this.postCreatePermissionControlUser(postControlUserPermisions);
            await this.postCreatePermissionCancelInvoiceWithFiscalNo(postCancelInvoiceWithFn);
            await this.postCreatePermissionCancelInvoiceWithOutFiscalNo(postCancelInvoiceWithOutFn);

            //console.log(this.findUser.id);

            await this.patchUpdateStatusUser(this.findUser.id);

            this.messageEvent.emit(true);
            this.closeModal();

          }

        } else {

          // Estructuración para crear los registros
          userSelected.status = 0;
          await this.postCreateuser(userSelected);
          await this.existsUser(userSelected.userName);

          // Para la tabla CONTROL_USER_PERMISSIONS
          let postControlUserPermisions: ControlUserPermissions = {};
          delete postControlUserPermisions.id;
          delete postControlUserPermisions.created;
          postControlUserPermisions.idUser = this.findUser.id;
          postControlUserPermisions.name = this.findUser.name;
          postControlUserPermisions.email = this.findUser.email;
          postControlUserPermisions.userName = this.findUser.userName;
          postControlUserPermisions.generateBill = this.generalBill === true ? "Y" : "N";
          postControlUserPermisions.authorizeInvoice = this.authorize === true ? "Y" : "N";
          postControlUserPermisions.issueSellerInvoice = this.emitAll === true ? "Y" : "N";
          postControlUserPermisions.assignDiscount = this.assignDiscount === true ? "Y" : "N";
          postControlUserPermisions.typeUser = userSelected.typeUser;


          //console.log(postControlUserPermisions);

          // Para la tabla CANCEL_INVOICE_WITH_FN
          let postCancelInvoiceWithFn: ControlUserPermissionsCancellation = {};
          delete postCancelInvoiceWithFn.id;
          delete postCancelInvoiceWithFn.created;
          postCancelInvoiceWithFn.idUser = this.findUser.id;
          postCancelInvoiceWithFn.userName = this.findUser.userName;
          postCancelInvoiceWithFn.permitStatus = this.cancellationWithFn === true ? "Y" : "N";
          //console.log(postCancelInvoiceWithFn);


          // Para la tabla CANCEL_INVOICE_WITH_OUT_FN
          let postCancelInvoiceWithOutFn: ControlUserPermissionsCancellation = {};
          delete postCancelInvoiceWithOutFn.id;
          delete postCancelInvoiceWithOutFn.created;
          postCancelInvoiceWithOutFn.idUser = this.findUser.id;
          postCancelInvoiceWithOutFn.userName = this.findUser.userName;
          postCancelInvoiceWithOutFn.permitStatus = this.cancellationWithOutFn === true ? "Y" : "N";
          //console.log(postCancelInvoiceWithOutFn);

          // Realizamos la inserción de los permisos
          await this.postCreatePermissionControlUser(postControlUserPermisions);
          await this.postCreatePermissionCancelInvoiceWithFiscalNo(postCancelInvoiceWithFn);
          await this.postCreatePermissionCancelInvoiceWithOutFiscalNo(postCancelInvoiceWithOutFn);

          //console.log(this.findUser.id);

          await this.patchUpdateStatusUser(this.findUser.id);

          this.messageEvent.emit(true);
          this.closeModal();




        }


      }

    });
  }

  // Método para validar la creación del usuario

  getUserPermissonsWithFn(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.getUserPermissonsWithFn(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserPermissionsCancellationResponse;

          this.userPermissions.userPermissionsWithFn = userPermissionsResponse.data[0];

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }

  getUserPermissonsWithOutFn(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.getUserPermissonsWithOutFn(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserPermissionsCancellationResponse;

          this.userPermissions.controlUserPermissionsWithOutFn = userPermissionsResponse.data[0];

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }


  /**
   * Método encargado de validar si existe el usuario en la tabla MEA_USERS
   * 
   * @param username 
   * @returns 
   */
  existsUser(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.existsUser(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserWithOutPermissionsResponse;

          this.arrayUserWithOutPermission = userPermissionsResponse.data;

          if (this.arrayUserWithOutPermission.length === 0) {
            this.utilService.showNotification(1, `El usuario ${username} no está disponible para asignar los permisos`);

          } else {
            this.findUser = this.arrayUserWithOutPermission[0];

            const NAME_CONTROL = this.searchInvoiceForm.get('name') as FormControl;
            NAME_CONTROL.setValidators([Validators.required, Validators.maxLength(100)]);
            NAME_CONTROL.setValue(this.arrayUserWithOutPermission[0].name);

            const EMAIL_CONTROL = this.searchInvoiceForm.get('email') as FormControl;
            EMAIL_CONTROL.setValidators([Validators.required, Validators.maxLength(100), Validators.email]);
            EMAIL_CONTROL.setValue(this.arrayUserWithOutPermission[0].email);

            const TYPE_CONTROL = this.searchInvoiceForm.get('typeUser') as FormControl;
            TYPE_CONTROL.setValidators([Validators.required]);
            TYPE_CONTROL.setValue('');

            NAME_CONTROL.updateValueAndValidity();
            EMAIL_CONTROL.updateValueAndValidity();
            TYPE_CONTROL.updateValueAndValidity();
          }


          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }


  /**
   * Método encargado de validar si existe el usuario en la tabla MEA_CONTROL_USER_PERMISSIONS
   * 
   * @param username 
   * @returns 
   */
  existsUserPermissions(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.existsUserPermissions(username).subscribe((response) => {

        if (response.status === 200) {
          //console.log(response.body);

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }


  /**
* Método que consume un servicio para traer los permisos del usuario
* 
* @returns 
*/
  getControlUserPermissions(username: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.getControlUserPermissonsByUser(username).subscribe((response) => {

        if (response.status === 200) {

          let userPermissionsResponse = response.body as UserControlPermissionsResponse;

          this.userPermissions.controlUserPermissions = userPermissionsResponse.data;

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }



  // Métodos asyncronos para actualizar los permisos
  putUpdatePermissionControlUser(body: any, id: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.putUpdatePermissionControlUser(body, id).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }

  // Métodos asyncronos para actualizar lel usuario
  putUpdateUser(body: any, id: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.putUpdateUser(body, id).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }
  /**
   * Método para actualizar el permiso de anular facturas sin #Fiscal
   * 
   */
  putUpdatePermissionCancelInvoiceWithFiscalNo(body: any, id: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.putUpdatePermissionCancelInvoiceWithFiscalNo(body, id).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }


  /**
   * Método para actualizar el permiso de anular facturas sin emitir
   * 
   * @param body 
   * @param id 
   * @returns 
   */
  putUpdatePermissionCancelInvoiceWithOutFiscalNo(body: any, id: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.putUpdatePermissionCancelInvoiceWithOutFiscalNo(body, id).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }



  /**
   * Métodos para asignar permisos
   * 
   */
  postCreatePermissionControlUser(body: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.postCreatePermissionControlUser(body).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }

  postCreatePermissionCancelInvoiceWithFiscalNo(body: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.postCreatePermissionCancelInvoiceWithFiscalNo(body).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }

  postCreatePermissionCancelInvoiceWithOutFiscalNo(body: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.postCreatePermissionCancelInvoiceWithOutFiscalNo(body).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }


  /**
   * Método que consume un servicio para actualizar el estado del
   * usuario de 0 a 1 indicando que ya se le asigno sus permisos
   * 
   */
  patchUpdateStatusUser(id: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.patchUpdateStatusUser(id).subscribe((response) => {

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }


  postCreateuser(body: any): Promise<boolean> {
    return new Promise((resolve, reject) => {

      // Consume el servicio
      this.invoiceService.postCreateuser(body).subscribe((response) => {

        //console.log(response);

        if (response.status === 200) {

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })
  }

}
