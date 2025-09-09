import { Paciente } from "./paciente.model"

export interface Factura{
    idFactura: number
    numFactura: String
    fecha: Date
    totalNeto: number
    iva: number
    total: number
    paciente: Paciente

    [key: string]: any;
}