import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Factura } from '../model/factura.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private baseUrl = "http://192.168.4.140:8080/api/facturas";

  constructor(private http: HttpClient) { }

  findAll(): Observable<Factura[]> {
    return this.http.get<Factura[]>(this.baseUrl);
  }

  findOne(id: number): Observable<Factura> {
    return this.http.get<Factura>(`${this.baseUrl}/${id}`);
  }

  save(factura: Factura): Observable<Factura> {
    return this.http.post<Factura>(this.baseUrl, factura);
  }

  update(id: number, factura: Factura): Observable<Factura> {
    return this.http.put<Factura>(`${this.baseUrl}/${id}`, factura);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
