export interface AnalyticsSummary {
  totalUploads: number;
  success: number;
  failed: number;
  partial: number;
  totalRows: number;
  validRows: number;
  invalidRows: number;
}

export interface UploadTrendPoint {
  date: string;
  count: number;
}

export interface UserUploadStats {
  username: string;
  uploads: number;
}

export interface UploadJob {
  id: number;
  filename: string;
  status: 'SUCCESS' | 'FAILED' | 'PARTIAL';
  uploadedAt: string;
  totalRows: number;
  validRows: number;
  invalidRows: number;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}