package org.example;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class APIRequest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final OkHttpClient client;
    private final Gson gson;

    public APIRequest() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    // Creating User
    public String createUser(Map<String, String> userData) throws IOException {
        String url = BASE_URL + "/users";
        String jsonString = gson.toJson(userData);
        RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    // Updating User
    public String updateUser(int userId, Map<String, String> userData) throws IOException {
        String url = BASE_URL + "/users/" + userId;
        String jsonString = gson.toJson(userData);
        RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    // Deleting User
    public boolean deleteUser(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId;
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }

    //  Get info of all users
    public List<Map<String, Object>> getAllUsers() throws IOException {
        String url = BASE_URL + "/users";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), List.class);
        }
    }

    //  Get info of a user with specific id
    public Map<String, Object> getUserById(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), Map.class);
        }
    }

    // Get info of a user with specific Username
    public Map<String, Object> getUserByUsername(String username) throws IOException {
        String url = BASE_URL + "/users?username=" + username;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            List<Map<String, Object>> users = gson.fromJson(response.body().string(), List.class);
            return users.isEmpty() ? null : users.get(0);
        }
    }

    // Get posts of a user
    public List<Map<String, Object>> getPosts(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId + "/posts";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), List.class);
        }
    }

    // Get the last post of a user
    public Map<String, Object> getLastPost(int userId) throws IOException {
        List<Map<String, Object>> posts = getPosts(userId);
        return posts.isEmpty() ? null : posts.get(posts.size() - 1);
    }

    // Get comments of a post
    public List<Map<String, Object>> getComments(int postId) throws IOException {
        String url = BASE_URL + "/posts/" + postId + "/comments";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), List.class);
        }
    }

    // Write comments to a file
    public void writeCommentsToFile(int userId, int postId, List<Map<String, Object>> comments) throws IOException {
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        try (FileWriter file = new FileWriter(fileName)) {
            gson.toJson(comments, file);
        }
        System.out.println("Comments for the last post of user " + userId + " saved to " + fileName);
    }

    // Вывод всех открытых задач для пользователя
    public List<Map<String, Object>> getOpenTasks(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId + "/todos";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            List<Map<String, Object>> todos = gson.fromJson(response.body().string(), List.class);
            List<Map<String, Object>> openTodos = new ArrayList<>();
            for (Map<String, Object> todo : todos) {
                if (Boolean.FALSE.equals(todo.get("completed"))) {
                    openTodos.add(todo);
                }
            }
            return openTodos;
        }
    }
}