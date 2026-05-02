export interface UploadResponse {
    successCount: number;
    errorCount: number;
    warnings: string[];
    errors: string[];
    message: string
}