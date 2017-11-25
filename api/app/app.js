const fs = require('fs');

const Koa = require('koa');
const Router = require('koa-router');
const bodyParser = require('koa-bodyparser');
const morgan = require('koa-morgan');

const Lang = require('../models/lang.model')
const { wait } = require('../utils/wait');

const init = (dataProvider) => {
    const app = new Koa();

    const langsRouter = new Router({
        prefix: "/langs"
    });

    const langsData = dataProvider.getFor(Lang);

    langsRouter
        .get('/', async (ctx, next) => {
            await wait(2);
            const langs = await langsData.getAll();

            ctx.body = langs;
            ctx.status = 200;

            await next();
        })
        .get('/:id', async(ctx, next) => {
            await wait(1);
            const lang = await langsData.getById(ctx.params.id);
            ctx.body = lang;
            ctx.status = 200;

            await next();
        })
        .post('/', async (ctx, next) => {
            const lang = await langsData.create(ctx.request.body);
            ctx.status = 201;
            ctx.body = lang;
            await next();
        });

    const router = new Router({
        prefix: "/api",
    });

    router.use(langsRouter.routes())

    const accessLogStream = fs.createWriteStream(__dirname + '/access.log',
        { flags: 'a' });

    app
        .use(morgan('combined', { stream: accessLogStream }))
        .use(bodyParser())
        .use(router.routes())
        .use(router.allowedMethods());

    return {
        start(port) {
            return new Promise((resolve) => {
                app.listen(port, resolve);
            });
        },
    };
};

module.exports = {
    init,
};
