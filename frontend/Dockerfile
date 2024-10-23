FROM node:18.20.4

WORKDIR /app
COPY ./package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD npm run dev
