const fs = require("fs");
const path = require("path");
require("dotenv").config(); // Load .env file

const homepageParam = process.env.REACT_APP_HOME_PATH_NAME || ""; // Default to empty if HOMEPAGE is not defined
const packageJsonPath = path.join(__dirname, "package.json");
const packageJson = require(packageJsonPath);

console.log(`Updating homepage to: ${homepageParam}`);

packageJson.homepage = homepageParam;

fs.writeFileSync(packageJsonPath, JSON.stringify(packageJson, null, 2));
console.log(`Homepage updated to: ${homepageParam}`);