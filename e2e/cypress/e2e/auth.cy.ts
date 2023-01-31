describe("Authentication", () => {
  it("should be able to register", () => {
    cy.intercept("POST", "**/auth/register").as("register");

    cy.visit("/register");
    cy.get("input[name=email]").type("admin@test.com");
    cy.get("input[name=username]").type("adminuser");
    cy.get("input[name=password]").type("password");
    cy.get("button[type=submit]").click();

    cy.wait("@register").its("response.statusCode").should("eq", 201);
  });

  it("should be able to login", () => {
    //intercept POST /api/auth/login
    cy.intercept("POST", "**/auth/login").as("login");

    cy.visit("/login");
    cy.get("input[name=email]").type("admin@test.com");
    cy.get("input[name=password]").type("password");
    cy.get("button[type=submit]").click();

    //wait for the request to finish with 200 and contain token as response body
    cy.wait("@login").its("response.statusCode").should("eq", 200);
  });

  it("should be able to logout", () => {
    cy.visit("/login");

    cy.get("input[name=email]").type("admin@test.com");
    cy.get("input[name=password]").type("password");
    cy.get("button[type=submit]").click();

    cy.get("button").contains("Logout").click();
    //check if the user is redirected to the login page
    cy.url().should("include", "/login");
    //check if token is deleted from localstorage
    cy.window().then((win) => {
      expect(win.localStorage.getItem("token")).to.be.null;
    });
  });
});
