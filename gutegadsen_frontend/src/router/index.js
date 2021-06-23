import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import PostList from "@/views/PostList";
import Login from "@/views/Login";
import PostCreate from "@/views/PostCreate";

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
        path: '/posts/create',
        name: 'PostCreate',
        component: PostCreate,
        meta: {
            requiresAuth: true
        }
    }, {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
            requiresNoAuth: true
        }
    }
]

const router = new VueRouter({
    routes
})

function checkAuthenticated(to, from, next) {
    let username = localStorage.getItem("username");
    if (username == null) {
        next({name: "Login",params:{showLoginToast:true}, query: {afterLogin: to.path}});
    } else next();
}

function checkNotAuthenticated(to, from, next) {
    let username = localStorage.getItem("username");
    if (username != null) {
        next({name: "Home"});
    } else next();
}

function checkAuthentification(to, from, next) {
    if (to.matched.some(value => value.meta.requiresAuth)) {
        checkAuthenticated(to, from, next);
    }
    if (to.matched.some(value => value.meta.requiresNoAuth)) {
        checkNotAuthenticated(to, from, next);
    } else {
        next();
    }
}

router.beforeEach(checkAuthentification)

export default router
