package util.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.base.Charsets;
import exception.http.BadRequestException;
import exception.http.HttpException;
import exception.http.RequestParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Optional;

public class RequestParser {
    private final String COOKIE_HEADER = "cookie";

    private final BufferedReader br;
    private Request request;

    public RequestParser(BufferedReader br) {
        this.br = br;
    }

    public Request parse() throws HttpException {
        if (request == null) {
            try {
                String requestLine = br.readLine();
                parseRequestLine(requestLine);
                parseHeaders(br);

                int contentLength = Integer.parseInt(Optional.ofNullable(request.getHeaders().get("content-length")).orElse("0"));

                if (contentLength > 0) {
                    parseBody(contentLength);
                }
            } catch (Exception e) {
                throw new RequestParsingException(e.getMessage());
            }
        }

        return request;
    }

    private void parseRequestLine(String str) throws BadRequestException {
        String[] splits = str.split(" ");

        if (splits.length != 3) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        String[] pathWithParam = splits[1].split("\\?", 2);
        this.request = new Request(splits[0], pathWithParam[0], splits[2]);

        if (pathWithParam.length > 1) {
            parseParams(pathWithParam[1]);
        }
    }

    private void parseParams(String params) {
        for (String q : params.split("&")) {
            String[] p = q.split("=");
            if (p.length == 2) {
                request.addParam(URLDecoder.decode(p[0], Charsets.UTF_8), URLDecoder.decode(p[1], Charsets.UTF_8));
            }
        }
    }

    private void parseHeaders(BufferedReader br) throws IOException {
        String line;

        while (StringUtils.isNotBlank(line = br.readLine())) {
            String[] splits = line.split(":", 2);
            if (splits.length == 2) {
                String headerName = splits[0].trim().toLowerCase();
                String headerValue = splits[1].trim();

                if (headerName.equals(COOKIE_HEADER)) {
                    parseCookie(headerValue);
                } else {
                    request.addHeader(headerName, headerValue);
                }
            }
        }
    }

    private void parseCookie(String values) {
        for (String value : values.split(";")) {
            String[] splits = value.split("=", 2);

            if (splits.length == 2) {
                request.addCookie(splits[0].trim(), splits[1].trim());
            }
        }
    }

    private void parseBody(int contentLength) throws IOException {
        String bodyStr = readBody(contentLength);

        for (String q : bodyStr.split("&")) {
            String[] p = q.split("=");
            if (p.length == 2) {
                request.addBody(URLDecoder.decode(p[0], Charsets.UTF_8), URLDecoder.decode(p[1], Charsets.UTF_8));
            }
        }
    }

    private String readBody(int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
