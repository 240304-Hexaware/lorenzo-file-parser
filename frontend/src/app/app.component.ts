import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthService } from './services/authentication/authentication.service';
import { FooterComponent } from './components/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AuthenticationComponent, NavbarComponent, FooterComponent ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.isAuthenticated();
  }

  title = 'frontend';
}
