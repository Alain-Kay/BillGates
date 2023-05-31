import { IReservation } from 'app/shared/model/reservation.model';
import { IClient } from 'app/shared/model/client.model';
import { Disponibilite } from 'app/shared/model/enumerations/disponibilite.model';

export interface IChambre {
  id?: number;
  numeroChambre?: string | null;
  prixChambre?: number | null;
  disponibilite?: Disponibilite | null;
  images?: string | null;
  reservations?: IReservation[] | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IChambre> = {};
