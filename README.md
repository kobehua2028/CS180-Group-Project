# CS180 Group Project
News feed style application, where users are able to...
- create an account with a username and password
- create a new post
- interact with posts from other users
- add/block users.
- have their comment/post/user data saved to a centralized database

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
   - Stores values about the user
     - username
     - password
     - bio
     - list of friends
     - list of blocked
     - instance of database
 - Post
   - Implements PostInterface 
   - Stores values about the post
     - author
     - title
     - body
     - list of comments
     - number of likes
     - number of dislikes
     - instance of database
 - Comment
   - Implements CommentInterface
   - Stores values about the comment
     - author
     - text
     - parent post
     - number of likes
     - number of dislikes
 - Database
   - Implements DatabaseInterface
   - Stores:
     - list of users
     - list of posts
     - name of user file
     - name of post file
   - Retrieves user objects from users file and adds them to the list of users
   - Retrieves post objects from posts file and adds them to the list of posts
   - Writes users anda posts to their respective files

### Files:
- User
  - Stores all Users in a file as a serializable object
    - All Users are then deserialized and fields are accessed through getters  
- Post
  - Stores all Posts in a file as a serializable object
    - All Posts are then deserialized and fields are accessed through getters
