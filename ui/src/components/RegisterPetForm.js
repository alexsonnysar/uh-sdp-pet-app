import React from "react";
import { TextField, Button, makeStyles, MenuItem } from "@material-ui/core";
import { Link } from "react-router-dom";

const RegisterPetForm = () => {
  const date = {
    someDate: new Date().toISOString().substring(0, 10)
  };
  const classes = useStyles();
  return (
    <div>
      <form className={classes.container}>
        <h1 align="center">Register Pet</h1>
        <TextField id="outlined-basic" label="Name" variant="outlined" m={20} />
        <TextField id="outlined-select" label="Type" variant="outlined" select>
          <MenuItem value="Dog">Dog</MenuItem>
          <MenuItem value="Cat">Cat</MenuItem>
          <MenuItem value="Bird">Bird</MenuItem>
          <MenuItem value="Fish">Fish</MenuItem>
          <MenuItem value="Lizard">Lizard</MenuItem>
          <MenuItem value="Furry">Furry</MenuItem>
          <MenuItem value="Farm Animal">Farm Animal</MenuItem>
          <MenuItem value="Bat">Bat</MenuItem>
        </TextField>
        <TextField id="outlined-select" label="Sex" variant="outlined" select>
          <MenuItem value="M">Male</MenuItem>
          <MenuItem value="F">Female</MenuItem>
        </TextField>
        <TextField id="outlined-select" label="Age" variant="outlined" select>
          <MenuItem value="Baby">Baby</MenuItem>
          <MenuItem value="Young">Young</MenuItem>
          <MenuItem value="Adult">Adult</MenuItem>
          <MenuItem value="Jurassic">Jurassic</MenuItem>
        </TextField>

        <TextField id="outlined-select" label="Size" variant="outlined" select>
          <MenuItem value="Small">Small</MenuItem>
          <MenuItem value="Medium">Medium</MenuItem>
          <MenuItem value="Large">Large</MenuItem>
        </TextField>
        <TextField id="outlined-basic" label="Weight" variant="outlined" />
        <TextField
          id="outlined-date"
          label="Arrival Date"
          type="date"
          defaultValue={date.someDate}
          InputLabelProps={{
            shrink: true
          }}
          variant="outlined"
        />
        <TextField
          id="multiline"
          multiline
          rowMax="4"
          label="Description"
          variant="outlined"
        />
        <TextField
          id="outlined-select"
          label="Active"
          variant="outlined"
          select
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>

        <TextField
          id="outlined-select"
          label="Adopted"
          variant="outlined"
          select
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>

        <Button variant="outlined" className={classes.button}>
          Pet Registration
        </Button>
      </form>
    </div>
  );
};

const useStyles = makeStyles(theme => ({
  container: {
    "& > *": {
      margin: theme.spacing(1)
    },
    width: "20rem",
    display: "flex",
    flexDirection: "column"
  },
  button: {
    color: "primary"
  },
  text: {
    textAlign: "center"
  }
}));

export default RegisterPetForm;
