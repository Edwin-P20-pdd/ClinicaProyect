
import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { RecetaMedicamento } from '../../model/recetamedicamento.model';
import { Receta } from '../../model/receta.model';
import { Medicamento } from '../../model/medicamento.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { RecetaMedicamentoService } from '../../services/receta-medicamento';
import { RecetaService } from '../../services/receta';
import { MedicamentoService } from '../../services/medicamento';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-receta-medicamento',
  standalone: false,
  templateUrl: './receta-medicamento.html',
  styleUrls: ['./receta-medicamento.css']
})
export class RecetaMedicamentoComponent implements OnInit {
  recetasMedicamentos: RecetaMedicamento[] = [];
  recetas: Receta[] = [];
  medicamentos: Medicamento[] = [];
  recetaMedicamento: RecetaMedicamento = {} as RecetaMedicamento;
  editar: boolean = false;
  idEditar: number | null = null;
  dataSource!: MatTableDataSource<RecetaMedicamento>;
  recetaSeleccionada: RecetaMedicamento | null = null;

  mostrarColumnas: string[] = ['detalles', 'idRecetaMedicamento', 'receta', 'medicamento', 'acciones'];

  @ViewChild('formularioRecetaMedicamento') formularioRecetaMedicamento!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('modalRecetaMedicamento') modalRecetaMedicamento!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;

  constructor(
    private recetaMedicamentoService: RecetaMedicamentoService,
    private recetaService: RecetaService,
    private medicamentoService: MedicamentoService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.findAll();
    this.cargarRecetas();
    this.cargarMedicamentos();
  }

  findAll(): void {
    this.recetaMedicamentoService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  cargarRecetas(): void {
    this.recetaService.findAll().subscribe(data => {
      this.recetas = data;
    });
  }

  cargarMedicamentos(): void {
    this.medicamentoService.findAll().subscribe(data => {
      this.medicamentos = data;
    });
  }

  save(): void {
    this.recetaMedicamentoService.save(this.recetaMedicamento).subscribe(() => {
      this.recetaMedicamento = {} as RecetaMedicamento;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.recetaMedicamentoService.update(this.idEditar, this.recetaMedicamento).subscribe(() => {
        this.recetaMedicamento = {} as RecetaMedicamento;
        this.idEditar = null;
        this.findAll();
      });
    }
  }

  delete(): void {
    Swal.fire({
      title: '¿Desea eliminar esta relación?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.recetaMedicamentoService.delete(this.recetaMedicamento.idRecetaMedicamento).subscribe(() => {
          this.findAll();
          this.recetaMedicamento = {} as RecetaMedicamento;
          Swal.fire('Eliminado', 'La relación ha sido eliminada', 'success');
        });
      }
    });
  }

  guardarRecetaMedicamento(): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
    } else {
      this.save();
    }
    this.dialog.closeAll();
  }

  filtroRecetaMedicamento(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }

  abrirModal(rm?: RecetaMedicamento): void {
    if (rm) {
      this.recetaMedicamento = { ...rm };
      this.editar = true;
      this.idEditar = rm.idRecetaMedicamento;
    } else {
      this.recetaMedicamento = {} as RecetaMedicamento;
      this.editar = false;
      this.idEditar = null;
    }

    this.dialog.open(this.modalRecetaMedicamento, {
      width: '800px',
      disableClose: true
    });
  }

  compararRecetas(r1: Receta, r2: Receta): boolean {
    return r1 && r2 ? r1.idReceta === r2.idReceta : r1 === r2;
  }

  compararMedicamentos(m1: Medicamento, m2: Medicamento): boolean {
    return m1 && m2 ? m1.idMedicamento === m2.idMedicamento : m1 === m2;
  }

  abrirModalDetalles(rm: RecetaMedicamento): void {
    this.recetaSeleccionada = rm;
    this.dialog.open(this.modalDetalles, {
      width: '500px'
    });
  }

  cerrarModal(): void {
    this.dialog.closeAll();
    this.recetaSeleccionada = null;
  }
}
