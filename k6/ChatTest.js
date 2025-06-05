import http from 'k6/http';
import { sleep } from 'k6';
import { check } from 'k6';

export const options = {
  vus: 1000,
  duration: '30s',
};

export default function () {
  const url = 'http://localhost:3000/v1/api/chats';

  const senderId = (__VU % 2) + 1; // 1 또는 2

  const payload = JSON.stringify({
    senderId: senderId,
    chatRoomId: 1,
    content: "메시지입니다".repeat(10),
    messageType: "CHAT"
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

 const res = http.post(url, payload, params);
 sleep(1);

 check(res, {
   'status is 200': (r) => r.status === 200,
   'response time < 200ms': (r) => r.timings.duration < 200
 });
}
