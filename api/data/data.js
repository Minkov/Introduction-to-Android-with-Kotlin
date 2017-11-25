const {
    ObjectID
} = require('mongodb');

class Data {
    constructor(db, ModelClass) {
        this.db = db;
        this.ModelClass = ModelClass;
        this.collectionName = this._getCollectionName();
        this.collection = this.db.collection(this.collectionName);
    }

    _getCollectionName() {
        return this.ModelClass.name.toLowerCase() + 's';
    }

    async getAll() {
        const models = await this.collection.find()
            .toArray();
        return models.map(this.ModelClass.toViewModel);
    }

    async create(model) {
        await this.collection.insert(model);
        return model;
    }

    async getById(id) {
        const model = this.collection.findOne({
            _id: new ObjectID(id),
        });

        return model;
    }
}

class LocalData {

}

class DataProvider {
    constructor(db) {
        this.db = db;
        this.datas = {};
    }

    getFor(ModelClass) {
        if (typeof (this.datas[ModelClass.name]) === 'undefined') {
            this.datas[ModelClass.name] = new Data(this.db, ModelClass);
        }

        return this.datas[ModelClass.name];
    }
}

const init = async(db) => {
    return new DataProvider(db);
};

module.exports = {
    init,
};