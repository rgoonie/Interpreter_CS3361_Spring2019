
public class LexicalAnalyzer {
    
    private String lex;
    private Tokenizer tokenizer = new Tokenizer();
        
    /**
     * @param input What is read in from keyboard
     * @param out Used to return code
     * @return 
     */
    public boolean isCorrect(String input){
        
        tokenizer.setNewExpression(input);
        
        //intital lex get
        lex = get();
        
        return B_Function();
        
    }
    
    
    //Syntax: <B> ::= <VA><IT>.
    private boolean B_Function(){
        
        if( "#~TF(".contains(lex) || lex.contains("var") ){
            if( VA_Function() ){
                if( IT_Function() ){
                    if( lex.equals(".") ){
                        lex = get();
                        return true;
                    }
                    
                    else{
                        System.out.println( "[B Function - 40] Error at pos " + tokenizer.getErrorPos() + "  - expected a '.' but got '"+ lex + "'\n" );
                        return false;
                    }
                }
                
                else{
                    return false;
                }
            }
            
            else{
                return false;
            }
        }
        
        else{
            System.out.println( "[B Function - 56] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { #, ~, T, F, ( } but got '"+ lex + "'\n" );
            return false;
        }
        
    }
    
    //Syntax: <VA> ::= #var := <IT>; <VA>
    //             ::=  
    private boolean VA_Function(){
        
        if( lex.equals("#") ){
            lex = get();
            if( lex.contains("var") ){
                lex = get();
                if( lex.equals(":=") ){
                    lex = get();
                    if( IT_Function() ){
                        if( lex.equals(";") ){
                            lex = get();
                            if( VA_Function() ){
                                return true;
                            }
                            
                            else{
                                return false;
                            }
                        }
                        
                        else{
                            System.out.println( "[VA Function - 88] Error at pos " + tokenizer.getErrorPos() + "  - expected a ';' but got '"+ lex + "'\n" );
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                }
                
                else{
                    System.out.println( "[VA Function - 98] Error at pos " + tokenizer.getErrorPos() + "  - expected a ':=' but got '"+ lex + "' - (not a valid variable name)\n" );
                    return false;
                }
            }
            
            else{
                System.out.println( "[VA Function - 104] Error at pos " + tokenizer.getErrorPos() + "  - not a valid var name\n" );
                return false;
            }
        }
        
        else{
            if("~TF(".contains(lex) || lex.contains("var")){
                return true;
            }
            
            else{
                System.out.println( "[VA Function 115] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { #, ~, T, F, ( } but got '"+ lex + "'\n" );
                return false;
            }
        }
        
    }
    
    //Syntax: <IT> ::= <CT><IT_Tail>
    private boolean IT_Function(){
        
        if( "~TF(".contains(lex) || lex.contains("var")){
            if( CT_Function() ){
                if( IT_Tail_Function() ){
                    return true;
                }
                
                else{
                    return false;
                }
            }
            
            else{
                return false;
            }
        }
        
        else{
            System.out.println( "[IT Function - 141] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { ~, T, F, ( } but got '"+ lex + "'\n" );
            return false;
        }
        
    }
    
    //Syntax: <IT_Tail> ::= -> <CT><IT_Tail>
    //                  ::=
    private boolean IT_Tail_Function(){
        
        if( lex.equals("->") ){
            lex = get();
            if( CT_Function() ){
                if(IT_Tail_Function()){
                    return true;
                }
                
                else{
                   return false; 
                }
            }
            
            else{
                return false;
            }
        }
        
        else{
            if(".;)".contains(lex)){
                return true;
            }
            
            else{
                System.out.println( "[IT Tail Function - 173] Error at pos " + tokenizer.getErrorPos() + "  - expected one of the following symbols { ->, ., ;, ) } but got '"+ lex + "'\n" );
                return false;
            }
        }
    }
    
    //Syntax: <CT> ::= <L><CT_Tail>
    private boolean CT_Function(){
    
        if("~TF(".contains(lex) || lex.contains("var")){
            if( L_Function() ){
                if( CT_Tail_Function() ){
                    return true;
                }
                
                else{
                    return false;
                }
            }
            
            else{
                return false;
            }
        }
        
        else{
            System.out.println( "[CT Function - 198] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { ~, T, F, ( } but got '"+ lex + "'\n" );
            return false;  
        }
        
    }
    
    //Syntax: <CT_Tail> ::= V <L> <CT_Tail>
    //                  ::= ^ <L> <CT_Tail>
    //                  ::=
    private boolean CT_Tail_Function(){
    
        if( lex.equals("V") ){
            lex = get();
            if( L_Function() ){
                if( CT_Tail_Function() ){
                    return true;
                }
                else{
                    return false;
                }
            }
            
            else{
                return false;
            }
        }
        
        else{
            if( lex.equals("^") ){
                lex = get();
                if( L_Function() ){
                    if( CT_Tail_Function() ){
                        return true;
                    }
                    
                    else{
                        return false;
                    }
                }
                
                else{
                  return false;  
                }
            }
            
            else{
                if( lex.equals("->") || ".;)".contains(lex) ){
                    return true;
                }
                
                else{
                    System.out.println( "[CT Tail Function - 248] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { V, ^, ->, ., ;, ) } but got '"+ lex + "'\n" );
                    return false; 
                }
            }
        }
        
    }
    
    //Syntax: <L> ::= <A>
    //            ::= ~ <L>
    private boolean L_Function(){
    
        if( "TF(".contains(lex) || lex.contains("var") ){
            if( A_Function() ){
                return true;
            }
            
            else{
                return false;
            }
        }
        
        else{
            if( lex.equals("~") ){
                lex = get();
                if( L_Function() ){
                    return true;
                }
                
                else{
                    return false;
                }
            }
            
            else{
                System.out.println( "[L Function - 282] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { ~, T, F, ( } but got '"+ lex + "'\n" );
                return false; 
            }
        }
        
    }
    
    //Syntax: <A> ::= T
    //            ::= F
    //            ::= var
    //            ::= ( <IT> )
    private boolean A_Function(){
    
        if( lex.equals("T") ){
            lex = get();
            return true;
        }
        
        else{
            if( lex.equals("F") ){
                lex = get();
                return true;
            }
            
            else{
                if( lex.contains("var") ){
                    lex = get();
                    return true;
                }
                
                else{
                    if( lex.equals("(") ){
                        lex = get();
                        if( IT_Function() ){
                            if( lex.equals(")")){
                                lex = get();
                                return true;
                            }
                            
                            else{
                                System.out.println( "[A Function - 323] Error at pos " + tokenizer.getErrorPos() + "  - expected a ')' but got '"+ lex + "'\n" );
                                return false; 
                            }
                        }
                        
                        else{
                            return false;
                        }
                    }
                    
                    else{
                        System.out.println( "[A Function - 334] Error at pos " + tokenizer.getErrorPos() + "  - expected a var or one of the following symbols { T, F, ( } but got '"+ lex + "'\n" );
                        return false; 
                    }
                }
            }
        }
        
    }
    
    //returns next token from tokenizer
    private String get(){
        return tokenizer.getNextToken();
    }
    
}
