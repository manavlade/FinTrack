import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { AuthRequest, AuthResponse } from '../models/auth-request';
import { tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class Auth {

  private readonly apiUrl = `${environment.apiUrl}/auth`;

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) { }

  login(credentials: AuthRequest) {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          this.setToken(response.token);
          this.router.navigate(['/'])
        })
      );
  }

  signUp(credentials: AuthRequest) {
    return this.http.post<any>(`${this.apiUrl}/signup`, credentials)
      .pipe(
        tap(() => this.router.navigate(['/login']))
      );
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getToken(): string {
    return localStorage.getItem('token') || '';
  }

  setToken(token: string) {
    localStorage.setItem('token', token);
  }

  getUserRole(token: string) {
    return localStorage.getItem('role') || "";
  }

}
