import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './chambre.reducer';

export const ChambreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const chambreEntity = useAppSelector(state => state.chambre.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="chambreDetailsHeading">
          <Translate contentKey="billGatesApp.chambre.detail.title">Chambre</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{chambreEntity.id}</dd>
          <dt>
            <span id="numeroChambre">
              <Translate contentKey="billGatesApp.chambre.numeroChambre">Numero Chambre</Translate>
            </span>
          </dt>
          <dd>{chambreEntity.numeroChambre}</dd>
          <dt>
            <span id="prixChambre">
              <Translate contentKey="billGatesApp.chambre.prixChambre">Prix Chambre</Translate>
            </span>
          </dt>
          <dd>{chambreEntity.prixChambre}</dd>
          <dt>
            <span id="disponibilite">
              <Translate contentKey="billGatesApp.chambre.disponibilite">Disponibilite</Translate>
            </span>
          </dt>
          <dd>{chambreEntity.disponibilite}</dd>
          <dt>
            <span id="images">
              <Translate contentKey="billGatesApp.chambre.images">Images</Translate>
            </span>
          </dt>
          <dd>{chambreEntity.images}</dd>
          <dt>
            <Translate contentKey="billGatesApp.chambre.client">Client</Translate>
          </dt>
          <dd>{chambreEntity.client ? chambreEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/chambre" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/chambre/${chambreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChambreDetail;
