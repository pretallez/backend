import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 100,
  duration: '30s',
};

export default function () {
  const url = 'http://localhost:3000/v1/api/chats';

  const memberId = (__VU % 2) + 1;

  const payload = JSON.stringify({
    memberId: memberId,
    chatroomId: 1,
    content: "메시지입니다".repeat(10),
    messageType: "CHAT"
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

    http.post(url, payload, params);
    sleep(1);
}
