import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['']);
      return false;
    }
    return true;
  }
}
