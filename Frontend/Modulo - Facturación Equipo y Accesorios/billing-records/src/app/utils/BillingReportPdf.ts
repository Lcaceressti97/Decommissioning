import { InvoiceEquipmentAccesories } from "../models/InvoiceEquipmentAccesories";
import { Logo as logo } from "./Resources";


export function getDetailBillingReport(invoiceEquipmentAccesories: InvoiceEquipmentAccesories[] = []) {

    let invoiceData = invoiceEquipmentAccesories.reduce((acc, obj) => {
        let valores = Object.values(obj);
        acc.push(valores);
        return acc;
    }, []);

    return {
        content: [
            {
                alignment: "left",
                columns: [
                    {
                        image: logo,
                        width: 75,
                    },
                    {
                        text: "\nDetalle de Guia de Remisión Venta\n\n",
                        bold: true,
                        fontSize: 18,
                        alignment: 'center',
                        decoration: 'underline',
                        margin: [0, 0, 0, 20]
                    },

                ],
            },
            {
                table: {
                    widths: ["*", "*", "*", "*", "*", "*", "*", "*", "*"],
                    bold: true,
                    body: [
                        ["No. Factura", "Tipo de Factura", "Estado", "Suscriptor", "ID - RTIN", "Correo", "Compañia", "Fecha Creación", "Usuario Creación"],

                        ...invoiceData,
                    ],
                },
            },
        ]
    };
}