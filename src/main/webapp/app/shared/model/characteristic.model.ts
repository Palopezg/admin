import { IServiceType } from 'app/shared/model/service-type.model';

export interface ICharacteristic {
  id?: number;
  characteristicId?: string;
  serviceId?: string;
  descripcion?: string;
  serviceType?: IServiceType;
}

export const defaultValue: Readonly<ICharacteristic> = {};
