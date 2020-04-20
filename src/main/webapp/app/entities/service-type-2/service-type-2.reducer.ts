import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceType2, defaultValue } from 'app/shared/model/service-type-2.model';

export const ACTION_TYPES = {
  FETCH_SERVICETYPE2_LIST: 'serviceType2/FETCH_SERVICETYPE2_LIST',
  FETCH_SERVICETYPE2: 'serviceType2/FETCH_SERVICETYPE2',
  CREATE_SERVICETYPE2: 'serviceType2/CREATE_SERVICETYPE2',
  UPDATE_SERVICETYPE2: 'serviceType2/UPDATE_SERVICETYPE2',
  DELETE_SERVICETYPE2: 'serviceType2/DELETE_SERVICETYPE2',
  RESET: 'serviceType2/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceType2>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ServiceType2State = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceType2State = initialState, action): ServiceType2State => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICETYPE2_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICETYPE2):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICETYPE2):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICETYPE2):
    case REQUEST(ACTION_TYPES.DELETE_SERVICETYPE2):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICETYPE2_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICETYPE2):
    case FAILURE(ACTION_TYPES.CREATE_SERVICETYPE2):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICETYPE2):
    case FAILURE(ACTION_TYPES.DELETE_SERVICETYPE2):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICETYPE2_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICETYPE2):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICETYPE2):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICETYPE2):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICETYPE2):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/service-type-2-s';

// Actions

export const getEntities: ICrudGetAllAction<IServiceType2> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICETYPE2_LIST,
    payload: axios.get<IServiceType2>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IServiceType2> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICETYPE2,
    payload: axios.get<IServiceType2>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IServiceType2> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICETYPE2,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceType2> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICETYPE2,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceType2> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICETYPE2,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
