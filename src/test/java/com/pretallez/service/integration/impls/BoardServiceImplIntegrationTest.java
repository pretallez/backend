package com.pretallez.service.integration.impls;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.Member;
import com.pretallez.repository.MemberRepository;
import com.pretallez.service.BoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceImplIntegrationTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember ;

    @BeforeEach()
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        savedMember = memberRepository.save(Fixture.member());
    }


    @Test
    @DisplayName("게시글 작성 시 ,성공적으로 Board 엔티티 생성되고 필드 값이 제대로 저장되었는지 확인")
    public void addBoard() {
        //given
        BoardCreate.Request boardCreateRequest = Fixture.boardCreateRequest();
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
