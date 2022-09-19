package model;

import com.google.gson.Gson;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return new Gson().toJson("User [userId=" + userId + ", name=" + name + ", email=" + email + "]");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.userId.equals(((User) obj).userId);
        } else {
            return false;
        }
    }
}
