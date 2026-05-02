import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import {  UploadResponse } from '../model/upload-model';

@Injectable({
  providedIn: 'root',
})
export class ExcelUpload {

  private readonly apiUrl = `${environment.apiUrl}/employees/upload`;

  constructor(
    private readonly http: HttpClient
  ) { }

  upload(file: File) {
    const formData = new FormData();
    formData.append("file", file);

    return this.http.post<UploadResponse>(this.apiUrl, formData);
  }
}
