<template>
  <v-layout row>
    <v-text-field
        label="New message"
        placeholder="Write something"
        v-model="text"
    />
    <v-col cols="1" class="d-flex">
      <v-btn @click="save"
             class="ml-2"
             block
      >
        Save
      </v-btn>
    </v-col>
  </v-layout>
</template>


<script>
import {sendMessage} from "utils/ws";

export default {
  props: ['messages', 'messageAttr'],
  data() {
    return {
      text: '',
      id: ''
    }
  },
  watch: {
    messageAttr: function (newVal) {
      this.text = newVal.text || '';
      this.id = newVal.id || '';
    }
  },
  methods: {
    save() {
      sendMessage({id: this.id, text: this.text})
      this.id=''
      this.text=''
    }
  }
}
</script>

<style>
/* Ваши стили здесь */
</style>