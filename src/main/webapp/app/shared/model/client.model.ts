import { IChambre } from 'app/shared/model/chambre.model';

export interface IClient {
  id?: number;
  nomClient?: string | null;
  postNom?: string | null;
  numeroClient?: string | null;
  emailClient?: string | null;
  chambres?: IChambre[] | null;
}

export const defaultValue: Readonly<IClient> = {};
