
export type UploadStatus = 'SUCCESS' | 'FAILED' | 'PARTIAL'

export interface UploadJob {
  id: number;
  filename: string;
  uploadedAt: string; 
  status: 'SUCCESS' | 'FAILED' | 'PARTIAL'
  totalRows: number;
  validRows: number;
  invalidRows: number;
  processingNotes: string;
}


export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number; 
}

export interface UploadAnalyticsFilters {
  status?: UploadStatus;
  from?: string;
  to?: string;
  page: number;
  size: number;
}