package com.pretallez.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.common.fixture.BoardFixture;
import com.pretallez.common.fixture.MemberFixture;
import com.pretallez.domain.posting.dto.board.BoardCreate;
import com.pretallez.domain.posting.repository.BoardRepository;
import com.pretallez.domain.posting.service.BoardServiceImpl;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.member.service.MemberService;

@ActiveProfiles("local")
@DisplayName("게시글 서비스 단위 테스트")
public class BoardServiceImplUnitTest {

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardServiceImpl boardService; // 테스트 대상 클래스

    private Member savedMember;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        savedMember = MemberFixture.fakeMember(1L);
        Mockito.doReturn(savedMember).when(memberRepository).save(Mockito.any(Member.class));
        Mockito.when(memberService.getMember(Mockito.anyLong())).thenReturn(savedMember);
    }

    @Test
    @DisplayName("게시판 생성 시, 정상적으로 생성되고 작성자 정보가 올바르게 저장되는지 확인")
    public void addBoard_Success() {
        // given
        Long memberFakeId = 1L;
        BoardCreate.Request boardCreateRequest = BoardFixture.boardCreateRequest();
        Mockito.when(boardRepository.save(Mockito.any())).thenReturn(BoardFixture.fakeBoard(1L, savedMember));

        // when
        Board board = boardService.addBoard(boardCreateRequest, memberFakeId);

        // then
        Assertions.assertEquals(board.getMember().getId(), memberFakeId);
        Assertions.assertEquals(boardCreateRequest.getTitle(), board.getTitle());
        Assertions.assertEquals(boardCreateRequest.getBoardType(), board.getBoardType());
        Assertions.assertEquals(boardCreateRequest.getContent(), board.getContent());
    }
}
