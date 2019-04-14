
import java.util.HashSet;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        
        HashSet<String> test = new HashSet<>();
        test.add("a");
        test.add("b");
        test.add("c");
        
        HashSet<String> cloneTest = (HashSet<String>)(test.clone());
        
        for(String item: test)
            System.out.println(item);
        
        System.out.println("\n\n");
        for(String item: cloneTest)
            System.out.println(item);
    }
    
}
