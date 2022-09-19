package util;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String method;

    HttpMethod(String method) {
        this.method = method.toUpperCase();
    }

    public String getMethod() {
        return method;
    }

    public static HttpMethod getByMethod(String method) {
        for (HttpMethod hm : values()) {
            if (hm.method.equals(method.toUpperCase())) {
                return hm;
            }
        }

        throw new IllegalArgumentException("Invalid method: " + method);
    }
}
