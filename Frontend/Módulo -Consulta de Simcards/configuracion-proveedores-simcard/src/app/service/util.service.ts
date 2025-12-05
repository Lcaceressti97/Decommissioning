import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UtilService {


  private readonly errorClass = 'alert alert-danger alert-with-icon';
  private readonly subs;
  private readonly successClass = 'alert alert-success alert-with-icon';
  private readonly infoClass = 'alert alert-info alert-with-icon';

  auth = false;

  constructor(private toastr: ToastrService, private activatedRoute: ActivatedRoute, private router: Router) {
    //  if (window.self !== window.top) {
    this.auth = true
    this.activatedRoute.queryParams.subscribe(params => {
      const sub = JSON.stringify(params.sub);

      if (sub) {
        sessionStorage.setItem('sub', sub);
      }

    });
    this.subs = sessionStorage.getItem('sub').split(',');
    //  }
  }

  handleApiResponse(response: any): void {
    if (response.code === 200) {
      this.showNotificationSuccess(response.description);
    } else {
      if (response.errors && response.errors.length > 0) {

        const errorMessage = response.errors[0].userMessage || 'Error en el proceso';
        this.showNotificationErrorFile(errorMessage);
      } else if (response.description) {

        this.showNotificationErrorFile(response.description);
      } else {

        this.showNotificationErrorFile('Error en el proceso');
      }
    }
  }


  showNotificationSuccess(msj: string): void {
    const text = '<span class="now-ui-icons emoticons_satisfied"></span><b>Éxito</b> ' + msj;
    this.toastr.info(
      text,
      '',
      {
        timeOut: 6000,
        closeButton: true,
        enableHtml: true,
        toastClass: this.successClass,
        positionClass: 'toast-top-right'
      }
    );
  }

  private showNotificationErrorFile(message: string): void {
    this.toastr.error(
      `<b>Error</b> ${message}`,
      '',
      {
        timeOut: 6000,
        closeButton: true,
        enableHtml: true,
        toastClass: this.errorClass,
        positionClass: 'toast-top-right'
      }
    );
  }

  showNotificationError(code: number) {
    let html;
    if (code === 302) {
      html = '<b>Error</b> El dato ya existe';
    } else if (code === 400) {
      html = '<b>Error</b> La información enviada es incorrecta';
    } else if (code === 401) {
      html = '<b>Error</b> No está autorizado para realizar esta acción';
    } else if (code === 404) {
      html = '<b>Error</b> No se encontraron datos';
    } else if (code === 500) {
      html = '<b>Error</b> Interno del servidor';
    }
    this.toastr.info(
      html,
      '',
      {
        timeOut: 2000,
        closeButton: true,
        enableHtml: true,
        toastClass: this.errorClass,
        positionClass: 'toast-top-right'
      }
    );
  }

  setMsg(msg_html: string, titulo: string = '', tiempo: number = 2000, clase: string, posicion: string = 'toast-top-right'): void {
    this.toastr.info(
      msg_html, titulo,
      {
        timeOut: tiempo,
        closeButton: true,
        enableHtml: true,
        toastClass: `alert alert-${clase} alert-with-icon`,
        positionClass: posicion
      }
    )
  }

  showNotification(code: number, msj: string) {
    let text;
    let text2;
    if (code === 0) {
      text = '<span class="now-ui-icons emoticons_satisfied"></span><b>Éxito</b> ' + msj;
      text2 = 'alert alert-success alert-with-icon';
    } else if (code === 1) {
      text = msj;
      text2 = 'alert alert-info alert-with-icon';
    } else {
      text = '<b>Error</b> ' + msj;
      text2 = this.errorClass;
    }
    this.toastr.info(
      text,
      '',
      {
        timeOut: 6000,
        closeButton: true,
        enableHtml: true,
        toastClass: text2,
        positionClass: 'toast-top-right'
      }
    );
  }

  showNotificationMsj(msj: string) {
    let text;
    let text2;
    text = '<b>Error</b> ' + msj;
    text2 = this.errorClass;
    this.toastr.info(
      text,
      '',
      {
        timeOut: 5000,
        closeButton: true,
        enableHtml: true,
        toastClass: text2,
        positionClass: 'toast-top-right'
      }
    );
  }

  getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  loadGrants(grant: string): boolean {
    const grants = this.subs[2].split('_');
    return grants.filter(x => x === grant).length > 0;
  }

  getMask(): number {
    return +this.subs[3].split('_')[0].replace('"', '');
  }

  getSupervisor(): number {
    return +this.subs[3].split('_')[1].replace('"', '');
  }

  getSystemUser(): string {
    return this.subs[0].toString().replace('"', '');
  }

  getSystemRol(): number {
    return +this.subs[1];
  }
}
