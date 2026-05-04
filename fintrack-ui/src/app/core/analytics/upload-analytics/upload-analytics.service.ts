import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { PageResponse, UploadAnalyticsFilters, UploadJob } from '../uppload/models/audit-model';

@Injectable({
  providedIn: 'root'
})
export class UploadAnalyticsService {

  private readonly apiUrl = `${environment.apiUrl}/employees/upload/analytics`;

  constructor(private readonly http: HttpClient) {}

  getAnalytics(filters: UploadAnalyticsFilters): Observable<PageResponse<UploadJob>> {

    let params = new HttpParams()
      .set('page', filters.page)
      .set('size', filters.size);

    if (filters.status) {
      params = params.set('status', filters.status);
    }

    if (filters.from) {
      params = params.set('from', filters.from);
    }

    if (filters.to) {
      params = params.set('to', filters.to);
    }

    return this.http.get<PageResponse<UploadJob>>(this.apiUrl, { params });
  }
}