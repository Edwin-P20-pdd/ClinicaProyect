import { Component, OnInit } from '@angular/core';
import { GuestCarritoService } from '../../services/guest-carrito';
import { MedicamentoService } from '../../services/medicamento';
import { Medicamento } from '../../model/medicamento.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-medicamentos-list',
  standalone: false,
  templateUrl: './medicamentos-list.html',
  styleUrls: ['./medicamentos-list.css']
})
export class MedicamentosListComponent implements OnInit {

  loading = false;
  medicamentos: Medicamento[] = [];

  constructor(
    private carritoService: GuestCarritoService,
    private medicamentoService: MedicamentoService,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.carritoService.createOrGet().subscribe();
    this.medicamentoService.findAll().subscribe({
      next: res => { this.medicamentos = res; this.loading = false; },
      error: _ => { this.loading = false; }
    });
  }

  add(medicamento: Medicamento) {
    this.carritoService.addItem(medicamento.idMedicamento, 1).subscribe({
      next: _ => this.snack.open('Medicamento agregado al carrito', 'OK', { duration: 1500 }),
      error: err => Swal.fire('Error', err?.error?.message || 'No se pudo agregar', 'error')
    });
  }
}
