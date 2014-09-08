package es.merchan.sample.portal.bean.locale;

import es.merchan.sample.portal.util.CustomLocaleUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;

import oracle.webcenter.navigationframework.NavigationModel;

import oracle.webcenter.navigationframework.NavigationResource;
import oracle.webcenter.navigationframework.ResourceNotFoundException;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;

import oracle.webcenter.portalframework.sitestructure.SiteStructureUtils;

import org.apache.commons.lang3.LocaleUtils;

/**
 * Managed Bean to handle the Locale
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class LocaleHandler {

    /**
     * List of the supported Locales by the WebCenter Portal Application
     */
    private List<SelectItem> supportedLocales;

    /**
     * Holds the current locale
     */
    private Locale currentLocale;
    
    /**
     * Flag to indicate if the language was changed
     */
    private boolean changed;

    /**
     * Default constructor
     */
    public LocaleHandler() {
        super();
        // Initialize the supportedLocales list
        Iterator<Locale> iteratorSupportedLocales = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        supportedLocales = new ArrayList<SelectItem>();
        SelectItem itemLocale = null;
        while (iteratorSupportedLocales.hasNext()) {
            Locale locale = iteratorSupportedLocales.next();
            itemLocale = new SelectItem(locale, locale.getDisplayName(), locale.getDisplayName());
            supportedLocales.add(itemLocale);
        }
        currentLocale = ADFContext.getCurrent().getLocale();
        changed = true;
    }

    /**
     * Change the language from a given action of an JSF ActionListener
     * @param ae
     */
    public void changeLanguageEvent(ActionEvent ae) {
        CustomLocaleUtils.setCookieLang(currentLocale);
        NavigationModel navModel = SiteStructureContext.getInstance().getCurrentNavigationModel();
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        try {
            NavigationResource navResource = navModel.getCurrentSelection();
            String prettyUrl = ectx.getRequestContextPath() + navResource.getGoLinkPrettyUrl();
            changed = true;
            ectx.redirect(prettyUrl);
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Set changed
     * @param changed
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Get changed flag
     * @return boolean
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Get the supported locales by the WebCenter Application
     * @return List of locales
     */
    public List<SelectItem> getSupportedLocales() {
        return supportedLocales;
    }

    /**
     * Set the current locale in a variable to easier access
     * @param currentLocale
     */
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    /**
     * Get the current locale
     * @return
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }
}
