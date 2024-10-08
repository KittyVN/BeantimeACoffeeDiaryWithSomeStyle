import { defineConfig } from "cypress";

export default defineConfig({
  reporter: "cypress-multi-reporters",
  reporterOptions: {
    configFile: "cypress/reporter-config.json",
  },
  watchForFileChanges: false,
  viewportWidth: 1920,
  viewportHeight: 1080,
  e2e: {
    //we can add support file later on
    supportFile: false,
    baseUrl: "http://localhost:4200/",
  },
});
