/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexeme;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author pedro
 */
public class Lexer {
    public static int line = 1;
    private char ch = ' ';
    private FileReader file;
    
    private Hashtable words = new Hashtable();
    
    public Lexer(String fileName) throws FileNotFoundException{
        this.file = new FileReader(fileName);
        
        //insere palavras reservadas na hashtable
        
    }
    
    
    private void reserve(Word word){
        this.words.put(word.getLexeme(),word);
    }
    
    private void readch() throws IOException{
        this.ch = (char) file.read();
    }
    
    private boolean readch(char character) throws IOException{
        readch();
        if(this.ch != character){
            return false;
        }else{
            return true;
        }
    }
    
    public Token scan() throws IOException{
        
        //desconsidera delimitadores na entrada
        for(;;readch()){
            if(this.ch == ' ' ||
                    this.ch == '\t'||
                        this.ch == '\r'||
                            this.ch == '\b'){
                continue;
                
            }else{
                break;
            }
        }
        
        switch(this.ch){
            case '=':
                if(readch('=')){
                    return Word.equal;
                }else{
                    return new Token('=');
                }
        }
        
        //numero
        if(Character.isDigit(this.ch)){
            int value = 0;
            do{
                value = 10*value + Character.digit(this.ch, 10);
                readch();
            }while(Character.isDigit(this.ch));
            return new Num(value);
 
        }
        
        //identificadores
        if(Character.isLetter(this.ch)){
            StringBuffer stringBuffer = new StringBuffer();
            do{
                stringBuffer.append(this.ch);
                readch();
            }while(Character.isLetterOrDigit(this.ch));
            
            String string = stringBuffer.toString();
            Word word = (Word)words.get(string);
            if(word != null){
                return word;
            }else{
                word = new Word(string,Tag.ID);
                words.put(string,word);
                return word;
            }
            
        }
        
        Token token = new Token(this.ch);
        this.ch = ' ';
        return token;
    }
    
}