package com.pretallez.common.fixture;

import com.pretallez.domain.board.dto.BoardCreate;
import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.Member;
import com.pretallez.common.enums.BoardType;
import org.springframework.test.util.ReflectionTestUtils;

public class BoardFixture {

    public static Board board(Member member) {
        String title = "[펜싱그라운드] 재밌게 펜싱하실 분 구해요";
        String content = "안녕하세요! 펜싱을 좋아하시는 분들, 많은 참여 부탁드립니다! 펜싱그라운드에서 뵐게요~";
        BoardType boardType = BoardType.RENTAL_SERVICE;

        return Board.of(member, title, content, boardType);
    }

    public static Board fakeBoard(Long id, Member member) {
        String title = "[펜싱그라운드] 재밌게 펜싱하실 분 구해요";
        String content = "안녕하세요! 펜싱을 좋아하시는 분들, 많은 참여 부탁드립니다! 펜싱그라운드에서 뵐게요~";
        BoardType boardType = BoardType.RENTAL_SERVICE;
        Board board = Board.of(member, title, content, boardType);
        ReflectionTestUtils.setField(board, "id", id);

        return board;
    }

    public static BoardCreate.Request boardCreateRequest() {
        String title = "[펜싱그라운드] 재밌게 펜싱하실 분 구해요";
        String content = "안녕하세요! 펜싱을 좋아하시는 분들, 많은 참여 부탁드립니다! 펜싱그라운드에서 뵐게요~";
        BoardType boardType = BoardType.RENTAL_SERVICE;

        return new BoardCreate.Request(title, content, boardType);
    }
}
