import { Injectable } from '@angular/core';
import { environment } from '../../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadHistory } from '../models/audit-model';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  private readonly apiUrl = `${environment.apiUrl}/employees/upload/history`;

  constructor(private readonly http: HttpClient) { }

  getUploadHistory(): Observable<UploadHistory[]> {
    return this.http.get<UploadHistory[]>(
      this.apiUrl
    )
  }

}
