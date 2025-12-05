import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  private readonly errorClass = 'alert alert-danger alert-with-icon';
  private readonly subs;
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
        timeOut: 2000,
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
        timeOut: 2000,
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

  public setUrl(param, pag) {
    setTimeout(() => {
      let url = `${pag}`
      this.router.navigateByUrl(`mov/${param}/${url}`)
        .finally(() => {
          parent.postMessage(url, '*');
        });
    }, 500);
  }
}
