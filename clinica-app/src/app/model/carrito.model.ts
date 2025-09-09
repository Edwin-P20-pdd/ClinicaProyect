import { CarritoItem } from "./carrito-item.model";
import { Paciente } from "./paciente.model";

export interface Carrito{
    idCarrito?: number;
    paciente?: Paciente;
    items: CarritoItem[];
    subtotal: number;
    descuento: number;
    impuestos: number;
    total: number;
    actualizadoEn?: string;

    [key: string]: any;
}