import { Cita } from './cita.model';

export interface Receta {
  idReceta: number;
  descripcion: string;
  fecha: Date;
  cita: Cita;

  [key: string]: any;
}
