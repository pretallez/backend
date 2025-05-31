-- 회원 더미 데이터
INSERT INTO member (email, nickname, name, manner_level, phone_num, gender, point, created_at)
VALUES
('jylim@example.com', '임종엽', '임종엽', 1, '010-1234-5678', 'M', 1000, CURRENT_TIMESTAMP),
('shkim@example.com', '김성호', '김성호', 1, '010-9876-5432', 'M', 2000, CURRENT_TIMESTAMP),
('iyEom@example.com', '엄인용', '엄인용', 1, '010-2345-6789', 'M', 3000, CURRENT_TIMESTAMP);

-- 펜싱클럽 더미 데이터
INSERT INTO fencing_club (type, contact, address, description, gear_exist_yn)
VALUES
('FOIL', '010-1111-2222', '서울', '중앙가르드 펜싱클럽', 'Y'),
('SABRE', '010-3333-4444', '의정부', '의정부 펜싱클럽', 'N');

-- 게시판 더미 데이터
INSERT INTO board (writer_id, title, content, created_at, board_type)
VALUES
(1, '첫 번째 게시글', '게시글 내용1', CURRENT_TIMESTAMP, 'OPEN_PISTE'),
(2, '두 번째 게시글', '게시글 내용2', CURRENT_TIMESTAMP, 'OPEN_PISTE'),
(3, '세 번째 게시글', '게시글 내용3', CURRENT_TIMESTAMP, 'OPEN_PISTE');

-- 투표글 더미 데이터
INSERT INTO vote_post (board_id, fencing_club_id, max_capacity, min_capacity, total_amount, training_date)
VALUES
(1, 1, 10, 2, 5000, CURRENT_TIMESTAMP),
(2, 2, 8, 4, 6000, CURRENT_TIMESTAMP),
(3, 2, 8, 4, 6000, CURRENT_TIMESTAMP);

-- 채팅방 더미 데이터
insert into chat_room (votepost_id, created_at, board_title) values (1, CURRENT_TIMESTAMP, '채팅방제목1');
insert into chat_room (votepost_id, created_at, board_title) values (2, CURRENT_TIMESTAMP, '채팅방제목2');

-- 회원 채팅방 더미 데이터
insert into participation (member_id, chat_room_id) values (1, 1);
insert into participation (member_id, chat_room_id) values (1, 2);
insert into participation (member_id, chat_room_id) values (2, 1);
insert into participation (member_id, chat_room_id) values (2, 2);