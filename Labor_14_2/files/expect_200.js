client.test("Request returns 200", function () {
    client.assert(response.status === 200, "Response status is not 200");
});