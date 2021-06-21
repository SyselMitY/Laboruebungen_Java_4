import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import PostList from "@/views/PostList";

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

function checkAuthenticated(next) {
    let username = localStorage.getItem("username");
    if (username == null) next({name: "login"});
    else next();
}

function checkAuthentification(to, from, next) {
    if (to.matched.some(value => value.meta.requiresAuth)) {
        checkAuthenticated(next);
    } else {
        next();
    }
}

router.beforeEach(checkAuthentification)

export default router
