import { IReservation } from 'app/shared/model/reservation.model';

export interface IOptions {
  id?: number;
  nomOptions?: string | null;
  reservations?: IReservation[] | null;
}

export const defaultValue: Readonly<IOptions> = {};
