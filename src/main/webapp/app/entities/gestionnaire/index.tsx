import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gestionnaire from './gestionnaire';
import GestionnaireDetail from './gestionnaire-detail';
import GestionnaireUpdate from './gestionnaire-update';
import GestionnaireDeleteDialog from './gestionnaire-delete-dialog';

const GestionnaireRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gestionnaire />} />
    <Route path="new" element={<GestionnaireUpdate />} />
    <Route path=":id">
      <Route index element={<GestionnaireDetail />} />
      <Route path="edit" element={<GestionnaireUpdate />} />
      <Route path="delete" element={<GestionnaireDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GestionnaireRoutes;
