import { BaseModel } from '../common/models/base.model';
import { CommonModule } from '@angular/common';

import { ProviderType } from '../providerType/providerType';


export class Provider extends BaseModel
{
    idProvider: number;
    fullNameProvider: string;
    statusProvider: string;
    phoneNumberProvider: number;
    addressProvider:Text;
    emailProvider:string;
    contactNameProvider:string;
    providerType: ProviderType;
    identificationDocProvider: string;
}