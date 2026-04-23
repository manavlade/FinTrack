import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-signup',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './signup.html',
  styleUrl: './signup.css',
})
export class Signup {
  email = '';
  password = '';
  errorMessage = '';

  private authService = inject(AuthService);
  private router = inject(Router);

  onSubmit(event: Event) {
    event.preventDefault();
    this.errorMessage = '';
    
    const userPayload = {
      employeeEmail: this.email,
      employeePassword: this.password,
      employeeRole: 'USER'
    };

    this.authService.signup(userPayload).subscribe({
      next: (res) => {
        alert('Account created successfully! You can now log in.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = 'Failed to create account. Email may already be in use.';
      }
    });
  }
}
