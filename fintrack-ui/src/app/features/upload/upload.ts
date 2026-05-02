import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { UploadResponse } from '../../core/upload/model/upload-model';
import { ExcelUpload } from '../../core/upload/service/excel-upload';
import { HotToastService } from '@ngxpert/hot-toast';

@Component({
  selector: 'app-upload',
  imports: [CommonModule],
  templateUrl: './upload.html',
  styleUrl: './upload.css',
})
export class Upload {

  selectedFile: File | null = null;
  response: UploadResponse | null = null;
  
  loading = signal(false);
  errorMessage = "";

  constructor(
    private readonly UploadService: ExcelUpload,
    private readonly toastService: HotToastService
  ) { }

  onFileSelected(event: Event) {

    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) return;


    const file = input.files[0];

    const allowedTypes = [
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'application/vnd.ms-excel'
    ]

    if (!allowedTypes.includes(file.type)) {
       this.errorMessage = 'Please upload a valid Excel file';
    }

    this.selectedFile = file;
    this.errorMessage = '';
  }

  uploadFile() {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select a file first';
      return;
    }

    this.loading.set(true);
    this.errorMessage = '';

    this.UploadService.upload(this.selectedFile).subscribe({
      next: (res: UploadResponse) => {
        this.response = res;
        this.loading.set(false)
      },

      error: (err) => {
        this.errorMessage = "Upload Failed";
        this.loading.set(false);
      }
    })
  }

}
