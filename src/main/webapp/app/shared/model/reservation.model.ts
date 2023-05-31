import { IChambre } from 'app/shared/model/chambre.model';
import { IOptions } from 'app/shared/model/options.model';
import { IGestionnaire } from 'app/shared/model/gestionnaire.model';

export interface IReservation {
  id?: number;
  numeroReservation?: number | null;
  heureDebut?: string | null;
  heureFin?: string | null;
  chambre?: IChambre | null;
  options?: IOptions | null;
  gestionnaire?: IGestionnaire | null;
}

export const defaultValue: Readonly<IReservation> = {};
