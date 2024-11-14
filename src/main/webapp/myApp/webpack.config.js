const path = require("path");
const fs = require("fs");
const CopyWebpackPlugin = require("copy-webpack-plugin");

const sourceDirectory = "./react";
const outputDirectory = "./static/react";

// Local directories for source and output files for testing
const localDirectory = "/Users/joshithreddy/Documents/Project/SpringFramework/target/JKPVT/myApp";

if (!fs.existsSync(localDirectory)) {
    console.error(`Local directory does not exist: ${localDirectory}`);
}

// Target directories for source and output files in the project
const targetSourceDirectory = path.join(localDirectory, "react");
const targetOutputDirectory = path.join(localDirectory, "static/react");
// Generate copy patterns only if directories exist
const copyPatterns = [];
if (fs.existsSync(sourceDirectory) && fs.existsSync(targetSourceDirectory)) {
    copyPatterns.push({ from: sourceDirectory, to: targetSourceDirectory });
}
if (fs.existsSync(outputDirectory) && fs.existsSync(targetOutputDirectory)) {
    copyPatterns.push({ from: outputDirectory, to: targetOutputDirectory });
}

// Recursively find all .js files in the source directory and its subdirectories
const findEntryPoints = (dir) => {
    const entries = fs.readdirSync(dir);

    return entries.reduce((acc, entry) => {
        const fullPath = path.join(dir, entry);
        const isDirectory = fs.statSync(fullPath).isDirectory();

        if (isDirectory) {
            // Recursively find entry points in subdirectories
            acc = { ...acc, ...findEntryPoints(fullPath) };
        } else if (entry.endsWith(".js")) {
            // Include JavaScript files as entry points
            const entryName = path
                .relative(sourceDirectory, fullPath)
                .replace(/\.js$/, "")
                .split(path.sep)
                .join("/");
            acc[entryName] = fullPath;
        }

        return acc;
    }, {});
};

const entryPoints = findEntryPoints(sourceDirectory);

module.exports = {
    mode: "development",
    devtool: 'source-map',
    resolve: {
        preferRelative: true, // Tries to resolve requests in the current directory
    },
    entry: entryPoints,
    output: {
        path: path.resolve(__dirname, outputDirectory),
        filename: "[name].js",
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: ["@babel/preset-react"],
                    },
                },
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.(png|svg|jpg|gif)$/, // Process image files
                use: ["file-loader"],
            },
        ],
    },
    plugins: [
        ...(copyPatterns.length > 0
            ? [new CopyWebpackPlugin({ patterns: copyPatterns })]
            : []),
    ],
};