export interface UploadHistory{
    fileName: string;
    uploadedAt: string;
    status: string;
    totalRows: number;
    validRows: number;
    InvalidRows: number;
    processingNotes: string;
}