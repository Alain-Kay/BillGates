import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGestionnaire } from 'app/shared/model/gestionnaire.model';
import { getEntities } from './gestionnaire.reducer';

export const Gestionnaire = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const gestionnaireList = useAppSelector(state => state.gestionnaire.entities);
  const loading = useAppSelector(state => state.gestionnaire.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="gestionnaire-heading" data-cy="GestionnaireHeading">
        <Translate contentKey="billGatesApp.gestionnaire.home.title">Gestionnaires</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="billGatesApp.gestionnaire.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/gestionnaire/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="billGatesApp.gestionnaire.home.createLabel">Create new Gestionnaire</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gestionnaireList && gestionnaireList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="billGatesApp.gestionnaire.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="billGatesApp.gestionnaire.nomGestionnaire">Nom Gestionnaire</Translate>
                </th>
                <th>
                  <Translate contentKey="billGatesApp.gestionnaire.postGestionnaire">Post Gestionnaire</Translate>
                </th>
                <th>
                  <Translate contentKey="billGatesApp.gestionnaire.numeroGestionnaire">Numero Gestionnaire</Translate>
                </th>
                <th>
                  <Translate contentKey="billGatesApp.gestionnaire.emailGestionnaire">Email Gestionnaire</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gestionnaireList.map((gestionnaire, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gestionnaire/${gestionnaire.id}`} color="link" size="sm">
                      {gestionnaire.id}
                    </Button>
                  </td>
                  <td>{gestionnaire.nomGestionnaire}</td>
                  <td>{gestionnaire.postGestionnaire}</td>
                  <td>{gestionnaire.numeroGestionnaire}</td>
                  <td>{gestionnaire.emailGestionnaire}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/gestionnaire/${gestionnaire.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/gestionnaire/${gestionnaire.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/gestionnaire/${gestionnaire.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="billGatesApp.gestionnaire.home.notFound">No Gestionnaires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Gestionnaire;
