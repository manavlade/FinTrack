import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment.development';
import { UploadJob } from '../models/audit-model';

@Injectable({
  providedIn: 'root'
})
export class UploadHistoryService {

  private readonly apiUrl = `${environment.apiUrl}/employees/upload/history`;

  constructor(private readonly http: HttpClient) {}

  getHistory(): Observable<UploadJob[]> {
    return this.http.get<UploadJob[]>(this.apiUrl);
  }
}