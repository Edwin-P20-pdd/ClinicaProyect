import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Cita } from '../../model/cita.model';
import { Paciente } from '../../model/paciente.model';
import { Doctor } from '../../model/doctor.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CitaService } from '../../services/cita';
import { PacienteService } from '../../services/paciente';
import { DoctorService } from '../../services/doctor';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-cita',
  standalone: false,
  templateUrl: './cita.html',
  styleUrl: './cita.css'
})
export class CitaComponent implements OnInit {
  citas: Cita[] = [];
  pacientes: Paciente[] = [];
  doctores: Doctor[] = [];
  cita: Cita = {} as Cita;
  editar: boolean = false;
  idEditar: number | null = null;
  dataSource!: MatTableDataSource<Cita>;
  citaSeleccionada: Cita | null = null;

  mostrarColumnas: string[] = [
  'detalles',
  'idCita',
  'fecha',
  'motivo',
  'cedulaPaciente',
  'paciente',
  'fechaNacimientoPaciente',
  'direccionPaciente',
  'telefonoPaciente',
  'correoPaciente',
  'doctor',
  'acciones'
];


  @ViewChild('formularioCita') formularioCita!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('modalCita') modalCita!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;

  constructor(
    private citaService: CitaService,
    private pacienteService: PacienteService,
    private doctorService: DoctorService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.findAll();
    this.cargarPacientes();
    this.cargarDoctores();
  }

  findAll(): void {
    this.citaService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  cargarPacientes(): void {
    this.pacienteService.findAll().subscribe(data => {
      this.pacientes = data;
    });
  }

  cargarDoctores(): void {
    this.doctorService.findAll().subscribe(data => {
      this.doctores = data;
    });
  }

  save(): void {
    this.citaService.save(this.cita).subscribe(() => {
      this.cita = {} as Cita;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.citaService.update(this.idEditar, this.cita).subscribe(() => {
        this.cita = {} as Cita;
        this.idEditar = null;
        this.findAll();
      });
    }
  }

  delete(): void {
    Swal.fire({
      title: '¿Desea eliminar la cita?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.citaService.delete(this.cita.idCita).subscribe(() => {
          this.findAll();
          this.cita = {} as Cita;
          Swal.fire('Eliminado', 'La cita ha sido eliminada', 'success');
        });
      }
    });
  }

  editarCita(cita: Cita): void {
    this.cita = { ...cita };
    this.idEditar = cita.idCita;
    this.editar = true;
    setTimeout(() => {
      this.formularioCita.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
  }

  editarCitaCancelar(form: NgForm): void {
    this.cita = {} as Cita;
    this.idEditar = null;
    this.editar = false;
    form.resetForm();
  }

  guardarCita(): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
    } else {
      this.save();
    }
    this.dialog.closeAll();
  }

  filtroCita(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }

  nombreCompletoPaciente(paciente: Paciente): string {
    return `${paciente.nombre} ${paciente.apellido}`;
  }

  nombreCompletoDoctor(doctor: Doctor): string {
    return `${doctor.nombre} ${doctor.apellido}`;
  }

  abrirModal(cita?: Cita): void {
    if (cita) {
      this.cita = { ...cita };
      this.editar = true;
      this.idEditar = cita.idCita;
    } else {
      this.cita = {} as Cita;
      this.editar = false;
      this.idEditar = null;
    }

    this.dialog.open(this.modalCita, {
      width: '800px',
      disableClose: true
    });
  }

  compararPacientes(p1: Paciente, p2: Paciente): boolean {
    return p1 && p2 ? p1.idPaciente === p2.idPaciente : p1 === p2;
  }

  compararDoctores(d1: Doctor, d2: Doctor): boolean {
    return d1 && d2 ? d1.idDoctor === d2.idDoctor : d1 === d2;
  }

  abrirModalDetalles(cita: Cita): void {
    this.citaSeleccionada = cita;
    this.dialog.open(this.modalDetalles, {
      width: '500px'
    });
  }

  cerrarModal(): void {
    this.dialog.closeAll();
    this.citaSeleccionada = null;
  }
}
