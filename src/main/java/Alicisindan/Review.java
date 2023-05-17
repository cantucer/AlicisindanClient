package Alicisindan;

/**
 * Review class.
 * Includes every Review getter and setter methods.
 * 
 * @author cantucer2@gmail.com
 * @version 17.05.2023
 */
public class Review {
    
    // Private instance variables.
    private String authorid, reviewedid, rating, title, text, date;
    
        
    // Variables to use while applying search filter.
    public final String NEWESTFIRST = "NewestFirst";
    public final String OLDESTFIRST = "OldesFirst";

    
    /**
     * Main constructor, for creating the Review object.
     * 
     * @param authorid ID number of the user.
     * @param reviewedid Username of the user.
     * @param rating Name(s) of the user.
     * @param title Surname of the user.
     * @param text Birthdate of the user in the format of epoch time in milliseconds.
     * @param date Address of the user.
     */
    private Review (String authorid, String reviewedid, String rating, String title, String text, String date) {
        this.authorid = authorid;
        this.reviewedid = reviewedid;
        this.rating = rating;
        this.title = title;
        this.text = text;
        this.date = date;
    }
    
    
    /**
     * Secondary constructor, for creating the Review object BEFORE UPLOAD.
     * 
     * @param authorid ID number of the user.
     * @param reviewedid Username of the user.
     * @param rating Name(s) of the user.
     * @param title Surname of the user.
     * @param text Birthdate of the user in the format of epoch time in milliseconds.
     */
    public Review (String authorid, String reviewedid, String rating, String title, String text) {
        this.authorid = authorid;
        this.reviewedid = reviewedid;
        this.rating = rating;
        this.title = title;
        this.text = text;
        this.date = "";
    }
    
        
    /**
     * Registers a review object to the database.
     * THEREFORE, CAN'T BE USED TO MAKE CHANGES TO AN EXISTING REVIEW!!
     * 
     * Already checks for review existence on the server but make sure to check beforehands anyway.
     * 
     * The date parameter is not sent and are created in the server!
     *
     * @param password of user (author).
     * @throws Exception when socket returns unexpected response.
     */
    public void addReview(String password) throws Exception {
        String[] content = new String[]{reviewedid, rating, title, text};
        Request req = new Request(Request.RequestType.AddReview, getAuthorID(), password, content);
        
        Response response = Connection.connect(req);
        
        try {
            this.date = response.getContent()[0];
        }
        catch (Exception e) {
        }
        
        if(response.getType() != Response.ResponseType.Success) {
            if(response.getType() == Response.ResponseType.WrongPassword) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.ReviewExists, response.getContent()[0]);
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
     * Checks if a review exists in the database.
     * 
     * @param authorid to check for
     * @param reviewedid to check for
     * @return bool answer
     * @throws Exception when socket returns unexpected response.
     */
    public static boolean reviewExists(String authorid, String reviewedid) throws Exception {
        String[] content = new String[]{authorid, reviewedid};
        Request req = new Request(Request.RequestType.CheckReview, "", "", content);
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.Boolean) {
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
        
        return response.getContent()[0].equals("true");
    }
    
    
    /**
     * Deletes the user from database.
     * 
     * @param password of the user 
     * @throws Exception when socket returns unexpected response.
     */
    public void deleteReview(String password) throws Exception {
        Request req = new Request(Request.RequestType.DeleteReview, getAuthorID(), password, new String[]{getReviewedID()});
        
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
        
        this.authorid = "";
        this.reviewedid = "";        
    }    
    
    
    /**
     * Searches for User objects in database.For any parameter, use the null keywoard for no filter.
     * 
     * @param authorid to filter for.
     * @param reviewedid to filter for.
     * @param exactTitle to filter for.
     * @param searchedTitle to filter for. DON'T USE WITH EXACTTITLE
     * @param minRating to filter for. Use an integer in String format.
     * @param maxRating to filter for. Use an integer in String format.
     * @param order to get users with. POSSIBLE INPUTS: User.NEWESTFIRST, User.OLDESTFIRST
     * @param offset Number of listings to skip while getting listings.
     * @param limit Number of listigns to get.
     * @return Array of User objects.
     * @throws Exception when socket returns unexpected response.
     */
    public static Review[] findReviews(String authorid, String reviewedid, String exactTitle, String searchedTitle, String minRating, String maxRating, String order, String offset, String limit) throws Exception {
        if (authorid != null && authorid.equals("")) {
            authorid = null;
        }
        if (reviewedid != null && reviewedid.equals("")) {
            reviewedid = null;
        }
        if (exactTitle != null && exactTitle.equals("")) {
            exactTitle = null;
        }
        if (searchedTitle != null && searchedTitle.equals("")) {
            searchedTitle = null;
        }
        if (minRating != null && minRating.equals("")) {
            minRating = null;
        }
        if (maxRating != null && maxRating.equals("")) {
            maxRating = null;
        }
        if (order != null && order.equals("")) {
            order = null;
        }
        if (offset != null && offset.equals("")) {
            offset = null;
        }
        if (limit != null && limit.equals("")) {
            limit = null;
        }
        
        if (exactTitle != null && searchedTitle != null) {
            throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
        }
        if (order != null) {
            if (!order.equals("NewestFirst") && !order.equals("OldestFirst")) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }
        
        if(minRating != null) {
            try {
                Double.valueOf(minRating);
            }
            catch(Exception e) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }
        if(maxRating != null) {
            try {
                Double.valueOf(maxRating);
            }
            catch(Exception e) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }
        }

        if(minRating != null && maxRating != null) {
            if (Double.valueOf(minRating) > Double.valueOf(maxRating)) {
                throw new AlicisindanException(AlicisindanException.ExceptionType.SearchFilterMisusage);
            }   
        }
        
        Request req = new Request(Request.RequestType.FindReviews, "", "", new String[]{authorid, reviewedid, exactTitle, searchedTitle, minRating, maxRating, order, offset, limit});
        
        Response response = Connection.connect(req);
        
        if(response.getType() != Response.ResponseType.UserObjects) {
            
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
        
        Review[] results = new Review[returned.length/6];
        for(int i = 0, j = 0; i<returned.length; i+=6, j++) {
            results[j] = new Review(returned[i], returned[i+1], returned[i+2], returned[i+3], returned[i+4], returned[i+5]);
        }
        
        return results;
    }
    
    
    /**
     * Gets the average reting for an user.
     * 
     * @param reviewedid of user.
     * @return average in double format
     * @throws Exception when socket returns unexpected response.
     */
    public static double getRatingAverageFor(String reviewedid) throws Exception {
        Request req = new Request(Request.RequestType.GetAverage, "", "", new String[]{reviewedid});
        
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
        
        String[] returned = response.getContent();
        
        return Double.valueOf(returned[0]);
    }
    
    
    /**
     * Returns review's author's id.
     * 
     * @return user id in string format
     */
    public String getAuthorID() {
        return authorid;
    }
    
    
    /**
     * Returns review's target's id.
     * 
     * @return user id in string format
     */
    public String getReviewedID() {
        return reviewedid;
    }
    
    
    /**
     * Returns review's rating.
     * 
     * @return user id in string format
     */
    public String getRating() {
        return rating;
    }
    
    
    /**
     * Returns review's title.
     * 
     * @return user id in string format
     */
    public String getTitle() {
        return title;
    }
    
    
    /**
     * Returns review's text.
     * 
     * @return user id in string format
     */
    public String getText() {
        return text;
    }
    
    
   /**
     * Returns review's date.
     * 
     * @return user id in string format
     */
    public String getDate() {
        return date;
    }
}
