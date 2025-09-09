import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Paciente } from '../../model/paciente.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { PacienteService } from '../../services/paciente';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-paciente',
  standalone: false,
  templateUrl: './paciente.html',
  styleUrl: './paciente.css'
})
export class PacienteComponent implements OnInit {
  
  pacientes: Paciente[] = [];
  paciente: Paciente = {} as Paciente;
  editar: boolean = false;
  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Paciente>;
  mostrarColumnas: String[] = [
    'idPaciente', 'cedula', 'nombre', 'apellido', 'fechaNacimiento',
    'direccion', 'telefono', 'correo', 'acciones'
  ];

  @ViewChild('formularioPaciente') formularioPaciente!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private pacienteService: PacienteService) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.pacienteService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  save(): void {
    this.pacienteService.save(this.paciente).subscribe(() => {
      this.paciente = {} as Paciente;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.pacienteService.update(this.idEditar, this.paciente).subscribe(() => {
        this.paciente = {} as Paciente;
        this.editar = false;
        this.idEditar = null;
        this.findAll();
      });
    }
  }

  delete(): void {
    Swal.fire({
      title: '¿Desea eliminar el dato?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.pacienteService.delete(this.paciente.idPaciente).subscribe(() => {
          this.findAll();
          this.paciente = {} as Paciente;
          Swal.fire('Eliminado', 'El paciente ha sido eliminado', 'success');
        });
      } else {
        this.paciente = {} as Paciente;
      }
    });
  }

  editarPaciente(paciente: Paciente): void {
    this.paciente = { ...paciente };
    this.idEditar = paciente.idPaciente;
    this.editar = true;

    setTimeout(() => {
      this.formularioPaciente.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
  }

  editarPacienteCancelar(form: NgForm): void {
    this.paciente = {} as Paciente;
    this.idEditar = null;
    this.editar = false;
    form.resetForm();
  }

  guardar(form: NgForm): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
      form.resetForm();
    } else {
      this.save();
      form.resetForm();
    }
  }

  filtroPacientes(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}
