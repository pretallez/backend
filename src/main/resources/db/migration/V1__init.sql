CREATE TABLE IF NOT EXISTS Board
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    board_type enum ('ONE_DAY_CLASS', 'OPEN_PISTE', 'RENTAL_SERVICE')      NOT NULL,
    content    LONGTEXT    NOT NULL,
    created_at datetime    NOT NULL,
    deleted_at datetime NULL,
    title      VARCHAR(30) NOT NULL,
    updated_at datetime NULL,
    writer_id  BIGINT      NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Chat
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    chatroom_id  BIGINT       NOT NULL,
    content      VARCHAR(500) NOT NULL,
    created_at   datetime     NOT NULL,
    member_id    BIGINT       NOT NULL,
    message_type TINYINT(4)            NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Chatroom
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    board_title VARCHAR(255) NULL,
    created_at  datetime NOT NULL,
    notice      VARCHAR(255) NULL,
    votepost_id BIGINT   NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ClubMember
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    fencing_club_id BIGINT NOT NULL,
    member_id       BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS FencingClub
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    address       VARCHAR(50) NOT NULL,
    contact       VARCHAR(20) NOT NULL,
    `description` LONGTEXT NULL,
    gear_exist_yn VARCHAR(1)  NOT NULL,
    type          VARCHAR(20) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Member
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime     NOT NULL,
    deleted_at   datetime NULL,
    email        VARCHAR(50)  NOT NULL,
    gender       VARCHAR(1)   NOT NULL,
    manner_level enum ('BEGINNER', 'DIAMOND', 'GOLD', 'PLATINUM', 'SILVER')   NOT NULL,
    name         VARCHAR(10)  NOT NULL,
    nickname     VARCHAR(20)  NOT NULL,
    phone_num    VARCHAR(255) NOT NULL,
    point        INT          NOT NULL,
    updated_at   datetime NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MemberChatroom
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    chatroom_id BIGINT NOT NULL,
    member_id   BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MemberPoint
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime NOT NULL,
    point_amount INT      NOT NULL,
    point_type   ENUM ('CHARGE', 'REFUND', 'USE')   NOT NULL,
    member_id    BIGINT   NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MemberRole
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    member_id BIGINT NOT NULL,
    role_id   BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Payment
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    paid_amount INT    NOT NULL,
    status      TINYINT(4)            NOT NULL,
    member_id   BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS PostMember
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    paid_points INT    NOT NULL,
    member_id   BIGINT NOT NULL,
    votepost_id BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Role`
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name enum ('ADMIN', 'MANAGER', 'PENDING', 'USER') NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS VotePost
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    max_capacity    INT      NOT NULL,
    min_capacity    INT      NOT NULL,
    total_amount    INT      NOT NULL,
    training_date   datetime NOT NULL,
    board_id        BIGINT   NOT NULL,
    fencing_club_id BIGINT   NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

DELIMITER //

CREATE OR REPLACE PROCEDURE add_constraint_uk47h6ufe8lcnv9gr5krnepo2uh()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'VotePost' AND constraint_name = 'UK47h6ufe8lcnv9gr5krnepo2uh' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE VotePost ADD CONSTRAINT UK47h6ufe8lcnv9gr5krnepo2uh UNIQUE (board_id);
    END IF;
END //
CALL add_constraint_uk47h6ufe8lcnv9gr5krnepo2uh() //
DROP PROCEDURE IF EXISTS add_constraint_uk47h6ufe8lcnv9gr5krnepo2uh //


CREATE OR REPLACE PROCEDURE add_constraint_ukjgnadc5a7jdx6wcm0qegeo09x()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'MemberChatroom' AND constraint_name = 'UKjgnadc5a7jdx6wcm0qegeo09x' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE MemberChatroom ADD CONSTRAINT UKjgnadc5a7jdx6wcm0qegeo09x UNIQUE (member_id, chatroom_id);
    END IF;
END //

CALL add_constraint_ukjgnadc5a7jdx6wcm0qegeo09x() //
DROP PROCEDURE IF EXISTS add_constraint_ukjgnadc5a7jdx6wcm0qegeo09x //


CREATE OR REPLACE PROCEDURE add_constraint_ukrvcjjygo4xkd24ehwqqaskjwf()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'PostMember' AND constraint_name = 'UKrvcjjygo4xkd24ehwqqaskjwf' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE PostMember ADD CONSTRAINT UKrvcjjygo4xkd24ehwqqaskjwf UNIQUE (member_id);
    END IF;
END //

CALL add_constraint_ukrvcjjygo4xkd24ehwqqaskjwf() //
DROP PROCEDURE IF EXISTS add_constraint_ukrvcjjygo4xkd24ehwqqaskjwf //


CREATE OR REPLACE PROCEDURE add_constraint_uks1yp1gupk72um38pmp98bi0qr()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'Chatroom' AND constraint_name = 'UKs1yp1gupk72um38pmp98bi0qr' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE Chatroom ADD CONSTRAINT UKs1yp1gupk72um38pmp98bi0qr UNIQUE (votepost_id);
    END IF;
END //

CALL add_constraint_uks1yp1gupk72um38pmp98bi0qr() //
DROP PROCEDURE IF EXISTS add_constraint_uks1yp1gupk72um38pmp98bi0qr //


CREATE OR REPLACE PROCEDURE add_constraint_fk5yts4o3b7nt0d5wq440pl3kfx()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'MemberPoint' AND constraint_name = 'FK5yts4o3b7nt0d5wq440pl3kfx' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE MemberPoint ADD CONSTRAINT FK5yts4o3b7nt0d5wq440pl3kfx FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fk5yts4o3b7nt0d5wq440pl3kfx() //
DROP PROCEDURE IF EXISTS add_constraint_fk5yts4o3b7nt0d5wq440pl3kfx //


CREATE OR REPLACE PROCEDURE add_constraint_fk6d76bjkolny0ctce8b0d2vpk2()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'PostMember' AND constraint_name = 'FK6d76bjkolny0ctce8b0d2vpk2' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE PostMember ADD CONSTRAINT FK6d76bjkolny0ctce8b0d2vpk2 FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fk6d76bjkolny0ctce8b0d2vpk2() //
DROP PROCEDURE IF EXISTS add_constraint_fk6d76bjkolny0ctce8b0d2vpk2 //


CREATE OR REPLACE PROCEDURE add_constraint_fk77qmelsuo616hn1d1sxlk69hh()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'Chatroom' AND constraint_name = 'FK77qmelsuo616hn1d1sxlk69hh' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE Chatroom ADD CONSTRAINT FK77qmelsuo616hn1d1sxlk69hh FOREIGN KEY (votepost_id) REFERENCES VotePost (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fk77qmelsuo616hn1d1sxlk69hh() //
DROP PROCEDURE IF EXISTS add_constraint_fk77qmelsuo616hn1d1sxlk69hh //


CREATE OR REPLACE PROCEDURE add_constraint_fk9nw0ouvt324f8yolgy9w9pwc5()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'ClubMember' AND constraint_name = 'FK9nw0ouvt324f8yolgy9w9pwc5' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE ClubMember ADD CONSTRAINT FK9nw0ouvt324f8yolgy9w9pwc5 FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fk9nw0ouvt324f8yolgy9w9pwc5() //
DROP PROCEDURE IF EXISTS add_constraint_fk9nw0ouvt324f8yolgy9w9pwc5 //


CREATE OR REPLACE PROCEDURE add_constraint_fk9rv1c0lieigyd3eey5qt3toal()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'VotePost' AND constraint_name = 'FK9rv1c0lieigyd3eey5qt3toal' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE VotePost ADD CONSTRAINT FK9rv1c0lieigyd3eey5qt3toal FOREIGN KEY (fencing_club_id) REFERENCES FencingClub (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fk9rv1c0lieigyd3eey5qt3toal() //
DROP PROCEDURE IF EXISTS add_constraint_fk9rv1c0lieigyd3eey5qt3toal //


CREATE OR REPLACE PROCEDURE add_constraint_fkgl9t48pk7h1n9ncecjjobje1k()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'Payment' AND constraint_name = 'FKgl9t48pk7h1n9ncecjjobje1k' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE Payment ADD CONSTRAINT FKgl9t48pk7h1n9ncecjjobje1k FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkgl9t48pk7h1n9ncecjjobje1k() //
DROP PROCEDURE IF EXISTS add_constraint_fkgl9t48pk7h1n9ncecjjobje1k //


CREATE OR REPLACE PROCEDURE add_constraint_fkhlp03ovorw0aqsnlv3bo29824()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'VotePost' AND constraint_name = 'FKhlp03ovorw0aqsnlv3bo29824' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE VotePost ADD CONSTRAINT FKhlp03ovorw0aqsnlv3bo29824 FOREIGN KEY (board_id) REFERENCES Board (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkhlp03ovorw0aqsnlv3bo29824() //
DROP PROCEDURE IF EXISTS add_constraint_fkhlp03ovorw0aqsnlv3bo29824 //


CREATE OR REPLACE PROCEDURE add_constraint_fkjnd2ug7q3jae5unnb0511w25g()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'MemberChatroom' AND constraint_name = 'FKjnd2ug7q3jae5unnb0511w25g' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE MemberChatroom ADD CONSTRAINT FKjnd2ug7q3jae5unnb0511w25g FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkjnd2ug7q3jae5unnb0511w25g() //
DROP PROCEDURE IF EXISTS add_constraint_fkjnd2ug7q3jae5unnb0511w25g //


CREATE OR REPLACE PROCEDURE add_constraint_fknven39442jqla3sjp2hxkb5um()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'PostMember' AND constraint_name = 'FKnven39442jqla3sjp2hxkb5um' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE PostMember ADD CONSTRAINT FKnven39442jqla3sjp2hxkb5um FOREIGN KEY (votepost_id) REFERENCES VotePost (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fknven39442jqla3sjp2hxkb5um() //
DROP PROCEDURE IF EXISTS add_constraint_fknven39442jqla3sjp2hxkb5um //


CREATE OR REPLACE PROCEDURE add_constraint_fkp3iwec7acnxv7qemq7kue3ho8()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'MemberRole' AND constraint_name = 'FKp3iwec7acnxv7qemq7kue3ho8' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE MemberRole ADD CONSTRAINT FKp3iwec7acnxv7qemq7kue3ho8 FOREIGN KEY (role_id) REFERENCES `Role` (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkp3iwec7acnxv7qemq7kue3ho8() //
DROP PROCEDURE IF EXISTS add_constraint_fkp3iwec7acnxv7qemq7kue3ho8 //


CREATE OR REPLACE PROCEDURE add_constraint_fkpj71wwo1cv27l8alv19dqpfp9()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'MemberRole' AND constraint_name = 'FKpj71wwo1cv27l8alv19dqpfp9' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE MemberRole ADD CONSTRAINT FKpj71wwo1cv27l8alv19dqpfp9 FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkpj71wwo1cv27l8alv19dqpfp9() //
DROP PROCEDURE IF EXISTS add_constraint_fkpj71wwo1cv27l8alv19dqpfp9 //

CREATE OR REPLACE PROCEDURE add_constraint_fkqoddbv2vksjutcf5gmmqr1m15()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'MemberChatroom' AND constraint_name = 'FKqoddbv2vksjutcf5gmmqr1m15' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE MemberChatroom ADD CONSTRAINT FKqoddbv2vksjutcf5gmmqr1m15 FOREIGN KEY (chatroom_id) REFERENCES Chatroom (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkqoddbv2vksjutcf5gmmqr1m15() //
DROP PROCEDURE IF EXISTS add_constraint_fkqoddbv2vksjutcf5gmmqr1m15 //


CREATE OR REPLACE PROCEDURE add_constraint_fksmfktwaojcekjww70pn7lwt8c()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'ClubMember' AND constraint_name = 'FKsmfktwaojcekjww70pn7lwt8c' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE ClubMember ADD CONSTRAINT FKsmfktwaojcekjww70pn7lwt8c FOREIGN KEY (fencing_club_id) REFERENCES FencingClub (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fksmfktwaojcekjww70pn7lwt8c() //
DROP PROCEDURE IF EXISTS add_constraint_fksmfktwaojcekjww70pn7lwt8c //


CREATE OR REPLACE PROCEDURE add_constraint_fkte8pt9idsi3g09biuignr6d3m()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'Board' AND constraint_name = 'FKte8pt9idsi3g09biuignr6d3m' AND constraint_schema = DATABASE()
    ) THEN
        ALTER TABLE Board ADD CONSTRAINT FKte8pt9idsi3g09biuignr6d3m FOREIGN KEY (writer_id) REFERENCES Member (id) ON DELETE NO ACTION;
    END IF;
END //

CALL add_constraint_fkte8pt9idsi3g09biuignr6d3m() //
DROP PROCEDURE IF EXISTS add_constraint_fkte8pt9idsi3g09biuignr6d3m //

DELIMITER ;

CREATE INDEX IF NOT EXISTS FK5yts4o3b7nt0d5wq440pl3kfx ON MemberPoint (member_id);

CREATE INDEX IF NOT EXISTS FK9nw0ouvt324f8yolgy9w9pwc5 ON ClubMember (member_id);

CREATE INDEX IF NOT EXISTS FK9rv1c0lieigyd3eey5qt3toal ON VotePost (fencing_club_id);

CREATE INDEX IF NOT EXISTS FKgl9t48pk7h1n9ncecjjobje1k ON Payment (member_id);

CREATE INDEX IF NOT EXISTS FKnven39442jqla3sjp2hxkb5um ON PostMember (votepost_id);

CREATE INDEX IF NOT EXISTS FKp3iwec7acnxv7qemq7kue3ho8 ON MemberRole (role_id);

CREATE INDEX IF NOT EXISTS FKpj71wwo1cv27l8alv19dqpfp9 ON MemberRole (member_id);

CREATE INDEX IF NOT EXISTS FKqoddbv2vksjutcf5gmmqr1m15 ON MemberChatroom (chatroom_id);

CREATE INDEX IF NOT EXISTS FKsmfktwaojcekjww70pn7lwt8c ON ClubMember (fencing_club_id);
