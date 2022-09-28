package util.http;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.http.Request;
import util.http.RequestParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParserTest {

    @Test
    void testRequestParsing() throws Exception {
        InputStream in1 = new ByteArrayInputStream((
                "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: login=true; Expires=Wed").getBytes());
        InputStream in2 = new ByteArrayInputStream((
                "A A\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").getBytes());

        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1, Charsets.UTF_8));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2, Charsets.UTF_8));

        // index.html 호출
        Request request1 = new RequestParser(br1).parse();

        assertThat(request1.getMethod().getMethod()).isEqualTo("GET");
        assertThat(request1.getPath()).isEqualTo("/index.html");
        assertThat(request1.getVersion()).isEqualTo("HTTP/1.1");

        assertThat(request1.getHeaders().size()).isEqualTo(3);
        assertThat(request1.getHeaders().get("host")).isEqualTo("localhost:8080");
        assertThat(request1.getHeaders().get("connection")).isEqualTo("keep-alive");
        assertThat(request1.getHeaders().get("accept")).isEqualTo("*/*");

        assertThat(request1.getCookie().getSize()).isEqualTo(2);
        assertThat(request1.getCookie().get("login")).isEqualTo("true");
        assertThat(request1.getCookie().get("Expires")).isEqualTo("Wed");

        Assertions.assertThrows(Exception.class, () -> {
            // 유효하지 않은 호출
            Request request2 = new RequestParser(br2).parse();
        });
    }
}