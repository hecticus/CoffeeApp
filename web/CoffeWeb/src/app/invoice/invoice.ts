import { BaseModel } from '../common/models/base.model';
import { CommonModule } from '@angular/common';

export class Invoice extends BaseModel
{
    idInvoice: number;
    statusInvoice: string;
    duedate_invoice:Date;
}