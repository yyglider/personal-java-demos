public class Main {

    public static void main(String[] args) {

        String file = "ZzwtServer20160728.exe";
        int index = file.lastIndexOf("\\");

        System.out.println(file.substring(index+1));
    }
}
