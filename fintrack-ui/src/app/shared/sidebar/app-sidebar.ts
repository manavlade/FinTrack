import { Component } from '@angular/core';
import { HlmSidebarImports } from '@spartan-ng/helm/sidebar';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { BarChart, CloudUpload, FileText, History, LayoutDashboard, LucideAngularModule, User } from 'lucide-angular';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    HlmSidebarImports,
    RouterLink,
    RouterLinkActive,
    LucideAngularModule
  ],
  templateUrl: './app-sidebar.html',
})
export class AppSidebar {

  user = User;
  layoutDashboard = LayoutDashboard;
  history = History;
  upload = CloudUpload;
  uploadAnalytics = BarChart;
  reports = FileText;


  protected readonly _items = [
    {
      title: 'Upload History',
      url: 'upload/history',
    },
    {
      title: 'Upload',
      url: '/upload'
    },
    {
      title: 'Upload Analytics',
      url: 'upload/analytics'
    },
    {
      title: 'Admin Analytics',
      url: 'upload/admin/analytics',
    },
    {
      title: 'Profile',
      url: '/profile',
    },
  ];
}