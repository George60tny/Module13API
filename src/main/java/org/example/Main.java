package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        APIRequest apiRequest = new APIRequest();
        Scanner scanner = new Scanner(System.in);

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
            System.out.println("Enter user ID:");
            userId = scanner.nextInt();
            Map<String, Object> userById = apiRequest.getUserById(userId);
            System.out.println("User with id " + userId + ": " + userById);

            // Get information about a user with a specific username
            System.out.println("Enter username:");
            String username = scanner.next();
            Map<String, Object> userByUsername = apiRequest.getUserByUsername(username);
            System.out.println("User with username " + username + ": " + userByUsername);

            // Get last post of a user and write comments to a file
            Map<String, Object> lastPost = apiRequest.getLastPost(userId);
            if (lastPost != null) {
                int lastPostId = ((Double) lastPost.get("id")).intValue();
                List<Map<String, Object>> comments = apiRequest.getComments(lastPostId);
                apiRequest.writeCommentsToFile(userId, lastPostId, comments);
            }

            // Print all open tasks for a user
            List<Map<String, Object>> openTasks = apiRequest.getOpenTasks(userId);
            System.out.println("Open tasks for user " + userId + ": " + openTasks);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}