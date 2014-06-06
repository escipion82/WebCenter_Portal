package oracle.webcenter.analytics.presenter;

/**
 * WebCenter Content Presenter Analytics Extension Exception Handler
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public class PresenterAnalyticsException extends Exception {
    
    /**
     * Serialization ID
     */
    @SuppressWarnings("compatibility:2396237989302294147")
    private static final long serialVersionUID = -6750723434479383917L;

    /**
     * Default constructor
     */
    public PresenterAnalyticsException() {
        super();
    }
    
    /**
     * Constructor by message param
     */
    public PresenterAnalyticsException(String message) {
        super(message);
    }
    
    /**
     * Constructor by original exception
     * @param cause Exception
     */
    public PresenterAnalyticsException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructor by message and exception
     * @param message Message of exception
     * @param cause Original exception
     */
    public PresenterAnalyticsException(String message, Throwable cause) {
        super(message,cause);
    }
}
