import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Chambre from './chambre';
import Reservation from './reservation';
import Client from './client';
import Gestionnaire from './gestionnaire';
import Options from './options';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="chambre/*" element={<Chambre />} />
        <Route path="reservation/*" element={<Reservation />} />
        <Route path="client/*" element={<Client />} />
        <Route path="gestionnaire/*" element={<Gestionnaire />} />
        <Route path="options/*" element={<Options />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
