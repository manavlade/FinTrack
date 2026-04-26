import { Routes } from '@angular/router';
import { authGuard } from './core/auth/guards/auth.guard';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () =>
            import('../app/features/auth/login/login')
                .then(m => m.Login)
    },
    {
        path: 'sign-up',
        loadComponent: () =>
            import('../app/features/auth/sign-up/sign-up')
                .then(m => m.SignUp)
    },

    {
        path: '**',
        redirectTo: 'login',
        canActivate: [authGuard]
    }
];
