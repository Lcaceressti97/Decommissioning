(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./$$_lazy_route_resource lazy recursive":
/*!******************************************************!*\
  !*** ./$$_lazy_route_resource lazy namespace object ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/app.component.html":
/*!**************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/app.component.html ***!
  \**************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"container p-3 col-lg-10\">\n  <router-outlet></router-outlet>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/create-order-modal/create-order-modal.component.html":
/*!***********************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/components/create-order-modal/create-order-modal.component.html ***!
  \***********************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div>\n    <div class=\"modal-header\">\n        <h4 class=\"modal-title font-weight-bold\" id=\"modal-basic-title\">\n            {{ getTitle() }}\n        </h4>\n        <button type=\"button\" class=\"btn-close\" (click)=\"closeModal()\">\n            <i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i>\n        </button>\n    </div>\n    <div class=\"modal-divider-container\">\n        <hr class=\"modal-divider\">\n    </div>\n    <div class=\"modal-body\">\n        <form [formGroup]=\"createOrderForm\">\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Proveedor:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"suppliersId\"\n                                    [value]=\"simcardPadre?.suppliersId\" readonly />\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"supplierName\"\n                                    [value]=\"simcardPadre?.supplierName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>No. Pedido:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"noOrder\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Cliente:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"customerName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Usuario:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"userName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n            <br>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-12\">\n                        <h6 class=\"text-center font-weight-bold\">IMSI e ICCID</h6>\n                    </div>\n                </div>\n                <br>\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Imsi Inicial:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"initialImsi\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Iccd Inicial:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"initialIccd\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <br>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Cantidad de Pedido:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"number\" class=\"form-control\" formControlName=\"orderQuantity\" />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Nombre de Archivo:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"fileName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Fecha:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"created\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Cliente:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"customer\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>HLr:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"hlr\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Batch:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"batch\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Key:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"key\" />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Tipo:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"type\" />\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"typeInput1\" />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Arte:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"art\" />\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"artInput1\" />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Graphic:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"graphic\" />\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"graphicInput1\" />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-12\">\n                        <h6 class=\"text-center font-weight-bold\">PRODUCTO</h6>\n                    </div>\n                </div>\n                <br>\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Modelo:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"model\" />\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"modelInput1\" />\n                            </div>\n                        </div>\n                    </div>\n\n                </div>\n                <div class=\"row\">\n\n                    <div class=\"col-md-12\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-2\"><b>Version y Tamaño:</b></label>\n                            <div class=\"col-sm-1\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSize\" />\n                            </div>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSizeInput1\" />\n                            </div>\n                            <div class=\"col-sm-3\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSizeInput2\" />\n                            </div>\n                            <div class=\"col-sm-3\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSizeInput3\" />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </form>\n    </div>\n\n    <div class=\"modal-footer\">\n        <button type=\"button\" class=\"btn btn-round bg-danger\" (click)=\"closeModal()\">\n            Cerrar\n        </button>\n        <button type=\"button\" [disabled]=\"createOrderForm.invalid\" class=\"btn btn-round bg-lemon\"\n            (click)=\"saveOrderControl(); updateOrderSimcardControl()\">\n            Crear\n        </button>\n    </div>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/file-detail-modal/file-detail-modal.component.html":
/*!*********************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/components/file-detail-modal/file-detail-modal.component.html ***!
  \*********************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div>\n    <div class=\"modal-header\">\n        <h4 class=\"modal-title font-weight-bold\" id=\"modal-basic-title\">\n            {{ getTitle() }}\n        </h4>\n        <button type=\"button\" class=\"btn-close\" (click)=\"closeModal()\">\n            <i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i>\n        </button>\n    </div>\n    <div class=\"modal-divider-container\">\n        <hr class=\"modal-divider\">\n    </div>\n    <div class=\"modal-body\">\n        <form [formGroup]=\"headerFileForm\">\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-12\">\n                        <h6 class=\"text-center font-weight-bold\">HEADER</h6>\n                    </div>\n                </div>\n                <br>\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>File Name:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"fileName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Order Date:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"orderDate\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Customer Name:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"customerName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Quantity:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"quantity\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Type:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"type\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>ArtWork:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"artWork\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Profile:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"profile\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Batch:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"batch\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Transport Key:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"transportKey\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>SIM-Reference:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"simReference\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Graph ref:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"graphRef\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n            <br>\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-12\">\n                        <h6 class=\"text-center font-weight-bold\">INPUT VARIABLES DESCRIPTION </h6>\n                    </div>\n                </div>\n                <br>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>IMSI:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"imsi\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Ser NB:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"serNb\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </form>\n\n        <br><br>\n        <div class=\"card-container\">\n            <div class=\"row\">\n                <div class=\"col-md-12\">\n                    <h6 class=\"text-center font-weight-bold\">VAR OUT </h6>\n                </div>\n            </div>\n            <br>\n            <div class=\"dataTables_wrapper\">\n                <div class=\"row\">\n                    <div class=\"col-sm-12 col-md-6\">\n                        <div class=\"dataTables_length\" id=\"datatable_length\">\n                            <label>\n                                Se muestran\n                                <select aria-controls=\"datatable\" class=\"form-control form-control-sm\"\n                                    name=\"datatable_length\" [(ngModel)]=\"resultsPerPage\">\n                                    <option [value]=\"10\">10</option>\n                                    <option [value]=\"15\">15</option>\n                                    <option [value]=\"50\">50</option>\n                                </select>\n                                resultados\n                            </label>\n                        </div>\n                    </div>\n                    <div class=\"col-sm-12 col-md-6\">\n                        <div class=\"dataTables_filter\" id=\"datatable_filter\">\n                            <label>\n                                <input aria-controls=\"datatable\" class=\"form-control form-control-sm\"\n                                    placeholder=\"Buscar\" type=\"search\" [(ngModel)]=\"searchedValue\"\n                                    (keyup)=\"searchDetail()\" (search)=\"reloadRows()\" />\n                            </label>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <ngx-datatable class=\"bootstrap selection-cell ngx-datatable fixed-header virtualized mt-2\"\n                [rows]=\"simcardDetailData\" [loadingIndicator]=\"loadingIndicator\" columnMode=\"force\" rowHeight=\"auto\"\n                [limit]=\"resultsPerPage\" [footerHeight]=\"100\"\n                [messages]=\"{emptyMessage: 'No hay detalles disponibles', totalMessage: getTotalText()}\">\n\n                <ngx-datatable-column name=\"ICC\" prop=\"icc\">\n                    <ng-template let-row=\"row\" ngx-datatable-cell-template>\n                        <div style=\"text-align: center;\">\n                            {{ row.icc }}\n                        </div>\n                    </ng-template>\n                </ngx-datatable-column>\n\n                <ngx-datatable-column name=\"IMSI\" prop=\"imsi\">\n                    <ng-template let-row=\"row\" ngx-datatable-cell-template>\n                        <div style=\"text-align: center;\">\n                            {{ row.imsi }}\n                        </div>\n                    </ng-template>\n                </ngx-datatable-column>\n\n                <ngx-datatable-column name=\"KI\" prop=\"ki\">\n                    <ng-template let-row=\"row\" ngx-datatable-cell-template>\n                        <div style=\"text-align: center;\">\n                            {{ row.ki }}\n                        </div>\n                    </ng-template>\n                </ngx-datatable-column>\n\n                <ngx-datatable-column name=\"PIN1\" prop=\"pin1\">\n                    <ng-template let-row=\"row\" ngx-datatable-cell-template>\n                        <div style=\"text-align: center;\">\n                            {{ row.pin1 }}\n                        </div>\n                    </ng-template>\n                </ngx-datatable-column>\n\n                <ngx-datatable-column name=\"PUK1\" prop=\"puk1\">\n                    <ng-template let-row=\"row\" ngx-datatable-cell-template>\n                        <div style=\"text-align: center;\">\n                            {{ row.puk1 }}\n                        </div>\n                    </ng-template>\n                </ngx-datatable-column>\n\n            </ngx-datatable>\n\n        </div>\n    </div>\n\n    <div class=\"modal-footer\">\n        <button type=\"button\" class=\"btn btn-round bg-danger\" (click)=\"closeModal()\">\n            Cerrar\n        </button>\n    </div>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/order-control-modal/order-control-modal.component.html":
/*!*************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/components/order-control-modal/order-control-modal.component.html ***!
  \*************************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div>\n    <div class=\"modal-header\">\n        <h4 class=\"modal-title \" id=\"modal-basic-title\" style=\"font-weight: bold;\">\n            {{getTitle()}}\n        </h4>\n        <button type=\"button\" class=\"btn-close\" (click)=\"closeModal()\">\n            <i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i>\n        </button>\n    </div>\n    <div class=\"modal-divider-container\">\n        <hr class=\"modal-divider\">\n    </div>\n    <div class=\"modal-body\">\n        <div class=\"dataTables_wrapper\">\n            <div class=\"row\">\n                <div class=\"col-sm-12 col-md-6\">\n                    <div class=\"dataTables_length\" id=\"datatable_length\">\n                        <label>\n                            Se muestran\n                            <select aria-controls=\"datatable\" class=\"form-control form-control-sm\"\n                                name=\"datatable_length\" [(ngModel)]=\"resultsPerPage\">\n                                <option [value]=\"10\">10</option>\n                                <option [value]=\"15\">15</option>\n                                <option [value]=\"50\">50</option>\n                            </select>\n                            resultados\n                        </label>\n                    </div>\n                </div>\n                <div class=\"col-sm-12 col-md-6\">\n                    <div class=\"dataTables_filter\" id=\"datatable_filter\">\n                        <label>\n                            <input aria-controls=\"datatable\" class=\"form-control form-control-sm\" placeholder=\"Buscar\"\n                                type=\"search\" [(ngModel)]=\"searchedValue\" (keyup)=\"searchOrderControl()\"\n                                (search)=\"reloadRows()\" />\n                        </label>\n                    </div>\n                </div>\n            </div>\n\n\n            <ngx-datatable class=\"bootstrap selection-cell ngx-datatable fixed-header virtualized mt-2\"\n                [rows]=\"orderControl\" [loadingIndicator]=\"loadingIndicator\" columnMode=\"force\" rowHeight=\"auto\"\n                [limit]=\"resultsPerPage\" [footerHeight]=\"100\"\n                [messages]=\"{emptyMessage: 'No hay ordenes disponibles', totalMessage: getTotalText()}\">\n                <ngx-datatable-column name=\"Proveedor\" prop=\"idSupplier\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Pedido\" prop=\"id\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Fecha\" prop=\"created\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Nombre Proveedor\" prop=\"supplierName\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Cliente\" prop=\"customerName\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Cantidad\" prop=\"orderQuantity\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"ICC\" prop=\"initialIccd\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"IMSI\" prop=\"initialImsi\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Status\" prop=\"status\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"File Name\" prop=\"fileName\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Procesar\" prop=\"Id\">\n                    <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\" let-row=\"row\">\n                        <button type=\"button\"\n                            class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                            data-toggle=\"tooltip\" data-placement=\"top\" title=\"Procesar\"\n                            [disabled]=\"!utilService.loadGrants('GB') || row.status !== 'E'\"  (click)=\"updateStatusOrder(row.id)\">\n                            <i class=\"fa fa-play icon\"></i>\n                        </button>\n                    </ng-template>\n                </ngx-datatable-column>\n            </ngx-datatable>\n        </div>\n    </div>\n    <div class=\"modal-footer\">\n        <button type=\"button\" class=\"btn btn-round bg-danger\" (click)=\"closeModal()\">\n            Cerrar\n        </button>\n    </div>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/see-order-modal/see-order-modal.component.html":
/*!*****************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/components/see-order-modal/see-order-modal.component.html ***!
  \*****************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div>\n    <div class=\"modal-header\">\n        <h4 class=\"modal-title font-weight-bold\" id=\"modal-basic-title\">\n            {{ getTitle() }}\n        </h4>\n        <button type=\"button\" class=\"btn-close\" (click)=\"closeModal()\">\n            <i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i>\n        </button>\n    </div>\n    <div class=\"modal-divider-container\">\n        <hr class=\"modal-divider\">\n    </div>\n    <div class=\"modal-body\">\n        <form [formGroup]=\"seeOrderForm\">\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Proveedor:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"suppliersId\" readonly />\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"supplierName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>No. Pedido:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"noOrder\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Cliente:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"customerName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Usuario:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"userName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n            <br>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-12\">\n                        <h6 class=\"text-center font-weight-bold\">IMSI e ICCID</h6>\n                    </div>\n                </div>\n                <br>\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Imsi Inicial:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"initialImsi\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Iccd Inicial:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"initialIccd\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <br>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Cantidad de Pedido:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"number\" class=\"form-control\" formControlName=\"orderQuantity\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Nombre de Archivo:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"fileName\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Fecha:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"created\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Cliente:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"customer\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>HLr:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"hlr\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Batch:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"batch\" readonly />\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Key:</b></label>\n                            <div class=\"col-sm-8\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"key\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Tipo:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"type\" readonly/>\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"typeInput1\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Arte:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"art\" readonly/>\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"artInput1\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Graphic:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"graphic\" readonly/>\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"graphicInput1\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n\n            <div class=\"card-container\">\n                <div class=\"row\">\n                    <div class=\"col-md-12\">\n                        <h6 class=\"text-center font-weight-bold\">PRODUCTO</h6>\n                    </div>\n                </div>\n                <br>\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-4\"><b>Modelo:</b></label>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"model\" readonly/>\n                            </div>\n                            <div class=\"col-sm-6\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"modelInput1\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n\n                </div>\n                <div class=\"row\">\n\n                    <div class=\"col-md-12\">\n                        <div class=\"form-group row\">\n                            <label class=\"col-sm-2\"><b>Version y Tamaño:</b></label>\n                            <div class=\"col-sm-1\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSize\" readonly/>\n                            </div>\n                            <div class=\"col-sm-2\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSizeInput1\" readonly/>\n                            </div>\n                            <div class=\"col-sm-3\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSizeInput2\" readonly/>\n                            </div>\n                            <div class=\"col-sm-3\">\n                                <input type=\"text\" class=\"form-control\" formControlName=\"versionSizeInput3\" readonly/>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </form>\n    </div>\n\n    <div class=\"modal-footer\">\n        <button type=\"button\" class=\"btn btn-round bg-danger\" (click)=\"closeModal()\">\n            Cerrar\n        </button>\n        <button type=\"button\" class=\"btn btn-round bg-lemon\" [disabled]=\"!utilService.loadGrants('GB') || statusOrder === 'R'\" (click)=\"updateStatusDetail(); updateStatusOrder()\">\n            Procesar\n        </button>\n    </div>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/suppliers-modal/suppliers-modal.component.html":
/*!*****************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/components/suppliers-modal/suppliers-modal.component.html ***!
  \*****************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div>\n    <div class=\"modal-header\">\n        <h4 class=\"modal-title \" id=\"modal-basic-title\" style=\"font-weight: bold;\">\n            {{getTitle()}}\n        </h4>\n        <button type=\"button\" class=\"btn-close\" (click)=\"closeModal()\">\n            <i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i>\n        </button>\n    </div>\n    <div class=\"modal-divider-container\">\n        <hr class=\"modal-divider\">\n    </div>\n    <div class=\"modal-body\">\n        <div class=\"dataTables_wrapper\">\n            <div class=\"row\">\n                <div class=\"col-sm-12 col-md-6\">\n                    <div class=\"dataTables_length\" id=\"datatable_length\">\n                        <label>\n                            Se muestran\n                            <select aria-controls=\"datatable\" class=\"form-control form-control-sm\"\n                                name=\"datatable_length\" [(ngModel)]=\"resultsPerPage\">\n                                <option [value]=\"10\">10</option>\n                                <option [value]=\"15\">15</option>\n                                <option [value]=\"50\">50</option>\n                            </select>\n                            resultados\n                        </label>\n                    </div>\n                </div>\n                <div class=\"col-sm-12 col-md-6\">\n                    <div class=\"dataTables_filter\" id=\"datatable_filter\">\n                        <label>\n                            <input aria-controls=\"datatable\" class=\"form-control form-control-sm\" placeholder=\"Buscar\"\n                                type=\"search\" [(ngModel)]=\"searchedValue\" (keyup)=\"searchSuppliers()\"\n                                (search)=\"reloadRows()\" />\n                        </label>\n                    </div>\n                </div>\n            </div>\n\n\n            <ngx-datatable class=\"bootstrap selection-cell ngx-datatable fixed-header virtualized mt-2\"\n                [rows]=\"rowsSuppliers\" [loadingIndicator]=\"loadingIndicator\" columnMode=\"force\" rowHeight=\"auto\"\n                [limit]=\"resultsPerPage\" [footerHeight]=\"100\"\n                [messages]=\"{emptyMessage: 'No hay proveedores disponibles', totalMessage: getTotalText()}\">\n                <ngx-datatable-column name=\"ID\" prop=\"id\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Nombre Proveedor\" prop=\"supplierName\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Teléfono\" prop=\"phone\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Correo\" prop=\"email\">\n                </ngx-datatable-column>\n                <ngx-datatable-column name=\"Seleccionar\" prop=\"Id\">\n                    <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\" let-row=\"row\">\n                        <button type=\"button\"\n                            class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                            data-toggle=\"tooltip\" data-placement=\"top\" title=\"Seleccionar\"\n                            [disabled]=\"!utilService.loadGrants('GB')\"\n                            (click)=\"updateSuppliersSimcardPadre(row.id);updateSuppliersSimcardControl(row.id)\">\n                            <i class=\"fa fa-check-circle icon\"></i>\n                        </button>\n                    </ng-template>\n                </ngx-datatable-column>\n\n            </ngx-datatable>\n        </div>\n    </div>\n    <div class=\"modal-footer\">\n        <button type=\"button\" class=\"btn btn-round bg-danger\" (click)=\"closeModal()\">\n            Cerrar\n        </button>\n    </div>\n</div>");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/views/simcard-control-file/simcard-control-file.component.html":
/*!**********************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/views/simcard-control-file/simcard-control-file.component.html ***!
  \**********************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"main-content\">\n    <div class=\"row\">\n        <div class=\"col-lg-12\">\n            <div class=\"card\">\n                <div class=\"card-header\">\n                    <div class=\"row\">\n                        <div class=\"col-sm-12 col-md-12 col-lg-12\" style=\"text-align: center;\">\n                            <h5 _ngcontent-c9=\"\" class=\"title\">CONTROL DE PEDIDOS\n                            </h5>\n                        </div>\n                    </div>\n                </div>\n                <br><br>\n                <div class=\"card-body\">\n                    <div class=\"dataTables_wrapper\">\n                        <input type=\"file\" #fileInput style=\"display: none\" (change)=\"onFileSelected($event)\" />\n\n                        <div class=\"row\">\n                            <div class=\"col-md-6\">\n                                <button class=\"btn btn-round mr-2 bg-darkBlue btn-outline-primary\"\n                                    (click)=\"fileInput.click()\">\n                                    <i class=\"fa fa-arrow-up icon\"></i> Subir Archivo\n                                </button>\n\n\n                                <button class=\"btn btn-round mr-2 bg-darkBlue btn-outline-primary\"\n                                    (click)=\"refrescar()\">\n                                    Refrescar\n                                </button>\n                            </div>\n\n                            <!--                   <div class=\"col-md-5 d-flex justify-content-end align-items-center\">\n                                \n                                <button class=\"btn btn-round bg-darkBlue btn-outline-primary\"\n                                        title=\"Control de Pedidos\" [disabled]=\"!utilService.loadGrants('GB')\"\n                                        (click)=\"openModalControlOrder()\">\n                                    <i class=\"fa fa-list-alt\"></i> Control de Pedidos\n                                </button>\n                            </div> -->\n                        </div>\n\n\n\n\n                        <br>\n                        <div class=\"row\">\n                            <div class=\"col-sm-12 col-md-6\">\n                                <div class=\"dataTables_length\" id=\"datatable_length\">\n                                    <label>\n                                        Se muestran\n                                        <select aria-controls=\"datatable\" class=\"form-control form-control-sm\"\n                                            name=\"datatable_length\" [(ngModel)]=\"resultsPerPage\">\n                                            <option [value]=\"10\">10</option>\n                                            <option [value]=\"15\">15</option>\n                                            <option [value]=\"50\">50</option>\n                                        </select>\n                                        resultados\n                                    </label>\n                                </div>\n                            </div>\n                            <div class=\"col-sm-12 col-md-6\">\n                                <div class=\"dataTables_filter\" id=\"datatable_filter\">\n                                    <label>\n                                        <input aria-controls=\"datatable\" class=\"form-control form-control-sm\"\n                                            placeholder=\"Buscar\" type=\"search\" [(ngModel)]=\"searchedValue\"\n                                            (keyup)=\"searchSimcardControl()\" (search)=\"reloadRows()\" />\n                                    </label>\n                                </div>\n                            </div>\n\n                        </div>\n                    </div>\n\n                    <ngx-datatable class=\"bootstrap selection-cell ngx-datatable fixed-header virtualized mt-2\"\n                        [rows]=\"simcardControl\" [loadingIndicator]=\"loadingIndicator\" columnMode=\"force\"\n                        rowHeight=\"auto\" [limit]=\"resultsPerPage\" [footerHeight]=\"100\"\n                        [messages]=\"{emptyMessage: 'No hay archivos disponibles', totalMessage: getTotalText()}\">\n                        <!-- <div visibilityobserver=\"\" class=\"visible\"></div> -->\n                        <ngx-datatable-column name=\"ID\" prop=\"id\">\n                        </ngx-datatable-column>\n                        <ngx-datatable-column name=\"Nombre del Archivo\" prop=\"nameFile\">\n                        </ngx-datatable-column>\n                        <ngx-datatable-column name=\"Estado\" prop=\"status\">\n                            <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\"\n                                let-row=\"row\">\n                                <div [ngClass]=\"getStatusColorClass(row?.statusColor)\">\n                                    {{ row?.status }}\n                                </div>\n                            </ng-template>\n                        </ngx-datatable-column>\n                        <ngx-datatable-column name=\"Fecha\" prop=\"created\">\n                        </ngx-datatable-column>\n                        <ngx-datatable-column name=\"Detalle Archivo\" prop=\"Id\">\n                            <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\"\n                                let-row=\"row\">\n                                <div class=\"text-center\">\n                                    <button type=\"button\"\n                                        class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                                        data-toggle=\"tooltip\" data-placement=\"top\" title=\"Ver contenido del archivo\"\n                                        [disabled]=\"!utilService.loadGrants('GB') || row.status === 'Pendiente'\"\n                                        (click)=\"openDetailModal($event, row)\">\n                                        <i class=\"fa fa-eye\"></i>\n                                    </button>\n\n                                </div>\n                            </ng-template>\n                        </ngx-datatable-column>\n                        <ngx-datatable-column name=\"Proveedores\" prop=\"Id\">\n                            <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\"\n                                let-row=\"row\">\n                                <div class=\"text-center\">\n                                    <button type=\"button\"\n                                        class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                                        data-toggle=\"tooltip\" data-placement=\"top\" title=\"Seleccionar Proveedor\"\n                                        [disabled]=\"!utilService.loadGrants('GB') || row.status === 'Pendiente' || row.suppliersId !== null\"\n\n                                        (click)=\"openModalSuppliers($event, row)\">\n                                        <i class=\"fa fa-user-circle\"></i>\n                                    </button>\n                                </div>\n                            </ng-template>\n                        </ngx-datatable-column>\n\n                        <ngx-datatable-column name=\"Pedido\" prop=\"Id\">\n                            <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\"\n                                let-row=\"row\">\n                                <button type=\"button\"\n                                    class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                                    data-toggle=\"tooltip\" data-placement=\"top\" title=\"Crear Pedido\"\n                                    [disabled]=\"!utilService.loadGrants('GB') || row.status === 'Pendiente' || row.idSimcardOrder !== null\"\n                                    (click)=\"openModalCreateOrder($event, row)\">\n                                    <i class=\"fa fa-cart-plus\"></i>\n                                </button>\n\n\n                                <button type=\"button\"\n                                    class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                                    data-toggle=\"tooltip\" data-placement=\"top\" title=\"Procesar Pedido\"\n                                    [disabled]=\"!utilService.loadGrants('GB') || row.status === 'Pendiente' || row.idSimcardOrder === null\"\n                                    (click)=\"openModalSeeOrder($event, row)\">\n                                    <i class=\"fa fa-cogs\"></i>\n                                </button>\n\n                            </ng-template>\n                        </ngx-datatable-column>\n                        <!--                         <ngx-datatable-column name=\"Ver-Procesar Pedido\" prop=\"Id\">\n                            <ng-template ngx-datatable-cell-template let-rowIndex=\"rowIndex\" let-value=\"value\"\n                                let-row=\"row\">\n                                <div class=\"text-center\">\n                                    <button type=\"button\"\n                                        class=\"btn btn-round btn-outline-danger bg-darkBlue smaller-button mr-2 mt-2 mt-md-2 mt-lg-1\"\n                                        data-toggle=\"tooltip\" data-placement=\"top\" title=\"Ver / Procesar Pedido\"\n                                        [disabled]=\"!utilService.loadGrants('GB') || row.status === 'Pendiente'\"\n                                        (click)=\"openModalSeeOrder($event, row)\">\n                                        <i class=\"fa fa-cogs\"></i>\n                                    </button>\n                                </div>\n                            </ng-template>\n                        </ngx-datatable-column> -->\n                    </ngx-datatable>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>");

/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js");
/* harmony import */ var _views_simcard_control_file_simcard_control_file_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./views/simcard-control-file/simcard-control-file.component */ "./src/app/views/simcard-control-file/simcard-control-file.component.ts");




const routes = [
    { path: 'mov', component: _views_simcard_control_file_simcard_control_file_component__WEBPACK_IMPORTED_MODULE_3__["SimcardControlFileComponent"] },
    { path: 'mov', redirectTo: 'mov', pathMatch: 'full' },
    { path: '', redirectTo: 'mov', pathMatch: 'full' },
    { path: '**', redirectTo: 'mov', pathMatch: 'full' },
];
let AppRoutingModule = class AppRoutingModule {
};
AppRoutingModule = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forRoot(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]]
    })
], AppRoutingModule);



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2FwcC5jb21wb25lbnQuY3NzIn0= */");

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");


let AppComponent = class AppComponent {
    constructor() {
        this.title = 'control-archivo-simcard';
    }
};
AppComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./app.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/app.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")).default]
    })
], AppComponent);



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/__ivy_ngcc__/fesm2015/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _swimlane_ngx_datatable__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @swimlane/ngx-datatable */ "./node_modules/@swimlane/ngx-datatable/__ivy_ngcc__/fesm2015/swimlane-ngx-datatable.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/__ivy_ngcc__/fesm2015/http.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/__ivy_ngcc__/fesm2015/animations.js");
/* harmony import */ var ngx_toastr__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ngx-toastr */ "./node_modules/ngx-toastr/__ivy_ngcc__/fesm2015/ngx-toastr.js");
/* harmony import */ var ngx_mask__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ngx-mask */ "./node_modules/ngx-mask/__ivy_ngcc__/fesm2015/ngx-mask.js");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var angular2_multiselect_dropdown__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! angular2-multiselect-dropdown */ "./node_modules/angular2-multiselect-dropdown/__ivy_ngcc__/fesm2015/angular2-multiselect-dropdown.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var ngx_color_picker__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ngx-color-picker */ "./node_modules/ngx-color-picker/__ivy_ngcc__/fesm2015/ngx-color-picker.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/__ivy_ngcc__/fesm2015/common.js");
/* harmony import */ var _views_simcard_control_file_simcard_control_file_component__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./views/simcard-control-file/simcard-control-file.component */ "./src/app/views/simcard-control-file/simcard-control-file.component.ts");
/* harmony import */ var _components_suppliers_modal_suppliers_modal_component__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ./components/suppliers-modal/suppliers-modal.component */ "./src/app/components/suppliers-modal/suppliers-modal.component.ts");
/* harmony import */ var _components_create_order_modal_create_order_modal_component__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./components/create-order-modal/create-order-modal.component */ "./src/app/components/create-order-modal/create-order-modal.component.ts");
/* harmony import */ var _components_order_control_modal_order_control_modal_component__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! ./components/order-control-modal/order-control-modal.component */ "./src/app/components/order-control-modal/order-control-modal.component.ts");
/* harmony import */ var _components_file_detail_modal_file_detail_modal_component__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! ./components/file-detail-modal/file-detail-modal.component */ "./src/app/components/file-detail-modal/file-detail-modal.component.ts");
/* harmony import */ var _components_see_order_modal_see_order_modal_component__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! ./components/see-order-modal/see-order-modal.component */ "./src/app/components/see-order-modal/see-order-modal.component.ts");





















let AppModule = class AppModule {
};
AppModule = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
        declarations: [
            _app_component__WEBPACK_IMPORTED_MODULE_10__["AppComponent"],
            _views_simcard_control_file_simcard_control_file_component__WEBPACK_IMPORTED_MODULE_15__["SimcardControlFileComponent"],
            _components_suppliers_modal_suppliers_modal_component__WEBPACK_IMPORTED_MODULE_16__["SuppliersModalComponent"],
            _components_create_order_modal_create_order_modal_component__WEBPACK_IMPORTED_MODULE_17__["CreateOrderModalComponent"],
            _components_order_control_modal_order_control_modal_component__WEBPACK_IMPORTED_MODULE_18__["OrderControlModalComponent"],
            _components_file_detail_modal_file_detail_modal_component__WEBPACK_IMPORTED_MODULE_19__["FileDetailModalComponent"],
            _components_see_order_modal_see_order_modal_component__WEBPACK_IMPORTED_MODULE_20__["SeeOrderModalComponent"]
        ],
        imports: [
            _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["BrowserModule"],
            _app_routing_module__WEBPACK_IMPORTED_MODULE_9__["AppRoutingModule"],
            angular2_multiselect_dropdown__WEBPACK_IMPORTED_MODULE_11__["AngularMultiSelectModule"],
            _swimlane_ngx_datatable__WEBPACK_IMPORTED_MODULE_3__["NgxDatatableModule"],
            _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClientModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormsModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ReactiveFormsModule"],
            _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_6__["BrowserAnimationsModule"],
            ngx_mask__WEBPACK_IMPORTED_MODULE_8__["NgxMaskModule"].forRoot(),
            ngx_toastr__WEBPACK_IMPORTED_MODULE_7__["ToastrModule"].forRoot(),
            ngx_color_picker__WEBPACK_IMPORTED_MODULE_13__["ColorPickerModule"]
        ],
        providers: [_ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_12__["NgbActiveModal"], _angular_common__WEBPACK_IMPORTED_MODULE_14__["DatePipe"]],
        bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_10__["AppComponent"]],
        entryComponents: []
    })
], AppModule);



/***/ }),

/***/ "./src/app/components/create-order-modal/create-order-modal.component.css":
/*!********************************************************************************!*\
  !*** ./src/app/components/create-order-modal/create-order-modal.component.css ***!
  \********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".btn-close {\r\n    border: none !important;\r\n    background: transparent;\r\n    padding: 0px;\r\n    width: 30px;\r\n    height: 30px;\r\n    font-size: 30px;\r\n}\r\n\r\n.fa {\r\n    color: #ff3636 !important;\r\n}\r\n\r\n.icon {\r\n    color: #ffffff !important;\r\n}\r\n\r\n.modal-footer {\r\n    justify-content: end;\r\n}\r\n\r\n.modal-footer button {\r\n    justify-content: end;\r\n    margin-left: 10px;\r\n}\r\n\r\n/* Importar Bootstrap */\r\n\r\n/* Estilos personalizados para el modal */\r\n\r\n.modal-body {\r\n    padding: 20px;\r\n}\r\n\r\n.table-bordered th,\r\n.table-bordered td {\r\n    border: 1px solid #dee2e6;\r\n}\r\n\r\n.table-bordered th {\r\n    background-color: #f8f9fa;\r\n}\r\n\r\n.form-check-input[type=\"checkbox\"] {\r\n    margin-right: 8px;\r\n}\r\n\r\n.modal-divider-container {\r\n    margin: 0 1rem;\r\n    /* Márgenes izquierdo y derecho */\r\n}\r\n\r\n.modal-divider {\r\n    border: none;\r\n    border-top: 1px solid #022649;\r\n    /* Color de la línea */\r\n    margin: 5;\r\n    /* Restablecer margen para la línea */\r\n}\r\n\r\n.card-container {\r\n    border: 1px solid #ddd; /* Añade un borde de 1 píxel sólido */\r\n    border-radius: 8px; /* Añade esquinas redondeadas */\r\n    padding: 15px; /* Añade relleno interno */\r\n    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Añade una suave sombra */\r\n    margin-bottom: 15px; /* Añade espacio en la parte inferior para separar de otros elementos */\r\n}\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9jcmVhdGUtb3JkZXItbW9kYWwvY3JlYXRlLW9yZGVyLW1vZGFsLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSx1QkFBdUI7SUFDdkIsdUJBQXVCO0lBQ3ZCLFlBQVk7SUFDWixXQUFXO0lBQ1gsWUFBWTtJQUNaLGVBQWU7QUFDbkI7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSxvQkFBb0I7QUFDeEI7O0FBRUE7SUFDSSxvQkFBb0I7SUFDcEIsaUJBQWlCO0FBQ3JCOztBQUVBLHVCQUF1Qjs7QUFHdkIseUNBQXlDOztBQUN6QztJQUNJLGFBQWE7QUFDakI7O0FBRUE7O0lBRUkseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0kseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0ksaUJBQWlCO0FBQ3JCOztBQUVBO0lBQ0ksY0FBYztJQUNkLGlDQUFpQztBQUNyQzs7QUFFQTtJQUNJLFlBQVk7SUFDWiw2QkFBNkI7SUFDN0Isc0JBQXNCO0lBQ3RCLFNBQVM7SUFDVCxxQ0FBcUM7QUFDekM7O0FBRUE7SUFDSSxzQkFBc0IsRUFBRSxxQ0FBcUM7SUFDN0Qsa0JBQWtCLEVBQUUsK0JBQStCO0lBQ25ELGFBQWEsRUFBRSwwQkFBMEI7SUFDekMsd0NBQXdDLEVBQUUsMkJBQTJCO0lBQ3JFLG1CQUFtQixFQUFFLHVFQUF1RTtBQUNoRyIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvY3JlYXRlLW9yZGVyLW1vZGFsL2NyZWF0ZS1vcmRlci1tb2RhbC5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLmJ0bi1jbG9zZSB7XHJcbiAgICBib3JkZXI6IG5vbmUgIWltcG9ydGFudDtcclxuICAgIGJhY2tncm91bmQ6IHRyYW5zcGFyZW50O1xyXG4gICAgcGFkZGluZzogMHB4O1xyXG4gICAgd2lkdGg6IDMwcHg7XHJcbiAgICBoZWlnaHQ6IDMwcHg7XHJcbiAgICBmb250LXNpemU6IDMwcHg7XHJcbn1cclxuXHJcbi5mYSB7XHJcbiAgICBjb2xvcjogI2ZmMzYzNiAhaW1wb3J0YW50O1xyXG59XHJcblxyXG4uaWNvbiB7XHJcbiAgICBjb2xvcjogI2ZmZmZmZiAhaW1wb3J0YW50O1xyXG59XHJcblxyXG4ubW9kYWwtZm9vdGVyIHtcclxuICAgIGp1c3RpZnktY29udGVudDogZW5kO1xyXG59XHJcblxyXG4ubW9kYWwtZm9vdGVyIGJ1dHRvbiB7XHJcbiAgICBqdXN0aWZ5LWNvbnRlbnQ6IGVuZDtcclxuICAgIG1hcmdpbi1sZWZ0OiAxMHB4O1xyXG59XHJcblxyXG4vKiBJbXBvcnRhciBCb290c3RyYXAgKi9cclxuQGltcG9ydCBcIn5ib290c3RyYXAvZGlzdC9jc3MvYm9vdHN0cmFwLm1pbi5jc3NcIjtcclxuXHJcbi8qIEVzdGlsb3MgcGVyc29uYWxpemFkb3MgcGFyYSBlbCBtb2RhbCAqL1xyXG4ubW9kYWwtYm9keSB7XHJcbiAgICBwYWRkaW5nOiAyMHB4O1xyXG59XHJcblxyXG4udGFibGUtYm9yZGVyZWQgdGgsXHJcbi50YWJsZS1ib3JkZXJlZCB0ZCB7XHJcbiAgICBib3JkZXI6IDFweCBzb2xpZCAjZGVlMmU2O1xyXG59XHJcblxyXG4udGFibGUtYm9yZGVyZWQgdGgge1xyXG4gICAgYmFja2dyb3VuZC1jb2xvcjogI2Y4ZjlmYTtcclxufVxyXG5cclxuLmZvcm0tY2hlY2staW5wdXRbdHlwZT1cImNoZWNrYm94XCJdIHtcclxuICAgIG1hcmdpbi1yaWdodDogOHB4O1xyXG59XHJcblxyXG4ubW9kYWwtZGl2aWRlci1jb250YWluZXIge1xyXG4gICAgbWFyZ2luOiAwIDFyZW07XHJcbiAgICAvKiBNw6FyZ2VuZXMgaXpxdWllcmRvIHkgZGVyZWNobyAqL1xyXG59XHJcblxyXG4ubW9kYWwtZGl2aWRlciB7XHJcbiAgICBib3JkZXI6IG5vbmU7XHJcbiAgICBib3JkZXItdG9wOiAxcHggc29saWQgIzAyMjY0OTtcclxuICAgIC8qIENvbG9yIGRlIGxhIGzDrW5lYSAqL1xyXG4gICAgbWFyZ2luOiA1O1xyXG4gICAgLyogUmVzdGFibGVjZXIgbWFyZ2VuIHBhcmEgbGEgbMOtbmVhICovXHJcbn1cclxuXHJcbi5jYXJkLWNvbnRhaW5lciB7XHJcbiAgICBib3JkZXI6IDFweCBzb2xpZCAjZGRkOyAvKiBBw7FhZGUgdW4gYm9yZGUgZGUgMSBww614ZWwgc8OzbGlkbyAqL1xyXG4gICAgYm9yZGVyLXJhZGl1czogOHB4OyAvKiBBw7FhZGUgZXNxdWluYXMgcmVkb25kZWFkYXMgKi9cclxuICAgIHBhZGRpbmc6IDE1cHg7IC8qIEHDsWFkZSByZWxsZW5vIGludGVybm8gKi9cclxuICAgIGJveC1zaGFkb3c6IDAgNHB4IDhweCByZ2JhKDAsIDAsIDAsIDAuMSk7IC8qIEHDsWFkZSB1bmEgc3VhdmUgc29tYnJhICovXHJcbiAgICBtYXJnaW4tYm90dG9tOiAxNXB4OyAvKiBBw7FhZGUgZXNwYWNpbyBlbiBsYSBwYXJ0ZSBpbmZlcmlvciBwYXJhIHNlcGFyYXIgZGUgb3Ryb3MgZWxlbWVudG9zICovXHJcbn0iXX0= */");

/***/ }),

/***/ "./src/app/components/create-order-modal/create-order-modal.component.ts":
/*!*******************************************************************************!*\
  !*** ./src/app/components/create-order-modal/create-order-modal.component.ts ***!
  \*******************************************************************************/
/*! exports provided: CreateOrderModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CreateOrderModalComponent", function() { return CreateOrderModalComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/services/simcard-control-file.service */ "./src/app/services/simcard-control-file.service.ts");
/* harmony import */ var src_app_services_util_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/services/util.service */ "./src/app/services/util.service.ts");
/* harmony import */ var sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! sweetalert2/dist/sweetalert2.js */ "./node_modules/sweetalert2/dist/sweetalert2.js");
/* harmony import */ var sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6__);
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm2015/operators/index.js");









let CreateOrderModalComponent = class CreateOrderModalComponent {
    constructor(simcardControlFileService, activeModal, utilService, fb) {
        this.simcardControlFileService = simcardControlFileService;
        this.activeModal = activeModal;
        this.utilService = utilService;
        this.fb = fb;
        this.idSimcardPadre = null;
        this.idSimcardControl = null;
        this.suppliersIdEvent = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.simcardPadre = [];
        this.rowsSimcardPadre = [];
        this.loadingIndicator = true;
    }
    ngOnInit() {
        this.getSimcardPadreById();
        this.getNextOrderNumber();
        this.initForm();
    }
    initForm() {
        this.createOrderForm = this.fb.group({
            suppliersId: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            supplierName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            noOrder: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            userName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            customerName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            initialImsi: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            initialIccd: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            orderQuantity: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            fileName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            created: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            customer: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            hlr: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            batch: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            key: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            type: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            typeInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            art: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            artInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            graphic: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            graphicInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            model: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            modelInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSize: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSizeInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSizeInput2: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSizeInput3: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
        });
    }
    //* COMPONENTS
    getSimcardPadreById() {
        const idSimcardPadre = this.idSimcardPadre;
        this.simcardControlFileService.getSimcardPadreById(idSimcardPadre).subscribe((res) => {
            if (res.status === 200) {
                let response = res.body.data;
                if (response) {
                    this.createOrderForm.reset();
                    this.createOrderForm.patchValue({
                        suppliersId: response === null || response === void 0 ? void 0 : response.suppliersId,
                        supplierName: response === null || response === void 0 ? void 0 : response.supplierName,
                        customerName: response === null || response === void 0 ? void 0 : response.customerName,
                        initialImsi: response === null || response === void 0 ? void 0 : response.imsi,
                        initialIccd: response === null || response === void 0 ? void 0 : response.serNb,
                        fileName: response === null || response === void 0 ? void 0 : response.fileName,
                        created: response === null || response === void 0 ? void 0 : response.orderDate,
                        customer: response === null || response === void 0 ? void 0 : response.customerName,
                        hlr: response === null || response === void 0 ? void 0 : response.profile,
                        batch: response === null || response === void 0 ? void 0 : response.batch,
                        userName: this.utilService.getSystemUser().toUpperCase(),
                        noOrder: this.nextOrderNumber
                    });
                    this.loadingIndicator = false;
                }
                else {
                    console.error("La respuesta del servidor no contiene datos.");
                }
            }
            else {
                this.utilService.showNotificationError(res.status);
            }
            if (!this.createOrderForm.value.suppliersId || !this.createOrderForm.value.supplierName) {
                sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6___default.a.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Debe seleccionar un proveedor para crear la orden.',
                });
            }
            else {
                this.suppliersIdEvent.emit(this.createOrderForm.value.suppliersId);
            }
        });
    }
    getNextOrderNumber() {
        this.simcardControlFileService.getNextOrderId().pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_7__["take"])(1)).subscribe((response) => {
            this.nextOrderNumber = response === null || response === void 0 ? void 0 : response.body.data;
            console.log('Next Order Number:', this.nextOrderNumber);
        }, (error) => {
            console.error('Error fetching next order ID:', error);
        });
    }
    saveOrderControl() {
        if (this.createOrderForm.valid) {
            const orderControl = {
                idSimcardPadre: this.idSimcardPadre,
                idSupplier: +this.createOrderForm.value.suppliersId,
                supplierName: this.createOrderForm.value.supplierName,
                noOrder: +this.createOrderForm.value.noOrder,
                userName: this.createOrderForm.value.userName,
                customerName: this.createOrderForm.value.customerName,
                initialImsi: this.createOrderForm.value.initialImsi,
                initialIccd: this.createOrderForm.value.initialIccd,
                orderQuantity: +this.createOrderForm.value.orderQuantity,
                fileName: this.createOrderForm.value.fileName,
                created: this.createOrderForm.value.created,
                customer: this.createOrderForm.value.customer,
                hlr: this.createOrderForm.value.hlr,
                batch: this.createOrderForm.value.batch,
                key: this.createOrderForm.value.key,
                type: this.createOrderForm.value.type + " " + this.createOrderForm.value.typeInput1,
                art: this.createOrderForm.value.art + " " + this.createOrderForm.value.artInput1,
                graphic: this.createOrderForm.value.graphic + " " + this.createOrderForm.value.graphicInput1,
                model: this.createOrderForm.value.model + " " + this.createOrderForm.value.modelInput1,
                versionSize: this.createOrderForm.value.versionSize + " " + this.createOrderForm.value.versionSizeInput1 + " " + this.createOrderForm.value.versionSizeInput2 + " " + this.createOrderForm.value.versionSizeInput3,
                status: 'E'
            };
            sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6___default.a.fire({
                html: `
          <h5>Se creará una nueva orden de control. ¿Desea continuar?</h5>`,
                showCancelButton: true,
                customClass: {
                    confirmButton: "btn btn-round bg-lemon",
                    cancelButton: "btn btn-round btn-danger",
                },
                reverseButtons: true,
                confirmButtonText: "Continuar",
                buttonsStyling: false,
            }).then((result) => {
                if (result.value) {
                    this.simcardControlFileService
                        .saveOrderControl(orderControl)
                        .subscribe((res) => {
                        if (res.status === 200) {
                            this.utilService.showNotificationSuccess("Orden guardada exitosamente");
                            this.closeModal();
                        }
                        else {
                            this.utilService.showNotificationError(res.status);
                        }
                    });
                }
            });
        }
        else {
            this.utilService.showNotification(1, "Por favor, complete todos los campos obligatorios.");
        }
    }
    updateOrderSimcardControl() {
        let id = parseInt(this.nextOrderNumber, 10);
        this.simcardControlFileService.updateOrderSimcardControl(this.idSimcardControl, id).subscribe(() => {
            //this.activeModal.close();
        }, error => {
            console.error(error);
        });
    }
    //* UTILS
    getTitle() {
        return "Control Pedidos Proveedores:";
    }
    closeModal() {
        this.activeModal.close();
    }
};
CreateOrderModalComponent.ctorParameters = () => [
    { type: src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_4__["SimcardControlFileService"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__["NgbActiveModal"] },
    { type: src_app_services_util_service__WEBPACK_IMPORTED_MODULE_5__["UtilService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] }
];
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], CreateOrderModalComponent.prototype, "idSimcardPadre", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], CreateOrderModalComponent.prototype, "formData", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], CreateOrderModalComponent.prototype, "idSimcardControl", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])()
], CreateOrderModalComponent.prototype, "suppliersIdEvent", void 0);
CreateOrderModalComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-create-order-modal',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./create-order-modal.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/create-order-modal/create-order-modal.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./create-order-modal.component.css */ "./src/app/components/create-order-modal/create-order-modal.component.css")).default]
    })
], CreateOrderModalComponent);



/***/ }),

/***/ "./src/app/components/file-detail-modal/file-detail-modal.component.css":
/*!******************************************************************************!*\
  !*** ./src/app/components/file-detail-modal/file-detail-modal.component.css ***!
  \******************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".btn-close {\r\n    border: none !important;\r\n    background: transparent;\r\n    padding: 0px;\r\n    width: 30px;\r\n    height: 30px;\r\n    font-size: 30px;\r\n}\r\n\r\n.fa {\r\n    color: #ff3636 !important;\r\n}\r\n\r\n.icon {\r\n    color: #ffffff !important;\r\n}\r\n\r\n.modal-footer {\r\n    justify-content: end;\r\n}\r\n\r\n.modal-footer button {\r\n    justify-content: end;\r\n    margin-left: 10px;\r\n}\r\n\r\n/* Importar Bootstrap */\r\n\r\n/* Estilos personalizados para el modal */\r\n\r\n.modal-body {\r\n    padding: 20px;\r\n}\r\n\r\n.table-bordered th,\r\n.table-bordered td {\r\n    border: 1px solid #dee2e6;\r\n}\r\n\r\n.table-bordered th {\r\n    background-color: #f8f9fa;\r\n}\r\n\r\n.form-check-input[type=\"checkbox\"] {\r\n    margin-right: 8px;\r\n}\r\n\r\n.modal-divider-container {\r\n    margin: 0 1rem;\r\n    /* Márgenes izquierdo y derecho */\r\n}\r\n\r\n.modal-divider {\r\n    border: none;\r\n    border-top: 1px solid #022649;\r\n    /* Color de la línea */\r\n    margin: 5;\r\n    /* Restablecer margen para la línea */\r\n}\r\n\r\n.card-container {\r\n    border: 1px solid #ddd;\r\n    /* Añade un borde de 1 píxel sólido */\r\n    border-radius: 8px;\r\n    /* Añade esquinas redondeadas */\r\n    padding: 15px;\r\n    /* Añade relleno interno */\r\n    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\r\n    /* Añade una suave sombra */\r\n    margin-bottom: 15px;\r\n    /* Añade espacio en la parte inferior para separar de otros elementos */\r\n}\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9maWxlLWRldGFpbC1tb2RhbC9maWxlLWRldGFpbC1tb2RhbC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0lBQ0ksdUJBQXVCO0lBQ3ZCLHVCQUF1QjtJQUN2QixZQUFZO0lBQ1osV0FBVztJQUNYLFlBQVk7SUFDWixlQUFlO0FBQ25COztBQUVBO0lBQ0kseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0kseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0ksb0JBQW9CO0FBQ3hCOztBQUVBO0lBQ0ksb0JBQW9CO0lBQ3BCLGlCQUFpQjtBQUNyQjs7QUFFQSx1QkFBdUI7O0FBR3ZCLHlDQUF5Qzs7QUFDekM7SUFDSSxhQUFhO0FBQ2pCOztBQUVBOztJQUVJLHlCQUF5QjtBQUM3Qjs7QUFFQTtJQUNJLHlCQUF5QjtBQUM3Qjs7QUFFQTtJQUNJLGlCQUFpQjtBQUNyQjs7QUFFQTtJQUNJLGNBQWM7SUFDZCxpQ0FBaUM7QUFDckM7O0FBRUE7SUFDSSxZQUFZO0lBQ1osNkJBQTZCO0lBQzdCLHNCQUFzQjtJQUN0QixTQUFTO0lBQ1QscUNBQXFDO0FBQ3pDOztBQUVBO0lBQ0ksc0JBQXNCO0lBQ3RCLHFDQUFxQztJQUNyQyxrQkFBa0I7SUFDbEIsK0JBQStCO0lBQy9CLGFBQWE7SUFDYiwwQkFBMEI7SUFDMUIsd0NBQXdDO0lBQ3hDLDJCQUEyQjtJQUMzQixtQkFBbUI7SUFDbkIsdUVBQXVFO0FBQzNFIiwiZmlsZSI6InNyYy9hcHAvY29tcG9uZW50cy9maWxlLWRldGFpbC1tb2RhbC9maWxlLWRldGFpbC1tb2RhbC5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLmJ0bi1jbG9zZSB7XHJcbiAgICBib3JkZXI6IG5vbmUgIWltcG9ydGFudDtcclxuICAgIGJhY2tncm91bmQ6IHRyYW5zcGFyZW50O1xyXG4gICAgcGFkZGluZzogMHB4O1xyXG4gICAgd2lkdGg6IDMwcHg7XHJcbiAgICBoZWlnaHQ6IDMwcHg7XHJcbiAgICBmb250LXNpemU6IDMwcHg7XHJcbn1cclxuXHJcbi5mYSB7XHJcbiAgICBjb2xvcjogI2ZmMzYzNiAhaW1wb3J0YW50O1xyXG59XHJcblxyXG4uaWNvbiB7XHJcbiAgICBjb2xvcjogI2ZmZmZmZiAhaW1wb3J0YW50O1xyXG59XHJcblxyXG4ubW9kYWwtZm9vdGVyIHtcclxuICAgIGp1c3RpZnktY29udGVudDogZW5kO1xyXG59XHJcblxyXG4ubW9kYWwtZm9vdGVyIGJ1dHRvbiB7XHJcbiAgICBqdXN0aWZ5LWNvbnRlbnQ6IGVuZDtcclxuICAgIG1hcmdpbi1sZWZ0OiAxMHB4O1xyXG59XHJcblxyXG4vKiBJbXBvcnRhciBCb290c3RyYXAgKi9cclxuQGltcG9ydCBcIn5ib290c3RyYXAvZGlzdC9jc3MvYm9vdHN0cmFwLm1pbi5jc3NcIjtcclxuXHJcbi8qIEVzdGlsb3MgcGVyc29uYWxpemFkb3MgcGFyYSBlbCBtb2RhbCAqL1xyXG4ubW9kYWwtYm9keSB7XHJcbiAgICBwYWRkaW5nOiAyMHB4O1xyXG59XHJcblxyXG4udGFibGUtYm9yZGVyZWQgdGgsXHJcbi50YWJsZS1ib3JkZXJlZCB0ZCB7XHJcbiAgICBib3JkZXI6IDFweCBzb2xpZCAjZGVlMmU2O1xyXG59XHJcblxyXG4udGFibGUtYm9yZGVyZWQgdGgge1xyXG4gICAgYmFja2dyb3VuZC1jb2xvcjogI2Y4ZjlmYTtcclxufVxyXG5cclxuLmZvcm0tY2hlY2staW5wdXRbdHlwZT1cImNoZWNrYm94XCJdIHtcclxuICAgIG1hcmdpbi1yaWdodDogOHB4O1xyXG59XHJcblxyXG4ubW9kYWwtZGl2aWRlci1jb250YWluZXIge1xyXG4gICAgbWFyZ2luOiAwIDFyZW07XHJcbiAgICAvKiBNw6FyZ2VuZXMgaXpxdWllcmRvIHkgZGVyZWNobyAqL1xyXG59XHJcblxyXG4ubW9kYWwtZGl2aWRlciB7XHJcbiAgICBib3JkZXI6IG5vbmU7XHJcbiAgICBib3JkZXItdG9wOiAxcHggc29saWQgIzAyMjY0OTtcclxuICAgIC8qIENvbG9yIGRlIGxhIGzDrW5lYSAqL1xyXG4gICAgbWFyZ2luOiA1O1xyXG4gICAgLyogUmVzdGFibGVjZXIgbWFyZ2VuIHBhcmEgbGEgbMOtbmVhICovXHJcbn1cclxuXHJcbi5jYXJkLWNvbnRhaW5lciB7XHJcbiAgICBib3JkZXI6IDFweCBzb2xpZCAjZGRkO1xyXG4gICAgLyogQcOxYWRlIHVuIGJvcmRlIGRlIDEgcMOteGVsIHPDs2xpZG8gKi9cclxuICAgIGJvcmRlci1yYWRpdXM6IDhweDtcclxuICAgIC8qIEHDsWFkZSBlc3F1aW5hcyByZWRvbmRlYWRhcyAqL1xyXG4gICAgcGFkZGluZzogMTVweDtcclxuICAgIC8qIEHDsWFkZSByZWxsZW5vIGludGVybm8gKi9cclxuICAgIGJveC1zaGFkb3c6IDAgNHB4IDhweCByZ2JhKDAsIDAsIDAsIDAuMSk7XHJcbiAgICAvKiBBw7FhZGUgdW5hIHN1YXZlIHNvbWJyYSAqL1xyXG4gICAgbWFyZ2luLWJvdHRvbTogMTVweDtcclxuICAgIC8qIEHDsWFkZSBlc3BhY2lvIGVuIGxhIHBhcnRlIGluZmVyaW9yIHBhcmEgc2VwYXJhciBkZSBvdHJvcyBlbGVtZW50b3MgKi9cclxufSJdfQ== */");

/***/ }),

/***/ "./src/app/components/file-detail-modal/file-detail-modal.component.ts":
/*!*****************************************************************************!*\
  !*** ./src/app/components/file-detail-modal/file-detail-modal.component.ts ***!
  \*****************************************************************************/
/*! exports provided: FileDetailModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FileDetailModalComponent", function() { return FileDetailModalComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/services/simcard-control-file.service */ "./src/app/services/simcard-control-file.service.ts");
/* harmony import */ var src_app_services_util_service__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! src/app/services/util.service */ "./src/app/services/util.service.ts");







let FileDetailModalComponent = class FileDetailModalComponent {
    constructor(simcardService, route, activeModal, utilService, fb) {
        this.simcardService = simcardService;
        this.route = route;
        this.activeModal = activeModal;
        this.utilService = utilService;
        this.fb = fb;
        this.messageEvent = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.resultsPerPage = 10;
        this.searchedValue = "";
        this.typeValue = "";
        this.rows = [];
        this.loadingIndicator = true;
    }
    ngOnInit() {
        this.getDataFromFile(this.rowsSimcardControl.id);
        console.log(this.rowsSimcardControl.id);
        this.initForm();
    }
    initForm() {
        this.headerFileForm = this.fb.group({
            fileName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            orderDate: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            customerName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            quantity: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            type: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            artWork: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            profile: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            batch: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            transportKey: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            simReference: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            graphRef: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            imsi: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            serNb: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            suppliersId: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            supplierName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
        });
    }
    //* COMPONENTS
    getDataFromFile(id) {
        this.simcardService.getSimcardControlById(id).subscribe((response) => {
            var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k, _l, _m, _o;
            this.simcardData = this.extractSimcardData(response.body.data);
            this.simcardDetailData = this.mapVarOutData(response.body.data.simcardFile);
            this.headerFileForm.patchValue({
                fileName: (_a = this.simcardData) === null || _a === void 0 ? void 0 : _a.fileName,
                orderDate: (_b = this.simcardData) === null || _b === void 0 ? void 0 : _b.orderDate,
                customerName: (_c = this.simcardData) === null || _c === void 0 ? void 0 : _c.customerName,
                quantity: (_d = this.simcardData) === null || _d === void 0 ? void 0 : _d.quantity,
                type: (_e = this.simcardData) === null || _e === void 0 ? void 0 : _e.type,
                artWork: (_f = this.simcardData) === null || _f === void 0 ? void 0 : _f.artWork,
                profile: (_g = this.simcardData) === null || _g === void 0 ? void 0 : _g.profile,
                batch: (_h = this.simcardData) === null || _h === void 0 ? void 0 : _h.batch,
                transportKey: (_j = this.simcardData) === null || _j === void 0 ? void 0 : _j.transportKey,
                simReference: (_k = this.simcardData) === null || _k === void 0 ? void 0 : _k.simReference,
                graphRef: (_l = this.simcardData) === null || _l === void 0 ? void 0 : _l.graphRef,
                imsi: (_m = this.simcardData) === null || _m === void 0 ? void 0 : _m.imsi,
                serNb: (_o = this.simcardData) === null || _o === void 0 ? void 0 : _o.serNb,
            });
            this.rows = [...this.simcardDetailData];
            console.log(this.simcardData);
            console.log(this.simcardDetailData);
        }, (error) => {
            console.error(error);
        });
    }
    mapVarOutData(simcardFileContent) {
        const varOutDetails = [];
        const varOutIndex = simcardFileContent.indexOf("Var out: ICC/IMSI/IMSIb/KI/PIN1/PUK1/PIN2/PUK2/ADM1/ADM2/ADM3/ACC");
        if (varOutIndex !== -1) {
            const varOutSection = simcardFileContent.substring(varOutIndex);
            const lines = varOutSection.split(/\r?\n/);
            let varOutStarted = false;
            for (const line of lines) {
                if (varOutStarted) {
                    if (line.trim() !== '') {
                        const values = line.trim().split(' ');
                        const simcardDetail = {
                            id: null,
                            idSimcardPadre: null,
                            icc: values[0],
                            imsi: values[1],
                            imsib: values[2],
                            ki: values[3],
                            pin1: values[4],
                            puk1: values[5],
                            pin2: values[6],
                            puk2: values[7],
                            adm1: values[8],
                            adm2: values[9],
                            adm3: values[10],
                            acc: values[11],
                            status: null,
                        };
                        varOutDetails.push(simcardDetail);
                    }
                }
                else if (line.trim() === 'Var out: ICC/IMSI/IMSIb/KI/PIN1/PUK1/PIN2/PUK2/ADM1/ADM2/ADM3/ACC') {
                    varOutStarted = true;
                }
            }
        }
        return varOutDetails;
    }
    //* UTILS
    searchDetail() {
        this.simcardDetailData = this.simcardDetailData.filter((simcardDetailData) => {
            return JSON.stringify(simcardDetailData)
                .toLowerCase()
                .includes(this.searchedValue.toString().toLowerCase());
        });
    }
    getTotalText() {
        return this.rows.length == 1 ? "Registro" : "Registros";
    }
    reloadRows() {
        this.getDataFromFile(this.rowsSimcardControl.id);
    }
    getTitle() {
        return "Ver Detalle:   " + this.rowsSimcardControl.nameFile;
    }
    closeModal() {
        this.activeModal.close();
    }
    extractSimcardData(data) {
        const headerLines = data.simcardFile.split('\r\n');
        const headerData = {
            id: data.id,
            fileName: this.extractHeaderValue(headerLines, 'File Name:'),
            orderDate: this.extractHeaderValue(headerLines, 'Order Date:'),
            customerName: this.extractHeaderValue(headerLines, 'Customer Name:'),
            quantity: this.extractHeaderValue(headerLines, 'Quantity:'),
            type: this.extractHeaderValue(headerLines, 'Type:'),
            artWork: this.extractHeaderValue(headerLines, 'ArtWork:'),
            profile: this.extractHeaderValue(headerLines, 'Profile:'),
            batch: this.extractHeaderValue(headerLines, 'Batch:'),
            transportKey: this.extractHeaderValue(headerLines, 'Transport Key:'),
            simReference: this.extractHeaderValue(headerLines, 'SIM-Reference:'),
            graphRef: this.extractHeaderValue(headerLines, 'Graph ref:'),
            imsi: this.extractVarInValue(headerLines, 'IMSI:'),
            serNb: this.extractVarInValue(headerLines, 'Ser NB:'),
            suppliersId: data.idSimcardPadre,
            supplierName: '',
        };
        return headerData;
    }
    extractHeaderValue(lines, key) {
        const line = lines.find((l) => l.includes(key));
        return line ? line.split(':')[1].trim() : '';
    }
    extractVarInValue(lines, key) {
        const varInLine = lines.find((l) => l.includes(key));
        const startIndex = varInLine ? varInLine.indexOf(key) : -1;
        return startIndex !== -1 ? varInLine.substring(startIndex + key.length).trim() : '';
    }
};
FileDetailModalComponent.ctorParameters = () => [
    { type: src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_5__["SimcardControlFileService"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_3__["ActivatedRoute"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__["NgbActiveModal"] },
    { type: src_app_services_util_service__WEBPACK_IMPORTED_MODULE_6__["UtilService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] }
];
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])()
], FileDetailModalComponent.prototype, "messageEvent", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], FileDetailModalComponent.prototype, "rowsSimcardControl", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], FileDetailModalComponent.prototype, "button", void 0);
FileDetailModalComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-file-detail-modal',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./file-detail-modal.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/file-detail-modal/file-detail-modal.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./file-detail-modal.component.css */ "./src/app/components/file-detail-modal/file-detail-modal.component.css")).default]
    })
], FileDetailModalComponent);



/***/ }),

/***/ "./src/app/components/order-control-modal/order-control-modal.component.css":
/*!**********************************************************************************!*\
  !*** ./src/app/components/order-control-modal/order-control-modal.component.css ***!
  \**********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".btn-close {\r\n    border: none !important;\r\n    background: transparent;\r\n    padding: 0px;\r\n    width: 30px;\r\n    height: 30px;\r\n    font-size: 30px;\r\n}\r\n\r\n.fa {\r\n    color: #ff3636 !important;\r\n}\r\n\r\n.icon {\r\n    color: #ffffff !important;\r\n}\r\n\r\n.modal-footer {\r\n    justify-content: end;\r\n}\r\n\r\n.modal-footer button {\r\n    justify-content: end;\r\n    margin-left: 10px;\r\n}\r\n\r\n/* Importar Bootstrap */\r\n\r\n/* Estilos personalizados para el modal */\r\n\r\n.modal-body {\r\n    padding: 20px;\r\n}\r\n\r\n.table-bordered th,\r\n.table-bordered td {\r\n    border: 1px solid #dee2e6;\r\n}\r\n\r\n.table-bordered th {\r\n    background-color: #f8f9fa;\r\n}\r\n\r\n.form-check-input[type=\"checkbox\"] {\r\n    margin-right: 8px;\r\n}\r\n\r\n.modal-divider-container {\r\n    margin: 0 1rem;\r\n    /* Márgenes izquierdo y derecho */\r\n}\r\n\r\n.modal-divider {\r\n    border: none;\r\n    border-top: 1px solid #022649;\r\n    /* Color de la línea */\r\n    margin: 5;\r\n    /* Restablecer margen para la línea */\r\n}\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9vcmRlci1jb250cm9sLW1vZGFsL29yZGVyLWNvbnRyb2wtbW9kYWwuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLHVCQUF1QjtJQUN2Qix1QkFBdUI7SUFDdkIsWUFBWTtJQUNaLFdBQVc7SUFDWCxZQUFZO0lBQ1osZUFBZTtBQUNuQjs7QUFFQTtJQUNJLHlCQUF5QjtBQUM3Qjs7QUFFQTtJQUNJLHlCQUF5QjtBQUM3Qjs7QUFFQTtJQUNJLG9CQUFvQjtBQUN4Qjs7QUFFQTtJQUNJLG9CQUFvQjtJQUNwQixpQkFBaUI7QUFDckI7O0FBRUEsdUJBQXVCOztBQUd2Qix5Q0FBeUM7O0FBQ3pDO0lBQ0ksYUFBYTtBQUNqQjs7QUFFQTs7SUFFSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSxpQkFBaUI7QUFDckI7O0FBRUE7SUFDSSxjQUFjO0lBQ2QsaUNBQWlDO0FBQ3JDOztBQUVBO0lBQ0ksWUFBWTtJQUNaLDZCQUE2QjtJQUM3QixzQkFBc0I7SUFDdEIsU0FBUztJQUNULHFDQUFxQztBQUN6QyIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvb3JkZXItY29udHJvbC1tb2RhbC9vcmRlci1jb250cm9sLW1vZGFsLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIuYnRuLWNsb3NlIHtcclxuICAgIGJvcmRlcjogbm9uZSAhaW1wb3J0YW50O1xyXG4gICAgYmFja2dyb3VuZDogdHJhbnNwYXJlbnQ7XHJcbiAgICBwYWRkaW5nOiAwcHg7XHJcbiAgICB3aWR0aDogMzBweDtcclxuICAgIGhlaWdodDogMzBweDtcclxuICAgIGZvbnQtc2l6ZTogMzBweDtcclxufVxyXG5cclxuLmZhIHtcclxuICAgIGNvbG9yOiAjZmYzNjM2ICFpbXBvcnRhbnQ7XHJcbn1cclxuXHJcbi5pY29uIHtcclxuICAgIGNvbG9yOiAjZmZmZmZmICFpbXBvcnRhbnQ7XHJcbn1cclxuXHJcbi5tb2RhbC1mb290ZXIge1xyXG4gICAganVzdGlmeS1jb250ZW50OiBlbmQ7XHJcbn1cclxuXHJcbi5tb2RhbC1mb290ZXIgYnV0dG9uIHtcclxuICAgIGp1c3RpZnktY29udGVudDogZW5kO1xyXG4gICAgbWFyZ2luLWxlZnQ6IDEwcHg7XHJcbn1cclxuXHJcbi8qIEltcG9ydGFyIEJvb3RzdHJhcCAqL1xyXG5AaW1wb3J0IFwifmJvb3RzdHJhcC9kaXN0L2Nzcy9ib290c3RyYXAubWluLmNzc1wiO1xyXG5cclxuLyogRXN0aWxvcyBwZXJzb25hbGl6YWRvcyBwYXJhIGVsIG1vZGFsICovXHJcbi5tb2RhbC1ib2R5IHtcclxuICAgIHBhZGRpbmc6IDIwcHg7XHJcbn1cclxuXHJcbi50YWJsZS1ib3JkZXJlZCB0aCxcclxuLnRhYmxlLWJvcmRlcmVkIHRkIHtcclxuICAgIGJvcmRlcjogMXB4IHNvbGlkICNkZWUyZTY7XHJcbn1cclxuXHJcbi50YWJsZS1ib3JkZXJlZCB0aCB7XHJcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjZjhmOWZhO1xyXG59XHJcblxyXG4uZm9ybS1jaGVjay1pbnB1dFt0eXBlPVwiY2hlY2tib3hcIl0ge1xyXG4gICAgbWFyZ2luLXJpZ2h0OiA4cHg7XHJcbn1cclxuXHJcbi5tb2RhbC1kaXZpZGVyLWNvbnRhaW5lciB7XHJcbiAgICBtYXJnaW46IDAgMXJlbTtcclxuICAgIC8qIE3DoXJnZW5lcyBpenF1aWVyZG8geSBkZXJlY2hvICovXHJcbn1cclxuXHJcbi5tb2RhbC1kaXZpZGVyIHtcclxuICAgIGJvcmRlcjogbm9uZTtcclxuICAgIGJvcmRlci10b3A6IDFweCBzb2xpZCAjMDIyNjQ5O1xyXG4gICAgLyogQ29sb3IgZGUgbGEgbMOtbmVhICovXHJcbiAgICBtYXJnaW46IDU7XHJcbiAgICAvKiBSZXN0YWJsZWNlciBtYXJnZW4gcGFyYSBsYSBsw61uZWEgKi9cclxufSJdfQ== */");

/***/ }),

/***/ "./src/app/components/order-control-modal/order-control-modal.component.ts":
/*!*********************************************************************************!*\
  !*** ./src/app/components/order-control-modal/order-control-modal.component.ts ***!
  \*********************************************************************************/
/*! exports provided: OrderControlModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OrderControlModalComponent", function() { return OrderControlModalComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/services/simcard-control-file.service */ "./src/app/services/simcard-control-file.service.ts");
/* harmony import */ var src_app_services_util_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/services/util.service */ "./src/app/services/util.service.ts");
/* harmony import */ var sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! sweetalert2/dist/sweetalert2.js */ "./node_modules/sweetalert2/dist/sweetalert2.js");
/* harmony import */ var sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_5__);






let OrderControlModalComponent = class OrderControlModalComponent {
    constructor(simcardControlFileService, utilService, activeModal, modalService) {
        this.simcardControlFileService = simcardControlFileService;
        this.utilService = utilService;
        this.activeModal = activeModal;
        this.modalService = modalService;
        this.messageEvent = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.orderControl = [];
        this.rows = [];
        this.loadingIndicator = true;
        this.resultsPerPage = 10;
        this.searchedValue = "";
        this.typeValue = "";
        this.mostrarTabla = false;
        this.infoCargado = false;
    }
    ngOnInit() {
        this.getOrderControl();
    }
    getOrderControl() {
        this.simcardControlFileService.getOrderControl().subscribe((res) => {
            if (res.status === 200) {
                let data = res.body.data;
                this.orderControl = data.map((v, k) => {
                    let dto = {};
                    dto.id = v.id;
                    dto.idSupplier = v === null || v === void 0 ? void 0 : v.idSupplier;
                    dto.supplierName = v === null || v === void 0 ? void 0 : v.supplierName;
                    dto.noOrder = v === null || v === void 0 ? void 0 : v.noOrder;
                    dto.userName = v === null || v === void 0 ? void 0 : v.userName;
                    dto.customerName = v === null || v === void 0 ? void 0 : v.customerName;
                    dto.initialImsi = v === null || v === void 0 ? void 0 : v.initialImsi;
                    dto.initialIccd = v === null || v === void 0 ? void 0 : v.initialIccd;
                    dto.orderQuantity = v === null || v === void 0 ? void 0 : v.orderQuantity;
                    dto.fileName = v === null || v === void 0 ? void 0 : v.fileName;
                    dto.created = this.formatDate(v === null || v === void 0 ? void 0 : v.created);
                    dto.customer = v === null || v === void 0 ? void 0 : v.customer;
                    dto.hlr = v === null || v === void 0 ? void 0 : v.hlr;
                    dto.batch = v === null || v === void 0 ? void 0 : v.batch;
                    dto.key = v === null || v === void 0 ? void 0 : v.key;
                    dto.status = v === null || v === void 0 ? void 0 : v.status;
                    return dto;
                });
                this.rows = [...this.orderControl];
            }
            else {
                this.utilService.showNotificationError(res.status);
            }
        });
    }
    updateStatusOrder(id) {
        sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_5___default.a.fire({
            html: `<h5>¿Está seguro de procesar esta orden?</h5>`,
            showCancelButton: true,
            customClass: {
                confirmButton: "btn btn-round bg-lemon",
                cancelButton: "btn btn-round btn-danger",
            },
            reverseButtons: true,
            confirmButtonText: "Sí, procesar",
            buttonsStyling: false,
        }).then((result) => {
            if (result.value) {
                this.simcardControlFileService.updateStatusOrder(id).subscribe(() => {
                    this.utilService.showNotification(0, "Orden procesada con éxito");
                    this.closeModal();
                }, (error) => {
                    console.error(error);
                });
            }
        });
    }
    //* UTILS
    searchOrderControl() {
        this.rows = this.orderControl.filter((orderControl) => {
            return JSON.stringify(orderControl)
                .toLowerCase()
                .includes(this.searchedValue.toString().toLowerCase());
        });
    }
    getTotalText() {
        return this.rows.length == 1 ? "Registro" : "Registros";
    }
    reloadRows() {
        this.getOrderControl();
    }
    getTitle() {
        return "Control de Pedidos";
    }
    closeModal() {
        this.activeModal.close();
    }
    formatDate(fecha) {
        if (!fecha) {
            return '';
        }
        const fechaFormateada = new Date(fecha);
        const dia = fechaFormateada.getDate().toString().padStart(2, '0');
        const mes = (fechaFormateada.getMonth() + 1).toString().padStart(2, '0');
        const año = fechaFormateada.getFullYear();
        const horas = fechaFormateada.getHours().toString().padStart(2, '0');
        const minutos = fechaFormateada.getMinutes().toString().padStart(2, '0');
        const segundos = fechaFormateada.getSeconds().toString().padStart(2, '0');
        return `${dia}/${mes}/${año}`;
    }
};
OrderControlModalComponent.ctorParameters = () => [
    { type: src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_3__["SimcardControlFileService"] },
    { type: src_app_services_util_service__WEBPACK_IMPORTED_MODULE_4__["UtilService"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__["NgbActiveModal"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__["NgbModal"] }
];
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])()
], OrderControlModalComponent.prototype, "messageEvent", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], OrderControlModalComponent.prototype, "button", void 0);
OrderControlModalComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-order-control-modal',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./order-control-modal.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/order-control-modal/order-control-modal.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./order-control-modal.component.css */ "./src/app/components/order-control-modal/order-control-modal.component.css")).default]
    })
], OrderControlModalComponent);



/***/ }),

/***/ "./src/app/components/see-order-modal/see-order-modal.component.css":
/*!**************************************************************************!*\
  !*** ./src/app/components/see-order-modal/see-order-modal.component.css ***!
  \**************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".btn-close {\r\n    border: none !important;\r\n    background: transparent;\r\n    padding: 0px;\r\n    width: 30px;\r\n    height: 30px;\r\n    font-size: 30px;\r\n}\r\n\r\n.fa {\r\n    color: #ff3636 !important;\r\n}\r\n\r\n.icon {\r\n    color: #ffffff !important;\r\n}\r\n\r\n.modal-footer {\r\n    justify-content: end;\r\n}\r\n\r\n.modal-footer button {\r\n    justify-content: end;\r\n    margin-left: 10px;\r\n}\r\n\r\n/* Importar Bootstrap */\r\n\r\n/* Estilos personalizados para el modal */\r\n\r\n.modal-body {\r\n    padding: 20px;\r\n}\r\n\r\n.table-bordered th,\r\n.table-bordered td {\r\n    border: 1px solid #dee2e6;\r\n}\r\n\r\n.table-bordered th {\r\n    background-color: #f8f9fa;\r\n}\r\n\r\n.form-check-input[type=\"checkbox\"] {\r\n    margin-right: 8px;\r\n}\r\n\r\n.modal-divider-container {\r\n    margin: 0 1rem;\r\n    /* Márgenes izquierdo y derecho */\r\n}\r\n\r\n.modal-divider {\r\n    border: none;\r\n    border-top: 1px solid #022649;\r\n    /* Color de la línea */\r\n    margin: 5;\r\n    /* Restablecer margen para la línea */\r\n}\r\n\r\n.card-container {\r\n    border: 1px solid #ddd;\r\n    /* Añade un borde de 1 píxel sólido */\r\n    border-radius: 8px;\r\n    /* Añade esquinas redondeadas */\r\n    padding: 15px;\r\n    /* Añade relleno interno */\r\n    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\r\n    /* Añade una suave sombra */\r\n    margin-bottom: 15px;\r\n    /* Añade espacio en la parte inferior para separar de otros elementos */\r\n}\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zZWUtb3JkZXItbW9kYWwvc2VlLW9yZGVyLW1vZGFsLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSx1QkFBdUI7SUFDdkIsdUJBQXVCO0lBQ3ZCLFlBQVk7SUFDWixXQUFXO0lBQ1gsWUFBWTtJQUNaLGVBQWU7QUFDbkI7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSxvQkFBb0I7QUFDeEI7O0FBRUE7SUFDSSxvQkFBb0I7SUFDcEIsaUJBQWlCO0FBQ3JCOztBQUVBLHVCQUF1Qjs7QUFHdkIseUNBQXlDOztBQUN6QztJQUNJLGFBQWE7QUFDakI7O0FBRUE7O0lBRUkseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0kseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0ksaUJBQWlCO0FBQ3JCOztBQUVBO0lBQ0ksY0FBYztJQUNkLGlDQUFpQztBQUNyQzs7QUFFQTtJQUNJLFlBQVk7SUFDWiw2QkFBNkI7SUFDN0Isc0JBQXNCO0lBQ3RCLFNBQVM7SUFDVCxxQ0FBcUM7QUFDekM7O0FBRUE7SUFDSSxzQkFBc0I7SUFDdEIscUNBQXFDO0lBQ3JDLGtCQUFrQjtJQUNsQiwrQkFBK0I7SUFDL0IsYUFBYTtJQUNiLDBCQUEwQjtJQUMxQix3Q0FBd0M7SUFDeEMsMkJBQTJCO0lBQzNCLG1CQUFtQjtJQUNuQix1RUFBdUU7QUFDM0UiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3NlZS1vcmRlci1tb2RhbC9zZWUtb3JkZXItbW9kYWwuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5idG4tY2xvc2Uge1xyXG4gICAgYm9yZGVyOiBub25lICFpbXBvcnRhbnQ7XHJcbiAgICBiYWNrZ3JvdW5kOiB0cmFuc3BhcmVudDtcclxuICAgIHBhZGRpbmc6IDBweDtcclxuICAgIHdpZHRoOiAzMHB4O1xyXG4gICAgaGVpZ2h0OiAzMHB4O1xyXG4gICAgZm9udC1zaXplOiAzMHB4O1xyXG59XHJcblxyXG4uZmEge1xyXG4gICAgY29sb3I6ICNmZjM2MzYgIWltcG9ydGFudDtcclxufVxyXG5cclxuLmljb24ge1xyXG4gICAgY29sb3I6ICNmZmZmZmYgIWltcG9ydGFudDtcclxufVxyXG5cclxuLm1vZGFsLWZvb3RlciB7XHJcbiAgICBqdXN0aWZ5LWNvbnRlbnQ6IGVuZDtcclxufVxyXG5cclxuLm1vZGFsLWZvb3RlciBidXR0b24ge1xyXG4gICAganVzdGlmeS1jb250ZW50OiBlbmQ7XHJcbiAgICBtYXJnaW4tbGVmdDogMTBweDtcclxufVxyXG5cclxuLyogSW1wb3J0YXIgQm9vdHN0cmFwICovXHJcbkBpbXBvcnQgXCJ+Ym9vdHN0cmFwL2Rpc3QvY3NzL2Jvb3RzdHJhcC5taW4uY3NzXCI7XHJcblxyXG4vKiBFc3RpbG9zIHBlcnNvbmFsaXphZG9zIHBhcmEgZWwgbW9kYWwgKi9cclxuLm1vZGFsLWJvZHkge1xyXG4gICAgcGFkZGluZzogMjBweDtcclxufVxyXG5cclxuLnRhYmxlLWJvcmRlcmVkIHRoLFxyXG4udGFibGUtYm9yZGVyZWQgdGQge1xyXG4gICAgYm9yZGVyOiAxcHggc29saWQgI2RlZTJlNjtcclxufVxyXG5cclxuLnRhYmxlLWJvcmRlcmVkIHRoIHtcclxuICAgIGJhY2tncm91bmQtY29sb3I6ICNmOGY5ZmE7XHJcbn1cclxuXHJcbi5mb3JtLWNoZWNrLWlucHV0W3R5cGU9XCJjaGVja2JveFwiXSB7XHJcbiAgICBtYXJnaW4tcmlnaHQ6IDhweDtcclxufVxyXG5cclxuLm1vZGFsLWRpdmlkZXItY29udGFpbmVyIHtcclxuICAgIG1hcmdpbjogMCAxcmVtO1xyXG4gICAgLyogTcOhcmdlbmVzIGl6cXVpZXJkbyB5IGRlcmVjaG8gKi9cclxufVxyXG5cclxuLm1vZGFsLWRpdmlkZXIge1xyXG4gICAgYm9yZGVyOiBub25lO1xyXG4gICAgYm9yZGVyLXRvcDogMXB4IHNvbGlkICMwMjI2NDk7XHJcbiAgICAvKiBDb2xvciBkZSBsYSBsw61uZWEgKi9cclxuICAgIG1hcmdpbjogNTtcclxuICAgIC8qIFJlc3RhYmxlY2VyIG1hcmdlbiBwYXJhIGxhIGzDrW5lYSAqL1xyXG59XHJcblxyXG4uY2FyZC1jb250YWluZXIge1xyXG4gICAgYm9yZGVyOiAxcHggc29saWQgI2RkZDtcclxuICAgIC8qIEHDsWFkZSB1biBib3JkZSBkZSAxIHDDrXhlbCBzw7NsaWRvICovXHJcbiAgICBib3JkZXItcmFkaXVzOiA4cHg7XHJcbiAgICAvKiBBw7FhZGUgZXNxdWluYXMgcmVkb25kZWFkYXMgKi9cclxuICAgIHBhZGRpbmc6IDE1cHg7XHJcbiAgICAvKiBBw7FhZGUgcmVsbGVubyBpbnRlcm5vICovXHJcbiAgICBib3gtc2hhZG93OiAwIDRweCA4cHggcmdiYSgwLCAwLCAwLCAwLjEpO1xyXG4gICAgLyogQcOxYWRlIHVuYSBzdWF2ZSBzb21icmEgKi9cclxuICAgIG1hcmdpbi1ib3R0b206IDE1cHg7XHJcbiAgICAvKiBBw7FhZGUgZXNwYWNpbyBlbiBsYSBwYXJ0ZSBpbmZlcmlvciBwYXJhIHNlcGFyYXIgZGUgb3Ryb3MgZWxlbWVudG9zICovXHJcbn0iXX0= */");

/***/ }),

/***/ "./src/app/components/see-order-modal/see-order-modal.component.ts":
/*!*************************************************************************!*\
  !*** ./src/app/components/see-order-modal/see-order-modal.component.ts ***!
  \*************************************************************************/
/*! exports provided: SeeOrderModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SeeOrderModalComponent", function() { return SeeOrderModalComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/services/simcard-control-file.service */ "./src/app/services/simcard-control-file.service.ts");
/* harmony import */ var src_app_services_util_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/services/util.service */ "./src/app/services/util.service.ts");
/* harmony import */ var sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! sweetalert2/dist/sweetalert2.js */ "./node_modules/sweetalert2/dist/sweetalert2.js");
/* harmony import */ var sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6__);







let SeeOrderModalComponent = class SeeOrderModalComponent {
    constructor(simcardControlFileService, activeModal, utilService, fb) {
        this.simcardControlFileService = simcardControlFileService;
        this.activeModal = activeModal;
        this.utilService = utilService;
        this.fb = fb;
        this.idSimcardPadre = null;
        this.loadingIndicator = true;
    }
    ngOnInit() {
        this.getOrderByIdPadre();
        this.initForm();
    }
    initForm() {
        this.seeOrderForm = this.fb.group({
            suppliersId: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            supplierName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            noOrder: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            userName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            customerName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            initialImsi: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            initialIccd: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            orderQuantity: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            fileName: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            created: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            customer: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            hlr: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            batch: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            key: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            type: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            typeInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            art: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            artInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            graphic: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            graphicInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            model: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            modelInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSize: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSizeInput1: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSizeInput2: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            versionSizeInput3: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
        });
    }
    //* COMPONENTS
    getOrderByIdPadre() {
        const idSimcardPadre = this.idSimcardPadre;
        this.simcardControlFileService.getOrderByIdPadre(idSimcardPadre).subscribe((res) => {
            var _a, _b, _c, _d, _e;
            if (res.status === 200) {
                let response = res.body.data;
                if (response) {
                    this.orderId = response.id;
                    this.statusOrder = response.status;
                    this.seeOrderForm.reset();
                    this.seeOrderForm.patchValue({
                        suppliersId: response === null || response === void 0 ? void 0 : response.idSupplier,
                        supplierName: response === null || response === void 0 ? void 0 : response.supplierName,
                        noOrder: response === null || response === void 0 ? void 0 : response.noOrder,
                        userName: response === null || response === void 0 ? void 0 : response.userName,
                        customerName: response === null || response === void 0 ? void 0 : response.customerName,
                        initialImsi: response === null || response === void 0 ? void 0 : response.initialImsi,
                        initialIccd: response === null || response === void 0 ? void 0 : response.initialIccd,
                        orderQuantity: response === null || response === void 0 ? void 0 : response.orderQuantity,
                        fileName: response === null || response === void 0 ? void 0 : response.fileName,
                        created: response === null || response === void 0 ? void 0 : response.created,
                        customer: response === null || response === void 0 ? void 0 : response.customerName,
                        hlr: response === null || response === void 0 ? void 0 : response.hlr,
                        batch: response === null || response === void 0 ? void 0 : response.batch,
                        key: response === null || response === void 0 ? void 0 : response.key,
                        type: response === null || response === void 0 ? void 0 : response.type,
                        art: response === null || response === void 0 ? void 0 : response.art,
                        graphic: response === null || response === void 0 ? void 0 : response.graphic,
                        model: response === null || response === void 0 ? void 0 : response.model,
                        versionSize: response === null || response === void 0 ? void 0 : response.versionSize,
                        status: response === null || response === void 0 ? void 0 : response.status,
                    });
                    // Desglose de campos
                    const typeArray = (_a = response === null || response === void 0 ? void 0 : response.type) === null || _a === void 0 ? void 0 : _a.split(" ");
                    if (typeArray && typeArray.length >= 1) {
                        this.seeOrderForm.patchValue({
                            type: typeArray[0],
                            typeInput1: typeArray[1]
                        });
                    }
                    const artArray = (_b = response === null || response === void 0 ? void 0 : response.art) === null || _b === void 0 ? void 0 : _b.split(" ");
                    if (artArray && artArray.length >= 1) {
                        this.seeOrderForm.patchValue({
                            art: artArray[0],
                            artInput1: artArray[1]
                        });
                    }
                    const graphicArray = (_c = response === null || response === void 0 ? void 0 : response.graphic) === null || _c === void 0 ? void 0 : _c.split(" ");
                    if (graphicArray && graphicArray.length >= 1) {
                        this.seeOrderForm.patchValue({
                            graphic: graphicArray[0],
                            graphicInput1: graphicArray[1]
                        });
                    }
                    const modelArray = (_d = response === null || response === void 0 ? void 0 : response.model) === null || _d === void 0 ? void 0 : _d.split(" ");
                    if (modelArray && modelArray.length >= 1) {
                        this.seeOrderForm.patchValue({
                            model: modelArray[0],
                            modelInput1: modelArray[1]
                        });
                    }
                    const versionSizeArray = (_e = response === null || response === void 0 ? void 0 : response.versionSize) === null || _e === void 0 ? void 0 : _e.split(" ");
                    if (versionSizeArray && versionSizeArray.length >= 3) {
                        this.seeOrderForm.patchValue({
                            versionSize: versionSizeArray[0],
                            versionSizeInput1: versionSizeArray[1],
                            versionSizeInput2: versionSizeArray[2],
                            versionSizeInput3: versionSizeArray[3],
                        });
                    }
                    this.loadingIndicator = false;
                }
                else {
                    console.error("La respuesta del servidor no contiene datos.");
                }
            }
            else {
                this.utilService.showNotificationError(res.status);
            }
        });
    }
    updateStatusDetail() {
        sweetalert2_dist_sweetalert2_js__WEBPACK_IMPORTED_MODULE_6___default.a.fire({
            html: `<h5>¿Está seguro de procesar esta orden?</h5>`,
            showCancelButton: true,
            customClass: {
                confirmButton: "btn btn-round bg-lemon",
                cancelButton: "btn btn-round btn-danger",
            },
            reverseButtons: true,
            confirmButtonText: "Sí, procesar",
            buttonsStyling: false,
        }).then((result) => {
            if (result.value) {
                this.simcardControlFileService.updateStatusDetail(this.idSimcardPadre).subscribe(() => {
                    this.utilService.showNotification(0, "Orden procesada con éxito");
                    this.closeModal();
                }, (error) => {
                    console.error(error);
                });
            }
        });
    }
    updateStatusOrder() {
        this.simcardControlFileService.updateStatusOrder(this.orderId).subscribe(() => {
            //this.closeModal();
        }, (error) => {
            console.error(error);
        });
    }
    //* UTILS
    getTitle() {
        return "Detalle del Pedido:";
    }
    closeModal() {
        this.activeModal.close();
    }
};
SeeOrderModalComponent.ctorParameters = () => [
    { type: src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_4__["SimcardControlFileService"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__["NgbActiveModal"] },
    { type: src_app_services_util_service__WEBPACK_IMPORTED_MODULE_5__["UtilService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] }
];
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], SeeOrderModalComponent.prototype, "idSimcardPadre", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], SeeOrderModalComponent.prototype, "formData", void 0);
SeeOrderModalComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-see-order-modal',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./see-order-modal.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/see-order-modal/see-order-modal.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./see-order-modal.component.css */ "./src/app/components/see-order-modal/see-order-modal.component.css")).default]
    })
], SeeOrderModalComponent);



/***/ }),

/***/ "./src/app/components/suppliers-modal/suppliers-modal.component.css":
/*!**************************************************************************!*\
  !*** ./src/app/components/suppliers-modal/suppliers-modal.component.css ***!
  \**************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".btn-close {\r\n    border: none !important;\r\n    background: transparent;\r\n    padding: 0px;\r\n    width: 30px;\r\n    height: 30px;\r\n    font-size: 30px;\r\n}\r\n\r\n.fa {\r\n    color: #ff3636 !important;\r\n}\r\n\r\n.icon {\r\n    color: #ffffff !important;\r\n}\r\n\r\n.modal-footer {\r\n    justify-content: end;\r\n}\r\n\r\n.modal-footer button {\r\n    justify-content: end;\r\n    margin-left: 10px;\r\n}\r\n\r\n/* Importar Bootstrap */\r\n\r\n/* Estilos personalizados para el modal */\r\n\r\n.modal-body {\r\n    padding: 20px;\r\n}\r\n\r\n.table-bordered th,\r\n.table-bordered td {\r\n    border: 1px solid #dee2e6;\r\n}\r\n\r\n.table-bordered th {\r\n    background-color: #f8f9fa;\r\n}\r\n\r\n.form-check-input[type=\"checkbox\"] {\r\n    margin-right: 8px;\r\n}\r\n\r\n.modal-divider-container {\r\n    margin: 0 1rem;\r\n    /* Márgenes izquierdo y derecho */\r\n}\r\n\r\n.modal-divider {\r\n    border: none;\r\n    border-top: 1px solid #022649;\r\n    /* Color de la línea */\r\n    margin: 5;\r\n    /* Restablecer margen para la línea */\r\n}\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdXBwbGllcnMtbW9kYWwvc3VwcGxpZXJzLW1vZGFsLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSx1QkFBdUI7SUFDdkIsdUJBQXVCO0lBQ3ZCLFlBQVk7SUFDWixXQUFXO0lBQ1gsWUFBWTtJQUNaLGVBQWU7QUFDbkI7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSx5QkFBeUI7QUFDN0I7O0FBRUE7SUFDSSxvQkFBb0I7QUFDeEI7O0FBRUE7SUFDSSxvQkFBb0I7SUFDcEIsaUJBQWlCO0FBQ3JCOztBQUVBLHVCQUF1Qjs7QUFHdkIseUNBQXlDOztBQUN6QztJQUNJLGFBQWE7QUFDakI7O0FBRUE7O0lBRUkseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0kseUJBQXlCO0FBQzdCOztBQUVBO0lBQ0ksaUJBQWlCO0FBQ3JCOztBQUVBO0lBQ0ksY0FBYztJQUNkLGlDQUFpQztBQUNyQzs7QUFFQTtJQUNJLFlBQVk7SUFDWiw2QkFBNkI7SUFDN0Isc0JBQXNCO0lBQ3RCLFNBQVM7SUFDVCxxQ0FBcUM7QUFDekMiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3N1cHBsaWVycy1tb2RhbC9zdXBwbGllcnMtbW9kYWwuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5idG4tY2xvc2Uge1xyXG4gICAgYm9yZGVyOiBub25lICFpbXBvcnRhbnQ7XHJcbiAgICBiYWNrZ3JvdW5kOiB0cmFuc3BhcmVudDtcclxuICAgIHBhZGRpbmc6IDBweDtcclxuICAgIHdpZHRoOiAzMHB4O1xyXG4gICAgaGVpZ2h0OiAzMHB4O1xyXG4gICAgZm9udC1zaXplOiAzMHB4O1xyXG59XHJcblxyXG4uZmEge1xyXG4gICAgY29sb3I6ICNmZjM2MzYgIWltcG9ydGFudDtcclxufVxyXG5cclxuLmljb24ge1xyXG4gICAgY29sb3I6ICNmZmZmZmYgIWltcG9ydGFudDtcclxufVxyXG5cclxuLm1vZGFsLWZvb3RlciB7XHJcbiAgICBqdXN0aWZ5LWNvbnRlbnQ6IGVuZDtcclxufVxyXG5cclxuLm1vZGFsLWZvb3RlciBidXR0b24ge1xyXG4gICAganVzdGlmeS1jb250ZW50OiBlbmQ7XHJcbiAgICBtYXJnaW4tbGVmdDogMTBweDtcclxufVxyXG5cclxuLyogSW1wb3J0YXIgQm9vdHN0cmFwICovXHJcbkBpbXBvcnQgXCJ+Ym9vdHN0cmFwL2Rpc3QvY3NzL2Jvb3RzdHJhcC5taW4uY3NzXCI7XHJcblxyXG4vKiBFc3RpbG9zIHBlcnNvbmFsaXphZG9zIHBhcmEgZWwgbW9kYWwgKi9cclxuLm1vZGFsLWJvZHkge1xyXG4gICAgcGFkZGluZzogMjBweDtcclxufVxyXG5cclxuLnRhYmxlLWJvcmRlcmVkIHRoLFxyXG4udGFibGUtYm9yZGVyZWQgdGQge1xyXG4gICAgYm9yZGVyOiAxcHggc29saWQgI2RlZTJlNjtcclxufVxyXG5cclxuLnRhYmxlLWJvcmRlcmVkIHRoIHtcclxuICAgIGJhY2tncm91bmQtY29sb3I6ICNmOGY5ZmE7XHJcbn1cclxuXHJcbi5mb3JtLWNoZWNrLWlucHV0W3R5cGU9XCJjaGVja2JveFwiXSB7XHJcbiAgICBtYXJnaW4tcmlnaHQ6IDhweDtcclxufVxyXG5cclxuLm1vZGFsLWRpdmlkZXItY29udGFpbmVyIHtcclxuICAgIG1hcmdpbjogMCAxcmVtO1xyXG4gICAgLyogTcOhcmdlbmVzIGl6cXVpZXJkbyB5IGRlcmVjaG8gKi9cclxufVxyXG5cclxuLm1vZGFsLWRpdmlkZXIge1xyXG4gICAgYm9yZGVyOiBub25lO1xyXG4gICAgYm9yZGVyLXRvcDogMXB4IHNvbGlkICMwMjI2NDk7XHJcbiAgICAvKiBDb2xvciBkZSBsYSBsw61uZWEgKi9cclxuICAgIG1hcmdpbjogNTtcclxuICAgIC8qIFJlc3RhYmxlY2VyIG1hcmdlbiBwYXJhIGxhIGzDrW5lYSAqL1xyXG59Il19 */");

/***/ }),

/***/ "./src/app/components/suppliers-modal/suppliers-modal.component.ts":
/*!*************************************************************************!*\
  !*** ./src/app/components/suppliers-modal/suppliers-modal.component.ts ***!
  \*************************************************************************/
/*! exports provided: SuppliersModalComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SuppliersModalComponent", function() { return SuppliersModalComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/services/simcard-control-file.service */ "./src/app/services/simcard-control-file.service.ts");
/* harmony import */ var src_app_services_util_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/services/util.service */ "./src/app/services/util.service.ts");






let SuppliersModalComponent = class SuppliersModalComponent {
    constructor(simcardControlFileService, modalService, utilService, activeModal, activatedRoute) {
        this.simcardControlFileService = simcardControlFileService;
        this.modalService = modalService;
        this.utilService = utilService;
        this.activeModal = activeModal;
        this.activatedRoute = activatedRoute;
        this.messageEvent = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.idSimcardPadre = null;
        this.idSimcardControl = null;
        this.suppliers = [];
        this.rowsSuppliers = [];
        this.loadingIndicator = true;
        this.resultsPerPage = 10;
        this.searchedValue = "";
        this.typeValue = "";
        this.mostrarTabla = false;
        this.infoCargado = false;
    }
    ngOnInit() {
        this.getSuppliers();
    }
    getSuppliers() {
        this.simcardControlFileService.getSuppliers().subscribe((res) => {
            if (res.status === 200) {
                this.rowsSuppliers = [];
                this.suppliers = [];
                let response = res.body.data;
                response.map((v, k) => {
                    let dto = {};
                    dto.id = v.id;
                    dto.supplierName = v === null || v === void 0 ? void 0 : v.supplierName;
                    dto.phone = v === null || v === void 0 ? void 0 : v.phone;
                    dto.email = v === null || v === void 0 ? void 0 : v.email;
                    dto.status = v === null || v === void 0 ? void 0 : v.status;
                    dto.created = this.formatDate(v === null || v === void 0 ? void 0 : v.created);
                    this.rowsSuppliers.push(dto);
                });
                this.loadingIndicator = false;
                this.rowsSuppliers = [...this.rowsSuppliers];
                this.suppliers = [...this.suppliers];
            }
            else {
                this.utilService.showNotificationError(res.status);
            }
        });
    }
    updateSuppliersSimcardPadre(id) {
        const idSimcardPadre = this.idSimcardPadre;
        this.simcardControlFileService.updateSuppliersSimcardPadre(idSimcardPadre, id).subscribe(() => {
            this.utilService.showNotification(0, "Proveedor actualizado con éxito");
            this.activeModal.close();
        }, error => {
            console.error(error);
        });
    }
    updateSuppliersSimcardControl(id) {
        this.simcardControlFileService.updateSupplierSimcardControl(this.idSimcardControl, id).subscribe(() => {
            this.activeModal.close();
        }, error => {
            console.error(error);
        });
    }
    //* UTILS
    searchSuppliers() {
        this.rowsSuppliers = this.rowsSuppliers.filter((rowsSuppliers) => {
            return JSON.stringify(rowsSuppliers)
                .toLowerCase()
                .includes(this.searchedValue.toString().toLowerCase());
        });
    }
    getTotalText() {
        return this.rowsSuppliers.length == 1 ? "Registro" : "Registros";
    }
    reloadRows() {
        this.getSuppliers();
    }
    getTitle() {
        return "Proveedores";
    }
    closeModal() {
        this.activeModal.close();
    }
    formatDate(fecha) {
        if (!fecha) {
            return '';
        }
        const fechaFormateada = new Date(fecha);
        const dia = fechaFormateada.getDate().toString().padStart(2, '0');
        const mes = (fechaFormateada.getMonth() + 1).toString().padStart(2, '0');
        const año = fechaFormateada.getFullYear();
        const horas = fechaFormateada.getHours().toString().padStart(2, '0');
        const minutos = fechaFormateada.getMinutes().toString().padStart(2, '0');
        const segundos = fechaFormateada.getSeconds().toString().padStart(2, '0');
        return `${dia}/${mes}/${año} ${horas}:${minutos}:${segundos}`;
    }
};
SuppliersModalComponent.ctorParameters = () => [
    { type: src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_4__["SimcardControlFileService"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__["NgbModal"] },
    { type: src_app_services_util_service__WEBPACK_IMPORTED_MODULE_5__["UtilService"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__["NgbActiveModal"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_2__["ActivatedRoute"] }
];
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])()
], SuppliersModalComponent.prototype, "messageEvent", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], SuppliersModalComponent.prototype, "simcardControl", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], SuppliersModalComponent.prototype, "button", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], SuppliersModalComponent.prototype, "idSimcardPadre", void 0);
Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], SuppliersModalComponent.prototype, "idSimcardControl", void 0);
SuppliersModalComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-suppliers-modal',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./suppliers-modal.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/components/suppliers-modal/suppliers-modal.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./suppliers-modal.component.css */ "./src/app/components/suppliers-modal/suppliers-modal.component.css")).default]
    })
], SuppliersModalComponent);



/***/ }),

/***/ "./src/app/services/simcard-control-file.service.ts":
/*!**********************************************************!*\
  !*** ./src/app/services/simcard-control-file.service.ts ***!
  \**********************************************************/
/*! exports provided: SimcardControlFileService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SimcardControlFileService", function() { return SimcardControlFileService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/__ivy_ngcc__/fesm2015/http.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm2015/index.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm2015/operators/index.js");
/* harmony import */ var _util_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./util.service */ "./src/app/services/util.service.ts");






let SimcardControlFileService = class SimcardControlFileService {
    constructor(http, utilService) {
        this.http = http;
        this.utilService = utilService;
        this.localUrl = "/api/";
        this.headers = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
            "Content-Type": "application/json",
            "Authorization": this.utilService.getSystemUser(),
        });
    }
    handleError(error, message = "") {
        this.utilService.showNotification(2, `${message}. Contacte al administrador del sistema.`);
        return Object(rxjs__WEBPACK_IMPORTED_MODULE_3__["throwError"])(error);
    }
    getSimcardControl() {
        return this.http
            .get(`${this.localUrl}simcardcontrol`, {
            observe: "response",
            headers: this.headers,
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido obtener los archivos ")));
    }
    processSimcardFile(file) {
        const headers = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]();
        return this.http.post(`${this.localUrl}simcardcontrol/processSimcardFile`, file, { headers });
    }
    getSimcardControlById(id) {
        return this.http
            .get(`${this.localUrl}simcardcontrol/${id}`, {
            observe: 'response',
            headers: this.headers,
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, 'No se ha podido obtener los detalles de la SIMCARD_CONTROL')));
    }
    updateSupplierSimcardControl(id, suppliersId) {
        const queryParams = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]().set('suppliersId', suppliersId.toString());
        return this.http.patch(`${this.localUrl}simcardcontrol/updateSupplier/${id}`, null, {
            headers: this.headers,
            params: queryParams
        }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido actualizar el proveedor")));
    }
    getSuppliers() {
        return this.http
            .get(`${this.localUrl}simcardsuppliers`, {
            observe: "response",
            headers: this.headers,
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido obtener los registros de Proveedores")));
    }
    getSuppliersById(id) {
        return this.http
            .get(`${this.localUrl}simcardsuppliers/${id}`, {
            observe: "response",
            headers: this.headers
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido obtener el proveedor")));
    }
    updateSuppliersSimcardPadre(id, suppliersId) {
        const queryParams = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]().set('suppliersId', suppliersId.toString());
        return this.http.patch(`${this.localUrl}simcardpadre/updateSuppliers/${id}`, null, {
            headers: this.headers,
            params: queryParams
        }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido actualizar el proveedor")));
    }
    getOrderControl() {
        return this.http
            .get(`${this.localUrl}simcardordercontrol`, {
            observe: "response",
            headers: this.headers,
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido obtener las ordenes")));
    }
    getSimcardPadreById(id) {
        return this.http
            .get(`${this.localUrl}simcardpadre/${id}`, {
            observe: "response",
            headers: this.headers
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido obtener los registros")));
    }
    saveOrderControl(orderControl) {
        return this.http
            .post(`${this.localUrl}simcardordercontrol/add`, orderControl, {
            observe: "response",
            headers: this.headers,
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido agregar la orden")));
    }
    updateStatusOrder(id) {
        return this.http.patch(`${this.localUrl}simcardordercontrol/updateStatus/${id}`, {
            observe: "response",
            headers: this.headers
        }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido procesar la orden")));
    }
    getNextOrderId() {
        return this.http
            .get(`${this.localUrl}simcardordercontrol/getNextOrderId`, {
            observe: 'response',
            headers: this.headers,
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, 'No se ha podido obtener el próximo ID de la orden')));
    }
    getOrderByIdPadre(idSimcardPadre) {
        return this.http
            .get(`${this.localUrl}simcardordercontrol/${idSimcardPadre}`, {
            observe: "response",
            headers: this.headers
        })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido obtener los registros")));
    }
    updateStatusDetail(id) {
        return this.http.patch(`${this.localUrl}simcarddetail/updateStatus/${id}`, {
            observe: "response",
            headers: this.headers
        }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido procesar la orden")));
    }
    updateOrderSimcardControl(id, idSimcardOrder) {
        const queryParams = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]().set('idSimcardOrder', idSimcardOrder.toString());
        return this.http.patch(`${this.localUrl}simcardcontrol/updateOrder/${id}`, null, {
            headers: this.headers,
            params: queryParams
        }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_4__["catchError"])((err) => this.handleError(err, "No se ha podido actualizar la orden")));
    }
};
SimcardControlFileService.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"] },
    { type: _util_service__WEBPACK_IMPORTED_MODULE_5__["UtilService"] }
];
SimcardControlFileService = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["Injectable"])({
        providedIn: 'root'
    })
], SimcardControlFileService);



/***/ }),

/***/ "./src/app/services/util.service.ts":
/*!******************************************!*\
  !*** ./src/app/services/util.service.ts ***!
  \******************************************/
/*! exports provided: UtilService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UtilService", function() { return UtilService; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js");
/* harmony import */ var ngx_toastr__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ngx-toastr */ "./node_modules/ngx-toastr/__ivy_ngcc__/fesm2015/ngx-toastr.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");





let UtilService = class UtilService {
    constructor(toastr, activatedRoute, router) {
        this.toastr = toastr;
        this.activatedRoute = activatedRoute;
        this.router = router;
        this.errorClass = 'alert alert-danger alert-with-icon';
        this.successClass = 'alert alert-success alert-with-icon';
        this.infoClass = 'alert alert-info alert-with-icon';
        this.auth = false;
        //  if (window.self !== window.top) {
        this.auth = true;
        this.activatedRoute.queryParams.subscribe(params => {
            const sub = JSON.stringify(params.sub);
            if (sub) {
                sessionStorage.setItem('sub', sub);
            }
        });
        this.subs = sessionStorage.getItem('sub').split(',');
        //  }
    }
    handleApiResponse(response) {
        if (response.code === 200) {
            this.showNotificationSuccess(response.description);
        }
        else {
            if (response.errors && response.errors.length > 0) {
                const errorMessage = response.errors[0].userMessage || 'Error en el proceso';
                this.showNotificationErrorFile(errorMessage);
            }
            else if (response.description) {
                this.showNotificationErrorFile(response.description);
            }
            else {
                this.showNotificationErrorFile('Error en el proceso');
            }
        }
    }
    showNotificationSuccess(msj) {
        const text = '<span class="now-ui-icons emoticons_satisfied"></span><b>Éxito</b> ' + msj;
        this.toastr.info(text, '', {
            timeOut: 6000,
            closeButton: true,
            enableHtml: true,
            toastClass: this.successClass,
            positionClass: 'toast-top-right'
        });
    }
    showNotificationErrorFile(message) {
        this.toastr.error(`<b>Error</b> ${message}`, '', {
            timeOut: 6000,
            closeButton: true,
            enableHtml: true,
            toastClass: this.errorClass,
            positionClass: 'toast-top-right'
        });
    }
    showNotificationError(code) {
        let html;
        if (code === 302) {
            html = '<b>Error</b> El dato ya existe';
        }
        else if (code === 400) {
            html = '<b>Error</b> La información enviada es incorrecta';
        }
        else if (code === 401) {
            html = '<b>Error</b> No está autorizado para realizar esta acción';
        }
        else if (code === 404) {
            html = '<b>Error</b> No se encontraron datos';
        }
        else if (code === 500) {
            html = '<b>Error</b> Interno del servidor';
        }
        this.toastr.info(html, '', {
            timeOut: 2000,
            closeButton: true,
            enableHtml: true,
            toastClass: this.errorClass,
            positionClass: 'toast-top-right'
        });
    }
    setMsg(msg_html, titulo = '', tiempo = 2000, clase, posicion = 'toast-top-right') {
        this.toastr.info(msg_html, titulo, {
            timeOut: tiempo,
            closeButton: true,
            enableHtml: true,
            toastClass: `alert alert-${clase} alert-with-icon`,
            positionClass: posicion
        });
    }
    showNotification(code, msj) {
        let text;
        let text2;
        if (code === 0) {
            text = '<span class="now-ui-icons emoticons_satisfied"></span><b>Éxito</b> ' + msj;
            text2 = 'alert alert-success alert-with-icon';
        }
        else if (code === 1) {
            text = msj;
            text2 = 'alert alert-info alert-with-icon';
        }
        else {
            text = '<b>Error</b> ' + msj;
            text2 = this.errorClass;
        }
        this.toastr.info(text, '', {
            timeOut: 6000,
            closeButton: true,
            enableHtml: true,
            toastClass: text2,
            positionClass: 'toast-top-right'
        });
    }
    showNotificationMsj(msj) {
        let text;
        let text2;
        text = '<b>Error</b> ' + msj;
        text2 = this.errorClass;
        this.toastr.info(text, '', {
            timeOut: 5000,
            closeButton: true,
            enableHtml: true,
            toastClass: text2,
            positionClass: 'toast-top-right'
        });
    }
    getDismissReason(reason) {
        if (reason === _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__["ModalDismissReasons"].ESC) {
            return 'by pressing ESC';
        }
        else if (reason === _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_4__["ModalDismissReasons"].BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        }
        else {
            return `with: ${reason}`;
        }
    }
    loadGrants(grant) {
        const grants = this.subs[2].split('_');
        return grants.filter(x => x === grant).length > 0;
    }
    getMask() {
        return +this.subs[3].split('_')[0].replace('"', '');
    }
    getSupervisor() {
        return +this.subs[3].split('_')[1].replace('"', '');
    }
    getSystemUser() {
        return this.subs[0].toString().replace('"', '');
    }
    getSystemRol() {
        return +this.subs[1];
    }
};
UtilService.ctorParameters = () => [
    { type: ngx_toastr__WEBPACK_IMPORTED_MODULE_3__["ToastrService"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_2__["ActivatedRoute"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"] }
];
UtilService = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
        providedIn: 'root'
    })
], UtilService);



/***/ }),

/***/ "./src/app/views/simcard-control-file/simcard-control-file.component.css":
/*!*******************************************************************************!*\
  !*** ./src/app/views/simcard-control-file/simcard-control-file.component.css ***!
  \*******************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".button-row {\r\n    display: flex;\r\n    justify-content: center;\r\n}\r\n\r\n.button-row button {\r\n    margin: 0 5px;\r\n    padding: 10px 20px;\r\n    color: white;\r\n    border: none;\r\n    cursor: pointer;\r\n    border-radius: 10px;\r\n}\r\n\r\n.btn-green {\r\n    margin: 0 700px;\r\n    padding: 10px 20px;\r\n    justify-content: right;\r\n    background-color: #00a8b4;\r\n  \r\n}\r\n\r\n.btn-blue {\r\n    justify-content: right;\r\n    background-color: #0dad5d;\r\n  \r\n}\r\n\r\n.font-size-large {\r\n    font-size: 15px;\r\n}\r\n\r\n.title-container {\r\n    display: flex;\r\n    justify-content: center;\r\n    align-items: center;\r\n    height: 100vh;\r\n}\r\n\r\n.title {\r\n    font-size: 25px;\r\n    background-image: linear-gradient(to right, #00e1ff, #402ee5);\r\n    -webkit-background-clip: text;\r\n    background-clip: text;\r\n    color: transparent;\r\n    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);\r\n    text-decoration: underline;\r\n    -webkit-text-decoration-color: linear-gradient(to right, #00e1ff, #402ee5);\r\n            text-decoration-color: linear-gradient(to right, #00e1ff, #402ee5);\r\n}\r\n\r\n.blue-text {\r\n    color: rgb(3, 47, 97);\r\n    font-weight: bold;\r\n}\r\n\r\n.yellow-text {\r\n    color: rgb(177, 177, 20);\r\n    font-weight: bold;\r\n}\r\n\r\n.green-text {\r\n    color: rgb(0, 129, 0);\r\n    font-weight: bold;\r\n}\r\n\r\n.red-text {\r\n    color: rgb(110, 1, 1);\r\n    font-weight: bold;\r\n}\r\n\r\n.custom-button-row {\r\n    justify-content: space-between;\r\n}\r\n\r\n.custom-button-row .col-md-6 {\r\n    display: flex;\r\n    justify-content: flex-end;\r\n}\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvdmlld3Mvc2ltY2FyZC1jb250cm9sLWZpbGUvc2ltY2FyZC1jb250cm9sLWZpbGUuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLGFBQWE7SUFDYix1QkFBdUI7QUFDM0I7O0FBRUE7SUFDSSxhQUFhO0lBQ2Isa0JBQWtCO0lBQ2xCLFlBQVk7SUFDWixZQUFZO0lBQ1osZUFBZTtJQUNmLG1CQUFtQjtBQUN2Qjs7QUFFQTtJQUNJLGVBQWU7SUFDZixrQkFBa0I7SUFDbEIsc0JBQXNCO0lBQ3RCLHlCQUF5Qjs7QUFFN0I7O0FBRUE7SUFDSSxzQkFBc0I7SUFDdEIseUJBQXlCOztBQUU3Qjs7QUFFQTtJQUNJLGVBQWU7QUFDbkI7O0FBR0E7SUFDSSxhQUFhO0lBQ2IsdUJBQXVCO0lBQ3ZCLG1CQUFtQjtJQUNuQixhQUFhO0FBQ2pCOztBQUVBO0lBQ0ksZUFBZTtJQUNmLDZEQUE2RDtJQUM3RCw2QkFBNkI7SUFDN0IscUJBQXFCO0lBQ3JCLGtCQUFrQjtJQUNsQiwyQ0FBMkM7SUFDM0MsMEJBQTBCO0lBQzFCLDBFQUFrRTtZQUFsRSxrRUFBa0U7QUFDdEU7O0FBRUE7SUFDSSxxQkFBcUI7SUFDckIsaUJBQWlCO0FBQ3JCOztBQUVBO0lBQ0ksd0JBQXdCO0lBQ3hCLGlCQUFpQjtBQUNyQjs7QUFFQTtJQUNJLHFCQUFxQjtJQUNyQixpQkFBaUI7QUFDckI7O0FBRUE7SUFDSSxxQkFBcUI7SUFDckIsaUJBQWlCO0FBQ3JCOztBQUVBO0lBQ0ksOEJBQThCO0FBQ2xDOztBQUVBO0lBQ0ksYUFBYTtJQUNiLHlCQUF5QjtBQUM3QiIsImZpbGUiOiJzcmMvYXBwL3ZpZXdzL3NpbWNhcmQtY29udHJvbC1maWxlL3NpbWNhcmQtY29udHJvbC1maWxlLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIuYnV0dG9uLXJvdyB7XHJcbiAgICBkaXNwbGF5OiBmbGV4O1xyXG4gICAganVzdGlmeS1jb250ZW50OiBjZW50ZXI7XHJcbn1cclxuXHJcbi5idXR0b24tcm93IGJ1dHRvbiB7XHJcbiAgICBtYXJnaW46IDAgNXB4O1xyXG4gICAgcGFkZGluZzogMTBweCAyMHB4O1xyXG4gICAgY29sb3I6IHdoaXRlO1xyXG4gICAgYm9yZGVyOiBub25lO1xyXG4gICAgY3Vyc29yOiBwb2ludGVyO1xyXG4gICAgYm9yZGVyLXJhZGl1czogMTBweDtcclxufVxyXG5cclxuLmJ0bi1ncmVlbiB7XHJcbiAgICBtYXJnaW46IDAgNzAwcHg7XHJcbiAgICBwYWRkaW5nOiAxMHB4IDIwcHg7XHJcbiAgICBqdXN0aWZ5LWNvbnRlbnQ6IHJpZ2h0O1xyXG4gICAgYmFja2dyb3VuZC1jb2xvcjogIzAwYThiNDtcclxuICBcclxufVxyXG5cclxuLmJ0bi1ibHVlIHtcclxuICAgIGp1c3RpZnktY29udGVudDogcmlnaHQ7XHJcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjMGRhZDVkO1xyXG4gIFxyXG59XHJcblxyXG4uZm9udC1zaXplLWxhcmdlIHtcclxuICAgIGZvbnQtc2l6ZTogMTVweDtcclxufVxyXG5cclxuXHJcbi50aXRsZS1jb250YWluZXIge1xyXG4gICAgZGlzcGxheTogZmxleDtcclxuICAgIGp1c3RpZnktY29udGVudDogY2VudGVyO1xyXG4gICAgYWxpZ24taXRlbXM6IGNlbnRlcjtcclxuICAgIGhlaWdodDogMTAwdmg7XHJcbn1cclxuXHJcbi50aXRsZSB7XHJcbiAgICBmb250LXNpemU6IDI1cHg7XHJcbiAgICBiYWNrZ3JvdW5kLWltYWdlOiBsaW5lYXItZ3JhZGllbnQodG8gcmlnaHQsICMwMGUxZmYsICM0MDJlZTUpO1xyXG4gICAgLXdlYmtpdC1iYWNrZ3JvdW5kLWNsaXA6IHRleHQ7XHJcbiAgICBiYWNrZ3JvdW5kLWNsaXA6IHRleHQ7XHJcbiAgICBjb2xvcjogdHJhbnNwYXJlbnQ7XHJcbiAgICB0ZXh0LXNoYWRvdzogMXB4IDFweCAycHggcmdiYSgwLCAwLCAwLCAwLjMpO1xyXG4gICAgdGV4dC1kZWNvcmF0aW9uOiB1bmRlcmxpbmU7XHJcbiAgICB0ZXh0LWRlY29yYXRpb24tY29sb3I6IGxpbmVhci1ncmFkaWVudCh0byByaWdodCwgIzAwZTFmZiwgIzQwMmVlNSk7XHJcbn1cclxuXHJcbi5ibHVlLXRleHQge1xyXG4gICAgY29sb3I6IHJnYigzLCA0NywgOTcpO1xyXG4gICAgZm9udC13ZWlnaHQ6IGJvbGQ7XHJcbn1cclxuXHJcbi55ZWxsb3ctdGV4dCB7XHJcbiAgICBjb2xvcjogcmdiKDE3NywgMTc3LCAyMCk7XHJcbiAgICBmb250LXdlaWdodDogYm9sZDtcclxufVxyXG5cclxuLmdyZWVuLXRleHQge1xyXG4gICAgY29sb3I6IHJnYigwLCAxMjksIDApO1xyXG4gICAgZm9udC13ZWlnaHQ6IGJvbGQ7XHJcbn1cclxuXHJcbi5yZWQtdGV4dCB7XHJcbiAgICBjb2xvcjogcmdiKDExMCwgMSwgMSk7XHJcbiAgICBmb250LXdlaWdodDogYm9sZDtcclxufVxyXG5cclxuLmN1c3RvbS1idXR0b24tcm93IHtcclxuICAgIGp1c3RpZnktY29udGVudDogc3BhY2UtYmV0d2VlbjtcclxufVxyXG5cclxuLmN1c3RvbS1idXR0b24tcm93IC5jb2wtbWQtNiB7XHJcbiAgICBkaXNwbGF5OiBmbGV4O1xyXG4gICAganVzdGlmeS1jb250ZW50OiBmbGV4LWVuZDtcclxufSJdfQ== */");

/***/ }),

/***/ "./src/app/views/simcard-control-file/simcard-control-file.component.ts":
/*!******************************************************************************!*\
  !*** ./src/app/views/simcard-control-file/simcard-control-file.component.ts ***!
  \******************************************************************************/
/*! exports provided: SimcardControlFileComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SimcardControlFileComponent", function() { return SimcardControlFileComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/__ivy_ngcc__/fesm2015/ng-bootstrap.js");
/* harmony import */ var src_app_components_create_order_modal_create_order_modal_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/components/create-order-modal/create-order-modal.component */ "./src/app/components/create-order-modal/create-order-modal.component.ts");
/* harmony import */ var src_app_components_file_detail_modal_file_detail_modal_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/components/file-detail-modal/file-detail-modal.component */ "./src/app/components/file-detail-modal/file-detail-modal.component.ts");
/* harmony import */ var src_app_components_order_control_modal_order_control_modal_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/components/order-control-modal/order-control-modal.component */ "./src/app/components/order-control-modal/order-control-modal.component.ts");
/* harmony import */ var src_app_components_see_order_modal_see_order_modal_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! src/app/components/see-order-modal/see-order-modal.component */ "./src/app/components/see-order-modal/see-order-modal.component.ts");
/* harmony import */ var src_app_components_suppliers_modal_suppliers_modal_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! src/app/components/suppliers-modal/suppliers-modal.component */ "./src/app/components/suppliers-modal/suppliers-modal.component.ts");
/* harmony import */ var src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! src/app/services/simcard-control-file.service */ "./src/app/services/simcard-control-file.service.ts");
/* harmony import */ var src_app_services_util_service__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! src/app/services/util.service */ "./src/app/services/util.service.ts");










let SimcardControlFileComponent = class SimcardControlFileComponent {
    constructor(simcardControlFileService, modalService, utilService) {
        this.simcardControlFileService = simcardControlFileService;
        this.modalService = modalService;
        this.utilService = utilService;
        this.simcardControl = [];
        this.rows = [];
        this.loadingIndicator = true;
        this.resultsPerPage = 10;
        this.searchedValue = "";
        this.typeValue = "";
    }
    ngOnInit() {
        this.getSimcardControl();
    }
    getSimcardControl() {
        this.simcardControlFileService.getSimcardControl().subscribe((res) => {
            if (res.status === 200) {
                this.loadingIndicator = false;
                let data = res.body.data;
                this.simcardControl = data.map((v) => {
                    let dto = {};
                    dto.id = v.id;
                    dto.nameFile = v.nameFile;
                    switch (v.status) {
                        case 0:
                            dto.status = 'Pendiente';
                            dto.statusColor = 'blue';
                            break;
                        case 1:
                            dto.status = 'Finalizado';
                            dto.statusColor = 'green';
                            break;
                        case -1:
                            dto.status = 'Error al procesar el archivo';
                            dto.statusColor = 'red';
                            break;
                        default:
                            dto.status = 'Desconocido';
                            dto.statusColor = 'black';
                    }
                    dto.created = this.formatDate(v.created);
                    dto.idSimcardPadre = v.idSimcardPadre;
                    dto.suppliersId = v.suppliersId;
                    dto.idSimcardOrder = v.idSimcardOrder;
                    return dto;
                });
                this.rows = [...this.simcardControl];
            }
            else {
                this.utilService.showNotificationError(res.status);
            }
        });
    }
    onFileSelected(event) {
        const selectedFile = event.target.files[0];
        if (selectedFile) {
            this.simcardControlFileService.processSimcardFile(selectedFile).subscribe((response) => {
                this.utilService.handleApiResponse(response);
                if (response.code === 200) {
                    this.ngOnInit();
                }
            }, (error) => {
                this.utilService.handleApiResponse(error.error);
            });
        }
        else {
            this.utilService.showNotificationError(500);
        }
    }
    // MODALS
    openDetailModal(button, rowsSimcardControl = null) {
        const modalRef = this.modalService.open(src_app_components_file_detail_modal_file_detail_modal_component__WEBPACK_IMPORTED_MODULE_4__["FileDetailModalComponent"], { size: 'xl', scrollable: false });
        modalRef.componentInstance.rowsSimcardControl = rowsSimcardControl;
        modalRef.componentInstance.button = button;
    }
    openModalSuppliers(event, row = null) {
        event.stopPropagation();
        const modalRef = this.modalService.open(src_app_components_suppliers_modal_suppliers_modal_component__WEBPACK_IMPORTED_MODULE_7__["SuppliersModalComponent"], { size: 'xl', backdrop: 'static' });
        if (row && row.idSimcardPadre !== undefined) {
            modalRef.componentInstance.idSimcardPadre = row.idSimcardPadre;
            modalRef.componentInstance.idSimcardControl = row.id;
        }
        else {
            console.error('Error: idSimcardPadre no está definido en el objeto row.');
            modalRef.close();
            return;
        }
        modalRef.result.then((result) => {
            this.getSimcardControl();
        });
        modalRef.componentInstance.messageEvent.subscribe((simcardControl) => {
        });
    }
    openModalCreateOrder(event, row = null) {
        event.stopPropagation();
        const modalRef = this.modalService.open(src_app_components_create_order_modal_create_order_modal_component__WEBPACK_IMPORTED_MODULE_3__["CreateOrderModalComponent"], { size: 'xl', backdrop: 'static' });
        if (row && row.idSimcardPadre !== undefined) {
            modalRef.componentInstance.idSimcardPadre = row.idSimcardPadre;
            modalRef.componentInstance.idSimcardControl = row.id;
        }
        else {
            console.error('Error: idSimcardPadre no está definido en el objeto row.');
            modalRef.close();
            return;
        }
        modalRef.result.then((result) => {
            this.getSimcardControl();
        });
    }
    openModalSeeOrder(event, row = null) {
        event.stopPropagation();
        const modalRef = this.modalService.open(src_app_components_see_order_modal_see_order_modal_component__WEBPACK_IMPORTED_MODULE_6__["SeeOrderModalComponent"], { size: 'xl', backdrop: 'static' });
        if (row && row.idSimcardPadre !== undefined) {
            modalRef.componentInstance.idSimcardPadre = row.idSimcardPadre;
        }
        else {
            console.error('Error: idSimcardPadre no está definido en el objeto row.');
            modalRef.close();
            return;
        }
    }
    openModalControlOrder(button, row = null) {
        const modalRef = this.modalService.open(src_app_components_order_control_modal_order_control_modal_component__WEBPACK_IMPORTED_MODULE_5__["OrderControlModalComponent"], { size: 'xl', backdrop: 'static' });
        modalRef.componentInstance.rowsOrderControl = row;
        modalRef.componentInstance.button = button;
        modalRef.componentInstance.messageEvent.subscribe((controlOrder) => {
        });
    }
    //* UTILS
    searchSimcardControl() {
        this.rows = this.simcardControl.filter((simcardControl) => {
            return JSON.stringify(simcardControl)
                .toLowerCase()
                .includes(this.searchedValue.toString().toLowerCase());
        });
    }
    getTotalText() {
        return this.rows.length == 1 ? "Registro" : "Registros";
    }
    reloadRows() {
        this.getSimcardControl();
    }
    refrescar() {
        this.ngOnInit();
    }
    formatDate(fecha) {
        if (!fecha) {
            return '';
        }
        const fechaFormateada = new Date(fecha);
        const dia = fechaFormateada.getDate().toString().padStart(2, '0');
        const mes = (fechaFormateada.getMonth() + 1).toString().padStart(2, '0');
        const año = fechaFormateada.getFullYear();
        return `${dia}/${mes}/${año}`;
    }
    getStatusColorClass(statusColor) {
        switch (statusColor) {
            case 'blue':
                return 'blue-text';
            case 'yellow':
                return 'yellow-text';
            case 'green':
                return 'green-text';
            case 'red':
                return 'red-text';
            default:
                return '';
        }
    }
};
SimcardControlFileComponent.ctorParameters = () => [
    { type: src_app_services_simcard_control_file_service__WEBPACK_IMPORTED_MODULE_8__["SimcardControlFileService"] },
    { type: _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__["NgbModal"] },
    { type: src_app_services_util_service__WEBPACK_IMPORTED_MODULE_9__["UtilService"] }
];
SimcardControlFileComponent = Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"])([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-simcard-control-file',
        template: Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! raw-loader!./simcard-control-file.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/views/simcard-control-file/simcard-control-file.component.html")).default,
        styles: [Object(tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"])(__webpack_require__(/*! ./simcard-control-file.component.css */ "./src/app/views/simcard-control-file/simcard-control-file.component.css")).default]
    })
], SimcardControlFileComponent);



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/__ivy_ngcc__/fesm2015/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(err => console.error(err));


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! C:\Users\laure\Documents\Tigo\Frontend\Proyecto_BSIM\Módulo -Consulta de Simcards\Pantallas SimcardInquiry\control-archivo-simcard\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main-es2015.js.map