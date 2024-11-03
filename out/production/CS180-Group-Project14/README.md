# CS180 Group Project
News feed style application, where users are able to...
- create an account with a username and password
- create a new post
- interact with posts from other users
- add/block users.
- have their comment/post/user data saved to a centralized database

#### Add JUnit 4.13.1 and 5.8.1 and testng to classpath
#### Compile all classes, run test cases to check functionality

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
