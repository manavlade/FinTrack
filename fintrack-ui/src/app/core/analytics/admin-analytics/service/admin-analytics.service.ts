import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment.development';
import { AnalyticsSummary, PageResponse, UploadJob, UploadTrendPoint, UserUploadStats } from '../models/admin-analytics';

@Injectable({
  providedIn: 'root'
})
export class AdminAnalyticsService {

  private readonly baseUrl = `${environment.apiUrl}/admin/analytics`;

  constructor(private readonly http: HttpClient) {}

  getSummary(from?: string, to?: string): Observable<AnalyticsSummary> {
    let params = new HttpParams();

    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);

    return this.http.get<AnalyticsSummary>(
      `${this.baseUrl}/summary`,
      { params }
    );
  }

  getUploadTrend(from?: string, to?: string): Observable<UploadTrendPoint[]> {
    let params = new HttpParams();

    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);

    return this.http.get<UploadTrendPoint[]>(
      `${this.baseUrl}/upload-trend`,
      { params }
    );
  }

  // USER STATS
  getUserStats(from?: string, to?: string): Observable<UserUploadStats[]> {
    let params = new HttpParams();

    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);

    return this.http.get<UserUploadStats[]>(
      `${this.baseUrl}/user-stats`,
      { params }
    );
  }

  // SUCCESS RATE
  getSuccessRate(): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}/success-rate`
    );
  }

  // UPLOAD HISTORY (PAGINATED)
  getUploadHistory(
    page: number,
    size: number,
    status?: string,
    from?: string,
    to?: string
  ): Observable<PageResponse<UploadJob>> {

    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (status) params = params.set('status', status);
    if (from) params = params.set('from', from);
    if (to) params = params.set('to', to);

    return this.http.get<PageResponse<UploadJob>>(
      `${this.baseUrl}/upload/analytics`,
      { params }
    );
  }
}