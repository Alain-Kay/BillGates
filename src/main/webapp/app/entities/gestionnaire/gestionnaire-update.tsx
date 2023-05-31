import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGestionnaire } from 'app/shared/model/gestionnaire.model';
import { getEntity, updateEntity, createEntity, reset } from './gestionnaire.reducer';

export const GestionnaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gestionnaireEntity = useAppSelector(state => state.gestionnaire.entity);
  const loading = useAppSelector(state => state.gestionnaire.loading);
  const updating = useAppSelector(state => state.gestionnaire.updating);
  const updateSuccess = useAppSelector(state => state.gestionnaire.updateSuccess);

  const handleClose = () => {
    navigate('/gestionnaire');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...gestionnaireEntity,
      ...values,
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
          ...gestionnaireEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="billGatesApp.gestionnaire.home.createOrEditLabel" data-cy="GestionnaireCreateUpdateHeading">
            <Translate contentKey="billGatesApp.gestionnaire.home.createOrEditLabel">Create or edit a Gestionnaire</Translate>
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
                  id="gestionnaire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('billGatesApp.gestionnaire.nomGestionnaire')}
                id="gestionnaire-nomGestionnaire"
                name="nomGestionnaire"
                data-cy="nomGestionnaire"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.gestionnaire.postGestionnaire')}
                id="gestionnaire-postGestionnaire"
                name="postGestionnaire"
                data-cy="postGestionnaire"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.gestionnaire.numeroGestionnaire')}
                id="gestionnaire-numeroGestionnaire"
                name="numeroGestionnaire"
                data-cy="numeroGestionnaire"
                type="text"
              />
              <ValidatedField
                label={translate('billGatesApp.gestionnaire.emailGestionnaire')}
                id="gestionnaire-emailGestionnaire"
                name="emailGestionnaire"
                data-cy="emailGestionnaire"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gestionnaire" replace color="info">
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

export default GestionnaireUpdate;
