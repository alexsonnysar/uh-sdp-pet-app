import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import { Formik, Form, Field } from "formik";
import axios from "axios";

// function validateEmail(value) {
//   let error;
//   if (!value) {
//     error = "Required";
//   } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value)) {
//     error = "Invalid email address";
//   }
//   return error;
// }

// function validatePassword(value) {
//   let error;
//   if (!value) {
//     error = "Required";
//   }
//   return error;
// }

// const LoginForm = () => {
//   const classes = useStyles();
//   return (
//     <div data-testid="loginForm">
//       <h1 align="center">Log In</h1>
//       <Formik
//         initialValues={{
//           email: "",
//           password: ""
//         }}
//         onSubmit={values => {
//           // Ping api here
//           console.log(values);
//         }}
//       >
//         {({ errors, touched, isValidating }) => (
//           <form className={classes.container}>
//             <label htmlFor="email">Email</label>
//             <Field name="email" label="Email" validate={validateEmail} />
//             {errors.email && touched.email && (
//               <div style={{ color: "red" }}>{errors.email}</div>
//             )}

//             <label htmlFor="Password">Password</label>
//             <Field
//               name="password"
//               label="Password"
//               validate={validatePassword}
//             />
//             {errors.password && touched.password && (
//               <div style={{ color: "red" }}>{errors.password}</div>
//             )}

//             <button type="submit" className={classes.button}>
//               Submit
//             </button>

//             <small className={classes.text}>
//               Don't have an account? Register <Link to="/register">here</Link>
//             </small>
//           </form>
//         )}
//       </Formik>
//     </div>
//   );
// };

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

const LoginForm = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const [loading, setLoading] = useState(false);

  const [isError, setError] = useState(false);

  const PostLoginUser = userData => {
    setLoading(true);
    axios({
      method: "post",
      url: "http://localhost:8080/signin",
      data: userData,
      headers: { "Content-Type": "application/json" }
    })
      .then(response => {
        console.log(response.data);
        alert(JSON.stringify(response.data));
      })
      .catch(error => {
        console.log(error);
        alert("Incorrect Username or Password");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleChange = e => {
    console.log(e.target.id);
    setFormData({
      ...formData,
      [e.target.id]: e.target.value
    });
  };

  const handleSubmit = () => {
    PostLoginUser(formData);
  };

  return (
    <div data-testid="loginForm">
      <form className={classes.container}>
        <h1 align="center">Log In</h1>
        <TextField
          id="email"
          label="Email"
          variant="outlined"
          m={20}
          onChange={handleChange}
          error={formData.email === ""}
        />
        <TextField
          id="password"
          label="Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
          onChange={handleChange}
        />
        <Button
          variant="outlined"
          className={classes.button}
          onClick={() => handleSubmit()}
        >
          Log In
        </Button>
        <small className={classes.text}>
          Don't have an account? Register <Link to="/register">here</Link>
        </small>
      </form>
    </div>
  );
};

export default LoginForm;
