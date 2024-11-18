import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class NewsFeed implements Serializable {
    private final SocialMediaDatabase sm;
    private User user;
    private int postsShown;
    private final Scanner scan;

    public NewsFeed(SocialMediaDatabase sm) {
        postsShown = sm.getPosts().size() - 1;
        this.sm = sm;
        user = null;
        scan = new Scanner(System.in); //Might need to be modified for server features.
    }

    public void logIn() {
        String username = "";
        do {
            System.out.print("Enter username (type -1 to cancel): ");
            username = scan.nextLine();

            if (username.equals("-1"))
                return;
            else if ((username.equalsIgnoreCase("[deleted]"))) {
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
                            return;
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
    }


    public void logOut() {
        if (user == null) {
            System.out.println("No account logged in.");
        } else {
            System.out.printf("Goodbye, %s!\n", user.getUsername());
            user = null;
        }
    }

    public void createUser() {
        String username = "";
        do {
            System.out.print("Enter username (type -1 to cancel): ");
            username = scan.nextLine();

            if (username.equals("-1"))
                return;
            for (int i = 0; i < sm.getUsers().size(); i++) {
                if (username.equals(sm.getUsers().get(i).getUsername())) {
                    System.out.println("Invalid username.\nUser with that name already exists.\n");
                    username = "";
                    break;
                } else if (i == sm.getUsers().size() - 1) { //When the provided username doesn't exist in the database.
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
                                    new ArrayList<User>(), new ArrayList<User>(), new ArrayList<>(),
                                    new ArrayList<>(), new ArrayList<>(),
                                    new ArrayList<>(), sm);
                            System.out.printf("Welcome, %s!\n", user.getUsername());
                            return;
                        }
                    } while (password.equals(""));
                    break;
                }
            }
        } while (username.equals(""));
    }

    public void createPost() {
        if (user == null) {
            System.out.println("You must log in or create an account to post.");
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

                                    if (confirm.equalsIgnoreCase("yes")) {
                                        new Post(user, title, subtext, new ArrayList<Comment>(), 0, 0, sm);
                                        return;
                                    } else {
                                        return;
                                    }
                                } while (!confirm.equalsIgnoreCase("yes") && !confirm.equalsIgnoreCase("no"));
                            } else {
                                subtext += inputString + "\n";
                            }
                        }
                    } while (title.replace(" ", "").equals(""));

                }
            } while (title.replace(" ", "").equals(""));
        }
    }


    public void deleteUser() {
        if (user == null) {
            System.out.println("No account logged in.");
        } else {
            String confirm = "";
            do {
                System.out.println("Are you sure you want to delete your account?\n(type \"yes\" or \"no\"): ");
                confirm = scan.nextLine();

                if (confirm.equalsIgnoreCase("yes")) {
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
                }
            } while (!confirm.equalsIgnoreCase("yes") && !confirm.equalsIgnoreCase("no"));
        }
    }


    private void displayPosts() {
        String postString = "";
        int postCount = 1;

        if (postsShown == sm.getPosts().size()) {
            System.out.println("No more posts to load.");
            return;
        }
        for (int i = 0; i < 5; i++) {
            Post displayingPost = sm.getPosts().get(postsShown--);
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

    public void previousPage() {
        if (postsShown == sm.getPosts().size())
            System.out.println("This is the first page.");
        else {
            postsShown += 5;
            displayPosts();
        }
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

    public void refreshPage() {
        displayPosts();
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