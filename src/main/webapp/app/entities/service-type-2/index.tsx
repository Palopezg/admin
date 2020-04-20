import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceType2 from './service-type-2';
import ServiceType2Detail from './service-type-2-detail';
import ServiceType2Update from './service-type-2-update';
import ServiceType2DeleteDialog from './service-type-2-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceType2DeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceType2Update} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceType2Update} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceType2Detail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceType2} />
    </Switch>
  </>
);

export default Routes;
