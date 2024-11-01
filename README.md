# CS180 Group Project
News feed style application.

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
  - Stores all fields of a user in the format:
  - `username,password,aboutMe`
- Post
  - Stores all fields of a user in the format:
  - `author,title,subtext`
- Comment
  - Stores all fields of a user in the format:
  - `author,post,text`