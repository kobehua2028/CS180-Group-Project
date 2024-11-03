public interface DatabaseInterface {
    
    User findUser(String username);
    
    boolean userExists(String username);
    
    Post findPost(String title);
    
    ArrayList<User> getUsers();
    
    ArrayList<Post> getPosts();
    
    void addUser(User user);
    
    void addPost(Post post);
    
    void readUsers();
    
    void readPosts();
    
    void writeUser(User user);
    
    void writePost(Post post);
}
