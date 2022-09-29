package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import enums.Mime;
import exception.BoardSaveException;
import exception.PageNotFoundException;
import model.Board;
import util.Cookie;
import util.Request;
import util.Response;
import webserver.service.BoardService;

import java.util.List;

public class BoardServiceHandler implements ServiceHandler {
    private final BoardService service;

    public BoardServiceHandler(BoardService boardService) {
        this.service = boardService;
    }

    @Override
    public Response handle(Request request) {
        String path = request.getPath();

        if (request.getMethod() == HttpMethod.POST && "/board".equals(path)) {
            return saveBoard(request);
        } else if (request.getMethod() == HttpMethod.GET && "/board/list".equals(path)) {
            return getBoardList(request);
        } else {
            throw new PageNotFoundException(request.getPath() + " 를 찾을 수 없습니다.");
        }
    }

    private Response saveBoard(Request request) {
        if (canWriteBoard(request.getCookie())) {
            service.saveBoard(request.getCookie().get("id"), request.getBody().get("content"));
        } else {
            throw new BoardSaveException("로그인한 사용자만 게시글을 작성할 수 있습니다.");
        }

        return new Response()
                .setHttpStatusCode(HttpStatusCode.CREATED)
                .setHeader("Content-Type", Mime.NONE.getMime() + ";charset=utf-8")
                .setBody("게시글 작성에 성공했습니다.");
    }

    private boolean canWriteBoard(Cookie cookie) {
        return cookie != null && "true".equals(cookie.get("logined"));
    }

    private Response getBoardList(Request request) {
        List<Board> boardList = service.getBoardList();

        return new Response()
                .setHttpStatusCode(HttpStatusCode.OK)
                .setHeader("Content-Type", Mime.NONE.getMime() + ";charset=utf-8")
                .setBody(generateBoardListBody(boardList));
    }

    private String generateBoardListBody(List<Board> boardList) {
        StringBuilder sb = new StringBuilder();

        for (Board board : boardList) {
            sb.append("boardId=").append(board.getId()).append("\n")
              .append("createdAt=").append(board.getCreatedAt()).append("\n")
              .append("author=").append(board.getAuthor()).append("\n")
              .append("content=").append(board.getContent()).append("\n\n");
        }

        return sb.toString();
    }
}
