import { Injectable } from '@angular/core';
import { environment } from '../../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuditModel } from '../models/audit-model';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  private readonly apiUrl = `${environment.apiUrl}/employees`;

  constructor(private readonly http: HttpClient) {}

  // Upload Excel file
  uploadFile(file: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/upload`, file);
  }

  // Get history
  getUploadHistory(): Observable<AuditModel[]> {
    return this.http.get<AuditModel[]>(`${this.apiUrl}/history`);
  }
}
