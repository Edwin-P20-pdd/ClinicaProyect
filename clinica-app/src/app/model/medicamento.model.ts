export interface Medicamento{
    idMedicamento: number
    nombre: string
    dosis: string
    descripcion: string
    precio: number
    stock: number
    portada: string

    [key: string]: any;
}
