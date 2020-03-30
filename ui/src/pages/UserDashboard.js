import React, { useState, useEffect } from "react";
import PetCardSlider from "../components/PetCardSlider";
import { makeStyles } from "@material-ui/core/styles";
import getAllPets from "../api/petRequests";

const UserDashboard = () => {
  const classes = useStyles();
  const url = "http://localhost:8080/pet";

  const [petList, setPetList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllPets(url)
      .then(petList => setPetList(petList))
      .catch(error => console.log(error))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      {loading ? (
        <div data-testid="loading">No List of Pets to Show :(</div>
      ) : (
        <div className={classes.root}>
          <PetCardSlider petList={petList} heading="Favorites" />
          <PetCardSlider petList={petList} heading="Recently Viewed" />
          <PetCardSlider petList={petList} heading="Adopted" />
        </div>
      )}
    </div>
  );
};

const useStyles = makeStyles({
  root: {
    alignContent: "left"
  }
});

export default UserDashboard;
