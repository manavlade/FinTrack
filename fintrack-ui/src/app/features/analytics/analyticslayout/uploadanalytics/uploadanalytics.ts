import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { HlmButton } from '@spartan-ng/helm/button';
import { HlmSkeleton } from '@spartan-ng/helm/skeleton';
import { UploadJob, UploadStatus } from '../../../../core/analytics/uppload/models/audit-model';
import { UploadAnalyticsService } from '../../../../core/analytics/upload-analytics/upload-analytics.service';
import { Router, ActivatedRoute } from '@angular/router';
import { computed } from '@angular/core';

@Component({
  selector: 'app-uploadanalytics',
  imports: [CommonModule, HlmSkeleton, HlmButton],
  templateUrl: './uploadanalytics.html',
  styleUrl: './uploadanalytics.css',
})
export class Uploadanalytics implements OnInit {
  data = signal<UploadJob[]>([]);
  loading = signal(false)
  totalPages = signal(0);
  currentPage = signal(0);
  totalElements = signal(0);

  //filters
  status = signal<UploadStatus | undefined>(undefined);
  from = signal<string | undefined>(undefined);
  to = signal<string | undefined>(undefined);

  constructor(
    private readonly service: UploadAnalyticsService,
    private readonly router: Router,
    private readonly route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.status.set(params["status"] as UploadStatus);
      this.from.set(params["from"]);
      this.to.set(params["to"]);
      this.currentPage.set(params["page"] || 0);

      this.fetch();
    })
  }

  fetch() {
    this.loading.set(true);

    this.service.getAnalytics({
      page: this.currentPage(),
      size: 10,
      status: this.status(),
      from: this.from(),
      to: this.to(),
    }).subscribe({
      next: (res) => {
        this.data.set(res.content);
        this.totalPages.set(res.totalPages);
        this.totalElements.set(res.totalElements);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  stats = computed(() => {
    const jobs = this.data();

    return {
      success: jobs.filter(j => j.status === 'SUCCESS').length,
      failed: jobs.filter(j => j.status === 'FAILED').length,
      partial: jobs.filter(j => j.status === 'PARTIAL').length
    };
  });

  private updateUrl() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        status: this.status(),
        from: this.from(),
        to: this.to(),
        page: this.currentPage()
      },
      queryParamsHandling: 'merge'
    });
  }

  applyFilters() {
    this.currentPage.set(0);
    this.updateUrl();
  }

  resetFilters() {
    this.status.set(undefined);
    this.from.set(undefined);
    this.to.set(undefined);
    this.currentPage.set(0);

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {}
    });
  }

  nextPage() {
    if (this.currentPage() < this.totalPages() - 1) {
      this.currentPage.update(p => p + 1);
      this.fetch();
    }
  }

  prevPage() {
    if (this.currentPage() > 0) {
      this.currentPage.update(p => p - 1);
      this.fetch();
    }
  }

}
