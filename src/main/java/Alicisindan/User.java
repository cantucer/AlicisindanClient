package Alicisindan;

/**
 * User class.
 * Includes every User getter and setter methods.
 *
 * You can register an User to the system by giving its credentials to the server.
 * You can get data of an already registered User from the system by providing an id.
 * You can change various details of an User by providing an id and password.
 * 
 * All getters and setters are non-static. 
 * 
 * Password and key data is not stored within the User object.
 * 
 * @author cantucer2@gmail.com
 * @version 05.05.2023
 */
public class User {
    
    // Private instance variables.
    private String id, username, name, surname, birthdate, address, email, phone, registerdate;

    
    /**
     * Main constructor, for creating the User object.
     * 
     * Password and Image data are not stored within the User object!
     *
     * @param id ID number of the user.
     * @param username Username of the user.
     * @param name Name(s) of the user.
     * @param surname Surname of the user.
     * @param birthdate Birthdate of the user in the format of epoch time in milliseconds.
     * @param address Address of the user.
     * @param email Email of the user.
     * @param phone Phone number of the user.
     * @param registerdate Account registeration date of the user.
     */
    private User (String id, String username, String name, String surname, String birthdate, String address, String email, String phone, String registerdate) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.registerdate = registerdate;
    }
    
    
    /**
     * Secondary constructor, for creating the User object BEFORE REGISTERATION.
     * 
     * Password and Image data are not stored within the User object!
     *
     * @param username Username of the user.
     * @param name Name(s) of the user.
     * @param surname Surname of the user.
     * @param birthdate Birthdate of the user in the format of epoch time in milliseconds.
     * @param address Address of the user.
     * @param email Email of the user.
     * @param phone Phone number of the user.
     */
    public User (String username, String name, String surname, String birthdate, String address, String email, String phone) {
        this.id = "";
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.registerdate = "";
    }
    
    
    /**
     * Registers an user object to the database.CREATES A NEW ID INDEPENDENT ON WHATEVER ID THE CURRENT OBJECT HAS!!
     * THEREFORE, CAN'T BE USED TO MAKE CHANGES TO AN EXISTING USER!!
     * 
     * Already checks for username, email and phone existence on the server but make sure to check beforehands anyway.
     * 
     * Doesn't upload images!
     * The ID and registeration date parameters are not sent and are created in the server!
     *
     * @param password of user.
     * @throws Exception when socket returns unexpected response.
     */
    public void registerUser(String password) throws Exception {
        String[] content = new String[]{username, name, surname, birthdate, address, email, phone};
        Request req = new Request(Request.RequestType.RegisterUser, "", password, content);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
    }
    
    
    /**
     * Returns a new User object that is imported from database.
     * 
     * @param id of user
     * @return User object imported from database.
     * @throws Exception when socket returns unexpected response.
     */
    public static User getUser(String id) throws Exception {
        Request req = new Request(Request.RequestType.GetUser, id, "", new String[0]);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.UserObject) {
            throw new Exception();
        }
        
        String[] returned = response.getContent();
        
        return new User(returned[0], returned[1], returned[2], returned[3], returned[4], returned[5], returned[6], returned[7], returned[8]);
    }


    /**
     * Returns user id.
     * 
     * @return id in string format
     */
    public String getID() {
        return id;
    }
    
    
    /**
     * Returns user username.
     * 
     * @return 
     */
    public String getUsername() {
        return username;
    }
    
    
    /**
     * Returns user name.
     * 
     * @return name in string format
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Returns user surname.
     * 
     * @return surname in string format
     */
    public String getSurname() {
        return surname;
    }
    
    
    /**
     * Returns user birthdate.
     * 
     * @return birthdate in string format (possibly epoch time in seconds?)
     */
    public String getBirthdate() {
        return birthdate;
    }
    
    
    /**
     * Returns user address.
     * 
     * @return address in string format
     */
    public String getAddress() {
        return address;
    }
    
    
    /**
     * Returns user email.
     * 
     * @return email in string format
     */
    public String getEmail() {
        return email;
    }
    
    
    /**
     * Returns user phone number.
     * 
     * @return phone number in string format
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Returns user registeration date.
     * 
     * @return date in string format (epoch seconds)
     */
    public String getRegisterDate() {
        return registerdate;
    }
    
    /**
     * Returns user image.
     * 
     * @return image in string format
     * @throws Exception when socket returns unexpected response.
     */
    public String getUserImage() throws Exception {
        Request req = new Request(Request.RequestType.GetUserImage, getID(), "", new String[0]);
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.UserImage) {
            throw new Exception();
        }
        
        String[] returned = response.getContent();
        
        return returned[0];
    }

    
    /**
     * Changes username of an User object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newUsername
     * @throws Exception when socket returns unexpected response.
     */
    public void setUsername(String id, String password, String newUsername) throws Exception {
        String[] content = new String[]{newUsername};
        Request req = new Request(Request.RequestType.SetUsername, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.username = newUsername;
    }
    
    
    /**
     * Changes name of an User object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newName
     * @throws Exception when socket returns unexpected response.
     */
    public void setName(String id, String password, String newName) throws Exception {
        String[] content = new String[]{newName};
        Request req = new Request(Request.RequestType.SetName, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.name = newName;
    }
    
    
    /**
     * Changes surname of an User object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newSurname
     * @throws Exception when socket returns unexpected response.
     */
    public void setSurname(String id, String password, String newSurname) throws Exception {
        String[] content = new String[]{newSurname};
        Request req = new Request(Request.RequestType.SetSurname, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.surname = newSurname;
    }
    
    
    /**
     * Changes birthdate of an User object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newBirthdate
     * @throws Exception when socket returns unexpected response.
     */
    public void setBirthdate(String id, String password, String newBirthdate) throws Exception {
        String[] content = new String[]{newBirthdate};
        Request req = new Request(Request.RequestType.SetBirthdate, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.birthdate = newBirthdate;
    }
    
    
    /**
     * Changes adddress of an User object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newAddress
     * @throws Exception when socket returns unexpected response.
     */
    public void setAddress(String id, String password, String newAddress) throws Exception {
        String[] content = new String[]{newAddress};
        Request req = new Request(Request.RequestType.SetAddress, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.address = newAddress;
    }
    
    
    /**
     * Changes email of an User object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newEmail
     * @throws Exception when socket returns unexpected response.
     */
    public void setEmail(String id, String password, String newEmail) throws Exception {
        String[] content = new String[]{newEmail};
        Request req = new Request(Request.RequestType.SetEmail, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.email = newEmail;
    }
    
    
    /**
     * Changes phone number of an user object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newPhone
     * @throws Exception when socket returns unexpected response.
     */
    public void setPhone(String id, String password, String newPhone) throws Exception {
        String[] content = new String[]{newPhone};
        Request req = new Request(Request.RequestType.SetPhone, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
        
        this.phone = newPhone;
    }
    
    /**
     * Changes image of an user object.
     * 
     * @param id of the user
     * @param password of the user
     * @param newImage
     * @throws Exception when socket returns unexpected response.
     */
    public void setImage(String id, String password, String newImage) throws Exception {
        String[] content = new String[]{newImage};
        Request req = new Request(Request.RequestType.SetUserImage, id, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
    }
    
    
    /**
     * Checks if an email exists in the database.
     * 
     * @param email to check for
     * @return bool
     * @throws IOException when a problem occurs during connection.
     * @throws Exception when socket returns unexpected response.
     */
    public static boolean emailExists(String email) throws Exception {
        String[] content = new String[]{email};
        Request req = new Request(Request.RequestType.CheckEmail, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Boolean) {
            throw new Exception();
        }
        
        return response.getContent()[0].equals("true");
    }
    
    
    /**
     * Checks if a phone number exists in the database.
     * 
     * @param phone to check for
     * @return bool
     * @throws IOException when a problem occurs during connection.
     * @throws Exception when socket returns unexpected response.
     */
    public static boolean phoneExists(String phone) throws Exception {
        String[] content = new String[]{phone};
        Request req = new Request(Request.RequestType.CheckPhone, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Boolean) {
            throw new Exception();
        }
        
        return response.getContent()[0].equals("true");
    }
    
    
    /**
     * Checks if an username exists in the database.
     * 
     * @param username to check for
     * @return bool
     * @throws IOException when a problem occurs during connection.
     * @throws Exception when socket returns unexpected response.
     */
    public static boolean usernameExists(String username) throws Exception {
        String[] content = new String[]{username};
        Request req = new Request(Request.RequestType.CheckUsername, "", "", content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Boolean) {
            throw new Exception();
        }
        
        return response.getContent()[0].equals("true");
    }
       
}