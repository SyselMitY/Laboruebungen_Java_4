const baseUrl = "http://localhost:10221"

export default {
    api: {
        baseUrl,
        posts: {
            all: `${baseUrl}/posts/list/`,
            create: `${baseUrl}/posts/create/`,
            upvote: (id) => `${baseUrl}/posts/upvote/${id}/`
        },
        images: {
            id: (id) => `${baseUrl}/images/${id}/`
        },
        users: {
            register: `${baseUrl}/users/register/`,
            login: `${baseUrl}/users/login/`,
            id: (id) => `${baseUrl}/users/${id}/`,
            upvotes: (id) => `${baseUrl}/users/${id}/upvotes/`
        },
        tags:{
            all: `${baseUrl}/tags/`
        }
    }
};
