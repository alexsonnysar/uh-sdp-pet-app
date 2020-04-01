# uh-sdp-pet-app

Pet Web Application for UH SDP Project

Application Stack:
- Frontend: ReactJS
- Backend: Java Spring
- Database: MongoDB

## To Get Started

To run the backend code, you will need to have `gradle` installed. You may go [here](https://gradle.org/install/)
to install it for your respective OS.

Here are a couple tips to get you up and running quickly!

```bash
git clone https://github.com/grant-williams/uh-sdp-pet-app.git
cd uh-sdp-pet-app
```

To add MongoDB URI please do:

```bash
cd api/src/main/resources
```

Edit the `application.properties` file with your favorite editor and add your MongoDB URI to the variable `spring.data.mongodb.uri`:

```java
spring.data.mongodb.uri=<your-URI>
```

After adding MongoDB URI then run (from the `resources` directory):

```bash
cd ../../../../ui/
yarn install
yarn dev
```

When running `yarn dev` it will run the frontend and backend for you using the [concurrently](https://github.com/kimmobrunfeldt/concurrently) dependency, therefore
you will not need to open another terminal window to start backend.

## Extra Commands

If you would like to run the backend separately from the terminal, you may run (from the `uh-sdp-pet-app` directory):

```bash
cd api
gradle bootRun
```

And in another terminal window, to run frontend code you will run (from `uh-sdp-pet-app` directory):

```bash
cd ui
yarn start
```

NOTE: If you are using VScode and don't want to run the backend in the terminal, you can install the [Spring Extension Pack](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-boot-dev-pack)
then click the play button next to 'SPRING-BOOT DASHBOARD' to start the pet api.

If you would like to run all default tasks for gradle:

From the `api` folder run:

```bash
gradle
```
