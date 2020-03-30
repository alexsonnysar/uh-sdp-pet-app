# README

This project was bootstrapped with [Spring Initializr](https://start.spring.io/).

## Available Scripts

In the project directory, you can run:

### `gradle bootRun`

Runs the backend in developer mode.

Open [http://localhost:8080](http://localhost:8080) to view it in the browser.

### `gradle`

Launches the build script, test runner, and coverage report.

Open [jenkins](./build/reports/jacoco/test/html/index.html) in your browser to check coverage.

## Authentication

#### Registering new user

Send

``` json
{
	"email": "victor@example.com",
	"password: "123"
}
```

to endpoint `/signup`

#### Retrieve JWT token

Send

```json
{
	"email": "victor@example.com",
	"password: "123"
}
```

to endpoint `/signin` which returns

```json
{
  "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWN0b3JAZXhhbXBsZS5jb20iLCJpYXQiOjE1ODQ4MzEwNDAsImV4cCI6MTU4NDkxNzQ0MH0.lS0wxSwPGaYCC0kppUkkStFdJicMrod8HO3z32OxMI9DG8UMKU3Mmx6dLP0feZKWg6JIFaCjozF6EUYtAjFq6g",
  "id": "5e769a39bfd2537f13344038",
  "email": "victor@example.com",
  "roles": [
    "ROLE_User"
  ]
}
```

#### Requesting resources that require authentication

Include Authorization Header with JWT token

``` md
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWMiLCJpYXQiOjE1ODQ4MzAwMTIsImV4cCI6MTU4NDkxNjQxMn0.zioskfrZqrNJa7jxJdCl-gUU6H2zTM6q9RjVNrCG_WHFaFx9kzNCozMAExlqxY1FcohbapDv4AqMRYBxb2Q6Hg
```