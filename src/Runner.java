/**
 *  @author Rajeev Goonie
 * 
 *  This file is the runner for the interpreter. This application will run in command line/bash
 * 
 */

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        
        //keyboard input
        Scanner kb = new Scanner(System.in);
        String input;
        
        //Syntax Analyzer and Semantic Runner
        LexicalAnalyzer syntax = new LexicalAnalyzer();
        SemanticAnalyzer semantics = new SemanticAnalyzer();
        
        //heading to program
        System.out.println("-------------------------------------------");
        System.out.println("The Rajeev Project - Boolean Evaluator v3.5");
        System.out.println("-------------------------------------------");
        
        
        while(true){
            System.out.print(">>>");
            
            //collects input until a termination symbol (period '.' ) is within input
            input = kb.nextLine();
            while( !input.contains(".") )
                input += kb.nextLine();
            
            
            //Check syntax before executing code (Errors will be printed from LexicalAnalyzer)
            if(syntax.isCorrect(input)){
                //Debug statement
                //System.out.println("\n\nConsiseCode" + consiseCode[0] + "\n\n");
                
                //used to return input incase there is a runtime error (undefined variable is used)
                Boolean[] output = {null};
                
                //executes code and prints output (Errors will get print from the SemanticAnalyzer)
                semantics.executeCode(input, output);
                if(output[0] != null)
                    System.out.println(output[0] + "\n");
                
            }       
            
            
        }     
        
    }
    
}
