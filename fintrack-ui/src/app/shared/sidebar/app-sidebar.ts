import { Component } from '@angular/core';
import { HlmSidebarImports } from '@spartan-ng/helm/sidebar';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    HlmSidebarImports,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './app-sidebar.html',
})
export class AppSidebar {

  protected readonly _items = [
    {
      title: 'Upload History',
      url: 'upload/history',
    },
    {
      title: 'Upload',
      url: 'upload'
    },
    {
      title: 'Reports',
      url: 'reports',
    },
    {
      title: 'Profile',
      url: '/profile',
    },
  ];
}