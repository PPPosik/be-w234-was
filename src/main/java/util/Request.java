package util;

import enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final HttpMethod method;
    private final String path;
    private final String version;

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> body = new HashMap<>();

    Request(String method, String path, String version) {
        this.method = HttpMethod.getByMethod(method);
        this.path = path;
        this.version = version;
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addBody(String name, String value) {
        body.put(name, value);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getBody() {
        return body;
    }
}
