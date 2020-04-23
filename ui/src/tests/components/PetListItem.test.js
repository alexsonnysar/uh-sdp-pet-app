import React from 'react';
import { shallow } from 'enzyme';
import { Button } from '@material-ui/core';
import PetListItem from '../../components/PetListItem';
import { pet } from '../mocks/pets';

describe('<PetListItem /> Tests', () => {
  const mockCallBack = jest.fn();
  let shallowWrapper;

  beforeEach(() => {
    shallowWrapper = shallow(<PetListItem removePet={mockCallBack} pet={pet} />);
  });

  test('should render pet list item', () => {
    expect(shallowWrapper).toHaveLength(1);
  });

  test('should render two buttons', () => {
    expect(shallowWrapper.find(Button).length).toEqual(2);
  });

  test('should render the Delete Button', () => {
    expect(shallowWrapper.find(Button).first().text()).toBe('Delete');
  });

  test('should render the Update Button', () => {
    expect(shallowWrapper.find(Button).at(1).text()).toBe('Update');
  });

  test('Click the Delete button', () => {
    shallowWrapper.find(Button).first().simulate('click');
    expect(mockCallBack.mock.calls.length).toEqual(1);
  });
});
