import { Injectable } from '@angular/core';
import { environmet } from '../../environments.ts/environments';
import { HttpClient, HttpParams } from '@angular/common/http';
import { getCartToken } from '../core/cart-token';
import { Observable } from 'rxjs';
import { Carrito } from '../model/carrito.model';

@Injectable({
  providedIn: 'root'
})
export class GuestCarritoService {

  private base = `${environmet.baseURL}/guest/cart`;

  constructor(private http: HttpClient){ }

  private paramsWithToken():{ params: HttpParams } {
    const token = getCartToken();
    return { params: new HttpParams().set('token', token)};
  }

  createOrGet(): Observable<Carrito>{
    return this.http.post<Carrito>(this.base, { }, this.paramsWithToken());
  }

  get(): Observable<Carrito>{
    return this.http.get<Carrito>(this.base, this.paramsWithToken());
  }

  addItem(medicamentoId: number, cantidad: number):Observable<Carrito>{
    const body = { medicamentoId, cantidad }
    return this.http.post<Carrito>(`${this.base}/items`, body, this.paramsWithToken());
  }

  updateItem(carritoItemId: number, cantidad: number):Observable<Carrito>{
    const body = { cantidad };
    return this.http.put<Carrito>(`${this.base}/items/${carritoItemId}`, body, this.paramsWithToken());
  }

  removeItem(carritoItemId: number){
    return this.http.delete<void>(`${this.base}/items/${carritoItemId}`, this.paramsWithToken());
  }

  clear(){
    return this.http.delete<void>(`${this.base}/clear`, this.paramsWithToken());
  }

  
}
