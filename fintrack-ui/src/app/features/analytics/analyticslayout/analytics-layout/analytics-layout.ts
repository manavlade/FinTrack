import { Component } from '@angular/core';
import { HlmSidebarImports } from '@spartan-ng/helm/sidebar';
import { AppSidebar } from '../../../../shared/sidebar/app-sidebar';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-analytics-layout',
  imports: [RouterOutlet, AppSidebar, HlmSidebarImports],
  template: `
    <app-sidebar>
      <main hlmSidebarInset>
        <header class="flex h-12 items-center justify-between px-4">
          <button hlmSidebarTrigger>
            <span class="sr-only">Toggle Sidebar</span>
          </button>
        </header>

        <router-outlet />
      </main>
    </app-sidebar>
  `,
  styleUrl: './analytics-layout.css',
})
export class AnalyticsLayout { }
