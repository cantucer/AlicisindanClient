package Alicisindan;

/**
 * Request object used for server-client interaction.
 * 
 * @author cantucer2@gmail.com
 * @version 26.05.2023
 */
public class Request implements java.io.Serializable {

    /**
     * Request types.
     */
    public enum RequestType {
        // Ping
        Ping,
        
        // Password class requests
        CheckPassword,
        EmailToID,
        PhoneToID,
        UsernameToID,
        SetPassword,
        ResetPassword,
        
        // User class requests
        RegisterUser,
        GetUser,
        GetUserWithPassword,
        GetUserImage,
        CheckEmail,
        CheckUsername,
        CheckPhone,
        SetUsername,
        SetName,
        SetSurname,
        SetBirthdate,
        SetAddress,
        SetEmail,
        SetPhone,
        SetToken,
        GetToken,
        SetUserImage,
        DeleteUser,
        GetFavorites,
        AddFavorite,
        RemoveFavorite,
        SetFavorites,
        ResetFavorites,
        IsSurnamePublic,
        IsBirthdatePublic,
        IsAddressPublic,
        IsEmailPublic,
        IsPhonePublic,
        SetSurnamePrivacy,
        SetBirthdatePrivacy,
        SetAddressPrivacy,
        SetEmailPrivacy,
        SetPhonePrivacy,
        FindUsers,
        FindUserIDs,
        
        // Listing class requests
        AddListing,
        GetListing,
        FindListings,
        FindListingIDs,
        DeleteListing,
        GetListingShowcase,
        FindListingShowcases,
        GetListingImages,
        GetListingsFirstImage,
        AddListingImage,
        RemoveListingImage,
        SetListingImages,
        ResetListingImages,
        SetTitle,
        SetDescription,
        SetPrice,
        SetCategory,
        SetLocation,
        SetCondition,
        SetBrand,
        
        // Reeview class requests
        AddReview,
        CheckReview,
        DeleteReview,
        FindReviews,
        GetAverage,
        
        // Verification class requests
        SendOTPSms,
        SendOTPEmail
    };

    private final RequestType type;
    private final String accessor_id;
    private final String accessor_key;
    private final String[] content;
    

    /**
     * Constructor for Request object.
     * 
     * @param type Request type.
     * @param accessor_id Accessor user id.
     * @param accessor_key Accessor user password.
     * @param content Request content (dependent on request type).
     */
    public Request(RequestType type, String accessor_id, String accessor_key, String[] content) {
        this.type = type;
        this.accessor_id = accessor_id;
        this.accessor_key = accessor_key;
        this.content = content;   
    }

    /**
     * Used to get request type.
     * 
     * @return Request type.
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Used to get accessor id.
     * 
     * @return Accessor user id.
     */
    public String getAccessorID() {
        return accessor_id;
    }
    
    /**
     * Used to get acessor key.
     * 
     * @return Accessor user password.
     */
    public String getAccessorKey() {
        return accessor_key;
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