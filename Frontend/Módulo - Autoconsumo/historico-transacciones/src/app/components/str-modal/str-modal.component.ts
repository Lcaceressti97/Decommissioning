import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TransactionHistoryModel } from 'src/app/models/TransactionHistoryModel';

@Component({
  selector: 'app-str-modal',
  templateUrl: './str-modal.component.html',
  styleUrls: ['./str-modal.component.css']
})
export class StrModalComponent implements OnInit {

  @Input() dataSrt: TransactionHistoryModel;

  // Tab
  active: number = 1;

  // Hidden
  hiddeRequest: boolean = false;
  hiddeResponse: boolean = true;

  constructor(private activeModal: NgbActiveModal) { }

  requestObj: any;
  responseObj: any;

  ngOnInit(): void {
    this.requestObj = JSON.parse(this.dataSrt.request);
    this.responseObj = JSON.parse(this.dataSrt.response);
  }

  formatJson(json: any): string {
    return JSON.stringify(json, null, 2);
  }

  // Methods

  /**
* Método que controla la visualización de las tablas 
* 
* @param option 
*/
  changeTab(option: number) {
    //this.resetFormDetail();

    if (option === 1) {
      this.hiddeRequest = false;
      this.hiddeResponse = true;

    } else if (option === 2) {
      this.hiddeRequest = true;
      this.hiddeResponse = false;
    }

  }

  closeModal() {
    this.activeModal.close();
  }


}
