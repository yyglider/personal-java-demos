public class Main {

    public static void main(String[] args) {
        String str = "EmployeeV1.class";
        String[] strs = str.split("\\.");
        for (String s : strs) {
            System.out.println(s);
        }
    }

}