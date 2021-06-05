client.test("Request returns 400", function () {
    client.assert(response.status === 400, "Response status is not 400");
});