import React from 'react';
import { shallow } from 'enzyme';
import PetList from '../../components/PetList';
import { petList } from '../mocks/pets';

describe('<PetList /> Tests', () => {
  let shallowWrapper;

  beforeEach(() => {
    shallowWrapper = shallow(
      <PetList heading="Pet List" petList={petList} deletePet={() => {}} />
    );
  });

  test('should render 3 PetListItem Components', () => {
    expect(shallowWrapper.find('PetListItem').length).toBe(3);
  });
});
