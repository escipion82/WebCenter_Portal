package es.merchan.sample.skin.customskyros;

import java.util.ListResourceBundle;

/**
 * Custom Resource Bundle for the custom-skyros Skin
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class CustomSkyrosBundle extends ListResourceBundle{
    
    /**
     * Overrided labels
     */
    private static final Object[][] labels = {{"af_calendar.TIP_NEXT", "Next Test"},
                                              {"af_calendar.LABEL_TODAY", "Today Custom"}};
    
    /**
     * Default Constructor
     */
    public CustomSkyrosBundle() {
        super();
    }

    /**
     * Returns the overrided labels
     * @return
     */
    protected Object[][] getContents() {
        return labels;
    }
}
