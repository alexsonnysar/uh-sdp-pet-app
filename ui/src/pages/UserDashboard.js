import React, { useState, useEffect } from 'react';
import { CircularProgress } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import RequestCardSlider from '../components/RequestCardSlider';
import PetCardSlider from '../components/PetCardSlider';
import { getAllRequestedPets, getAllFavs, getAllRecents } from '../api/petRequests';

const useStyles = makeStyles({
  root: {
    alignContent: 'left',
  },
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const UserDashboard = () => {
  const auth = `Bearer ${localStorage.getItem('jwt')}`;

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: auth,
  };

  const classes = useStyles();
  const favUrl = 'http://localhost:8080/user/fav';
  const recUrl = 'http://localhost:8080/user/recent';
  const requestUrl = 'http://localhost:8080/request/request-info';

  const [favList, setFavList] = useState([]);
  const [recList, setRecList] = useState([]);
  const [reqList, setReqList] = useState([]);
  const [loading, setLoading] = useState(true);
  let favID = [];
  let reqID = [];

  const handleError = () => {};
  useEffect(() => {
    axios
      .all([
        getAllFavs(favUrl, reqHeaders),
        getAllRecents(recUrl, reqHeaders),
        getAllRequestedPets(requestUrl, reqHeaders),
      ])
      .then(
        axios.spread((allFavRes, allRecRes, allReqRes) => {
          setFavList(allFavRes.data.filter((fav) => fav.active === true));
          setRecList(allRecRes.data);
          setReqList(allReqRes.data.filter((req) => req.status !== 'CANCELED'));
        })
      )
      .catch(handleError)
      .finally(() => setLoading(false));
  }, []);

  favID = favList.map((pr) => pr.id);
  reqID = reqList.map((pr) => pr.petId);
  localStorage.setItem('favIDs', JSON.stringify(favID));
  localStorage.setItem('reqIDs', JSON.stringify(reqID));

  return (
    <div>
      {loading ? (
        <div data-testid="loading" className={classes.progress}>
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div data-testid="loaded" className={classes.root}>
          <PetCardSlider petList={favList} userFav={favID} heading="Favorites" />
          <PetCardSlider
            petList={recList.filter((o) => o.active !== false)}
            userFav={favID}
            heading="Recently Viewed"
          />
          <RequestCardSlider
            requestList={reqList.filter((o) => o.status !== 'CANCELED')}
            heading="Requested & Adopted"
          />
        </div>
      )}
    </div>
  );
};

export default UserDashboard;
