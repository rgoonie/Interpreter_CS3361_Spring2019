
import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        
        Scanner kb = new Scanner(System.in);
        String input;
        LexicalAnalyzer syntax = new LexicalAnalyzer();
        SemanticAnalyzer semantics = new SemanticAnalyzer();
        
        System.out.println("----------------------------");
        System.out.println("The Rajeev Project IDLE v3.5");
        System.out.println("----------------------------");
        
        
        while(true){
            System.out.print(">>>");
            
            input = kb.nextLine();
            while( !input.contains(".") )
                input += kb.nextLine();
            
            
            Boolean[] output = {null};
            
            if(syntax.isCorrect(input)){
                
                //System.out.println("\n\nConsiseCode" + consiseCode[0] + "\n\n");
                semantics.executeCode(input, output);
                if(output[0] != null)
                    System.out.println(output[0] + "\n");
                
            }       
            
            
        }     
        
    }
    
}
