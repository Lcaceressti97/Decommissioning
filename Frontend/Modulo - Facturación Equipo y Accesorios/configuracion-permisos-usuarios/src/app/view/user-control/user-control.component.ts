import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserDetailComponent } from 'src/app/components/user-detail/user-detail.component';
import { ControlPermissionsPagesResponse, ControlUserPermissionsConsult, TypeUserResponse, UserControlPermissionsResponse, UserPermissionsResponse } from 'src/app/entity/entity';
import { ControlUserPermissions, TypeUser, UserModel } from 'src/app/models/model';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import Swal from "sweetalert2/dist/sweetalert2.js";

@Component({
  selector: 'app-user-control',
  templateUrl: './user-control.component.html',
  styleUrls: ['./user-control.component.css']
})
export class UserControlComponent implements OnInit {

  // Props

  // Props of table
  loadingIndicator: boolean = true;
  resultsPerPage: number = 10;
  searchedValue: string = "";
  rows: ControlUserPermissions[] = [];
  rows2: ControlUserPermissions[] = [];

  // Propiedades de paginación
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;

  // Type User
  typesUser: TypeUser[] = [];

  consultForm!: FormGroup;

  inputClassesConsult = "my-auto";


  constructor(public utilService: UtilService,
    private modalService: NgbModal, private invoiceService: InvoiceService, private readonly fb: FormBuilder) { }

  async ngOnInit() {
    this.consultForm = this.initForm();

    await this.getTypesUser();

    this.getControlUserPermissions();

  }


  initForm(): FormGroup {
    return this.fb.group({
      user: ['', [Validators.required]],
    })
  }

  /**
 * Nos ayuda a filtrar, es decir: nos ayuda a buscar
 * valores que están en la tabla
 * 
 */
  search(): void {
    this.rows = this.rows2.filter((row) => {
      return JSON.stringify(row)
        .toLowerCase()
        .includes(this.searchedValue.toString()
          .toLowerCase());
    });
  }

  searchControlUserPermissions() {
    this.rows = this.rows2.filter((controlUserPermissions) => {
      return JSON.stringify(controlUserPermissions)
        .toLowerCase()
        .includes(this.searchedValue.toString().toLowerCase());
    });
  }

  getTotalText() {
    return this.rows2.length == 1 ? "Registro" : "Registros";
  }

  reloadRows() {
    //this.getInvoiceEquipmentAccessoriesExo();
    this.refreshTable();
    //this.getControlUserPermissions();
  }

  async refreshTable() {
    // Limpiar el valor del formulario
    this.consultForm.get('user')?.setValue('');

    this.pageSize = 20;
    this.currentPage = 0;

    // Mostrar el loading
    Swal.fire({
      title: 'Cargando ...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });

    // Llamar a la función para obtener todos los registros
    await this.getControlUserPermissions();

    // Cerrar el loading
    Swal.close();
  }

  /**
   * Método para abrir la modal
   * 
   * @param button identifica si es editar o solo ver
   * @param row el objeto en este caso el usuario
   */
  openModal(button: string, row: ControlUserPermissions = null) {

    const modalRef = this.modalService.open(UserDetailComponent, {
      size: "xl"
    });

    modalRef.componentInstance.userModel = row;
    modalRef.componentInstance.button = button;
    modalRef.componentInstance.typesUser = this.typesUser;
    modalRef.componentInstance.messageEvent.subscribe((reload: boolean) => {

      if (reload) {
        this.reloadRows();
        this.utilService.showNotification(0, "Se asignaron los permisos al usuario");
      }

    });

  }

  // Servicios

  getControlUserPermissonsByUser(username: any) {
    this.invoiceService.getControlUserPermissonsByUser(username).subscribe((response) => {
      if (response.status === 200) {
        this.rows = []; 
        this.rows2 = []; 

        const userData = response.body.data; 

        this.currentPage = 0;
        this.pageSize = 1;
        this.totalElements = 1;
        this.totalPages = 1;

        if (userData) {
          this.rows.push(userData);
          this.rows2.push(userData); 
          this.loadingIndicator = false; 

          this.utilService.showNotification(0, "Datos cargados");
        } else {
          this.utilService.showNotification(1, "No se encontraron datos para el usuario ingresado.!!");
        }
      } else {
        this.utilService.showNotification(1, "No se encontraron datos para el usuario ingresado.!!");
      }
    }, (error) => {
      this.utilService.showNotification(1, "No se encontraron datos para el usuario ingresado.!!");
      this.rows = [];
      this.rows2 = [];
      this.loadingIndicator = false;
      this.currentPage = 0;
      this.pageSize = 0;
      this.totalElements = 0;
      this.totalPages = 0;
    });
  }
  // Método para manejar el cambio de página
  async onPageChange(event: any) {
    this.currentPage = event.offset;
    await this.getControlUserPermissions();
  }

  /**
* Método encargado de obtener los registros de las facturas
* 
*/
  getControlUserPermissions() {

    // Se llama e método del servicio
    this.invoiceService.getControlUserPermissions(this.currentPage, this.pageSize).subscribe((response) => {

      // Validamos si responde con un 200
      if (response.status === 200) {

        // Vaciamos las 
        this.rows = [];
        this.rows2 = [];

        // Mapeamos el body del response
        let billingResponse = response.body as ControlPermissionsPagesResponse;

        // Actualizar la información de paginación
        this.totalElements = billingResponse.data.totalElements;
        this.totalPages = billingResponse.data.totalPages;
        this.currentPage = billingResponse.data.number;

        // Agregamos los valores a los rows

        billingResponse.data.content.map((resourceMap, configError) => {

          let dto: ControlUserPermissions = resourceMap;

          this.rows.push(dto);

        });

        this.loadingIndicator = false;
        this.rows = [...this.rows];
        this.rows2 = [...this.rows];
        if (this.rows.length > 0) {
          this.utilService.showNotification(0, "Datos cargados");
        }

      }

    }, (error) => {

    })
  }

  /**
  * Método encargado de obtener los tipos de usuarios
  * 
  */
  getTypesUser(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      // Se llama e método del servicio
      this.invoiceService.getTypeUser().subscribe((response) => {

        // Validamos si responde con un 200
        if (response.status === 200) {

          // Vaciamos las 
          this.typesUser = [];

          // Mapeamos el body del response
          let typeUserResponse = response.body as TypeUserResponse;

          // Agregamos los valores a los rows

          typeUserResponse.data.map((resourceMap, configError) => {

            let dto: TypeUser = resourceMap;

            this.typesUser.push(dto);

          });

          this.typesUser = [...this.typesUser];
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
