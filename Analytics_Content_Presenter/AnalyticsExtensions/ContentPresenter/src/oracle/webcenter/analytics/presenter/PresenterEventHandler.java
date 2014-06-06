package oracle.webcenter.analytics.presenter;

import java.util.List;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.webcenter.content.integration.Node;

import org.apache.myfaces.trinidad.model.SortableModel;

/**
 * Handles detail/list views events in Content Presenter
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class PresenterEventHandler {
    
    /**
     * Logger
     */
    private static final ADFLogger _logger = ADFLogger.createADFLogger(PresenterEventHandler.class);
    
    /**
     * Default Constructor
     */
    public PresenterEventHandler() {
        super();
    }

    /**
     * Collects the information in Analytcis of a content shown in a Detail Content Presenter Template
     */
    public void contentPresenterDetailView() {
        _logger.entering(PresenterEventHandler.class.getName(),"contentPresenterDetailView");
        // Check if Analytics is configured for the current WebCenter Portal Application
        if (!PresenterAnalytics.isAnalyticsSendingEvents()) {
            _logger.info("Analytics is not sending events.");
            return;
        }
        
        // Not collecting views in Edit Mode (Composer)
        if (PresenterUtilities.isEditMode()) {
            _logger.info("Analytics not collecting information. Composer Edit Mode: ON");
            return;
        }

        // Invoking the same backing bean used by the .jsff presenter to retrieve the nodes
        // FIXME: Better create own bean to call the result??? In case of CP changes in future patches of Oracle will crash
        Node node = (Node)ADFContext.getCurrent().getExpressionEvaluator().evaluate("#{backingBeanScope.presenterBacking.result}");
        
        // Verify if the Node has information
        if (node != null) {
            try {
                PresenterAnalytics.sendContentPresenterViewEvent(node, PresenterConstants.Event.PRESENTER_DETAIL_EVENT);
            } catch (PresenterAnalyticsException e) {
                _logger.warning("Presenter Analytics Extension not working properly",e);
            }
        } else {
            _logger.info("No content to collect in this Content Presenter Detail Template");
        }
        _logger.exiting(PresenterEventHandler.class.getName(), "contentPresenterDetailView");
    }
    
    /**
     * Collects the information of the content shown in a List Content Presenter Template
     */
    public void contentPresenterListView() {
        _logger.entering(PresenterEventHandler.class.getName(),"contentPresenterListView");
        // Check if Analytics is configured for the current WebCenter Portal Application
        if (!PresenterAnalytics.isAnalyticsSendingEvents()) {
            _logger.info("Analytics is not sending events.");
            return;
        }
        
        // Not collecting views in Edit Mode (Composer)
        if (PresenterUtilities.isEditMode()) {
            _logger.info("Analytics not collecting information. Composer Edit Mode: ON");
            return;
        }

        // Invoking the same backing bean used by the .jsff presenter to retrieve the nodes
        // FIXME: Better create own bean to call the result??? In case of CP changes in future patches of Oracle will crash
        SortableModel sortableNodes = (SortableModel)ADFContext.getCurrent().getExpressionEvaluator().evaluate("#{backingBeanScope.presenterBacking.results}");
        
        // Checking if there are content to collect
        if (sortableNodes != null && sortableNodes.getEstimatedRowCount() > 0) {
            List<Node> nodes = (List<Node>)sortableNodes.getWrappedData();
            for (Node node : nodes) {
                try {
                    PresenterAnalytics.sendContentPresenterViewEvent(node, PresenterConstants.Event.PRESENTER_LIST_EVENT);
                } catch (PresenterAnalyticsException e) {
                    _logger.warning("Presenter Analytics Extension not working properly",e);
                    break;
                }
            }
        } else {
            _logger.info("No content to collect in this Content Presenter List Template");
        }
        _logger.exiting(PresenterEventHandler.class.getName(), "contentPresenterListView");
    }
}
