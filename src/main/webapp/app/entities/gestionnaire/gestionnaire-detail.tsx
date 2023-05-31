import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gestionnaire.reducer';

export const GestionnaireDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gestionnaireEntity = useAppSelector(state => state.gestionnaire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gestionnaireDetailsHeading">
          <Translate contentKey="billGatesApp.gestionnaire.detail.title">Gestionnaire</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{gestionnaireEntity.id}</dd>
          <dt>
            <span id="nomGestionnaire">
              <Translate contentKey="billGatesApp.gestionnaire.nomGestionnaire">Nom Gestionnaire</Translate>
            </span>
          </dt>
          <dd>{gestionnaireEntity.nomGestionnaire}</dd>
          <dt>
            <span id="postGestionnaire">
              <Translate contentKey="billGatesApp.gestionnaire.postGestionnaire">Post Gestionnaire</Translate>
            </span>
          </dt>
          <dd>{gestionnaireEntity.postGestionnaire}</dd>
          <dt>
            <span id="numeroGestionnaire">
              <Translate contentKey="billGatesApp.gestionnaire.numeroGestionnaire">Numero Gestionnaire</Translate>
            </span>
          </dt>
          <dd>{gestionnaireEntity.numeroGestionnaire}</dd>
          <dt>
            <span id="emailGestionnaire">
              <Translate contentKey="billGatesApp.gestionnaire.emailGestionnaire">Email Gestionnaire</Translate>
            </span>
          </dt>
          <dd>{gestionnaireEntity.emailGestionnaire}</dd>
        </dl>
        <Button tag={Link} to="/gestionnaire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gestionnaire/${gestionnaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GestionnaireDetail;
