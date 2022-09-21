package util;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private final Map<String, String> data;

    public Cookie() {
        this.data = new HashMap<>();
    }

    public String get(String name) {
        return data.get(name);
    }

    public void put(String name, String value) {
        data.put(name, value);
    }

    public int getSize() {
        return data.size();
    }

    public Map<String, String> getAll() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }

        return sb.toString();
    }
}
