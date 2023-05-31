import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChambre } from 'app/shared/model/chambre.model';
import { getEntities as getChambres } from 'app/entities/chambre/chambre.reducer';
import { IOptions } from 'app/shared/model/options.model';
import { getEntities as getOptions } from 'app/entities/options/options.reducer';
import { IGestionnaire } from 'app/shared/model/gestionnaire.model';
import { getEntities as getGestionnaires } from 'app/entities/gestionnaire/gestionnaire.reducer';
import { IReservation } from 'app/shared/model/reservation.model';
import { getEntity, updateEntity, createEntity, reset } from './reservation.reducer';

export const ReservationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chambres = useAppSelector(state => state.chambre.entities);
  const options = useAppSelector(state => state.options.entities);
  const gestionnaires = useAppSelector(state => state.gestionnaire.entities);
  const reservationEntity = useAppSelector(state => state.reservation.entity);
  const loading = useAppSelector(state => state.reservation.loading);
  const updating = useAppSelector(state => state.reservation.updating);
  const updateSuccess = useAppSelector(state => state.reservation.updateSuccess);

  const handleClose = () => {
    navigate('/reservation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChambres({}));
    dispatch(getOptions({}));
    dispatch(getGestionnaires({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...reservationEntity,
      ...values,
      chambre: chambres.find(it => it.id.toString() === values.chambre.toString()),
      options: options.find(it => it.id.toString() === values.options.toString()),
      gestionnaire: gestionnaires.find(it => it.id.toString() === values.gestionnaire.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...reservationEntity,
          chambre: reservationEntity?.chambre?.id,
          options: reservationEntity?.options?.id,
          gestionnaire: reservationEntity?.gestionnaire?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="billGatesApp.reservation.home.createOrEditLabel" data-cy="ReservationCreateUpdateHeading">
            <Translate contentKey="billGatesApp.reservation.home.createOrEditLabel">Create or edit a Reservation</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="reservation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('billGatesApp.reservation.numeroReservation')}
                id="reservation-numeroReservation"
                name="numeroReservation"
                data-cy="numeroReservation"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.reservation.heureDebut')}
                id="reservation-heureDebut"
                name="heureDebut"
                data-cy="heureDebut"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.reservation.heureFin')}
                id="reservation-heureFin"
                name="heureFin"
                data-cy="heureFin"
                type="text"
              />
              <ValidatedField
                id="reservation-chambre"
                name="chambre"
                data-cy="chambre"
                label={translate('billGatesApp.reservation.chambre')}
                type="select"
              >
                <option value="" key="0" />
                {chambres
                  ? chambres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reservation-options"
                name="options"
                data-cy="options"
                label={translate('billGatesApp.reservation.options')}
                type="select"
              >
                <option value="" key="0" />
                {options
                  ? options.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reservation-gestionnaire"
                name="gestionnaire"
                data-cy="gestionnaire"
                label={translate('billGatesApp.reservation.gestionnaire')}
                type="select"
              >
                <option value="" key="0" />
                {gestionnaires
                  ? gestionnaires.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reservation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReservationUpdate;
