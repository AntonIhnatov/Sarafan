import { createApp } from 'vue';
import VueResource from "vue-resource";
import App from './pages/App.vue';
import axios from 'axios';
import { connect } from "./utils/ws";
import { createVuetify } from 'vuetify';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import 'vuetify/styles';
import '@mdi/font/css/materialdesignicons.css'; // Импорт иконок Material Design

if (frontendData.profile) {
    connect();
}

const vuetify = createVuetify({
    components,
    directives,
});

const app = createApp(App);

app.config.globalProperties.$http = axios;

app.use(vuetify);

app.mount('#app');

import { createApp } from 'vue';
import App from './pages/App.vue';
import vuetify from './plugins/vuetify';
import axios from './plugins/axios';
import { connect } from './utils/ws';

if (frontendData.profile) {
    connect();
}

const app = createApp(App);

app.config.globalProperties.$http = axios;

app.use(vuetify);
app.mount('#app');
