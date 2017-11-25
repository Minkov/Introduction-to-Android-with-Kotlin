const {
    MongoClient,
} = require('mongodb');

const init = async(connectionString) => {
    return MongoClient.connect(connectionString);
};

module.exports = {
    init,
};
