package Alicisindan;

/**
 * Response object used for server-client interaction.
 * 
 * @author cantucer2@gmail.com
 * @version 17.04.2023
 */
public class Response implements java.io.Serializable {
    
    public enum ResponseType {
        Pong,
        UserObject,
        UserImage,
        ListingObject,
        ListingObjects,
        ListingImages,
        Success,
        Error,
        CorrectPassword,
        WrongPassword,
        SingleString,
        Boolean
    };

    private final ResponseType type;
    private final String[] content;
    

    /**
     * Constructor for Response object.
     * 
     * @param type Response type.
     * @param content Response content (dependent on response type).
     */
    public Response(ResponseType type, String[] content) {
        this.type = type;
        this.content = content;   
    }

     /**
     * Used to get response type.
     * 
     * @return Response type.
     */
    public ResponseType getType() {
        return type;
    }
    
    /**
     * Used to get content.
     * 
     * @return Content array.
     */
    public String[] getContent() {
        return content;
    }
}

