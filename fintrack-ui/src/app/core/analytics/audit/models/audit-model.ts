export interface AuditModel {
    id: number;
    filename: string;
    uploadedAt: string;
    status: 'SUCCESS' | 'PARTIAL' | 'FAILED';
    totalRows: number;
    validRows: number;
    invalidRows: number;
}
