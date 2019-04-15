/**
 *  @author Rajeev Goonie
 * 
 *  This file is the runner for the interpreter. This application will run in command line/bash
 *  
 *  Assumptions:
 *      Input will continue to collect input until a period is in it
 * 
 *  Note:
 *      -In line comments placed in all files
 *      -Assumptions made are at the top of all files
 *      
 *  Test Cases:
 *      Input: (((T -> F) -> (T -> F)) ^ (F -> T)) -> F.
 *      Output: false
 * 
 *      Input: (((T -> F) -> (T -> F)) ^ (F -> T)) -> T.
 *      Output: true
 * 
 *      Input: Input: ~~F -> ~~T.
 *      Output: true
 *      
 *      Input: (T v F) -> (F ^ T).
 *      Output: false
 *
 *      Input: T - > T.
 *      Output: Error token not found '-'
 * 
 *      Input: T.T -> F.
 *      Output: true
 *      Note: Anything after the first period is ignored. If wanted to run both lines, do separately
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
