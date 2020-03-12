import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import {
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CardMedia
} from "@material-ui/core";
import FavoriteRoundedIcon from "@material-ui/icons/FavoriteRounded";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import ButtonBase from "@material-ui/core/ButtonBase";

const PetCard = ({ pet }) => {
  const { name, type } = pet;
  const classes = useStyles();

  return (
    <Card className={classes.root} data-testid="petcard">
      <ButtonBase href="pet-profile" style={{ textDecorationLine: "none" }}>
        <CardActionArea>
          <CardMedia
            component="img"
            alt="Pet Image"
            height="140"
            image="/images/garfield.jpg"
            title="Pet Image"
          />
          <CardContent className={classes.text}>
            <Typography gutterBottom variant="h5" component="h2">
              {name}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              {type}
            </Typography>
          </CardContent>
        </CardActionArea>
      </ButtonBase>
      <CardActions>
        <Button
          size="medium"
          color="secondary"
          startIcon={<FavoriteRoundedIcon />}
        >
          Favorite
        </Button>
      </CardActions>
    </Card>
  );
};

const useStyles = makeStyles({
  root: {
    margin: 5,
    maxWidth: 180,
    minWidth: 180
  }
});

export default PetCard;
