import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PacienteComponent } from './components/paciente/paciente';
import { DoctorComponent } from './components/doctor/doctor';
import { RecetaComponent } from './components/receta/receta';
import { CitaComponent } from './components/cita/cita';
import { MedicamentoComponent } from './components/medicamento/medicamento';
import { RecetaMedicamentoComponent } from './components/receta-medicamento/receta-medicamento';

const routes: Routes = [
  {path: '', redirectTo: 'pacientes', pathMatch: 'full'},
  {path: 'pacientes', component: PacienteComponent },
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
