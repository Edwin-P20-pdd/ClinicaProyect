import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Receta } from '../model/receta.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecetaService {

  private baseUrl = "http://192.168.4.164:8080/recetas";

  constructor(private http: HttpClient) { }

  findAll(): Observable<Receta[]> {
    return this.http.get<Receta[]>(this.baseUrl);
  }

  findOne(id: number): Observable<Receta> {
    return this.http.get<Receta>(`${this.baseUrl}/${id}`);
  }

  save(receta: Receta): Observable<Receta> {
    return this.http.post<Receta>(this.baseUrl, receta);
  }

  update(id: number, receta: Receta): Observable<Receta> {
    return this.http.put<Receta>(`${this.baseUrl}/${id}`, receta);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

}
