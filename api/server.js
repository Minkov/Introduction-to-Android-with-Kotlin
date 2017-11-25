const runServer = async() => {
    const config = require('./config');
    const db = await require('./db').init(config.connectionString);
    const data = await require('./data').init(db);
    const app = require('./app').init(data);

    await app.start(config.port);
    console.log(`App running at ${config.port}`);
};

require('babel-polyfill');
require('babel-register');

runServer();
