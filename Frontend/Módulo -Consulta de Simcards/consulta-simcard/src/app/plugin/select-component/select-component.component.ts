import { Component, OnInit, Input } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-select-component',
  templateUrl: './select-component.component.html',
  styleUrls: ['./select-component.component.css']
})
export class SelectComponentComponent implements OnInit {

  // Props
  active: number = 1;

  @Input() orderControl: any = null;
  @Input() orderDetail: any = null;
  
  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  closeModal() {
    this.activeModal.close();
  }

  /**
 * Este método controla el flujo de visualizar los datos generales o los detalles
 * si y solo si el action es "see"
 * 
 * @param option 
 */
  changeTab(option: number) {
    if (option === 1) {
      this.active = 1;
    }
    if (option === 2) {
      this.active = 2;
    }


  }

}
