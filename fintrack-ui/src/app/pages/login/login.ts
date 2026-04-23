import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  email = '';
  password = '';
  errorMessage = '';

  private authService = inject(AuthService);
  private router = inject(Router);

  onSubmit(event: Event) {
    event.preventDefault();
    this.errorMessage = '';
    this.authService.login({ employeeEmail: this.email, employeePassword: this.password }).subscribe({
      next: (res) => {
        if (res && res.token) {
          // Save the token if needed
          // localStorage.setItem('token', res.token);
          alert('Logged in successfully!');
        }
      },
      error: (err) => {
        this.errorMessage = 'Invalid email or password';
      }
    });
  }
}
