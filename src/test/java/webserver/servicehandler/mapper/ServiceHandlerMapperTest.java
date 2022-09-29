package webserver.servicehandler.mapper;

import org.junit.jupiter.api.Test;
import webserver.servicehandler.BoardServiceHandler;
import webserver.servicehandler.StaticFileServiceHandler;
import webserver.servicehandler.UserServiceHandler;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceHandlerMapperTest {

    @Test
    void userHandlerTest() {
        assertThat(ServiceHandlerMapper.getHandler("/user")).isInstanceOf(UserServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/user/create")).isInstanceOf(UserServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/user/login")).isInstanceOf(UserServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/user/list")).isInstanceOf(UserServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/user/aaa/bbb/ccc")).isInstanceOf(UserServiceHandler.class);
    }

    @Test
    void boardHandlerTest() {
        assertThat(ServiceHandlerMapper.getHandler("/board")).isInstanceOf(BoardServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/board/list")).isInstanceOf(BoardServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/board/aaa/bbb/ccc")).isInstanceOf(BoardServiceHandler.class);
    }

    @Test
    void staticFileHandlerTest() {
        assertThat(ServiceHandlerMapper.getHandler("/index.html")).isInstanceOf(StaticFileServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/css/styles.css")).isInstanceOf(StaticFileServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/js/scripts.js")).isInstanceOf(StaticFileServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/font/glyphicons-halflings-regular.ttf")).isInstanceOf(StaticFileServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/user/form.html")).isInstanceOf(StaticFileServiceHandler.class);
        assertThat(ServiceHandlerMapper.getHandler("/user/login.html")).isInstanceOf(StaticFileServiceHandler.class);
    }
}