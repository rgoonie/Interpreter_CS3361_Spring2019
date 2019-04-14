/**
 * @author Rajeev Goonie
 * 
 * This file is used to parse through input and get next token in the input
 */


public class Tokenizer {
    
    private String expression;
    private int pos;
    private int prevPos;
    
    public Tokenizer(){
        expression = "";
    }
    
    public void setNewExpression(String ex){
        expression = ex;
        
        //used to get rid of beginning white space
        
        prevPos = pos = 0;
        while(expression.charAt( pos ) == ' ' || expression.charAt(pos) == '\n') pos++;
        
    }
    
    public String getNextToken(){
        String res;
        
        if(pos >= expression.length())
            return "";
        
        //gets single char tokens { ., #, ;, V, ^, ~, T, F, (, ) }
        if( ".#;V^~TF()".contains( expression.substring( pos, pos+1 ) ) ){
            res = expression.substring( pos, pos+1 );
            prevPos = pos;
            pos++;
        }
        
        //gets 2 char tokens { :=, -> }
        else if( pos+2 < expression.length() && ( ":=".equals( expression.substring(pos, pos+2 ) ) || "->".equals( expression.substring( pos, pos+2 ) ) ) ){
            res = expression.substring(pos, pos+2 );
            prevPos = pos;
            pos += 2;
        }
        
        else if( 97 <= expression.charAt(pos) && expression.charAt(pos) <= 123 ){
            res = "var " + expression.substring( pos, pos+1 );
            prevPos = pos;
            pos++;
        }
        else{
            res = expression.substring( pos, pos+1 );
            //System.out.print("This is not a valid token:: '" + expression.charAt(pos) + "' -----------------");
            prevPos = pos;
            pos++;
        }            
        
        //removes white space
        while( pos+1 < expression.length() && (expression.charAt( pos ) == ' ' || expression.charAt(pos) == '\n')) pos++;        
        
        //System.out.println("At pos:: " + pos + " -- char '" + res + "'");
        return res;
    }
    
    public String peekNextToken(){
        String res;
        
        if(pos >= expression.length())
            return "";
        
        //gets single char tokens { ., #, ;, V, ^, ~, T, F, (, ) }
        if( ".#;V^~TF()".contains( expression.substring( pos, pos+1 ) ) ){
            res = expression.substring( pos, pos+1 );
        }
        
        //gets 2 char tokens { :=, -> }
        else if( pos+2 < expression.length() && ( ":=".equals( expression.substring(pos, pos+2 ) ) || "->".equals( expression.substring( pos, pos+2 ) ) ) ){
            res = expression.substring(pos, pos+2 );
        }
        
        else if( 97 <= expression.charAt(pos) && expression.charAt(pos) <= 123 ){
            res = "var " + expression.substring( pos, pos+1 );
        }
        else{
            res = expression.substring( pos, pos+1 );
        }     
        
        //System.out.println("At pos:: " + pos + " -- char '" + res + "'");
        return res;
    }
    
    //if previous token results in error, this returns the pos of the previous token
    public int getErrorPos(){
        return prevPos;
    }
    
    //use for debugg
    public void printAllTokens(){
        while( pos < expression.length() )
            System.out.println(getNextToken());
     
    }
    
    
}
