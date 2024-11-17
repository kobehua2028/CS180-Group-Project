# CS180 Group Project
News feed style application, where users are able to...
- create an account with a username and password
- create a new post
- interact with posts from other users
- add/block users.
- have their comment/post/user data saved to a centralized database

#### Add JUnit 4.13.1 and 5.8.1 and testng to classpath
#### Compile all classes, run test cases to check functionality
### vocareum workspace is submitted by __

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
    
## SMClient
##### Purpose: 
Acts as the user interface for the application. It enables users to interact with the social media system by sending requests to the Server class.

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
First run SocialMediaServer to connect the client and server class. Then run the main method in SMClient with the test cases to ensure the functionality of the class.

## SocialMediaServer
##### Purpose: 
Serves as the intermediary between the Client and the SocialMediaDatabase. It handles all logic for managing users, posts, comments, and interactions.

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
