import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Logs } from 'src/app/model/model';

@Component({
  selector: 'app-equipment-accessories-log-detail',
  templateUrl: './equipment-accessories-log-detail.component.html',
  styleUrls: ['./equipment-accessories-log-detail.component.css']
})
export class EquipmentAccessoriesLogDetailComponent implements OnInit {

  // Props
  @Input() log: Logs;

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit(): void {

    /*
    const parse = JSON.parse(this.log.srt);
    const formatJson = JSON.stringify(parse, null, 2);
    this.log.srt = formatJson;
    */

  }

  closeModal() {
    this.activeModal.close();
  }

}
