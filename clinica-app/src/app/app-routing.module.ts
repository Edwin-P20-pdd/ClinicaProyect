import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PacienteComponent } from './components/paciente/paciente';
import { CarritoComponent } from './components/carrito/carrito';
import { DoctorComponent } from './components/doctor/doctor';
import { RecetaComponent } from './components/receta/receta';
import { CitaComponent } from './components/cita/cita';
import { MedicamentoComponent } from './components/medicamento/medicamento';
import { RecetaMedicamentoComponent } from './components/receta-medicamento/receta-medicamento';
import { MedicamentosListComponent } from './components/medicamentos-list/medicamentos-list';

const routes: Routes = [
  {path: '', redirectTo: 'medicamentos1', pathMatch: 'full'},
  {path: 'medicamentos1', component: MedicamentosListComponent},
  {path: 'pacientes', component: PacienteComponent },
  {path: 'carrito', component: CarritoComponent},
  {path: 'doctores', component: DoctorComponent},
  {path: 'recetas', component: RecetaComponent},
  {path: 'citas', component: CitaComponent},
  {path: 'medicamentos', component: MedicamentoComponent},
  {path: 'recetasMedicamentos', component: RecetaMedicamentoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
