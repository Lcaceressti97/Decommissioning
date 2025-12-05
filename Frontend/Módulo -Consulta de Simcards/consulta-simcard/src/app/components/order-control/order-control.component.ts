import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-order-control',
  templateUrl: './order-control.component.html',
  styleUrls: ['./order-control.component.css']
})
export class OrderControlComponent implements OnInit {

  // Props

  @Input() orderControl: any = null;

  constructor() { }

  ngOnInit(): void {
  }

}
