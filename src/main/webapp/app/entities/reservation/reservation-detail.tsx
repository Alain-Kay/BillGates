import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservation.reducer';

export const ReservationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reservationEntity = useAppSelector(state => state.reservation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reservationDetailsHeading">
          <Translate contentKey="billGatesApp.reservation.detail.title">Reservation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.id}</dd>
          <dt>
            <span id="numeroReservation">
              <Translate contentKey="billGatesApp.reservation.numeroReservation">Numero Reservation</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.numeroReservation}</dd>
          <dt>
            <span id="heureDebut">
              <Translate contentKey="billGatesApp.reservation.heureDebut">Heure Debut</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.heureDebut}</dd>
          <dt>
            <span id="heureFin">
              <Translate contentKey="billGatesApp.reservation.heureFin">Heure Fin</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.heureFin}</dd>
          <dt>
            <Translate contentKey="billGatesApp.reservation.chambre">Chambre</Translate>
          </dt>
          <dd>{reservationEntity.chambre ? reservationEntity.chambre.id : ''}</dd>
          <dt>
            <Translate contentKey="billGatesApp.reservation.options">Options</Translate>
          </dt>
          <dd>{reservationEntity.options ? reservationEntity.options.id : ''}</dd>
          <dt>
            <Translate contentKey="billGatesApp.reservation.gestionnaire">Gestionnaire</Translate>
          </dt>
          <dd>{reservationEntity.gestionnaire ? reservationEntity.gestionnaire.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reservation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reservation/${reservationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReservationDetail;
