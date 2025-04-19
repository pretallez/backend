package com.pretallez.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.common.fixture.BoardFixture;
import com.pretallez.common.fixture.TestFixtureFactory;
import com.pretallez.domain.posting.dto.board.BoardCreate;
import com.pretallez.domain.posting.service.BoardService;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("게시글 서비스 통합 테스트")
public class BoardServiceImplIntegrationTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private TestFixtureFactory testFixtureFactory;

    private Member savedMember ;

    @BeforeEach()
    public void setUp() {
        savedMember = testFixtureFactory.createMember();
    }


    @Test
    @DisplayName("게시글 작성 시 ,성공적으로 Board 엔티티 생성되고 필드 값이 제대로 저장되었는지 확인")
    public void addBoard() {
        //given
        BoardCreate.Request boardCreateRequest = BoardFixture.boardCreateRequest();
        Long writerId = savedMember.getId();

        //when
        Board board = boardService.addBoard(boardCreateRequest,writerId);

        //then
        Assertions.assertEquals(savedMember.getId(), board.getMember().getId());
        Assertions.assertEquals(board.getTitle(), boardCreateRequest.getTitle());
        Assertions.assertEquals(board.getBoardType(), boardCreateRequest.getBoardType());
        Assertions.assertEquals(board.getContent(), boardCreateRequest.getContent());
    }
}
