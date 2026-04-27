import { Component } from '@angular/core';
import { Auth } from '../../../core/auth/services/auth';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  constructor(public authService: Auth) { }

  isDropdownOpen = false;

  get isLoggedIn(): boolean {
    return this.authService.isAuthenticated();
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  logout() {
    this.authService.logout();
  }

}
