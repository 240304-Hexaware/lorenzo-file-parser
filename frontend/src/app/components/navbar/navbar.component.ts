import { Component } from '@angular/core';
import { AuthService } from '../../services/authentication/authentication.service';
import { NgIf } from '@angular/common';
import { Router } from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [ NgIf, MatToolbarModule ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(public authService: AuthService, private router: Router) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['']);
  }
}
