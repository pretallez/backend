package com.pretallez.service.unit.impls;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.Member;
import com.pretallez.repository.BoardRepository;
import com.pretallez.repository.MemberRepository;
import com.pretallez.service.MemberService;
import com.pretallez.service.impls.BoardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;

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
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        savedMember = Fixture.member();
        Mockito.doReturn(savedMember).when(memberRepository).save(Mockito.any(Member.class));
        Mockito.when(memberService.getMember(Mockito.anyLong())).thenReturn(savedMember);
    }

    @Test
    @DisplayName("게시판 생성 시, 정상적으로 생성되고 작성자 정보가 올바르게 저장되는지 확인")
    public void addBoard_Success() throws NoSuchFieldException, IllegalAccessException {
        // given
        Long memberFakeId = 1L;
        BoardCreate.Request boardCreateRequest = Fixture.boardCreateRequest();
        Mockito.when(boardRepository.save(Mockito.any())).thenReturn(Fixture.board(savedMember));

        // when
        Board board = boardService.addBoard(boardCreateRequest, memberFakeId);

        // then
        Assertions.assertEquals(board.getMember().getId(), memberFakeId);
        Assertions.assertEquals(boardCreateRequest.getTitle(), board.getTitle());
        Assertions.assertEquals(boardCreateRequest.getBoardType(), board.getBoardType());
        Assertions.assertEquals(boardCreateRequest.getContent(), board.getContent());
    }
}
