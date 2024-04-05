import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { TokenService } from '../token/token.service';
import { AuthResponse } from '../../interfaces/AuthResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';


  constructor( private http: HttpClient, private token: TokenService ) { }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/authenticate`, { username, password })
      .pipe(
        map(response =>{
          this.token.storeToken(response.token);
          this.token.storeUserId(response.userId);
          return response;
        })
      );
  }

  register(username: string, email: string, password: string, isAccountDisabled: boolean): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, { username, email, password, isAccountDisabled })
      .pipe(
        map((response) => {
          if(response.token){
            this.token.storeTokenAndUserId(response.token, response.userId);
          }
          return response;
        })
      );
  }

  logout() {
    this.token.clearTokenAndUserId();
  }

  isAuthenticated(): boolean {
    return !!this.token.getToken();
  }
}
