package oracle.webcenter.analytics.presenter;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.webcenter.content.integration.Node;
import oracle.webcenter.content.integration.RepositoryException;
import oracle.webcenter.content.integration.spi.ucm.UCMConstants;
import oracle.webcenter.doclib.internal.model.VCRUtils;
import oracle.webcenter.framework.service.AnalyticsUtil;
import oracle.webcenter.framework.service.ServiceContext;

/**
 * Wrapper class between WebCenter Analytics and WebCenter Content Presenter Analytics Extension
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class PresenterAnalytics {
    
    /**
     * Logging
     */
    private static final ADFLogger _logger = ADFLogger.createADFLogger(PresenterAnalytics.class);
    
    /**
     * Hidden constructor / Services class
     */
    private PresenterAnalytics() {
        super();
    }
    
    /**
     * Send a WebCenter Content Node to be collected by Analytics
     * @param node - Content Node with the content information
     * @param event - Which event type
     * @throws PresenterAnalyticsException - In case of get a incorrect or corrupted field
     */
    public static void sendContentPresenterViewEvent(Node node, PresenterConstants.Event event) throws PresenterAnalyticsException {
        try {
            // Retrieving the informaiton to be stored as extra informaiton in the Dimension Table of Content Presenter Content
            String contentID = node.getId().getUid(); // dDocName - CONTENTID_
            String description = VCRUtils.getStringProperty(node, UCMConstants.COMMENTS); // xComments - DESCRIPTION_
            String repository = node.getId().getRepositoryName(); // Repository - REPOSITORY_
            String contentType = node.getContentType().getName(); // Profile/Region Definition - CONTENTTYPE_
            String resourceType = VCRUtils.getStringProperty(node, UCMConstants.RESOURCE_TYPE_PROP_DEF_NAME); // PresenterConstants.ResourceType - RESOURCETYPE_
            String fileName = node.getName(); // UCM file name - FILENAME_
            String path = node.getPath(); // UCM Path - PATH_
            String smallIcon = PresenterUtilities.getSmallContentIcon(node);
            String largeIcon = PresenterUtilities.getLargeContentIcon(node);
            
            // Analytics API mandatory field information
            String resourceId = repository + "#dDocName:" + contentID; // Unique ID by repository - RESOURCEID
            String serviceId = VCRUtils.getStringProperty(node, UCMConstants.SERVICE_ID_PROP_DEF_NAME);
            String resourceName = VCRUtils.getStringProperty(node, UCMConstants.DOCUMENT_TITLE); // dDocTitle - CONTENTNAME_
            
            if (_logger.isFinest()) {
                _logger.finest("***Content Presenter Metrics Content Information***");
                _logger.finest("resourceId: " + resourceId);
                _logger.finest("serviceId: " + serviceId);
                _logger.finest("resourceName: " + resourceName);
                _logger.finest("contentId: " + contentID);
                _logger.finest("description: " + description);
                _logger.finest("contentType: " + contentType);
                _logger.finest("resourceType" + resourceType);
                _logger.finest("fileName" + fileName);
                _logger.finest("path" + path);
                _logger.finest("smallIcon: " + smallIcon);
                _logger.finest("largeIcon: " + largeIcon);
            }
            
            // Fill the extended property map with the custom information
            Map<String, String> propertyMap = new HashMap<String, String>();
            propertyMap.put(PresenterConstants.DIM_CONTENT_ID,contentID);
            propertyMap.put(PresenterConstants.DIM_CONTENT_DESCRIPTION,description);
            propertyMap.put(PresenterConstants.DIM_CONTENT_REPOSITORY,repository);
            propertyMap.put(PresenterConstants.DIM_CONTENT_TYPE,contentType);
            propertyMap.put(PresenterConstants.DIM_RESOURCE_TYPE,resourceType);
            propertyMap.put(PresenterConstants.DIM_FILE_NAME,fileName);
            propertyMap.put(PresenterConstants.DIM_CONTENT_PATH, path); 
            propertyMap.put(PresenterConstants.DIM_CONTENT_ICON_SMALL,smallIcon);
            propertyMap.put(PresenterConstants.DIM_CONTENT_ICON_LARGE,largeIcon);
            
            // Send the event to Analytics
            AnalyticsUtil.sendResourceViewEvent(event.eventNamespaceValue(),
                                                            PresenterConstants.PRESENTER_DIMENSION,
                                                            serviceId, resourceId,
                                                            resourceName, ServiceContext.getContext().getScope(), propertyMap);
            _logger.info("Content Presenter Analytics Collector - Content Collected: " + resourceId);
        } catch (RepositoryException re) {
            throw new PresenterAnalyticsException(re);
        }
    }
    
    /**
     * Check if Analytics is configured for the current WebCenter Application
     * @return true in case of collecting events
     */
    public static boolean isAnalyticsSendingEvents() {
        return AnalyticsUtil.isSendingEvents();
    }
}
