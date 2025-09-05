import { Factura } from "./factura.model"
import { Medicamento } from "./medicamento.model"

export interface FacturaDetalle {
    idFacturaDetalle: string
    cantidad: string
    subtotal: number
    medicamento: Medicamento
    factura: Factura

    [key: string]: any;
}