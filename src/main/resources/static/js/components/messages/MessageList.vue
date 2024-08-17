<template>
<!--  <v-list>-->
<!--    <message-form :messages="messages" :messageAttr="message" />-->
<!--    <message-row v-for="message in messages"-->
<!--                 :key="message.id"-->
<!--                 :message="message"-->
<!--                 :editMessage="editMessage"-->
<!--                 :deleteMessage="deleteMessage"-->
<!--                 :messages="messages" />-->
<!--  </v-list>-->

  <v-list lines="two">
    <v-list-item>
      <message-form :messages="messages" :messageAttr="message" />
      <message-row v-for="message in sortedMessages"
                   :key="message.id"
                   :message="message"
                   :editMessage="editMessage"
                   :deleteMessage="deleteMessage"
                   :messages="messages" />
    </v-list-item>
  </v-list>
</template>

<script>
import axios from 'axios';
import MessageRow from 'components/messages/MessageRow.vue'
import MessageForm from 'components/messages/MessageForm.vue'

export default {
  props: ['messages'],
  components: {
    MessageRow,
    MessageForm
  },
  data() {
    return {
      message: null
    }
  },
  computed:{
    sortedMessages(){
      return this.messages.sort((a, b) => - (a.id - b.id) )
    }
  },
  methods: {
    editMessage(message) {
      this.message = message
    },
    deleteMessage(message) {
      axios.delete(`/message/${message.id}`)
          .then(response => {
            if (response.status === 200) {
              this.messages.splice(this.messages.indexOf(message), 1);
            }
          })
          .catch(error => {
            console.error('Ошибка при удалении:', error);
          });
    }
  }}
</script>

<style>

</style>