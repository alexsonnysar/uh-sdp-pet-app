# uh-sdp-pet-app

Pet Web Application for UH SDP Project

## To Get Started

Here are a couple tips to get you up and running quickly!

```sh
git clone <this repo>

cd <this repo>

```

Install the VScode [Spring Extension Pack](https://marketplace.visualstudio.com/items?itemName=Pivotal.vscode-boot-dev-pack)

then click the play button next to 'SPRING-BOOT DASHBOARD' to start the pet-api. you should now be able to see it a `Whitelabel Error Page` on [localhost:8080](localhost:8080)

alternatively you can run this

```sh
cd api
gradle
gradle BootRun

```

If you would like you can checkout the pets at  [/pet](localhost:8080/pet)

The front end runs on port [localhost:3000](localhost:3000)

To see this

```sh
cd uh-sdp-pet-app/ui
yarn install
yarn start
```

You should see a user dashboard that shows off all the pets in the database
