public class CreateMockDatabase {
    public static void main(String[] args) {
        SocialMediaDatabase sm = new SocialMediaDatabase("users.dat", "posts.dat");
        System.out.println("CREATE DATABASE STARTED");

        System.out.println("CREATING USER STARTED");
        User user1 = sm.createUser("Bob", "Bob2020", "My Name is Bob");
        User user2 = sm.createUser("Jimmy", "Jimmy2020", "My Name is Jimmy");
        User user3 = sm.createUser("Henry", "henry2020", "My Name is Henry");
        User user4 = sm.createUser("Joe", "Joe2020", "My Name is Joe");
        User user5 = sm.createUser("Sarah", "Sarah2020", "My Name is Sarah");
        User user6 = sm.createUser("Ethan", "Ethan2020", "My Name is Ethan");
        User user7 = sm.createUser("Alex", "Alex2020", "My Name is Alex");
        User user8 = sm.createUser("Andy", "Andy2020", "My Name is Andy");
        User user9 = sm.createUser("Anthony", "Anthony2020", "My Name is Anthony");
        User user10 = sm.createUser("Berry", "Berry2020", "My Name is Berry");
        User user11 = sm.createUser("John", "John2020", "My Name is John");
        User user12 = sm.createUser("Levin", "Levin2020", "My Name is Levin");
        System.out.println("CREATING USER FINISHED");

        System.out.println("CREATING POST STARTED");
        Post post1 = user1.createPost("Freshman Orientation Tips", "Don't be afraid to ask questions! Everyone is new and figuring things out.");
        System.out.println("CREATE POST 1");
        Post post2 = user2.createPost("Best Study Spots on Campus", "The library's 3rd floor is a quiet zone, perfect for focused study sessions.");
        System.out.println("CREATE POST 2");
        Post post3 = user3.createPost("How to Manage Stress During Finals", "Take short breaks and stay hydrated! Meditation apps can be a lifesaver.");
        System.out.println("CREATE POST 3");
        Post post4 = user4.createPost("Upcoming College Fest Details", "The annual music festival is happening next Saturday. Don’t miss it!");
        System.out.println("CREATE POST 4");
        Post post5 = user5.createPost("Group Project Horror Stories", "Ever been the only one doing all the work? Let's share some laughs and tips.");
        System.out.println("CREATE POST 5");
        Post post6 = user6.createPost("Best Cafeteria Food Options", "The pasta bar on Wednesdays is the best! What’s your go-to meal?");
        System.out.println("CREATE POST 6");
        Post post7 = user7.createPost("Internship Opportunities in Tech", "Check out the career portal for postings. Don’t forget to update your resume.");
        System.out.println("CREATE POST 7");
//        Post post8 = user8.createPost("Roommate Drama", "How do you deal with roommates who never clean up? Need advice ASAP!");
//        System.out.println("CREATE POST 8");
//        Post post9 = user9.createPost("Fun Electives to Take Next Semester", "Thinking of signing up for Creative Writing or Intro to Guitar. Any suggestions?");
//        System.out.println("CREATE POST 9");
//        Post post10 = user10.createPost("Lost & Found: Blue Water Bottle", "I left my blue Hydro Flask in the gym. Let me know if you’ve seen it!");
//        System.out.println("CREATE POST 10");
//        Post post11 = user11.createPost("Best Professors for CS Majors", "Prof. Smith is great for Algorithms, but watch out for tough assignments!");
//        System.out.println("CREATE POST 11");
//        Post post12 = user12.createPost("How to Make Friends in College", "Join clubs and attend campus events! It’s the best way to meet people.");
//        System.out.println("CREATE POST 12");
        System.out.println("CREATING POST FINISHED");

        System.out.println("CREATING COMMENTS STARTED");
        // Comments for post1
        post1.createComment(user2, "Great tips, Bob! Freshman year can be overwhelming.");
        post1.createComment(user3, "I agree! Asking questions helped me a lot.");
        post1.createComment(user4, "This post is super helpful for new students.");
        System.out.println("CREATE COMMENTS FOR POST 1");
//        // Comments for post2
//        post2.createComment(user5, "The 3rd floor is my favorite too!");
//        post2.createComment(user6, "I prefer the café near the library for studying.");
//        post2.createComment(user7, "Thanks for the suggestion, Jimmy!");
//        System.out.println("CREATE COMMENTS FOR POST 2");
//        // Comments for post3
//        post3.createComment(user8, "Meditation apps really work wonders!");
//        post3.createComment(user9, "Short walks also help reduce stress.");
//        post3.createComment(user10, "Don’t forget to get enough sleep if possible!");
//        System.out.println("CREATE COMMENTS FOR POST 3");
//        // Comments for post4
//        post4.createComment(user11, "Looking forward to the music festival!");
//        post4.createComment(user12, "This is going to be so much fun.");
//        post4.createComment(user1, "I heard there's going to be a famous band performing!");
//        System.out.println("CREATE COMMENTS FOR POST 4");
//        // Comments for post5
//        post5.createComment(user2, "Group projects can be such a pain sometimes.");
//        post5.createComment(user3, "I’ve been in that situation too many times!");
//        post5.createComment(user4, "Sharing responsibilities upfront can help.");
//        System.out.println("CREATE COMMENTS FOR POST 5");
//        // Comments for post6
//        post6.createComment(user5, "Pasta bar Wednesdays are the best!");
//        post6.createComment(user6, "I usually go for the salad bar.");
//        post6.createComment(user7, "Try the sushi rolls, they’re surprisingly good.");
//        System.out.println("CREATE COMMENTS FOR POST 6");
//        // Comments for post7
//        post7.createComment(user8, "Updating your resume is crucial. Great tip!");
//        post7.createComment(user9, "Also, practice interviews are super helpful.");
//        post7.createComment(user10, "Networking at career fairs works well too.");
//        System.out.println("CREATE COMMENTS FOR POST 7");
        // Comments for post8
//        post8.createComment(user11, "Communication is key. Have a chat with your roommate.");
//        post8.createComment(user12, "Maybe set up a cleaning schedule?");
//        post8.createComment(user1, "Try discussing boundaries and expectations early.");
//        // Comments for post9
//        post9.createComment(user2, "Creative Writing sounds like a fun elective.");
//        post9.createComment(user3, "Intro to Guitar would be my choice!");
//        post9.createComment(user4, "Check out Art History, it’s super interesting.");
//        // Comments for post10
//        post10.createComment(user5, "I’ll keep an eye out for your water bottle.");
//        post10.createComment(user6, "Have you checked the lost and found?");
//        post10.createComment(user7, "Hope you find it soon!");
//        // Comments for post11
//        post11.createComment(user8, "Prof. Smith’s lectures are great but challenging.");
//        post11.createComment(user9, "Totally agree! Take good notes in his class.");
//        post11.createComment(user10, "He’s tough but fair. You’ll learn a lot.");
//        // Comments for post12
//        post12.createComment(user11, "Joining clubs helped me make friends quickly.");
//        post12.createComment(user12, "Campus events are great for meeting new people.");
//        post12.createComment(user1, "Being friendly and approachable goes a long way.");
        System.out.println("CREATING COMMENTS FINISHED");

        post1.incrementLikes();

        System.out.println("DONE");
    }
}
