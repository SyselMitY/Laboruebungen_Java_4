const baseUrl = "http://localhost:10221/"

export default {
    api: {
        baseUrl,
        posts: {
            all: `${baseUrl}posts/list/`
        },
        images: {
            id: (id) => `${baseUrl}/images/${id}`
        },
        users: {
            register: `${baseUrl}/users/register`
        }
    }
};
