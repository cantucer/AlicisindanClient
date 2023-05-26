package Alicisindan;

/**
 * Verification class.
 * Includes every verification method.
 * 
 * @author cantucer2@gmail.com
 * @version 26.05.2023
 */
public class Verification {
    
    /**
     * Not working.
     * @deprecated 
     */
    @Deprecated
    public static void sendOTPSMS() {
    }
    
    
    /**
     * Checks if an username exists in the database.
     * 
     * @param email to send OTP mail
     * @param username to use for email
     * @return OTP code sent
     * @throws Exception when socket returns unexpected response.
     */
    public static String sendOTPEmail(String email, String username) throws Exception {
        String[] content = new String[]{email, username};
        Request req = new Request(Request.RequestType.SendOTPEmail, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.SingleString) {
            if(response.getType() == Response.ResponseType.WrongPassword) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.WrongPassword);
            }
            else if(response.getType() == Response.ResponseType.Error) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.ServerError, response.getContent()[0]);
            }
            else {
                throw new AlicisindanException(AlicisindanException.ExceptionType.UnexpectedResponseType);
            }
        }
        
        return response.getContent()[0];
    }
    
}
