import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util.service';
import { EquipmentInsuranceModel } from 'src/app/models/EquipmentInsuranceModel';
import { Buttons } from 'src/types';

@Component({
  selector: 'app-safe-control-equipment-detail',
  templateUrl: './safe-control-equipment-detail.component.html',
  styleUrls: ['./safe-control-equipment-detail.component.css']
})
export class SafeControlEquipmentDetailComponent implements OnInit {


  @Output() messageEvent = new EventEmitter<EquipmentInsuranceModel>();
  @Input() rowsSafeControlEquipment: EquipmentInsuranceModel;
  @Input() button: Buttons;

  constructor(private activeModal: NgbActiveModal, private modalService: NgbModal, public utilService: UtilService) { }

  ngOnInit(): void {

  }


  closeModal() {
    this.activeModal.close();
  }

}
