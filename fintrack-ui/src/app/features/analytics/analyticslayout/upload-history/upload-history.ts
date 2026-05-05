import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgIcon, provideIcons } from '@ng-icons/core';
import { lucideFileSpreadsheet, lucideUpload } from '@ng-icons/lucide';

import { HlmButton } from '@spartan-ng/helm/button';
import { HlmEmptyImports } from '@spartan-ng/helm/empty';
import { HlmIcon } from '@spartan-ng/helm/icon';
import { UploadJob } from '../../../../core/analytics/uppload/models/audit-model';
import { UploadHistoryService } from '../../../../core/analytics/uppload/service/upload-history.service.ts';
import { HlmSkeleton } from '@spartan-ng/helm/skeleton';
import { RouterModule } from '@angular/router';
import { computed } from '@angular/core';

@Component({
  selector: 'app-upload-history',
  standalone: true,
  imports: [
    CommonModule,
    NgIcon,
    HlmButton,
    HlmEmptyImports,
    HlmIcon,
    HlmSkeleton,
    RouterModule
  ],
  providers: [
    provideIcons({ lucideFileSpreadsheet, lucideUpload })
  ],
  templateUrl: './upload-history.html',
  styleUrl: './upload-history.css'
})
export class UploadHistory implements OnInit {

  history = signal<UploadJob[]>([]);
  loading = signal(false);
  errorMessage = "";
  searchTerm = signal("");
  statusFilter = signal("ALL");

  constructor(private readonly historyService: UploadHistoryService) { }

  ngOnInit(): void {
    this.fetchHistory();
  }

  fetchHistory() {
    this.loading.set(true);
    this.errorMessage = "";

    this.historyService.getHistory().subscribe({
      next: (res) => {
        this.history.set(res);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage = "Failed to load upload history";
        this.loading.set(false);
      }
    });
  }

  filteredUploadHistory = computed(() => {
    const term = this.searchTerm().toLowerCase();
    const status = this.statusFilter();

    return this.history().filter(job => {
      const matchesSearch =
        job.filename.toLowerCase().includes(term) ||
        job.id.toString().includes(term);

      const matchesStatus =
        status === "ALL" || job.status === status;

      return matchesSearch && matchesStatus;
    });
  });
}