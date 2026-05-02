import { Routes } from '@angular/router';
import { authGuard } from './core/auth/guards/auth.guard';
import { Home } from '../app/shared/components/home/home';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () =>
            import('../app/shared/components/home/home')
                .then(m => Home)
    },

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
        path: 'upload',
        loadComponent: () =>
            import('./features/upload/upload')
                .then(m => m.Upload),
        canActivate: [authGuard]
    }
];
