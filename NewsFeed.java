/**
 * Methods to be made:
 *
 *What about to comments?
 * Add a Posts array to User.java*
 * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class NewsFeed implements Serializable {
    private SocialMediaDatabase sm;
    private User user;
    private Post expandedPost;
    private int postsShown;
    private int commentsShown;
    private Scanner scan;
    private ArrayList<Post> postsOnDisplay;
    private ArrayList<Comment> commentsOnDisplay;

    public NewsFeed(SocialMediaDatabase sm) {
        postsShown = sm.getPosts().size() - 1;
        commentsShown = 0;
        this.sm = sm;
        user = null;
        expandedPost = null;
        scan = new Scanner(System.in); //Might need to be modified for server features.
        postsOnDisplay = new ArrayList<Post>();
        commentsOnDisplay = new ArrayList<Comment>();
    }

    public boolean logIn() {
        String username = "";
        do {
            System.out.print("Enter username (type -1 to cancel): ");
            username = scan.nextLine();

            if (username.equals("-1"))
                return false;
            else if  ((username.toLowerCase().equals("[deleted]"))) {
                System.out.println("Invalid username.\nPlease check your spelling.\n");
                username = "";
                continue;
            }
            for (int i = 0; i < sm.getUsers().size(); i++) {
                if (username.equals(sm.getUsers().get(i).getUsername())) {

                    String password = "";
                    do {
                        System.out.print("Enter password (type -1 to cancel): ");
                        password = scan.nextLine();

                        if (password.equals("-1")) {
                            username = "";
                            break;
                        } else if (password.equals(sm.getUsers().get(i).getPassword())) {
                            System.out.printf("Welcome, %s!\n", sm.getUsers().get(i).getUsername());
                            user = sm.getUsers().get(i);
                            return true;
                        } else {
                            System.out.println("Invalid username or password.\nPlease check your spelling.\n");
                            password = "";
                        }
                    } while (password.equals(""));
                    break;
                } else if (i == sm.getUsers().size() - 1) { //When the provided username doesn't exist in the database.
                    System.out.println("Invalid username.\nPlease check your spelling.\n");
                    username = "";
                }
            }
        } while (username.equals(""));
        return false;
    }


    public void logOut() {
        if (user == null) {
            System.out.println("No account logged in.");
        } else {
            System.out.printf("Goodbye, %s!\n", user.getUsername());
            user = null;
        }
    }

    public boolean createUser() {
        String username = "";
        int backgroundCheck = 0;
        do {
            System.out.print("Enter username (type -1 to cancel): ");
            username = scan.nextLine();

            if (username.equals("-1"))
                return false;
            else if (sm.getUsers().size() == 0) {
                backgroundCheck = 1;
            } else {
                backgroundCheck = sm.getUsers().size();
            }
            for (int i = 0; i < backgroundCheck; i++) {
                if (sm.getUsers().size() != 0 && username.equals(sm.getUsers().get(i).getUsername())) {
                    System.out.println("Invalid username.\nUser with that name already exists.\n");
                    username = "";
                    break;
                } else if (sm.getUsers().size() == 0 || i == sm.getUsers().size() - 1) { //When the provided username doesn't exist in the database.
                    String password = "";
                    do {
                        System.out.print("Enter password (type -1 to cancel): ");
                        password = scan.nextLine();

                        if (password.equals("-1")) {
                            username = "";
                            break;
                        } else if (password.length() < 8) {
                            System.out.println("Invalid password.\nPassword must be at least 8 characters long.\n");
                            password = "";
                        } else if (password.length() > 50) {
                            System.out.println("Invalid password.\nPassword must be less than 50 characters long.\n");
                            password = "";
                        } else {
                            user = new User(username, password, "",
                                    new ArrayList<User>(), new ArrayList<User>(), sm);
                            System.out.printf("Welcome, %s!\n", user.getUsername());
                            return true;
                        }
                    } while (password.equals(""));
                    break;
                }
            }
        } while (username.equals(""));

        return false;
    }

    public boolean createPost() {
        if (user == null) {
            System.out.println("You must log in or create an account to post.");
            return false;
        } else {
            String title = "";
            do {
                System.out.print("Enter the title of your post (-1 to cancel): ");
                title = scan.nextLine();

                if (title.replace(" ", "").equals("")) {
                    System.out.println("Invalid post title.\nTitle cannot be empty.");
                } else {
                    String subtext = "";
                    String inputString;
                    do {
                        System.out.println("Type subtext (Type /post when complete): \n");
                        while (scan.hasNextLine()) {
                            inputString = scan.nextLine();
                            if (inputString.equals("/post")) {
                                System.out.println("***********\nPost draft:\n***********\n");
                                System.out.println(displayPostDraft(title, subtext));

                                String confirm = "";
                                do {
                                    System.out.println("Do you want to publish this post?\n(type \"yes\" or \"no\"): ");
                                    confirm = scan.nextLine();

                                    if (confirm.toLowerCase().equals("yes")) {
                                        Post newPost = new Post(user, title, subtext, new ArrayList<Comment>(), 0, 0, sm);
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));
                            } else {
                                subtext += inputString + "\n";
                            }
                        }
                    } while (title.replace(" ", "").equals(""));

                }
            } while (title.replace(" ", "").equals(""));
        }
        return false;
    }

    public boolean createComment() {
        if (user == null) {
            System.out.println("You must log in or create an account to leave a comment.");
            return false;
        } else {
            String text = "";
            String inputString;
            System.out.println("Type your comment's text (Type /comment when complete): \n");
            while (scan.hasNextLine()) {
                inputString = scan.nextLine();
                if (inputString.equals("/comment")) {
                    System.out.println("***********\nComment draft:\n***********\n");
                    System.out.println(displayCommentDraft(text));

                    String confirm = "";
                    do {
                        System.out.println("Do you want to publish this comment?\n(type \"yes\" or \"no\"): ");
                        confirm = scan.nextLine();

                        if (confirm.toLowerCase().equals("yes")) {
                            new Comment(user, text, 0, 0, expandedPost, sm);
                            return true;
                        } else {
                            return false;
                        }
                    } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));
                } else {
                    text += inputString + "\n";
                }
            }


        }
        return false;
    }

    public void selectPost(int postIndex) {
        String displayedPostString = "";
        expandedPost = postsOnDisplay.get(postIndex - 1);

        displayedPostString += String.format("%s says:\n", expandedPost.getAuthor().getUsername());
        displayedPostString += String.format("%s\n\n", expandedPost.getTitle());
        displayedPostString += String.format("%s\n\n", expandedPost.getSubtext());
        displayedPostString += String.format("Likes: %d    Dislikes: %d    Comments: %d\n",
                expandedPost.getLikes(), expandedPost.getDislikes(), expandedPost.getComments().size());
        displayedPostString += "******************************************\n";

        displayedPostString += displayComments();

        System.out.println(displayedPostString);
    }

    public boolean returnToFeed() {
        expandedPost = null;
        refreshPage();
        return true;
    }
    public boolean likeComment(int commentIndex) {
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("You must select a post first.");
            return false;
        } else {
            try {
                if (user.getLikedComments().contains(commentsOnDisplay.get(commentIndex - 1))) {
                    System.out.println("You have already liked this comment.");
                    return false;
                } else if (user.getDislikedPosts().contains(expandedPost)) {
                    System.out.println("You cannot simultaneously like and dislike the same comment.");
                    String confirm = "";
                    do {
                        System.out.print("Do you want to remove your dislike and add a like? (type \"yes or \"no\"): ");
                        confirm = scan.nextLine();
                        if (confirm.toLowerCase().equals("yes")) {
                            user.removeDislikedComment(commentsOnDisplay.get(commentIndex - 1));
                            commentsOnDisplay.get(commentIndex - 1).removeDislike();

                            user.addLikedComment(commentsOnDisplay.get(commentIndex - 1));
                            commentsOnDisplay.get(commentIndex - 1).incrementLikes();

                            System.out.printf("You have liked %s's comment.",
                                    commentsOnDisplay.get(commentIndex - 1).getAuthor().getUsername());
                            return true;
                        } else if (confirm.toLowerCase().equals("no")) {
                            return false;
                        }
                    } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));


                } else {
                    user.addLikedComment(commentsOnDisplay.get(commentIndex - 1));
                    commentsOnDisplay.get(commentIndex - 1).incrementLikes();
                    System.out.printf("You have liked %s's comment.",
                            commentsOnDisplay.get(commentIndex - 1).getAuthor().getUsername());
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Please select from the comments on display.");
                return false;
            }
        }
        return false;
    }

    public boolean dislikeComment(int commentIndex) {
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("You must select a post first.");
            return false;
        } else {
            try {
                if (user.getDislikedComments().contains(commentsOnDisplay.get(commentIndex - 1))) {
                    System.out.println("You have already disliked this comment.");
                    return false;
                } else if (user.getDislikedPosts().contains(expandedPost)) {
                    System.out.println("You cannot simultaneously like and dislike the same comment.");
                    String confirm = "";
                    do {
                        System.out.print("Do you want to remove your dislike and add a like? (type \"yes or \"no\"): ");
                        confirm = scan.nextLine();
                        if (confirm.toLowerCase().equals("yes")) {
                            user.removeLikedComment(commentsOnDisplay.get(commentIndex - 1));
                            commentsOnDisplay.get(commentIndex - 1).removeLike();

                            user.addDislikedComment(commentsOnDisplay.get(commentIndex - 1));
                            commentsOnDisplay.get(commentIndex - 1).incrementDislikes();

                            System.out.printf("You have disliked %s's comment.",
                                    commentsOnDisplay.get(commentIndex - 1).getAuthor().getUsername());
                        } else if (confirm.toLowerCase().equals("no")) {
                            return false;
                        }
                    } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));


                } else {
                    user.addDislikedComment(commentsOnDisplay.get(commentIndex - 1));
                    commentsOnDisplay.get(commentIndex - 1).incrementDislikes();
                    System.out.printf("You have disliked %s's comment.",
                            commentsOnDisplay.get(commentIndex - 1).getAuthor().getUsername());
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Please select from the comments on display.");
                return false;
            }
        }
        return false;
    }

    public boolean unlikeComment(int commentIndex) {
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("You must select a post first.");
            return false;
        } else {
            try {
                if (user.getLikedComments().contains(commentsOnDisplay.get(commentIndex - 1))) {
                    System.out.println("You have not liked this comment.");
                    return false;
                } else {
                    user.removeLikedComment(commentsOnDisplay.get(commentIndex - 1));
                    commentsOnDisplay.get(commentIndex - 1).removeLike();
                    System.out.printf("You have unliked %s's comment.",
                            commentsOnDisplay.get(commentIndex - 1).getAuthor().getUsername());
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Please select from the comments on display.");
                return false;
            }
        }
    }

    public boolean undisikeComment(int commentIndex) {
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("You must select a post first.");
            return false;
        } else {
            try {
                if (user.getLikedComments().contains(commentsOnDisplay.get(commentIndex - 1))) {
                    System.out.println("You have not disliked this comment.");
                    return false;
                } else {
                    user.removeDislikedComment(commentsOnDisplay.get(commentIndex - 1));
                    commentsOnDisplay.get(commentIndex - 1).removeDislike();
                    System.out.printf("You have un-disliked %s's comment.",
                            commentsOnDisplay.get(commentIndex - 1).getAuthor().getUsername());
                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Please select from the comments on display.");
                return false;
            }
        }
    }



    public boolean likePost() {
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("Select a post to like.");
            return false;
        } else {
            for (int i = 0; i < user.getLikedPosts().size(); i++) {
                if (expandedPost.equals(user.getLikedPosts().get(i))) {
                    System.out.println("You have already liked this post.");
                    return false;
                }
            }

            for (int i = 0; i < user.getDislikedPosts().size(); i++) {
                if (expandedPost.equals(user.getDislikedPosts().get(i))) {
                    System.out.println("You have disliked this post.");
                    String confirm = "";
                    do {
                        System.out.print("Do you want to remove your dislike and add a like? (type \"yes or \"no\"): ");
                        confirm = scan.nextLine();
                        if (confirm.toLowerCase().equals("yes")) {
                            user.removeDislikedPost(expandedPost);
                            expandedPost.removeDislike();

                            user.addLikedPost(expandedPost);
                            expandedPost.incrementLikes();
                            System.out.printf("You have liked %s's post.", expandedPost.getAuthor().getUsername());
                            //sm.writeUser(user);
                            return true;
                        } else if (confirm.toLowerCase().equals("no")) {
                            return false;
                        }
                    } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));

                }
            }

            user.addLikedPost(expandedPost);
            expandedPost.incrementLikes();
            System.out.printf("You have liked %s's post.", expandedPost.getAuthor().getUsername());
            sm.writeUser(user);
            return true;
        }
    }

    public boolean disLikePost() {
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("Select a post to dislike.");
            return false;
        } else {
            for (int i = 0; i < user.getDislikedPosts().size(); i++) {
                if (expandedPost.equals(user.getDislikedPosts().get(i))) {
                    System.out.println("You have already disliked this post.");
                    return false;
                }
            }

            for (int i = 0; i < user.getLikedPosts().size(); i++) {
                if (expandedPost.equals(user.getLikedPosts().get(i))) {
                    System.out.println("You have liked this post.");
                    String confirm = "";
                    do {
                        System.out.print("Do you want to remove your like and add a dislike? (type \"yes or \"no\"): ");
                        confirm = scan.nextLine();
                        if (confirm.toLowerCase().equals("yes")) {
                            user.removeLikedPost(expandedPost);
                            expandedPost.removeLike();

                            user.addDislikedPost(expandedPost);
                            expandedPost.incrementDislikes();
                            System.out.printf("You have disliked %s's post.", expandedPost.getAuthor().getUsername());
                            //sm.writeUser(user);
                            return true;
                        } else if (confirm.toLowerCase().equals("no")) {
                            return false;
                        }
                    } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));

                }
            }

            user.addDislikedPost(expandedPost);
            expandedPost.incrementDislikes();
            System.out.printf("You have disliked %s's post.", expandedPost.getAuthor().getUsername());
            sm.writeUser(user);
            return true;
        }
    }

    public boolean unlikePost() { //removes like from liked post
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("Select a post to unlike.");
            return false;
        } else {
            for (int i = 0; i < user.getLikedPosts().size(); i++) {
                if (expandedPost.equals(user.getLikedPosts().get(i))) {
                    user.removeLikedPost(expandedPost);
                    expandedPost.removeLike();
                    System.out.printf("You have unliked %s's post.", expandedPost.getAuthor().getUsername());
                    return true;
                }
            }
            System.out.println("You have not liked this post.");
            return false;
        }
    }

    public boolean unDislikePost() { //removes like from liked post
        if (user == null) {
            System.out.println("No account logged in.");
            return false;
        } else if (expandedPost == null) {
            System.out.println("Select a post to un-dislike.");
            return false;
        } else if (!user.getDislikedPosts().contains(expandedPost)) {

        } else {
            for (int i = 0; i < user.getDislikedPosts().size(); i++) {
                if (expandedPost.equals(user.getDislikedPosts().get(i))) {
                    user.removeDislikedPost(expandedPost);
                    expandedPost.removeDislike();
                    System.out.printf("You have un-disliked %s's post.", expandedPost.getAuthor().getUsername());
                    return true;
                }
            }

            System.out.println("You have not disliked this post.");
            return false;
        }
        return false;
    }


    public boolean deleteUser() {
        if (user == null) {
            System.out.println("No account logged in.");
        } else {
            String confirm = "";
            do {
                System.out.println("Are you sure you want to delete your account?\n(type \"yes\" or \"no\"): ");
                confirm = scan.nextLine();

                if (confirm.toLowerCase().equals("yes")) {
                    sm.getUsers().remove(user);
                    for (int i = 0; i < sm.getPosts().size(); i++) {
                        if (user.equals(sm.getPosts().get(i).getAuthor())) {
                            sm.getPosts().get(i).getAuthor().setDeleted(true);
                        }
                        for (int j = 0; j < sm.getPosts().get(i).getComments().size(); j++) {
                            if (user.equals(sm.getPosts().get(i).getComments().get(j).getAuthor())) {
                                sm.getPosts().get(i).getComments().get(j).getAuthor().setDeleted(true);
                            }
                        }
                    }
                    user = null;
                    return true;
                }
            } while (!confirm.toLowerCase().equals("yes") && !confirm.toLowerCase().equals("no"));
        }
        return false;
    }

    private String displayComments() {
        String commentString = "";
        int commentCount = 1;

        if (commentsShown == expandedPost.getComments().size()) {
            return "No more comments to load.";
        }
        commentsOnDisplay.clear();

        for (int i = 0; i < 5; i++) {
            Comment displayingComment = expandedPost.getComments().get(commentsShown++);

            //If the comment was created by a blocked user, don't display it.
            if (user != null && user.getBlockedList().contains(displayingComment.getAuthor())) {
                i--;
                continue;
            }
            commentsOnDisplay.add(displayingComment);
            commentString += String.format("%d)\n", commentCount);
            commentString += String.format("%s commented:\n", displayingComment.getAuthor().getUsername());
            commentString += String.format("%s\n", displayingComment.getText());
            commentString += String.format("Likes: %d    Dislikes: %d\n",
                    displayingComment.getLikes(), displayingComment.getDislikes());
            commentString += "******************************************\n";
            commentCount++;
            if (commentsShown == expandedPost.getComments().size())
                break;
        }
        return commentString;
    }

    public void previousPageComments() {
        if (commentsShown == 0)
            System.out.println("This is the first page.");
        else if (commentsShown == expandedPost.getComments().size()) {
            commentsShown -= 5;
            commentsShown -= (commentsShown % 5);
            System.out.println(displayComments());
        } else {
            commentsShown %= 5;
            System.out.println(displayComments());
        }
    }

    public void refreshPageComments() {
        commentsShown -= 5;
        System.out.println(displayComments());
    }

    public void nextPageComments() {
        if (commentsShown == expandedPost.getComments().size()) {
            System.out.println("No more comments to load.");
        } else {
            System.out.println(displayComments());
        }
    }

    private void displayPosts() {
        String postString = "";
        int postCount = 1;

        if (postsShown == sm.getPosts().size() || postsShown == 0) {
            System.out.println("No more posts to load.");
            return;
        }
        postsOnDisplay.clear();

        for (int i = 0; i < 5; i++) {
            Post displayingPost = sm.getPosts().get(postsShown--);

            //If the post was created by a blocked user, don't display it.
            if (user != null && user.getBlockedList().contains(displayingPost.getAuthor())) {
                i--;
                continue;
            }
            postsOnDisplay.add(displayingPost);
            postString += String.format("%d)\n", postCount);
            postString += String.format("%s says:\n", displayingPost.getAuthor().getUsername());
            postString += String.format("%s\n\n", displayingPost.getTitle());
            postString += String.format("%s\n\n", displayingPost.getSubtext());
            postString += String.format("Likes: %d    Dislikes: %d    Comments: %d\n",
                    displayingPost.getLikes(), displayingPost.getDislikes(), displayingPost.getComments().size());
            postString += "__________________________________________\n";
            postCount++;
            if (postsShown == -1)
                break;
        }
        System.out.print(postString);
    }

    public String displayPostDraft(String title, String subtext) {
        String postDraftString = "";
        postDraftString += String.format("%s says:\n", user.getUsername());
        postDraftString += String.format("%s\n\n", title);
        postDraftString += String.format("%s\n\n", subtext);
        postDraftString += String.format("Likes: %d    Dislikes: %d    Comments: %d\n",
                0, 0, 0);
        postDraftString += "__________________________________________\n";

        return postDraftString;
    }

    public String displayCommentDraft(String text) {
        String commentDraftString = "";

        commentDraftString += String.format("%s commented:\n", user.getUsername());
        commentDraftString += String.format("%s", text);
        commentDraftString += String.format("Likes: %d    Dislikes: %d\n",
                0, 0);
        commentDraftString += "******************************************\n\n";

        return commentDraftString;
    }

    public void previousPage() {
        if (postsShown == sm.getPosts().size())
            System.out.println("This is the first page.");
        else if (postsShown == 0) {
            postsShown += (sm.getPosts().size() % 5 - 1) ;
            postsShown += 5;
            displayPosts();
        } else {
            postsShown += 5;
            displayPosts();
        }
    }

    public void refreshPage() {
        displayPosts();
        if (sm.getPosts().size() < 5) {
            postsShown = sm.getPosts().size() - 1;
        } else
            postsShown += 5;
    }

    public void nextPage() {
        if (postsShown == -1) {
            System.out.println("No more posts to load.");
        } else {
            displayPosts();
        }
    }

    public User getUser() {
        return user;
    }
}