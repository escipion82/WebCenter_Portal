package oracle.webcenter.analytics.presenter;

/**
 * Extended class for the new Content Presenter configuration params
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class PresenterAnalyticsConfig {
    
    /**
     * Analytics Flag about if collects data
     */
    private Boolean analyticsEnabled;
    
    /**
     * Default constructor
     */
    public PresenterAnalyticsConfig() {
        super();
        analyticsEnabled = Boolean.FALSE;
    }

    /**
     * Set collecting flag
     * @param analyticsEnabled
     */
    public void setAnalyticsEnabled(Boolean analyticsEnabled) {
        if (analyticsEnabled != null) {
            this.analyticsEnabled = analyticsEnabled;   
        } else {
            this.analyticsEnabled = Boolean.FALSE;
        }
    }

    /**
     * Get analytics flag
     * @return true in case of collecting enabled
     */
    public Boolean getAnalyticsEnabled() {
        if (this.analyticsEnabled != null) {
            return analyticsEnabled;
        } else {
            return Boolean.FALSE;
        }
    }
}
