package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        APIRequest apiRequest = new APIRequest();
        Scanner scanner = new Scanner(System.in);

        // Create a new user
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setUsername("johndoe");
        newUser.setEmail("johndoe@example.com");

        try {
            User createdUser = apiRequest.createUser(newUser);
            System.out.println("User created: " + createdUser);

            // Update user
            int userId = 1; // Change this to an existing user ID
            createdUser.setName("John Doe Updated");
            User updatedUser = apiRequest.updateUser(userId, createdUser);
            System.out.println("User updated: " + updatedUser);

            // Delete user
            boolean success = apiRequest.deleteUser(userId);
            System.out.println("User deletion status: " + success);

            // Get information about all users
            List<User> users = apiRequest.getAllUsers();
            System.out.println("All users: " + users);

            // Get information about a user with a specific ID
            System.out.println("Enter user ID:");
            userId = scanner.nextInt();
            User userById = apiRequest.getUserById(userId);
            System.out.println("User with id " + userId + ": " + userById);

            // Get information about a user with a specific username
            System.out.println("Enter username:");
            String username = scanner.next();
            User userByUsername = apiRequest.getUserByUsername(username);
            System.out.println("User with username " + username + ": " + userByUsername);

            // Get last post of a user and write comments to a file
            Post lastPost = apiRequest.getLastPost(userId);
            if (lastPost != null) {
                int lastPostId = lastPost.getId();
                List<Comment> comments = apiRequest.getComments(lastPostId);
                apiRequest.writeCommentsToFile(userId, lastPostId, comments);
            }

            // Print all open tasks for a user
            List<Todo> openTasks = apiRequest.getOpenTasks(userId);
            System.out.println("Open tasks for user " + userId + ": " + openTasks);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}