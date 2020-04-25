import React from 'react';
import { shallow } from 'enzyme';
import { Button } from '@material-ui/core';
import RequestListItem from '../../components/RequestListItem';
import { request } from '../mocks/requests';

describe('All <RequestListItem /> Tests', () => {
  describe('<RequestListItem /> shallow Tests', () => {
    let mockCallBack;
    let shallowWrapper;

    beforeEach(() => {
      mockCallBack = jest.fn();
      shallowWrapper = shallow(
        <RequestListItem requests={request} requestUpdated={mockCallBack} />
      );
    });

    afterEach(() => {
      jest.resetAllMocks();
    });

    test('should render request list item', () => {
      expect(shallowWrapper).toHaveLength(1);
    });

    test('should render two buttons', () => {
      expect(shallowWrapper.find(Button).length).toBe(2);
    });

    test('should render the Deny Button', () => {
      expect(shallowWrapper.find(Button).first().text()).toBe('Deny');
    });

    test('should render the Approve Button', () => {
      expect(shallowWrapper.find(Button).at(1).text()).toBe('Approve');
    });

    test('Click the Deny button', () => {
      shallowWrapper.find(Button).first().simulate('click');
      expect(mockCallBack.mock.calls.length).toEqual(1);
    });

    test('Click the Approve button', () => {
      shallowWrapper.find(Button).at(1).simulate('click');
      expect(mockCallBack.mock.calls.length).toEqual(1);
    });
  });
});
