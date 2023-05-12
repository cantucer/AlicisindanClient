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
 * @version 10.05.2023
 */
public class Listing {
    
    // Private instance variables.
    private String id, ownerid, type, title, description, price, category, location, creationdate, condition;
    
    
    // Variables to use while applying search filter.
    public final String SELL = "sell";
    public final String BUY = "buy";
    public final String NEWESTFIRST = "NewestFirst";
    public final String OLDESTFIRST = "OldesFirst";
    public final String CHEAPFIRST = "CheapFirst";
    public final String EXPENSIVEFIRST = "ExpensiveFirst";

    
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
     * @param category Listing category.
     * @param location Listing location.
     * @param creationdate Listing creation date.
     * @param condition Listing's condition.
     */
    private Listing (String id, String ownerid, String type, String title, String description, String price, String category, String location, String creationdate, String condition) {
        this.id = id;
        this.ownerid = ownerid;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.location = location;
        this.creationdate = creationdate;
        this.condition = condition;
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
     * @param category Listing category.
     * @param location Listing location.
     * @param condition Listing's condition.
     */
    public Listing (String ownerid, String type, String title, String description, String price, String category, String location, String condition) {
        this.id = "";
        this.ownerid = ownerid;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.location = location;
        this.creationdate = "";
        this.condition = condition;
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
        if(!userId.equals(this.ownerid)) {
            throw new AlicisindanException(AlicisindanException.ExceptionType.WrongID);
        }
        
        String[] content = new String[]{type, title, description, price, category, location, condition};
        Request req = new Request(Request.RequestType.AddListing, userId, userPassword, content);
        
        Response response = Connection.connect(req);
        
        try {
            this.id = response.getContent()[0];
            this.creationdate = response.getContent()[1];
        }
        catch(Exception e) {
        }
        
        if(response.getType() != Response.ResponseType.Success) {
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
    }
    
    /**
     * Returns a new Listing object that is imported from database.
     * 
     * @param id of listing
     * @return Listing object imported from database.
     * @throws Exception when socket returns unexpected response.
     */
    public static Listing getListing(String id) throws Exception {
        Request req = new Request(Request.RequestType.GetListing, "", "", new String[] {id});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.ListingObject) {
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
        
        String[] returned = response.getContent();
        
        return new Listing(returned[0], returned[1], returned[2], returned[3], returned[4], returned[5], returned[6], returned[7], returned[8], returned[9]);
    }
    
    
    /**
     * Searches for Listing objects in database. For any parameter, use the null keywoard for no filter.
     * 
     * 
     * @param ownerID to filter for.
     * @param categories to filter for. Seperate multiple categories with ";".
     * @param exactTitle to filter for.
     * @param searchedTitle to filter for. Will return listings with similar titles too. Don't use both exactTitle and searchedTitle parameters!
     * @param type to filter for. Listing.SELL or Listing.BUY
     * @param condition to filter for.
     * @param order to get listings with. POSSIBLE INPUTS: Listing.NEWESTFIRST, Listing.OLDESTFIRST, Listing.CHEAPFIRST, Listing.EXPENSIVEFIRST
     * @param offset Number of listings to skip while getting listings.
     * @param limit Number of listigns to get.
     * @return Array of Listing objects.
     * @throws Exception when socket returns unexpected response.
     */
    public static Listing[] findListings (String ownerID, String categories, String exactTitle, String searchedTitle, String type, String condition, String order, String offset, String limit) throws Exception {
        if (ownerID.equals("")) {
            ownerID = null;
        }
        if (categories.equals("")) {
            categories = null;
        }
        if (exactTitle.equals("")) {
            exactTitle = null;
        }
        if (searchedTitle.equals("")) {
            searchedTitle = null;
        }
        if (type.equals("")) {
            type = null;
        }
        if (condition.equals("")) {
            condition = null;
        }
        if (order.equals("")) {
            order = null;
        }
        if (offset.equals("")) {
            offset = null;
        }
        if (limit.equals("")) {
            limit = null;
        }
        
        if (exactTitle != null && searchedTitle != null) {
            throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
        }
        if (type != null) {
            if (!type.equals("buy") && !type.equals("sell")) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }
        if (order != null) {
            if (!order.equals("NewestFirst") && !order.equals("OldestFirst") && !order.equals("CheapFirst") && !order.equals("ExpensiveFirst")) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }
        
        Request req = new Request(Request.RequestType.FindListings, "", "", new String[]{ownerID, categories, exactTitle, searchedTitle, type, condition, order, offset, limit});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.ListingObjects) {
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
        
        String[] returned = response.getContent();
        
        Listing[] results = new Listing[returned.length/10];
        for(int i = 0, j = 0; i<returned.length; i+=10, j++) {
            results[j] = new Listing(returned[i], returned[i+1], returned[i+2], returned[i+3], returned[i+4], returned[i+5], returned[i+6], returned[i+7], returned[i+8], returned[i+9]);
        }
        
        return results;
    }
    
    
    
    /**
     * Searches for Listing objects in database. For any parameter, use the null keywoard for no filter.
     * 
     * 
     * @param ownerID to filter for.
     * @param categories to filter for. Seperate multiple categories with ";".
     * @param exactTitle to filter for.
     * @param searchedTitle to filter for. Will return listings with similar titles too. Don't use both exactTitle and searchedTitle parameters!
     * @param type to filter for. Listing.SELL or Listing.BUY
     * @param condition to filter for.
     * @param order to get listings with. POSSIBLE INPUTS: Listing.NEWESTFIRST, Listing.OLDESTFIRST, Listing.CHEAPFIRST, Listing.EXPENSIVEFIRST
     * @param offset Number of listings to skip while getting listings.
     * @param limit Number of listigns to get.
     * @return Array of Listing ids.
     * @throws Exception when socket returns unexpected response.
     */
    public static String[] findListingIDs (String ownerID, String categories, String exactTitle, String searchedTitle, String type, String condition, String order, String offset, String limit) throws Exception {
        if (ownerID.equals("")) {
            ownerID = null;
        }
        if (categories.equals("")) {
            categories = null;
        }
        if (exactTitle.equals("")) {
            exactTitle = null;
        }
        if (searchedTitle.equals("")) {
            searchedTitle = null;
        }
        if (type.equals("")) {
            type = null;
        }
        if (condition.equals("")) {
            condition = null;
        }
        if (order.equals("")) {
            order = null;
        }
        if (offset.equals("")) {
            offset = null;
        }
        if (limit.equals("")) {
            limit = null;
        }
        
        if (exactTitle != null && searchedTitle != null) {
            throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
        }
        if (type != null) {
            if (!type.equals("buy") && !type.equals("sell")) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }
        if (order != null) {
            if (!order.equals("NewestFirst") && !order.equals("OldestFirst") && !order.equals("CheapFirst") && !order.equals("ExpensiveFirst")) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }
        
        Request req = new Request(Request.RequestType.FindListingIDs, "", "", new String[]{ownerID, categories, exactTitle, searchedTitle, type, condition, order, offset, limit});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.ListingObjects) {
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
        
        return response.getContent();
    }
    
    
    /**
     * Deletes listing.
     * 
     * @param ownerId of owner
     * @param userPassword of owner
     * @throws Exception when socket returns unexpected response.
     */
    public void deleteListing(String ownerId, String userPassword) throws Exception {
        Request req = new Request(Request.RequestType.DeleteListing, ownerId, userPassword, new String[] {getID()});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.id = "";
    }
    
     
    /**
     * Gets listing images.
     * 
     * @return string array of images
     * @throws Exception when socket returns unexpected response.
     */
    public String[] getListingImages() throws Exception {
        Request req = new Request(Request.RequestType.GetListingImages, "", "", new String[] {getID()});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.ListingImages) {
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
        
        String[] returned = response.getContent();
        
        return returned;
    }
    
    
    /**
     * Adds a new image to the listing's images.
     * 
     * @param ownerId of owner
     * @param userPassword of owner
     * @param image to add
     * @throws Exception when socket returns unexpected response. 
     */
    public void addListingImage(String ownerId, String userPassword, String image) throws Exception {
        String[] content = new String[]{getID(), image};
        Request req = new Request(Request.RequestType.AddListingImage, ownerId, userPassword, content);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
    }
    
    
    /**
     * Removes an image from the listing's images.
     * 
     * @param ownerId of owner
     * @param userPassword of owner
     * @param image to remove
     * @throws Exception when socket returns unexpected response. 
     */
    public void removeListingImage(String ownerId, String userPassword, String image) throws Exception {
        String[] content = new String[]{getID(), image};
        Request req = new Request(Request.RequestType.RemoveListingImage, ownerId, userPassword, content);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
    }
    
    
    /**
     * Sets the listing's images.
     * 
     * @param ownerId of owner
     * @param userPassword of owner
     * @param images to set
     * @throws Exception when socket returns unexpected response. 
     */
    public void setListingImages(String ownerId, String userPassword, String[] images) throws Exception {
        String[] content = new String[images.length+1];
        content[0] = getID();
        for(int i = 0; i < images.length; i++) {
            content[i+1] = images[i];
        }
        Request req = new Request(Request.RequestType.SetListingImages, ownerId, userPassword, content);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
    }
    
    
    /**
     * Resets the listing's images.
     * 
     * @param ownerId of owner
     * @param userPassword of owner
     * @throws Exception when socket returns unexpected response. 
     */
    public void resetListingImages(String ownerId, String userPassword) throws Exception {
        Request req = new Request(Request.RequestType.ResetListingImages, ownerId, userPassword, new String[0]);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
     * @return owner id in string format
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
     * Returns listing's category.
     * 
     * @return category in string format
     */
    public String getCategory() {
        return category;
    }
    
    
    /**
     * Returns listing's location.
     * 
     * @return location in string format
     */
    public String getLocation() {
        return location;
    }
    
    
    /**
     * Returns listing's creation date.
     * 
     * @return creation date in string format
     */
    public String getCreationDate() {
        return creationdate;
    }
    
    
    /**
     * Returns listing's condition.
     * 
     * @return condition in string format
     */
    public String getCondition() {
        return condition;
    }
    
    
    /**
     * Changes title of a Listing object.
     * 
     * @param ownerID of the owner
     * @param password of the owner
     * @param newTitle for listing
     * @throws Exception when socket returns unexpected response.
     */
    public void setTitle(String ownerID, String password, String newTitle) throws Exception {
        String[] content = new String[]{getID(), newTitle};
        Request req = new Request(Request.RequestType.SetTitle, ownerID, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.title = newTitle;
    }
    
    
    /**
     * Changes description of a Listing object.
     * 
     * @param ownerID of the owner
     * @param password of the owner
     * @param newDescription for listing
     * @throws Exception when socket returns unexpected response.
     */
    public void setDescription(String ownerID, String password, String newDescription) throws Exception {
        String[] content = new String[]{getID(), newDescription};
        Request req = new Request(Request.RequestType.SetDescription, ownerID, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.description = newDescription;
    }
    
    
    /**
     * Changes price of a Listing object.
     * 
     * @param ownerID of the owner
     * @param password of the owner
     * @param newPrice for listing
     * @throws Exception when socket returns unexpected response.
     */
    public void setPrice(String ownerID, String password, String newPrice) throws Exception {
        String[] content = new String[]{getID(), newPrice};
        Request req = new Request(Request.RequestType.SetPrice, ownerID, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.price = newPrice;
    }
    
    
    /**
     * Changes category of a Listing object.
     * 
     * @param ownerID of the owner
     * @param password of the owner
     * @param newCategory for listing
     * @throws Exception when socket returns unexpected response.
     */
    public void setCategory(String ownerID, String password, String newCategory) throws Exception {
        String[] content = new String[]{getID(), newCategory};
        Request req = new Request(Request.RequestType.SetCategory, ownerID, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.category = newCategory;
    }
    
    
    /**
     * Changes location of a Listing object.
     * 
     * @param ownerID of the owner
     * @param password of the owner
     * @param newLocation for listing
     * @throws Exception when socket returns unexpected response.
     */
    public void setLocation(String ownerID, String password, String newLocation) throws Exception {
        String[] content = new String[]{getID(), newLocation};
        Request req = new Request(Request.RequestType.SetLocation, ownerID, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.location = newLocation;
    }
    
    
    /**
     * Changes condition of a Listing object.
     * 
     * @param ownerID of the owner
     * @param password of the owner
     * @param newCondition for listing
     * @throws Exception when socket returns unexpected response.
     */
    public void setCondition(String ownerID, String password, String newCondition) throws Exception {
        String[] content = new String[]{getID(), newCondition};
        Request req = new Request(Request.RequestType.SetCondition, ownerID, password, content);
 
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Success) {
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
        
        this.condition = newCondition;
    }
         
}