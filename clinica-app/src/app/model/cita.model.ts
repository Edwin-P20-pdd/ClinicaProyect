import { Paciente } from "./paciente.model";
import { Doctor } from "./doctor.model";

export interface Cita {
    idCita: number;
    fecha: Date;
    motivo: string;
    paciente: Paciente;
    doctor: Doctor;

    [key: string]: any;
}
