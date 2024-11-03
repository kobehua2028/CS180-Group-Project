# CS180 Group Project
News feed style application, where users are able to...
- create an account with a username and password
- create a new post
- interact with posts from other users
- add/block users.
- have their comment/post/user data saved to a centralized database

(instructions on how to run/compile project)

### Interfaces:
 - UserInterface
   - Declares methods and variables for User to implement, outlining the class
 - PostInterface
   - Declares methods and variables for Post to implement, outlining the class
 - CommentInterface
   - Declares methods and variables for Comment to implement, outlining the class
 - DatabaseInterface
   - Declares methods useful for retrieving and formatting information from files

### Classes:
 - User
   - Implements UserInterface
   - Stores values about the user like username, password, and friendsList
 - Post
   - Implements PostInterface 
   - Stores values about the post like title, author, comments, and likes/dislikes
 - Comment
   - Implements CommentInterface
   - Stores the author of the comment, the parent post, and the text
 - Database
   - Implements DatabaseInterface
   - Retrieves values from the user, post, and comment files and creates objects from them

### Files:
- User
  - Stores all Users in a txt file as a serializable object
    - All Users are then deserialized and fields are accessed through getters  
- Post
  - Stores all Posts in a txt file as a serializable object
    - All Posts are then deserialized and fields are accessed through getters  
- Comment
  - Stores all Comments in a txt file as a serializable object
    - All Comments are then deserialized and fields are accessed through getters  
