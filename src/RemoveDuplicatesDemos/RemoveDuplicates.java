package RemoveDuplicatesDemos;

import java.util.*;


public class RemoveDuplicates {
    public static void removeDuplicates(List<User> list){
        Set<User> set = new TreeSet<User>(new Comparator<User>() {

            @Override
            public int compare(User o1, User o2) {
                if(o1.getId()>o2.getId())
                    return 1;
                if(o1.getId()<o2.getId())
                    return -1;
                return 0;
            }

        });
        set.addAll(list);
        List result = Arrays.asList(set.toArray());

        System.out.println(result);
    }
}
