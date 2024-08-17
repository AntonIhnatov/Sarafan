import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

let stompClient = null;
const handlers = []

export function connect() {
    const socket = new SockJS('http://localhost:9000/gs-guide-websocket');

    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        debug: (str) => {
            // console.log(str);
        },
        onConnect: (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/activity', (message) => {
                console.log('Received message: ', message.body);
                handlers.forEach(handler => handler(JSON.parse(message.body)))
            });
        },
        onWebSocketError: (error) => {
            console.error('Error with websocket', error);
        },
        onStompError: (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        },
    });

    stompClient.activate();
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient) {
        stompClient.deactivate();
        console.log('Disconnected');
    }
}

export function sendMessage(message) {
    if (stompClient && stompClient.connected) {
        stompClient.publish({
            destination: "/app/changeMessage",
            body: JSON.stringify(message)
        });
    }
}