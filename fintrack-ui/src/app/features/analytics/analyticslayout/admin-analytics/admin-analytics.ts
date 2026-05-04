import { Component, OnInit, signal } from '@angular/core';
import { AnalyticsSummary, UploadJob, UploadTrendPoint, UserUploadStats } from '../../../../core/analytics/admin-analytics/models/admin-analytics';
import { AdminAnalyticsService } from '../../../../core/analytics/admin-analytics/service/admin-analytics.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-analytics',
  imports: [CommonModule],
  templateUrl: './admin-analytics.html',
  styleUrl: './admin-analytics.css',
})
export class AdminAnalytics implements OnInit {
  summary = signal<AnalyticsSummary | null>(null);
  trend = signal<UploadTrendPoint[] | null>(null);
  userStats = signal<UserUploadStats[] | null>(null);
  history = signal<UploadJob[] | null>(null);

  loading = signal(false);

  fromDate = signal<string | null>(null);
  toDate = signal<string | null>(null);
  successRate = signal(0);

  constructor(private readonly adminService: AdminAnalyticsService) { }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.loadSummary();
    this.loadTrend();
    this.loadUserStats();
    this.loadHistory();
  }

  loadSummary() {
    this.adminService.getSummary(
      this.fromDate() ?? undefined,
      this.toDate() ?? undefined
    ).subscribe(res => {
      this.summary.set(res);

      // calculate success rate
      const total = res.totalUploads || 0;
      const success = res.success || 0;

      this.successRate.set(total ? (success / total) * 100 : 0);
    });
  }

  loadTrend() {
    this.adminService.getUploadTrend(
      this.fromDate() ?? undefined,
      this.toDate() ?? undefined
    )
      .subscribe(res => this.trend.set(res));
  }


  loadUserStats() {
    this.adminService.getUserStats(
      this.fromDate() ?? undefined,
      this.toDate() ?? undefined
    )
      .subscribe(res => this.userStats.set(res));
  }

  loadHistory() {
    this.loading.set(true);

    this.adminService.getUploadHistory(0, 20)
      .subscribe({
        next: res => {
          this.history.set(res.content);
          this.loading.set(false);
        },
        error: () => this.loading.set(false)
      });
  }
}
