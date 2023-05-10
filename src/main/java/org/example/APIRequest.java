package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class APIRequest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final OkHttpClient client;
    private final Gson gson;
    private final Type userListType = new TypeToken<ArrayList<User>>() {
    }.getType();
    private final Type postListType = new TypeToken<ArrayList<Post>>() {
    }.getType();
    private final Type commentListType = new TypeToken<ArrayList<Comment>>() {
    }.getType();
    private final Type todoListType = new TypeToken<ArrayList<Todo>>() {
    }.getType();

    public APIRequest() {
        client = new OkHttpClient();
        gson = new Gson();
    }

    // Creating User
    public User createUser(User user) throws IOException {
        String url = BASE_URL + "/users";
        String jsonString = gson.toJson(user);
        RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), User.class);
        }
    }

    // Updating User
    public User updateUser(int userId, User user) throws IOException {
        String url = BASE_URL + "/users/" + userId;
        String jsonString = gson.toJson(user);
        RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), User.class);
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
    public List<User> getAllUsers() throws IOException {
        String url = BASE_URL + "/users";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), userListType);
        }
    }

    //  Get info of a user with specific id
    public User getUserById(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), User.class);
        }
    }

    // Get info of a user with specific Username
    public User getUserByUsername(String username) throws IOException {
        String url = BASE_URL + "/users?username=" + username;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            List<User> users = gson.fromJson(response.body().string(), userListType);
            return users.isEmpty() ? null : users.get(0);
        }
    }

    // Get posts of a user
    public List<Post> getPosts(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId + "/posts";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), postListType);
        }
    }

    // Get the last post of a user
    public Post getLastPost(int userId) throws IOException {
        List<Post> posts = getPosts(userId);
        return posts.isEmpty() ? null : posts.get(posts.size() - 1);
    }

    // Get comments of a post
    public List<Comment> getComments(int postId) throws IOException {
        String url = BASE_URL + "/posts/" + postId + "/comments";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), commentListType);
        }
    }

    // Write comments to a file
    public void writeCommentsToFile(int userId, int postId, List<Comment> comments) throws IOException {
        String fileName = "user-" + userId + "-post-" + postId + "-comments.json";
        try (FileWriter file = new FileWriter(fileName)) {
            gson.toJson(comments, file);
        }
        System.out.println("Comments for the last post of user " + userId + " saved to " + fileName);
    }

    // Get all open tasks for a user
    public List<Todo> getOpenTasks(int userId) throws IOException {
        String url = BASE_URL + "/users/" + userId + "/todos";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            List<Todo> todos = gson.fromJson(response.body().string(), todoListType);
            List<Todo> openTodos = new ArrayList<>();
            for (Todo todo : todos) {
                if (!todo.isCompleted()) {
                    openTodos.add(todo);
                }
            }
            return openTodos;
        }
    }
}