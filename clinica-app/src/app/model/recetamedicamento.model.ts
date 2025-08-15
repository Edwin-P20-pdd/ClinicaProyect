import { Receta } from './receta.model';
import { Medicamento } from './medicamento.model';

export interface RecetaMedicamento {
  idRecetaMedicamento: number;
  receta: Receta;
  medicamento: Medicamento;

  [key: string]: any;
}
