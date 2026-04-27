import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Auth } from '../../../core/auth/services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login implements OnInit {

  loginForm!: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly auth: Auth
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      employeeEmail: ['', [Validators.required, Validators.email]],
      employeePassword: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.auth.login(this.loginForm.value as any).subscribe({
      next: () => console.log('Login success'),
      error: (err) => console.error('Login failed', err)
    });
  }
}