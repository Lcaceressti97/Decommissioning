
export interface ExemptInvoiceApprovals {
    id?: number;
    idInvoice: number;
    commentApproval: string;
    userApproved: string;
    approvalDate: Date;
    status: number;
}