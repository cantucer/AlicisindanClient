package Alicisindan;

/**
 * Custom exception class.
 * Extends default Exception class to include an ExceptionType enumerated type.
 * Helpful when trying to find the cause of exception.
 * 
 * @author cantucer2@gmail.com
 * @version 28.05.2023
 */
public class AlicisindanException extends Exception {
    
    ExceptionType type;
    
    public enum ExceptionType {
        WrongID,
        WrongPassword,
        ServerError,
        UnexpectedResponseType,
        SearchFilterMisusage,
        UserWithParameterExists,
        ReviewExists,
        FunctionOnCooldown
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
    
    @Override
    public void printStackTrace() {
        System.out.println("LIBRARY ERROR: " + getType().toString());
        super.printStackTrace();
    }
        
}
