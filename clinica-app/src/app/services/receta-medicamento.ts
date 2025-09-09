import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RecetaMedicamento } from '../model/recetamedicamento.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecetaMedicamentoService {

  private baseUrl = "http://192.168.4.140:8080/receta-medicamentos";

  constructor(private http: HttpClient) {}

  findAll(): Observable<RecetaMedicamento[]> {
    return this.http.get<RecetaMedicamento[]>(this.baseUrl);
  }

  findOne(id: number): Observable<RecetaMedicamento> {
    return this.http.get<RecetaMedicamento>(`${this.baseUrl}/${id}`);
  }

  save(recetaMedicamento: RecetaMedicamento): Observable<RecetaMedicamento> {
    return this.http.post<RecetaMedicamento>(this.baseUrl, recetaMedicamento);
  }

  update(id: number, recetaMedicamento: RecetaMedicamento): Observable<RecetaMedicamento> {
    return this.http.put<RecetaMedicamento>(`${this.baseUrl}/${id}`, recetaMedicamento);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

}
