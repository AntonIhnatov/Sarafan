<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title><span class="material-icons">verified</span>Sarafan</v-toolbar-title>
      <v-spacer></v-spacer>
      <span v-if="profile && profile.fullName">{{ profile.fullName }}</span>
      <v-btn v-if="profile" href="/logout">
        <span class="material-icons">login</span>Exit
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container v-if="!profile">
        Необходимо авторизоваться через
        <a href="/login">Google</a>
      </v-container>
      <v-container v-if="profile">
        <messages-list :messages="messages"/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import MessagesList from 'components/messages/MessageList.vue';
import {addHandler} from "../utils/ws";
import {getIndex} from "../utils/collections";

export default {
  components: {
    MessagesList
  },
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    };
  },
  created() {
    addHandler(data => {
      let index = getIndex(this.messages, data.id);
      if (index > -1) {
        this.messages.splice(index, 1, data);
      } else {
        this.messages.push(data);
      }
    });
  }
}
</script>

<style>
/* Добавь свои стили здесь, если необходимо */
</style>