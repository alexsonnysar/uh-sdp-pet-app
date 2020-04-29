import React from 'react';
import { shallow } from 'enzyme';
import { Snackbar } from '@material-ui/core';
import MuiAlert from '@material-ui/lab/Alert';
import RegisterMsg from '../../components/RegisterMsg';

describe('<RegisterMsg /> Tests', () => {
  let mockCallBack;
  let shallowWrapper;

  beforeEach(() => {
    mockCallBack = jest.fn();
    shallowWrapper = shallow(
      <RegisterMsg open msg="Test Msg" handleClose={mockCallBack} />
    );
  });

  afterEach(() => {
    jest.resetAllMocks();
  });

  test('should render the SucccessRequestMsg Component', () => {
    expect(shallowWrapper).toHaveLength(1);
  });

  test('Check if Snackbar component has been rendered', () => {
    expect(shallowWrapper.find(Snackbar)).toHaveLength(1);
  });

  test('Check if Snackbar component has the open prop as true', () => {
    expect(shallowWrapper.find(Snackbar).prop('open')).toBe(true);
  });

  test('Check if MuiAlert Component has been rendered', () => {
    expect(shallowWrapper.find(MuiAlert)).toHaveLength(1);
  });

  test('Check if MuiAlert Component has correct text', () => {
    expect(shallowWrapper.find(MuiAlert).text()).toEqual('Test Msg');
  });

  test('Emulate Click Away at SnackBar Level', () => {
    shallowWrapper.find(Snackbar).simulate('close');
    expect(mockCallBack.mock.calls.length).toEqual(1);
  });

  test('Emulate Click Away at MuiAlert Level', () => {
    shallowWrapper.find(MuiAlert).simulate('close');
    expect(mockCallBack.mock.calls.length).toEqual(1);
  });
});
