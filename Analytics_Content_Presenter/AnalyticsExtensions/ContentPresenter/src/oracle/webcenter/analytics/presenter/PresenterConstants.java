package oracle.webcenter.analytics.presenter;

import oracle.webcenter.content.integration.spi.ucm.UCMConstants;

/**
 * Constants used by WebCenter Analytics - Content Presenter Metrics extension
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class PresenterConstants {

    /**
     * The dimension the other parameters of this call apply to. This should be filled with the display name of the corresponding dimension column in the event's FACT table
     */
    public static final String PRESENTER_DIMENSION = "CONTENT";
    
    // Extra properties stored in Dimension Table for Content Presenter
    
    /**
     * dDocName
     */
    public static final String DIM_CONTENT_ID = "CONTENTID";
    
    /**
     * Content Type
     */
    public static final String DIM_CONTENT_TYPE = "CONTENTTYPE";
    
    /**
     * Resource Type
     * @see oracle.webcenter.analytics.presenter.PresenterConstants.ResourceType
     */
    public static final String DIM_RESOURCE_TYPE = "RESOURCETYPE";
    
    /**
     * FileName
     */
    public static final String DIM_FILE_NAME = "FILENAME";
    
    /**
     * WebCenter Content Repository Name
     */
    public static final String DIM_CONTENT_REPOSITORY = "REPOSITORY";
    
    /**
     * File path
     */
    public static final String DIM_CONTENT_PATH = "PATH";
    
    /**
     * xComments
     */
    public static final String DIM_CONTENT_DESCRIPTION = "DESCRIPTION";
    
    /**
     * Icon16 path
     */
    public static final String DIM_CONTENT_ICON_SMALL = "SMALLICON";
    
    /**
     * Icon32 path
     */
    public static final String DIM_CONTENT_ICON_LARGE = "LARGEICON";

    /**
     * Hidden constructor
     */
    private PresenterConstants() {
        super();
    }

    /**
     * Enum of Content Presenter Events
     * @author Daniel Merchan Garcia
     * @version 1.0
     */
    public enum Event {

        // Detail view
        PRESENTER_DETAIL_EVENT("CPDETAILVIEW"),

        // List view
        PRESENTER_LIST_EVENT("CPLISTVIEW");

        /**
         * Handles event value
         */
        private String contentPresenterEvent;

        /**
         * Initializate event value
         * @param event
         */
        private Event(String event) {
            this.contentPresenterEvent = event;
        }

        /**
         * Return the event namespace associated to for AnalyticsUtil API
         * @return Event Namespace Value
         */
        public String eventNamespaceValue() {
            return contentPresenterEvent;
        }
    }
    
    /**
     * Enum of the Resource Types
     * @author Daniel Merchan Garcia
     * @version 1.0
     */
    public enum ResourceType {

        // document
        DOCUMENT_RESOURCE_TYPE(UCMConstants.DOCUMENT_RESOURCE_TYPE),

        // folder
        FOLDER_RESOURCE_TYPE(UCMConstants.FOLDER_RESOURCE_TYPE),
        
        // webContent
        WEB_CONTENT_RESOURCE_TYPE(UCMConstants.WEB_CONTENT_RESOURCE_TYPE),
        
        // wiki
        WIKI_RESOURCE_TYPE(UCMConstants.WIKI_RESOURCE_TYPE),
        
        // blog
        BLOG_RESOURCE_TYPE(UCMConstants.BLOG_RESOURCE_TYPE);

        /**
         * Resource Type
         */
        private String resourceType;

        /**
         * Initializate type value
         * @param type - Resource Type
         */
        private ResourceType(String type) {
            this.resourceType = type;
        }

        /**
         * Return the resource type value
         * @return Resource Type
         */
        public String resourceTypeValue() {
            return this.resourceType;
        }
    }
}
