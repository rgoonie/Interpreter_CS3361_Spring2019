
import java.util.HashMap;
import java.util.Stack;

public class SemanticAnalyzer {

    private Tokenizer tokenizer = new Tokenizer();
    
    //hold the values of variables
    private HashMap<String, Boolean> state = new HashMap<>();
    
    
    public void executeCode(String input, Boolean[] out){
        tokenizer.setNewExpression(input);
        
        //clones current state to use for later. If error occurs current state stays. If fully executes, updated stated stays
        HashMap<String, Boolean> updatedState = (HashMap<String, Boolean>)state.clone();
        
        if( loadVariables(updatedState) ){
            
            //catch exception if evaluateExression throws one 
            try{
                out[0] = evaulateExpressionTo(".", updatedState);
                state = updatedState;
            }
            catch(Exception e){
                System.out.println("Error at " + tokenizer.getErrorPos() + " - variable is undefined");
            }
        }
        
        
    }
    
    /**
     * Adds/Update variables from executable code. If an undefined variable is 
     * used in expression, errors out and notifies users
     * 
     * @param updatedState The new state of variables
     * @return if the function was fully executed (true), or if there was an error (false);
     */
    private boolean loadVariables(HashMap<String, Boolean> updatedState){
        
        while( tokenizer.peekNextToken().equals("#") ){
            
            //removes hash
            tokenizer.getNextToken();
            //get var name
            String varName = tokenizer.getNextToken();
            //remove assignment
            tokenizer.getNextToken();
            
            //evaluate expression and update state
            try{
                boolean varVal = evaulateExpressionTo(";", updatedState);
                updatedState.put(varName, varVal);
            }
            catch(Exception e){
                System.out.println("Error at " + tokenizer.getErrorPos() + " - variable is undefined");
                return false;
            }
            
            //remove semi colon
            tokenizer.getNextToken();
            
        }
        
        //Debug Statements
//        System.out.println("\n\n\n\n----Keys and Vals----");
//        for(String key: updatedState.keySet())
//            System.out.println("Key:: " + key + " ---- Value:: " + updatedState.get(key));
        
        return true;
        
    }
    
    /**
     * Evaluates expression up to given symbol using a stack for variable and operators.
     * Stacks are used to try and mimic a converter from in-fix evaluator to prefix evaluator
     * 
     * If an undefined variable is used, throws an exception to later be caught and dealt with
     * 
     * @param end Where to stop evaluating code
     * @param updatedState The new state of variables
     * @return value of expression
     */
    private boolean evaulateExpressionTo(String end, HashMap<String, Boolean> updatedState) throws Exception{
        Stack<Boolean> values = new Stack<>();
        Stack<String> operators = new Stack<>();
        
        while( !tokenizer.peekNextToken().equals(end) ){
            String token = tokenizer.getNextToken();
            
            if( token.equals("T") ){
                Boolean valToPush = true;
                while(!values.isEmpty() && values.peek() == null){
                    values.pop();
                    valToPush = (!valToPush);
                }
                
                values.push(valToPush);
            }
            
            else if( token.equals("F") ){
                Boolean valToPush = false;
                while(!values.isEmpty() && values.peek() == null){
                    values.pop();
                    valToPush = (!valToPush);
                }
                
                values.push(valToPush);
            }
            
            else if( token.contains("var") ){
                Boolean valToPush = updatedState.get( token );
                if(valToPush == null)
                    throw new Exception();
                
                while(!values.isEmpty() && values.peek() == null){
                    values.pop();
                    valToPush = (!valToPush);
                }
                
                values.push(valToPush);
            }
            
            else if( token.equals("(") )
                operators.push(token);
            
            else if( token.equals(")") ){
                while( !operators.peek().equals("(") )
                    values.push( appyOperator( operators.pop(), values.pop(), values.pop() ) );
                                
                operators.pop();  
                
                Boolean valToPush = values.pop();
                while(!values.isEmpty() && values.peek() == null){
                    values.pop();
                    valToPush = (!valToPush);
                }
                
                values.push(valToPush);
            }
            
            else if( token.equals("~") )
                values.push(null);
            
            else
                operators.add(token);
        }
        
        while( !operators.isEmpty() )
            values.push( appyOperator(operators.pop(), values.pop(), values.pop() ) );

        return values.pop();
        
    }
    
    
    /**
     * @param op Operation to be performed
     * @param right Right operand
     * @param left Left operand
     * @return Result of operation
     */
    private Boolean appyOperator(String op, boolean right, boolean left){
        
        switch(op){
            case "->": return (!left) || right;
            case "V": return left || right;
            case "^": return left && right;
            
            default: return null;
        }
        
    }

    
}
