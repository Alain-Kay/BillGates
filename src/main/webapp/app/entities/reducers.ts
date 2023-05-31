import chambre from 'app/entities/chambre/chambre.reducer';
import reservation from 'app/entities/reservation/reservation.reducer';
import client from 'app/entities/client/client.reducer';
import gestionnaire from 'app/entities/gestionnaire/gestionnaire.reducer';
import options from 'app/entities/options/options.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  chambre,
  reservation,
  client,
  gestionnaire,
  options,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
