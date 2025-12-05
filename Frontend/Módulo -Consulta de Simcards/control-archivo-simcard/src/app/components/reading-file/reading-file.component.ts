import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OderControlFileResponse } from 'src/app/entities/response';
import { OrderControlModel } from 'src/app/models/model';
import { SimcardControlFileService } from 'src/app/services/simcard-control-file.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-reading-file',
  templateUrl: './reading-file.component.html',
  styleUrls: ['./reading-file.component.css']
})
export class ReadingFileComponent implements OnInit {

  // Props
  active: number = 1;

  // Input
  @Input() data: OrderControlModel = null;

  fileOrder: string = "";
  fileProvider: string = "";

  // Files
  orderFileStr: string = "";
  orderFileDetailStr: string = "";
  inputFileStr: string = "";

  constructor(public utilService: UtilService, private simcardService: SimcardControlFileService, private activeModal: NgbActiveModal) { }

  async ngOnInit() {
    //console.log(this.data);

    await this.getFilesFromOrdersControlById(this.data.id);

    if(this.orderFileStr!==""){
      this.fileOrder = await this.decodeBase64(this.orderFileStr);
      this.inputFileStr = this.fileOrder;
    }

    if(this.orderFileDetailStr!==""){
      this.fileProvider = await this.decodeBase64(this.orderFileDetailStr);
    }

  
  }

  // Methods
  closeModal() {
    this.activeModal.close();
  }

  changeTab(option: number) {
    this.active = option;

    if (option === 1) {
      this.inputFileStr = this.fileOrder;
    } else {
      this.inputFileStr = this.fileProvider;

    }

  }

  // Método para leer el base64 y retornar el string resultante
  decodeBase64(base64String: string): Promise<string> {

    return new Promise((resolve, reject) => {

      try {
        const CONTENT_FILE_STR = atob(base64String);
        resolve(CONTENT_FILE_STR);
      } catch (error) {
        resolve("");
      }

    });

  }

  /**
   * Método encargado de descargar el archivo
   * 
   */
  downloadFile() {
    let fileName: String = "";

    if(this.inputFileStr!==""){

      if(this.active===2){
        
        let filaNameSubString: string = "";
  
        // Valores para substraer el File Name del contenido del archivo del proveedor
        const searchString = 'File Name:';
        const searchStringTwo = 'Order Date:';

        // Se buscan esos valores
        const index = this.inputFileStr.indexOf(searchString);
        const indexFinal = this.inputFileStr.indexOf(searchStringTwo);
    
        /**
         * Si se encuentra se extrae el nombre, sino se utiliza el valor por defecto
         * que aparece en la tabla
         * 
         */
        if (index !== -1 || indexFinal !== -1) {
           filaNameSubString = this.inputFileStr.substring(index , indexFinal).trim();
          const arrayOne: string[] = filaNameSubString.split(":");
          const arrayTwo: string [] = arrayOne[1].split(".");
          fileName = arrayTwo[0].trim();

        } else {
          fileName = this.data.fileName;
        }

      }else{
        fileName = this.data.fileName;
      }

      //console.log(fileName);
      
      
      const blob = new Blob([this.inputFileStr], { type: 'text/plain' });
      
      const anchor = document.createElement('a');

      if(this.active===1){
        anchor.download = `${fileName}.INP`;
      }else{
        anchor.download = `${fileName}.OUT`;
      }
  
  
      anchor.href = window.URL.createObjectURL(blob);
      anchor.click();
      
      
    }else{
      this.utilService.showNotification(1, "No se puede descargar el archivo porque no existe un contenido");
    }


  }


  /**
   * Método que consume un servicio para obtener los base64 de la orden
   * por el id secuencial de la tabla
   * 
   * @param id 
   * @returns 
   */
  getFilesFromOrdersControlById(id: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.simcardService.getFilesFromOrdersControlById(id).subscribe((response) => {

        if (response.status === 200) {

          const orderControlFileResponse = response.body as OderControlFileResponse;

          if (orderControlFileResponse.data.orderFile) {
            this.orderFileStr = orderControlFileResponse.data.orderFile;
          }

          if (orderControlFileResponse.data.orderDetailFile) {
            this.orderFileDetailStr = orderControlFileResponse.data.orderDetailFile;
          }


          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      });

    });
  }

}
