import { Medicamento } from "./medicamento.model";

export interface CarritoItem{

    idCarritoItem?: number;
    medicamento: Medicamento;
    cantidad: number;
    precioUnitario: number;
    total: number;    

    [key: string]: any;
}