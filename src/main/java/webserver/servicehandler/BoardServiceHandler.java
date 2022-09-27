package webserver.servicehandler;

import enums.HttpMethod;
import enums.HttpStatusCode;
import enums.Mime;
import exception.http.BadRequestException;
import exception.http.HttpException;
import exception.http.UnauthorizedUserException;
import model.Board;
import util.http.Cookie;
import util.http.Request;
import util.http.Response;
import webserver.service.BoardService;

import java.util.List;

public class BoardServiceHandler implements ServiceHandler {
    private final BoardService service;

    public BoardServiceHandler(BoardService boardService) {
        this.service = boardService;
    }

    @Override
    public Response handle(Request request) throws HttpException {
        String path = request.getPath();

        if (request.getMethod() == HttpMethod.POST && "/board".equals(path)) {
            return saveBoard(request);
        } else if (request.getMethod() == HttpMethod.GET && "/board/list".equals(path)) {
            return getBoardList(request);
        } else {
            throw new BadRequestException(request.getMethod().getMethod() + " " + request.getPath() + "은 잘못된 요청입니다.");
        }
    }

    private Response saveBoard(Request request) throws UnauthorizedUserException {
        if (canWriteBoard(request.getCookie())) {
            service.saveBoard(request.getCookie().get("id"), request.getBody().get("content"));
        } else {
            throw new UnauthorizedUserException("로그인한 사용자만 게시글을 작성할 수 있습니다.");
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
