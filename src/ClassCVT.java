import java.util.Scanner;

public class ClassCVT {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)){
            ClassAPI classAPI = new ClassAPI("https://reqres.in/api/users/");
            System.out.println(classAPI.getName(scanner.nextInt()));
        }
    }

}
