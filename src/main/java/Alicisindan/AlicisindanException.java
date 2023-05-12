package Alicisindan;

/**
 * Custom exception class.
 * Extends default Exception class to include an ExceptionType enumerated type.
 * Helpful when trying to find the cause of exception.
 * 
 * @author cantucer2@gmail.com
 * @version 10.05.2023
 */
public class AlicisindanException extends Exception {
    
    ExceptionType type;
    
    public enum ExceptionType {
        WrongID,
        WrongPassword,
        ServerError,
        UnexpectedResponseType,
        SearchFilterMisusage;
    }
    
    public AlicisindanException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
    
    public AlicisindanException(ExceptionType type) {
        super();
        this.type = type;
    }
    
    public ExceptionType getType() {
        return type;
    }
        
}
