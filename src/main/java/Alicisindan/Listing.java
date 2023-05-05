package Alicisindan;

/**
 * Listing class.
 * Includes every Listing getter and setter methods.
 *
 * You can create and save a Listing by giving its credentials to the server.
 * 
 * All getters and setters are non-static. 
 * 
 * @author cantucer2@gmail.com
 * @version 05.05.2023
 */
public class Listing {
    
    // Private instance variables.
    private String id, ownerid, type, title, description, price, creationdate;
    
    
    // Type variables
    public static final String SELL = "sell";
    public static final String BUY = "buy";

    
    /**
     * Main constructor, for creating the Listing object.
     * 
     * Image and tags are not stored within the Listing object!
     *
     * @param id ID number of the listing.
     * @param ownerid ID number of the listing's owner.
     * @param type Listing type. CAN BE "sell" OR "buy"
     * @param title Listing title.
     * @param description Listing description.
     * @param price Listing price.
     * @param creationdate Listing creation date.
     */
    private Listing (String id, String ownerid, String type, String title, String description, String price, String creationdate) {
        this.id = id;
        this.ownerid = ownerid;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.creationdate = creationdate;
    }
    
    
    /**
     * Secondary constructor, for creating the Listing object BEFORE REGISTERATION.
     * 
     * Image and tags are not stored within the Listing object!
     *
     * @param ownerid ID number of the listing's owner.
     * @param type Listing type. CAN BE "sell" OR "buy"
     * @param title Listing title.
     * @param description Listing description.
     * @param price Listing price.
     */
    public Listing (String ownerid, String type, String title, String description, String price) {
        this.id = "";
        this.ownerid = ownerid;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.creationdate = "";
    }
    
    
    /**
     * Registers a Listing object to the database. CREATES A NEW ID INDEPENDENT ON WHATEVER ID THE CURRENT OBJECT HAS!!
     * THEREFORE, CAN'T BE USED TO MAKE CHANGES TO AN EXISTING LISTING!!
     * 
     * Doesn't upload images!
     * 
     * @param userId of owner of the listing
     * @param userPassword of owner of the listing
     * @throws Exception when socket returns unexpected response.
     */
    public void addListing(String userId, String userPassword) throws Exception {
        String[] content = new String[]{type, title, description, price};
        Request req = new Request(Request.RequestType.AddListing, userId, userPassword, content);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }
    }
    
    /**
     * Returns a new Listing object that is imported from database.
     * 
     * @param id of listing
     * @return Listing object imported from database.
     * @throws Exception when socket returns unexpected response.
     */
    public static Listing getListing(String id) throws Exception {
        Request req = new Request(Request.RequestType.GetListing, "", "", new String[]{id});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.ListingObject) {
            throw new Exception();
        }
        
        String[] returned = response.getContent();
        
        return new Listing(returned[0], returned[1], returned[2], returned[3], returned[4], returned[5], returned[6]);
    }
    
    
    /**
     * Searches for Listing objects in database.
     * 
     * FOR BELOW PARAMETERS, YOU CAN USE NULL INSTEAD OF ""!
     * @param owner OWNER ID TO SEARCH FOR LISTING OWNER, "" FOR NO FILTER!
     * @param tag TAGS TO SEARCH FOR, SEPERATE TAGS WITH ;, "" FOR NO FILTER!
     * @param title LISTING TITLE TO SEARCH FOR, "" FOR NO FILTER!
     * @param type LISTING TYPE "BUY" OR "SELL", "" FOR NO FILTER!
     * @param limit NUMBER OF LISTINGS TO GET, "" FOR NO FILTER!
     * @return Array of Listing objects.
     */
    public static Listing[] searchListings (String owner, String tag, String title, String type, String limit) throws Exception {
        Request req = new Request(Request.RequestType.SearchListings, "", "", new String[]{owner, tag, title, type, limit});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.ListingObjects) {
            System.out.println(response.getType());
            throw new Exception();
        }
        
        String[] returned = response.getContent();
        
        Listing[] results = new Listing[returned.length/7];
        for(int i = 0, j = 0; i<returned.length; i+=7, j++) {
            results[j] = new Listing(returned[i], returned[i+1], returned[i+2], returned[i+3], returned[i+4], returned[i+5], returned[i+6]);
        }
        
        return results;
    }
    
    
    /**
     * Deletes listing.
     * 
     * @param userId of owner
     * @param userPassword of password
     */
    public void deleteListing(String userId, String userPassword) throws Exception{
        Request req = new Request(Request.RequestType.DeleteListing, userId, userPassword, new String[]{id});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
            throw new Exception();
        }  
    }
    
    
    /**
     * 
     * @param images
     * @param userId
     * @param userPassword 
     */
    public void setListingImages(String[] images, String userId, String userPassword) {
        
    }
    
    // TODO!
    public void getListingImages() {
        
    }
    
    /**
     * Returns listing id.
     * 
     * @return id in string format
     */
    public String getID() {
        return id;
    }
    
    
    /**
     * Returns listing's owner's id.
     * 
     * @return id in string format
     */
    public String getOwnerID() {
        return ownerid;
    }
    
    
    /**
     * Returns listing's type.
     * 
     * @return type in string format ("buy" or "sell")
     */
    public String getType() {
        return type;
    }
    
    
    /**
     * Returns listing's title.
     * 
     * @return title in string format
     */
    public String getTitle() {
        return title;
    }
    
    
    /**
     * Returns listing's description.
     * 
     * @return description in string format
     */
    public String getDescription() {
        return description;
    }
    
    
    /**
     * Returns listing's price.
     * 
     * @return price in string format
     */
    public String getPrice() {
        return price;
    }
    
    
    /**
     * Returns listing's creation date.
     * 
     * @return creation date in string format
     */
    public String getCreationDate() {
        return creationdate;
    }
    

    
       
}