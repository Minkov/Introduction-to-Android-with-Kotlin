class Lang {
    constructor(name, platform) {
        this.name = name;
        this.platform = platform
    }

    static toViewModel(model) {
        return {
            id: model._id,
            name: model.name,
            platform: model.platform,
        }
    }
}

module.exports = Lang;
