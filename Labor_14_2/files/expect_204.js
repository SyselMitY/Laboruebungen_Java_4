client.test("Request returns 204", function () {
    client.assert(response.status === 204, "Response status is not 204");
});