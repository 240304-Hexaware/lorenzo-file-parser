import { Component } from '@angular/core';
import { NgIf } from '@angular/common';
import { NgModel, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/authentication/authentication.service';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-authentication',
  standalone: true,
  imports: [ 
    NgIf, 
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule ,
    ReactiveFormsModule,
    MatIconModule
  ],
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.css'
})
export class AuthenticationComponent {
  showRegisterForm: boolean = false;
  loginForm: any = {}; 
  registerForm: any = {}; 
  hidePassword: boolean = true;
  loginFailed: boolean = false;
  registrationError: string = '';
  
  constructor(
    private authService: AuthService,
    private router: Router
    ) { }

  toggleForm() {
    this.showRegisterForm = !this.showRegisterForm;
    this.loginForm = {};
    this.registerForm = {};
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  onEnterKeyPressed(event: any) {
    if (event.target === document.activeElement && event.target === event.currentTarget) {
      event.preventDefault();
      this.login(event);
    }
  }

  login(event: Event) {
    const { username, password } = this.loginForm;
    this.authService.login(username, password).subscribe(
      response => {
        this.router.navigate(['/dashboard']);
      },
      error => {
        // console.error('Login failed:', error.error.errors);
        this.loginFailed = true;
      }
    );
  }

  register(event: Event) {
    event.preventDefault();
    const { username, email, password } = this.registerForm;
    if (this.registerForm.password !== this.registerForm.confirmPassword) {
      this.registerForm.confirmPassword = '';
      return;
    }
    const isAccountDisabled: boolean = false;
    this.authService.register(username, email, password, isAccountDisabled).subscribe(
      response => {
        this.router.navigate(['/dashboard']);
      },
      (errorResponse: HttpErrorResponse) => {
        if (errorResponse.status === 400) {
            const errorBody = errorResponse.error;
            if (errorBody && errorBody.errors) {
                this.registrationError = errorBody.errors.join(', ');
            } else {
                this.registrationError = 'An error occurred during registration.';
            }
        } else {
            this.registrationError = 'An unexpected error occurred.';
        }
    }
    );
  }
}
