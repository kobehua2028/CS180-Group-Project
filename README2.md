# CS180 Group Project Phase 2
News feed style application, where users are able to...
- create an account with a username and password
- create a new post
- interact with posts from other users
- add/block users.
- have their comment/post/user data saved to a centralized database

#### Add JUnit 4.13.1 and 5.8.1 and testng to classpath
#### Compile all classes, run test cases to check functionality

#### Vocareum workspace is submmited by ___

### Client
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
Manually run and test code to ensure its functionality.
(include more detailed testing functionality)
### Server
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

