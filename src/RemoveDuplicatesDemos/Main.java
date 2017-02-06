package RemoveDuplicatesDemos;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main() {
        User user1 = new User(1, "user1");
        User user2 = new User(2, "user2");
        User user3 = new User(3, "user3");

        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user1);
        list.add(user2);
        list.add(user2);
        list.add(user3);
        list.add(user1);

        for(User usr : list){
            System.out.println("user: "+usr.getId());
        }

        RemoveDuplicates.removeDuplicates(list);

        System.out.println("after remove ...");

        for(User usr : list){
            System.out.println("user: "+usr.getId());
        }


    }


}
