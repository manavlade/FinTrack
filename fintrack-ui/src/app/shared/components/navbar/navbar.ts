import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Auth } from '../../../core/auth/services/auth';
import { User, LucideAngularModule } from "lucide-angular"
import {HlmDropdownMenuImports} from "../../../../../libs/ui/dropdown-menu/src"


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, LucideAngularModule, HlmDropdownMenuImports],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  constructor (public auth: Auth) {}

  readonly user = User;

}
