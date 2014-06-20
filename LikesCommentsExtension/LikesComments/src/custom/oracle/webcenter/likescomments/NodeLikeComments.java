package custom.oracle.webcenter.likescomments;

import java.util.List;

import oracle.webcenter.content.integration.Node;
import oracle.webcenter.likes.Like;

/**
 * Store the information about a node and the related Likes and Comments
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class NodeLikeComments {
    
    /**
     * WebCenter Content Integration Node with metadata and region definition info
     */
    private Node node;
    
    /**
     * List of the recent comments associated to the node
     */
    private List recentComments;
    
    /**
     * Number of comments
     */
    private Integer commentsCount;
    
    /**
     * Number of likes
     */
    private Integer likesCount;
    
    /**
     * Current like status of the current user for the selected Node
     */
    private Like myLike;
    
    /**
     * ResourceId of the item of the activity
     */
    private String activityId;
    
    /**
     * Resource Type of the item
     */
    private String activityType;
    
    /**
     * ScopeGUID where was produced the activity
     */
    private String scopeGUID;
    
    /**
     * Default constructor
     */
    public NodeLikeComments() {
        super();
        likesCount = 0;
        commentsCount = 0;
        scopeGUID = null;
    }

    /**
     * Set WebCenter Content Node with all information of a specific content
     * @param node
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Get a Node containing all metadata and site studio information in case of a Data File
     * @return Node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Set the list of recent comments associated to the Node
     * @param recentComments
     */
    public void setRecentComments(List recentComments) {
        this.recentComments = recentComments;
    }

    /**
     * Get recent comments associated
     * @return List
     */
    public List getRecentComments() {
        return recentComments;
    }

    /**
     * Set the amount of comments linked to the item
     * @param commentsCount
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    /**
     * Get the amount of comments
     * @return Integer
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     * Set the number of likes
     * @param likesCount
     */
    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    /**
     * Get the number of likes
     * @return Integer
     */
    public Integer getLikesCount() {
        return likesCount;
    }

    /**
     * Set the JPA WcLike manager
     * @param myLike
     */
    public void setMyLike(Like myLike) {
        this.myLike = myLike;
    }

    /**
     * Get the JPA WcLike object required by the likes tag
     * @return Like 
     */
    public Like getMyLike() {
        return myLike;
    }

    /**
     * Set the resource Id of the item responsible of the activity
     * @param activityId
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    /**
     * Get the resourceId of the item responsible of the activity
     * @return resourceId
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * Set the resourceType of the item responsible of the activity
     * @param activityType
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    /**
     * Get the resourceType of the item responsible of the activity
     * @return activityType
     */
    public String getActivityType() {
        return activityType;
    }
    
    /**
     * Set the scopeGUID of the activity object
     * @param scopeGUID
     */
    public void setScopeGUID(String scopeGUID) {
        this.scopeGUID = scopeGUID;
    }

    /**
     * Get the scopeGUID of the activity object
     * @return
     */
    public String getScopeGUID() {
        return scopeGUID;
    }

    /**
     * Overrided toString
     * @return String
     */
    @Override
    public String toString() {
        return String.format("NodeLikeComments [activityId=%s, activityType=%s, node=%s, recentComments=%s, commentsCount=%d, likesCount=%d, myLike=%s, scopeGUID=%s]", activityId, activityType, node, recentComments, commentsCount, likesCount, myLike, scopeGUID);
    }
}
