import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Medicamento } from '../../model/medicamento.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MedicamentoService } from '../../services/medicamento';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

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

  // ✅ Para la imagen
  selectedFile: File | null = null;
  imagenPreview: string | null = null;

  dataSource!: MatTableDataSource<Medicamento>;
  mostrarColumnas: String[] = [
    'idMedicamento',
    'nombre',
    'dosis',
    'descripcion',
    'precio',
    'stock',
    'portada',
    'acciones'
  ];

  @ViewChild('formularioMedicamento') formularioMedicamento!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private medicamentoService: MedicamentoService,
    private http: HttpClient // ✅ Ahora sí puedes usar this.http
  ) {}

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
      if (result.isConfirmed && this.medicamento.idMedicamento) {
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

  // ✅ Métodos para la imagen
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];

      // Mostrar preview local
      const reader = new FileReader();
      reader.onload = () => {
        this.imagenPreview = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  subirImagen(): void {
    if (!this.selectedFile) {
      Swal.fire('Error', 'Debe seleccionar un archivo antes de subirlo', 'error');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    if (this.medicamento.portada) {
      formData.append('oldImage', this.medicamento.portada);
    }

    this.http
      .post<{ ruta: string }>('http://192.168.4.79:8080/api/upload-portada', formData)
      .subscribe({
        next: (res: { ruta: string }) => {
          this.medicamento.portada = res.ruta;
          this.imagenPreview = 'http://192.168.4.79:8080/' + res.ruta;
          this.selectedFile = null;
          Swal.fire('Éxito', 'La imagen se subió correctamente', 'success');
        },
        error: (err: unknown) => {
          console.error(err);
          Swal.fire('Error', 'No se pudo subir la imagen', 'error');
        }
      });
  }
}
