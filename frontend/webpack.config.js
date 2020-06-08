module.exports = {
    mode: "development",

    entry: "./src/entrypoint.ts",
    output: {
        path: `${__dirname}/dist`,
        filename: "entrypoint.js"
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: "ts-loader"
            }
        ]
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json"]
    }
};