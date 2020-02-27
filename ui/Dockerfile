FROM node:12-slim

WORKDIR /usr/src/pet/
ENV NODE_ENV development

COPY package.json /usr/src/pet/package.json

RUN yarn

COPY . /usr/src/pet/

CMD ["yarn","start"]

EXPOSE 3000
