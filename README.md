# uh-sdp-pet-app

Pet Web Application for UH SDP Project

## To Get Started

Here are a couple tips to get you up and running quickly!
```sh
git clone https://github.com/grant-williams/uh-sdp-pet-app.git
cd uh-sdp-pet-app
```
To add MongoDB URI please do:

```sh
cd api/src/main/resources
```

Edit the ```application.properties``` file with your favorite edior and add your MongoDB URI to the ```pring.data.mongodb.uri```:

```
pring.data.mongodb.uri="<your-URI>"
```
After adding MongoDB URI then run (from the resources directory):

```sh
cd ../../../../ui/
yarn install
yarn dev
```

When running `yarn dev` it will run the frontend and backend for you using the [concurrently](https://github.com/kimmobrunfeldt/concurrently) dependency, therefore
you will not need to open another terminal window to start backend. 

NOTE: If you see a message "No pets to show :(" when running `yarn dev` for the first time, refresh the page and you should see cards at [homepage](localhost:3000)
## Extra Commands

If you would like to run the backend seperately from the you may run:

```sh
cd uh-sdp-pet-app/api
./gradlew bootRun
```

NOTE: If you are using VScode and dont want to run the backend in the terminal, you can install the [Spring Extension Pack](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-boot-dev-pack)
then click the play button next to 'SPRING-BOOT DASHBOARD' to start the pet api. 

If you would like to run all default tasks for gradle:

From the api folder run:
```sh
./gradlew
```
