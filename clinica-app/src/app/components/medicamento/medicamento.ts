import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Medicamento } from '../../model/medicamento.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MedicamentoService } from '../../services/medicamento';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-medicamento',
  standalone: false,
  templateUrl: './medicamento.html',
  styleUrl: './medicamento.css'
})
export class MedicamentoComponent implements OnInit {
  
  medicamentos: Medicamento[] = [];
  medicamento: Medicamento = {} as Medicamento;
  editar: boolean = false;
  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Medicamento>;
  mostrarColumnas: String[] = ['idMedicamento', 'nombre', 'dosis', 'descripcion','precio','stock','portada','acciones'];

  @ViewChild('formularioMedicamento') formularioMedicamento!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private medicamentoService: MedicamentoService) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.medicamentoService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  save(): void {
    this.medicamentoService.save(this.medicamento).subscribe(() => {
      this.medicamento = {} as Medicamento;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.medicamentoService.update(this.idEditar, this.medicamento).subscribe(() => {
        this.medicamento = {} as Medicamento;
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
        this.medicamentoService.delete(this.medicamento.idMedicamento).subscribe(() => {
          this.findAll();
          this.medicamento = {} as Medicamento;
          Swal.fire('Eliminado', 'El medicamento ha sido eliminado', 'success');
        });
      } else {
        this.medicamento = {} as Medicamento;
      }
    });
  }

  editarMedicamento(medicamento: Medicamento): void {
    this.medicamento = { ...medicamento };
    this.idEditar = medicamento.idMedicamento;
    this.editar = true;

    setTimeout(() => {
      this.formularioMedicamento.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
  }

  editarMedicamentoCancelar(form: NgForm): void {
    this.medicamento = {} as Medicamento;
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

  filtroMedicamentos(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}
