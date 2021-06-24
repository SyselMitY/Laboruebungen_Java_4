const baseUrl = "https://gadsenapi.soisi.cf"

export default {
    api: {
        baseUrl,
        posts: {
            all: `${baseUrl}/posts/list/`,
            create: `${baseUrl}/posts/create/`,
            upvote: (id) => `${baseUrl}/posts/upvote/${id}/`,
            delete: (id) => `${baseUrl}/posts/delete/${id}/`,
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
