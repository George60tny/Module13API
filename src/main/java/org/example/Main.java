package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        APIRequest apiRequest = new APIRequest();

        // Create a new user
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "John Doe");
        userData.put("username", "johndoe");
        userData.put("email", "johndoe@example.com");

        try {
            String response = apiRequest.createUser(userData);
            System.out.println("User created: " + response);

            // Update user
            int userId = 1; // Change this to an existing user ID
            userData.put("name", "John Doe Updated");
            response = apiRequest.updateUser(userId, userData);
            System.out.println("User updated: " + response);

            // Delete user
            boolean success = apiRequest.deleteUser(userId);
            System.out.println("User deletion status: " + success);

            // Get information about all users
            List<Map<String, Object>> users = apiRequest.getAllUsers();
            System.out.println("All users: " + users);

            // Get information about a user with a specific ID
            Map<String, Object> userById = apiRequest.getUserById(userId);
            System.out.println("User with id " + userId + ": " + userById);

            // Get information about a user with a specific username
            String username = "johndoe";
            Map<String, Object> userByUsername = apiRequest.getUserByUsername(username);
            System.out.println("User with username " + username + ": " + userByUsername);

            // Print comments for the last post of a user and write them to a file
            apiRequest.getLastPostComments(userId);

            // Print all open tasks for a user
            List<Map<String, Object>> openTasks = apiRequest.getOpenTasks(userId);
            System.out.println("Open tasks for user " + userId + ": " + openTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}