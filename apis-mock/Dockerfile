#Dockerfile for apis-mock nodejs and expose port 3000

FROM node:20.10.0-alpine

WORKDIR /usr/src/app

COPY . .

EXPOSE 3000

CMD [ "npm", "start" ]

#Commands for build and execute
#docker build -t apis-mock .
#docker run -p 3000:3000 apis-mock