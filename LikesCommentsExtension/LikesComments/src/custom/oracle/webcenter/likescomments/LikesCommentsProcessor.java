package custom.oracle.webcenter.likescomments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.webcenter.activitystreaming.ActivityException;
import oracle.webcenter.activitystreaming.ActivityObject;
import oracle.webcenter.activitystreaming.ActivityStreamingService;
import oracle.webcenter.activitystreaming.ActivityStreamingServiceFactory;
import oracle.webcenter.comments.Comment;
import oracle.webcenter.comments.CommentsSummary;
import oracle.webcenter.content.integration.Node;
import oracle.webcenter.content.integration.RepositoryException;
import oracle.webcenter.content.integration.spi.ucm.UCMConstants;
import oracle.webcenter.doclib.internal.model.VCRUtils;
import oracle.webcenter.framework.service.Scope;
import oracle.webcenter.framework.service.ServiceContext;
import oracle.webcenter.framework.service.ServiceObjectType;
import oracle.webcenter.likes.Like;
import oracle.webcenter.likes.LikesSummary;

/**
 * Utility class to access to Likes and Comments of a specific Node.
 * This class will access in Map EL Expression way
 * TODO: Implement a Declarative Component / or bean bigger scope to don't recalculate everything
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class LikesCommentsProcessor {
    
    /**
     * Logger
     */
    private static final ADFLogger LOG =
        ADFLogger.createADFLogger(LikesCommentsProcessor.class);
    
    /**
     * Class name to be used by the logger
     */
    private static final String CLASS_NAME =
        LikesCommentsProcessor.class.getName();

    /**
     * Map holding nodeLikesComments
     */
    private Map<Node, NodeLikeComments> nodeLikesComments;

    /**
     * Default Constructor
     */
    public LikesCommentsProcessor() {
        super();
        
        // Implementation via Map EL expression
        nodeLikesComments = new HashMap<Node, NodeLikeComments>() {
                @Override
                public NodeLikeComments get(Object key) {
                    if (key != null && key instanceof Node) {
                        Node node = (Node)key;
                        NodeLikeComments nlc = this.getCommentsLikes(node);
                        return nlc;
                    } else {
                        return super.get(key);
                    }
                }

                /**
                 * Extract from a Node all Comments and Likes
                 * @param node
                 */
                private NodeLikeComments getCommentsLikes(Node node) {
                    LOG.entering(CLASS_NAME, "getCommentsLikes");
                    // FIXME prevent Folder item asking about isFolder
                    NodeLikeComments nlc = new NodeLikeComments();
                    nlc.setNode(node);
                    try {
                        ActivityStreamingService as = ActivityStreamingServiceFactory.getInstance().getActivityStreamingService();
                        // Extract inforamtion required for ActivityStreaming API and Likes Tag
                        String resourceId = getResourceId(node);
                        String serviceId = VCRUtils.getStringProperty(node, UCMConstants.SERVICE_ID_PROP_DEF_NAME);
                        String resourceType = VCRUtils.getStringProperty(node, UCMConstants.RESOURCE_TYPE_PROP_DEF_NAME);
                        String name = node.getName();
                        ServiceObjectType serviceObjType = as.findObjectType(serviceId, resourceType);
                        ActivityObject activityObject = as.createObject(resourceId, serviceObjType, name);
                        activityObject.setServiceID(serviceId);
                        ActivityObject actObj = ActivityStreamingServiceFactory.getInstance().getActivityStreamingService().getObjectDetailsManager().getObjectDetail(activityObject);
                        // Extract all information using ActivityObject and node information
                        if (actObj != null) {
                            nlc = getCommentsLikesFromActivityObject(actObj, nlc);
                        } else {
                            // In case of not being registered yet the Id and the Type must to be provided
                            // FIXME: Current GUID or default GUID???? to be decided...
                            nlc.setScopeGUID(ServiceContext.getContext().getScope().getGUID());
                            //nlc.setScopeGUID(ServiceContext.getContext().getDefaultScope().getGUID());
                            nlc.setActivityId(activityObject.getId());
                            nlc.setActivityType(activityObject.getType().getName());
                        }
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    } catch (ActivityException e) {
                        e.printStackTrace();
                    }
                    return nlc;
                }

                /**
                 * Auxiliar method to get the resourceId expected from the content
                 * @param node
                 * @return [repositoryName]#dDocName:[dDocNameValue]
                 */
                private String getResourceId(Node node) {
                    String repository = node.getId().getRepositoryName();
                    String dDocName = node.getId().getUid();
                    return repository + "#dDocName:" + dDocName;
                }

                /**
                 * Extract and store likes and comments from an ActivityObject
                 * @param actObj with all content information about comments and Likes
                 * @param nlc NodeLikeComments to fill
                 */
                private NodeLikeComments getCommentsLikesFromActivityObject(ActivityObject actObj,
                                                                            NodeLikeComments nlc) {
                    int commentsCount = 0;
                    int likesCount = 0;
                    Like myLike = null;
                    List<Comment> recentComments = null;
                    try {
                        // Retrieving all comments 
                        CommentsSummary commentsSummary = actObj.getCommentsSummary();
                        if (commentsSummary != null) {
                            commentsCount = commentsSummary.getCount();
                            recentComments = commentsSummary.getRecentComments();
                            for (Comment o : recentComments) {
                                // TODO: Testing API purpose
                                LOG.fine("Comment:" + o.toString());
//                                LOG.fine("AuthorId:" + o.getId());
//                                LOG.fine("CommentText:" + o.getCommentText());
//                                LOG.fine("Creation Date:" + o.getCreationDate());
                            }
                        }
                        LikesSummary likesSummary = actObj.getLikesSummary();
                        if (likesSummary != null) {
                            likesCount = likesSummary.getCount();
                            myLike = likesSummary.getMyLike();
                        }
                        nlc.setActivityType(actObj.getType().getName());
                        nlc.setActivityId(actObj.getId());
                        Scope scope = actObj.getScope();
                        if (scope != null) {
                            nlc.setScopeGUID(scope.getGUID());
                        } else {
                            nlc.setScopeGUID(ServiceContext.getContext().getDefaultScope().getGUID());
                        }
                        // nlc.setScopeGUID(actObj.getScope().getGUID());
                        //nlc.setScopeGUID(ServiceContext.getContext().getScope().getGUID());
                        nlc.setRecentComments(recentComments);
                        nlc.setCommentsCount(Integer.valueOf(commentsCount));
                        nlc.setLikesCount(Integer.valueOf(likesCount));
                        nlc.setMyLike(myLike);
                        if (LOG.isFinest()) {
                            LOG.finest(CLASS_NAME,"getCommentsLikes",nlc.toString());
                        }
                    } catch (ActivityException e) {
                        LOG.warning(CLASS_NAME,"getCommentsLikes","Error using Activity Stream API for Likes / Comments",e);
                    }
                    LOG.exiting(CLASS_NAME, "getCommentsLikes");
                    return nlc;
                }
            };
    }

    /**
     * Get map containing the nodes and likes associated to the content
     * @return Map
     */
    public Map<Node, NodeLikeComments> getNodeLikesComments() {
        return nodeLikesComments;
    }
}
