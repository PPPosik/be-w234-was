package util;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final String method;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    private final String path;
    private final String version;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();

    Request(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }
}
