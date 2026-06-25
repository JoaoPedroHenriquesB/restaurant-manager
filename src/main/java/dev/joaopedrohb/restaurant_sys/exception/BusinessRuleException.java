package dev.joaopedrohb.restaurant_sys.exception;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String message){
        super(message);
    }
}
