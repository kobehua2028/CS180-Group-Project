# CS180 Group Project

News feed style application, where users are able to...

- create an account with a username and password
- create a new post
- interact with posts from other users
- add/block users.
- have their comment/post/user data saved to a centralized database

#### Add JUnit 4.13.1 and 5.8.1 and testng to classpath

#### Compile all classes, run test cases to check functionality

#### vocareum workspace is submitted by Levin

## Phase 1

### Users

- User class
    - Stores important values like username, password, bio, friends, and blocked.
    - Validates all fields are correct upon creation.
    - Provides methods useful for changing bio, adding friends/blocked, checking if equals, etc.
- User interface
    - Defines methods for user class.
- User test class
    - Ensures that users are being modified correctly when using the methods.
    - Ensures users are storing the values correctly.
- User file
    - Stores all users on platform.

### Posts

- Post class
    - Stores important values like the author, text, body, list of comments, and likes/dislikes.
    - Provides useful methods like adding comments, equals, and incrementing dislikes/likes.
- Post interface
    - Defines methods for post class.
- Post test class
    - Ensures that posts values are being modified correctly when using the methods.
    - Ensures that users are storing the values correctly.
- Post file
    - Stores all posts on platform.

### Database

- Database class
    - Stores the names of the user and post file.
    - Reads and writes users and posts to their respective files synchronously.
    - Provides essential methods such as checking if a user already exists, and adding posts and users.
- Database interface
    - Defines methods for database to implement.
- Database test class
    - Ensures database is reading, writing, and utilizing the user and post data properly.

### Comments

- Comment class
    - Creates comment with fields like author, text, parent post, likes, and dislikes.
    - Stores itself inside the parent post's list of comments.
- Comment interface
    - Defines methods for comment class
- Comment test class
    - Ensures that methods such as incrementing likes/dislikes or creating comments is working properly.

## Phase 2

### SMClient

##### Purpose:

Acts as the user interface for the application. It enables users to interact with the social media system by sending
requests to the Server class.

##### Functionality:

The Client class provides methods for:

- User login and profile management.
- Creating, viewing, and interacting with posts and comments.
- Managing friends and blocking users.
- Handling user commands and sending appropriate requests to the server.

##### Relationship to Other Classes:

- Sends requests to the Server class, which processes them and interacts with the SocialMediaDatabase.
- Does not directly interact with SocialMediaDatabase

##### Testing:

First run SocialMediaServer to connect the client and server class. Then run the main method in SMClient with the test
cases to ensure the functionality of the class.

### SocialMediaServer

##### Purpose:

Serves as the intermediary between the Client and the SocialMediaDatabase. It handles all logic for managing users,
posts, comments, and interactions.

##### Functionality:

The Server class provides methods for:

- Profile Management: Displaying profiles, adding/removing friends, and blocking/unblocking users.
- Post Management: Creating, hiding, unhiding, liking, disliking, and deleting posts.
- Comment Management: Adding, deleting, liking, and disliking comments.
- Validation: Ensures operations are only performed if valid (e.g., users exist, users aren’t blocked).

##### Relationship to Other Classes:

- Receives requests from the Client class.
- Directly interacts with the SocialMediaDatabase to manage data.

##### Testing:

Verified in SocialMediaServerTest through manual input tests covering:

- Account creation and login.
- Interactions such as posting, commenting, liking, and blocking.
- Invalid commands and error handling.

## Phase 3

### LoginFrame
##### LoginFrame is the first screen a user interacts with. It handles user login and registration, including input validation and error handling.
#### Key Features:
 - Allows users to log in or register for a new account.
 - Provides feedback to the user (e.g., "Invalid credentials").
 -  Once logged in, transitions to the FeedFrame.
   
### FeedFrame
##### FeedFrame displays the user's feed, showing a list of posts. It allows users to interact with posts by liking, disliking, commenting, and hiding them.
#### Key Features:
  - Displays a list of posts from the user’s feed.
  - Provides buttons to like, dislike, comment, and hide posts.
  - Updates the feed in real-time based on user actions.
    
### CreatePostFrame
##### CreatePostFrame is used to create new posts. It provides a text area for entering the title and content of a post, which can then be submitted to the server.
#### Key Features:
 - Allows users to create posts by providing a title and content.
 - Submits the new post to the server for processing.

### OwnProfileFrame
##### OwnProfileFrame is the profile screen for the logged-in user. It displays the user’s profile information, including the "About Me" section, friends list, and blocked users. Users can edit their profile, remove friends, and delete their account.
#### Key Features:
  - Displays the user's profile details, including their "About Me" section and friends list.
  - Allows the user to edit their profile, including updating the "About Me" section.
  - Users can remove friends and block/unblock other users from this frame.
  - Supports deleting the profile.

### OtherProfileFrame
##### OtherProfileFrame is a GUI class that displays another user's profile. It allows the logged-in user to interact with the profile by adding or removing friends, blocking or unblocking the user, and viewing their posts and "About Me" section.
#### Key Features:
 - Displays the user's profile, including the "About Me" section and friends list.
 - Allows actions like blocking, unblocking, adding, or removing friends.
 - Dynamically updates the UI based on the user’s actions.
 - Integrates with FeedFrame to reflect changes in the feed.

### How to Use

#### Run the Application:
  - Run the Server class to start the server and then run the main() method in SMClient to start the application and open the login window.

#### Create an Account or Log In:
  - If you don't have an account, click the register button and create one.
  - If you have an account, log in with your credentials.

#### Navigate the Feed:
  - Once logged in, you will be taken to the FeedFrame where you can interact with posts (like, dislike, comment, hide posts, etc.).

#### Manage Your Profile:
  - From the OwnProfileFrame, you can edit your "About Me" section, view your friends, hide and block users, and delete your account.

#### Interact with Other Users:
  - Use the OtherProfileFrame to search for other users, add/remove friends, and manage your interactions with them.
