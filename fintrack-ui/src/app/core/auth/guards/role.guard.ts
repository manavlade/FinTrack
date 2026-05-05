import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { Auth } from "../services/auth";


export const RoleGuard: CanActivateFn = (route) => {

    const authService = inject(Auth);
    const router = inject(Router);

    const expectedRole = route?.data?.['role'];

    if (authService.getRole() === expectedRole) {
        return true;
    }

    router.navigate(['/unauthorized']);
    return false;
}