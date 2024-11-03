public interface UserInterface {
    
    boolean equals(Object account);
    
    void createPost(String title, String subtext);
    
    void removeFriend(User formerFriend);
    
    void addFriend(User newFriend);
    
    void block(User blockedUser);
    
    String getPassword();
    
    String getUsername();
    
    String getAboutMe();
    
    ArrayList<User> getFriendsList();
    
    ArrayList<User> getBlockedList();
}
