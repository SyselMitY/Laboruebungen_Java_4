client.test("Request returns 201", function () {
    client.assert(response.status === 201, "Response status is not 201");
});