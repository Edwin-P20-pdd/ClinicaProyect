import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Doctor } from '../../model/doctor.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DoctorService } from '../../services/doctor';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-doctor',
  standalone: false,
  templateUrl: './doctor.html',
  styleUrl: './doctor.css'
})
export class DoctorComponent implements OnInit {

  doctores: Doctor[] = [];
  doctor: Doctor = {} as Doctor;
  editar: boolean = false;
  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Doctor>;
  mostrarColumnas: String[] = ['idDoctor', 'nombre', 'apellido', 'especialidad', 'telefono', 'correo', 'acciones'];

  @ViewChild('formularioDoctor') formularioDoctor!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private doctorService: DoctorService) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.doctorService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  save(): void {
    this.doctorService.save(this.doctor).subscribe(() => {
      this.doctor = {} as Doctor;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.doctorService.update(this.idEditar, this.doctor).subscribe(() => {
        this.doctor = {} as Doctor;
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
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.doctorService.delete(this.doctor.idDoctor).subscribe(() => {
          this.findAll();
          this.doctor = {} as Doctor;
          Swal.fire('Eliminado', 'El doctor ha sido eliminado', 'success');
        });
      } else {
        this.doctor = {} as Doctor;
      }
    });
  }

  editarDoctor(doctor: Doctor): void {
    this.doctor = { ...doctor };
    this.idEditar = doctor.idDoctor;
    this.editar = true;

    setTimeout(() => {
      this.formularioDoctor.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
  }

  editarDoctorCancelar(form: NgForm): void {
    this.doctor = {} as Doctor;
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

  filtroDoctores(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}
