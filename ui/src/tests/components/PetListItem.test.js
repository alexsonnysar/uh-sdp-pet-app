import React from 'react';
import { shallow } from 'enzyme';
import PetListItem from '../../components/PetListItem';
import ListButton from '../../components/ListButton';

const pet = {
  Name: 'Garfield',
  Type: 'Cat',
};

describe('<PetListItem /> Tests', () => {
  let shallowWrapper;

  beforeEach(() => {
    shallowWrapper = shallow(
      <PetListItem
        heading="Pet List"
        pet={pet}
        action={() => {}}
        deleteButton
        updateButton
      />
    );
  });

  test('should render pet list item', () => {
    expect(shallowWrapper).toHaveLength(1);
  });

  test('should render two buttons', () => {
    expect(shallowWrapper.find('ListButton').length).toBe(2);
  });
});
