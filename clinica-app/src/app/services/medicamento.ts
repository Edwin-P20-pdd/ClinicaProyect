import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Medicamento } from '../model/medicamento.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MedicamentoService {

  //private baseUrl = 'http://192.168.4.164:8080/medicamentos';
  private baseUrl = 'http://localhost:8080/medicamentos';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Medicamento[]> {
    return this.http.get<Medicamento[]>(this.baseUrl);
  }

  findOne(id: number): Observable<Medicamento> {
    return this.http.get<Medicamento>(`${this.baseUrl}/${id}`);
  }

  save(medicamento: Medicamento): Observable<Medicamento> {
    return this.http.post<Medicamento>(this.baseUrl, medicamento);
  }

  update(id: number, medicamento: Medicamento): Observable<Medicamento> {
    return this.http.put<Medicamento>(`${this.baseUrl}/${id}`, medicamento);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
