package Alicisindan;

/**
 * Password class.
 * Includes every Password getter and setter methods.
 *
 * You can check password of an user, change it.
 * 
 * Everything is static, there is no Password object! 
 * 
 * @author cantucer2@gmail.com
 * @version 05.05.2023
 */
public class Password {
    
    // Server host ip.
    private static final String HOST = "13.51.177.36";
    private static final int PORT = 5133;
    
    
    /**
     * Checks if given password matches the password of the user with given id.
     * 
     * @param id of the user
     * @param password to check for
     * @return bool
     * @throws Exception when socket returns unexpected response.
     */
    public static boolean isCorrectPassword(String id, String password) throws Exception {
        Request req = new Request(Request.RequestType.CheckPassword, id, password, new String[0]);
 
        Response response = Connection.connect(req);
        
        if(response.getType() == Response.ResponseType.CorrectPassword) {
            return true;
        }
        else if (response.getType() == Response.ResponseType.WrongPassword) {
            return false;
        }
        else {
            throw new Exception();
        }
    }
    
    
    /**
     * Changes password of an user with given id.
     * 
     * @param id of the user
     * @param oldPassword of the user
     * @param newPassword
     * @throws Exception when socket returns unexpected response OR WHEN OLD PASSWORD IS WRONG.
     */
    public static void setPassword(String id, String oldPassword, String newPassword) throws Exception {
        String[] content = new String[]{newPassword};
        Request req = new Request(Request.RequestType.SetUsername, id, oldPassword, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
    }
    
    
    /**
     * Returns user id of an user with given email.
     * 
     * @param email to check for
     * @return user id as String
     * @throws Exception when socket returns unexpected response.
     */
    public static String emailToID(String email) throws Exception {
        String[] content = new String[]{email};
        Request req = new Request(Request.RequestType.EmailToID, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.SingleString) {
            throw new Exception();
        }
        
        return response.getContent()[0];
    }
    
    
    /**
     * Returns user id of an user with given phone.
     * 
     * @param phone number to check for
     * @return user id as String
     * @throws Exception when socket returns unexpected response.
     */
    public static String phoneToID(String phone) throws Exception {
        String[] content = new String[]{phone};
        Request req = new Request(Request.RequestType.PhoneToID, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.SingleString) {
            throw new Exception();
        }
        
        return response.getContent()[0];
    }
    
    
    /**
     * Returns user id of an user with given username.
     * 
     * @param username to check for
     * @return user id as String
     * @throws Exception when socket returns unexpected response.
     */
    public static String usernameToID(String username) throws Exception {
        String[] content = new String[]{username};
        Request req = new Request(Request.RequestType.UsernameToID, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.SingleString) {
            throw new Exception();
        }
        
        return response.getContent()[0];
    }
    
}
