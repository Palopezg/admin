import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-type-2.reducer';
import { IServiceType2 } from 'app/shared/model/service-type-2.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceType2DetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceType2Detail = (props: IServiceType2DetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceType2Entity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ServiceType2 [<b>{serviceType2Entity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="serviceId">Service Id</span>
          </dt>
          <dd>{serviceType2Entity.serviceId}</dd>
          <dt>
            <span id="descripcion">Descripcion</span>
          </dt>
          <dd>{serviceType2Entity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/service-type-2" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-type-2/${serviceType2Entity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceType2 }: IRootState) => ({
  serviceType2Entity: serviceType2.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceType2Detail);
