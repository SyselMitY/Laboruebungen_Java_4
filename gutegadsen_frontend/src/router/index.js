import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import PostList from "@/views/PostList";
import Login from "@/views/Login";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    }, {
        path: '/posts',
        name: 'PostList',
        component: PostList
    }, {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    }
]

const router = new VueRouter({
    routes
})

function checkAuthenticated(to,from,next) {
    let username = localStorage.getItem("username");
    if (username == null) {
        next({name: "login",query: from.path});
    } else next();
}

function checkAuthentification(to, from, next) {
    if (to.matched.some(value => value.meta.requiresAuth)) {
        checkAuthenticated(to,from,next);
    } else {
        next();
    }
}

router.beforeEach(checkAuthentification)

export default router
