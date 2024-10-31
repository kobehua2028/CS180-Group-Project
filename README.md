We're going to be developing a social media news-feed.

Classes:
- **User**
    - *Fields*  
    - String Username
    - String Password
    - String aboutMe
    - ArrayList<User> friendsList
    - ArrayList<User> blockedList
    - (type?) profileImage
 
    - 
    - *Methods*
    - addFriend(User newFriend);
    - 
    - blockUser(User blocked);
    - likePost(Post post);
    - dislikePost(Post post);
    - hidePost(Post post); //Hides this post specifically
      addComment(String text)
      removeComment(Comment comment); //delete this comment
      userSearch()
      createPost(String title, boolean commentsAllowed);
      deletePost()
      setPFP( insertpfp);
      createComment(Post post, String text);
      likeComment(Post post, Comment comment);
      dislikeComment(Post post, Comment comment);

      deletePostComment(Post post, Comment comment);

      displayUserProfile(User user);

      logIn(String userName, String password);      
      



- **Post**
  *Fields*
  - int likes
  - int dislikes
  - int commentCount
  - ArrayList of comments
  - User poster
  - String title
  - String subtext???
 
  - *Methods*
  -  addLike();

     addDislike();

    void incrementCommentCount();
    void decrementCommentCount();
  - 
  
- **Comment**
  *Fields*
  - int likes
  - int dislikes
  - int commentCount
  - ArrayList of comments
  - User poster
  - String title
 
  - *Methods*
  -  addLike();

     addDislike();

    void incrementCommentCount();
    void decrementCommentCount();
  
- **Database**
    Files
  read files
  store contents in array of users/posts/comments

  

