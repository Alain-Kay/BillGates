import { IReservation } from 'app/shared/model/reservation.model';

export interface IGestionnaire {
  id?: number;
  nomGestionnaire?: string | null;
  postGestionnaire?: string | null;
  numeroGestionnaire?: string | null;
  emailGestionnaire?: string | null;
  reservations?: IReservation[] | null;
}

export const defaultValue: Readonly<IGestionnaire> = {};
