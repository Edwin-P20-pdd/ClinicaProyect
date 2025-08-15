import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Receta } from '../../model/receta.model';
import { Cita } from '../../model/cita.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { RecetaService } from '../../services/receta';
import { CitaService } from '../../services/cita';
import Swal from 'sweetalert2';
import { MatDialog } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-receta',
  standalone: false,
  templateUrl: './receta.html',
  styleUrls: ['./receta.css']
})
export class RecetaComponent implements OnInit {
  recetas: Receta[] = [];
  citas: Cita[] = [];
  receta: Receta = {} as Receta;
  editar: boolean = false;
  idEditar: number | null = null;
  dataSource!: MatTableDataSource<Receta>;
  recetaSeleccionada: Receta | null = null;

  mostrarColumnas: string[] = ['detalles', 'idReceta', 'fecha', 'descripcion', 'cita', 'acciones'];

  @ViewChild('formularioReceta') formularioReceta!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('modalReceta') modalReceta!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;

  constructor(
    private recetaService: RecetaService,
    private citaService: CitaService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.findAll();
    this.cargarCitas();
  }

  findAll(): void {
    this.recetaService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  cargarCitas(): void {
    this.citaService.findAll().subscribe(data => {
      this.citas = data;
    });
  }

  save(): void {
    this.recetaService.save(this.receta).subscribe(() => {
      this.receta = {} as Receta;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.recetaService.update(this.idEditar, this.receta).subscribe(() => {
        this.receta = {} as Receta;
        this.idEditar = null;
        this.findAll();
      });
    }
  }

  delete(): void {
    Swal.fire({
      title: '¿Desea eliminar la receta?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.recetaService.delete(this.receta.idReceta).subscribe(() => {
          this.findAll();
          this.receta = {} as Receta;
          Swal.fire('Eliminado', 'La receta ha sido eliminada', 'success');
        });
      }
    });
  }

  guardarReceta(): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
    } else {
      this.save();
    }
    this.dialog.closeAll();
  }

  filtroReceta(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }

  abrirModal(receta?: Receta): void {
    if (receta) {
      this.receta = { ...receta };
      this.editar = true;
      this.idEditar = receta.idReceta;
    } else {
      this.receta = {} as Receta;
      this.editar = false;
      this.idEditar = null;
    }

    this.dialog.open(this.modalReceta, {
      width: '800px',
      disableClose: true
    });
  }

  compararCitas(c1: Cita, c2: Cita): boolean {
    return c1 && c2 ? c1.idCita === c2.idCita : c1 === c2;
  }

  abrirModalDetalles(receta: Receta): void {
    this.recetaSeleccionada = receta;
    this.dialog.open(this.modalDetalles, {
      width: '500px'
    });
  }

  cerrarModal(): void {
    this.dialog.closeAll();
    this.recetaSeleccionada = null;
  }
}
