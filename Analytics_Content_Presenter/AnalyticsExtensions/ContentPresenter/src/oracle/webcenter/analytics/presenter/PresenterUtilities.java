package oracle.webcenter.analytics.presenter;

import oracle.adf.view.page.editor.mode.ModeContext;

import oracle.webcenter.content.integration.Node;
import oracle.webcenter.content.integration.RepositoryException;
import oracle.webcenter.content.integration.icon.IIconService;
import oracle.webcenter.content.integration.icon.IconServiceImpl;
import oracle.webcenter.content.integration.internal.PluginHelper;
import oracle.webcenter.content.integration.spi.ucm.UCMConstants;
import oracle.webcenter.doclib.internal.model.VCRUtils;

/**
 * Wrapper of utility classes used by WebCenter Content Presenter Analytics Extension
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class PresenterUtilities {
    
    /**
     * IconService to retrieve WebCenter Content icons
     */
    private static final IIconService iconService;
    
    /**
     * Initializing unique instance of iconService
     */
    static {
        iconService = PluginHelper.getImplementation(IIconService.class, IconServiceImpl.class.getClassLoader());
    }

    /**
     * Private constructor
     */
    private PresenterUtilities() {
        super();
    }

    /**
     * Retrieve the 16 icon version of the content
     * @param node - Content with all information
     * @return Path to the icon16 version
     */
    public static String getSmallContentIcon(Node node) throws RepositoryException {
        String icon = null;
        String resourceType = VCRUtils.getStringProperty(node, UCMConstants.RESOURCE_TYPE_PROP_DEF_NAME);
        if (PresenterConstants.ResourceType.WIKI_RESOURCE_TYPE.resourceTypeValue().equals(resourceType)) {
            icon = "/adf/webcenter/wiki_qualifier.png";
        } else if (PresenterConstants.ResourceType.WEB_CONTENT_RESOURCE_TYPE.resourceTypeValue().equals(resourceType)) {
            icon = "/adf/webcenter/content_qualifier.png";
        } else if (PresenterConstants.ResourceType.BLOG_RESOURCE_TYPE.resourceTypeValue().equals(resourceType)) {
            icon = "/adf/webcenter/blog_qualifier.png";
        } else {
            icon = iconService.getSmallIcon(node.getPrimaryProperty());
        }
        return icon;
    }

    /**
     * Retrieve the 32 icon version of the content
     * @param node - Content with all information
     * @return Path to the icon16 version
     */
    public static String getLargeContentIcon(Node node) throws RepositoryException {
        String icon = null;
        String resourceType = VCRUtils.getStringProperty(node, UCMConstants.RESOURCE_TYPE_PROP_DEF_NAME);
        if (PresenterConstants.ResourceType.WIKI_RESOURCE_TYPE.resourceTypeValue().equals(resourceType)) {
            icon = "/adf/webcenter/wiki_lg_qualifier.png";
        } else if (PresenterConstants.ResourceType.WEB_CONTENT_RESOURCE_TYPE.resourceTypeValue().equals(resourceType)) {
            icon = "/adf/webcenter/content_lg_qualifier.png";
        } else if (PresenterConstants.ResourceType.BLOG_RESOURCE_TYPE.resourceTypeValue().equals(resourceType)) {
            icon = "/adf/webcenter/blog_lg_qualifier.png";
        } else {
            icon = iconService.getLargeIcon(node.getPrimaryProperty());
        }
        return icon;
    }
    
    
    /**
     * Check if is Composer Mode ON
     * @return true in case of editing the page
     */
    public static boolean isEditMode() {
        return ModeContext.getCurrent().isInEditMode();    
    }
}
