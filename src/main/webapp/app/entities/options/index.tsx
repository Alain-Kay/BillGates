import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Options from './options';
import OptionsDetail from './options-detail';
import OptionsUpdate from './options-update';
import OptionsDeleteDialog from './options-delete-dialog';

const OptionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Options />} />
    <Route path="new" element={<OptionsUpdate />} />
    <Route path=":id">
      <Route index element={<OptionsDetail />} />
      <Route path="edit" element={<OptionsUpdate />} />
      <Route path="delete" element={<OptionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OptionsRoutes;
