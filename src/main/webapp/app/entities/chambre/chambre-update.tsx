import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { IChambre } from 'app/shared/model/chambre.model';
import { Disponibilite } from 'app/shared/model/enumerations/disponibilite.model';
import { getEntity, updateEntity, createEntity, reset } from './chambre.reducer';

export const ChambreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const chambreEntity = useAppSelector(state => state.chambre.entity);
  const loading = useAppSelector(state => state.chambre.loading);
  const updating = useAppSelector(state => state.chambre.updating);
  const updateSuccess = useAppSelector(state => state.chambre.updateSuccess);
  const disponibiliteValues = Object.keys(Disponibilite);

  const handleClose = () => {
    navigate('/chambre');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...chambreEntity,
      ...values,
      client: clients.find(it => it.id.toString() === values.client.toString()),
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
          disponibilite: 'DISPONIBLE',
          ...chambreEntity,
          client: chambreEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="billGatesApp.chambre.home.createOrEditLabel" data-cy="ChambreCreateUpdateHeading">
            <Translate contentKey="billGatesApp.chambre.home.createOrEditLabel">Create or edit a Chambre</Translate>
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
                  id="chambre-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('billGatesApp.chambre.numeroChambre')}
                id="chambre-numeroChambre"
                name="numeroChambre"
                data-cy="numeroChambre"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.chambre.prixChambre')}
                id="chambre-prixChambre"
                name="prixChambre"
                data-cy="prixChambre"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.chambre.disponibilite')}
                id="chambre-disponibilite"
                name="disponibilite"
                data-cy="disponibilite"
                type="select"
              >
                {disponibiliteValues.map(disponibilite => (
                  <option value={disponibilite} key={disponibilite}>
                    {translate('billGatesApp.Disponibilite.' + disponibilite)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('billGatesApp.chambre.images')}
                id="chambre-images"
                name="images"
                data-cy="images"
                type="text"
              />
              <ValidatedField
                id="chambre-client"
                name="client"
                data-cy="client"
                label={translate('billGatesApp.chambre.client')}
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/chambre" replace color="info">
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

export default ChambreUpdate;
