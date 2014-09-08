package es.merchan.sample.portal.filter.locale;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Wrapper used to override the custom HttpServletRequest of the Locale Filter
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class LocaleRequestWrapper extends HttpServletRequestWrapper{
    
    /**
     * Locale to apply
     */
    private Locale locale = null;


    /**
     * Initializes de wrapped request setting the language to be used
     * @param req
     * @param lang
     */
    public LocaleRequestWrapper(HttpServletRequest req, String lang) {
        super(req);
        if (StringUtils.isNotEmpty(lang)) {
            // Preferred locale by the user (Cookie)
            locale = LocaleUtils.toLocale(lang);
        } else {
            // Default locale, english hardcoded
            locale = new Locale("en");
        }
    }

    /**
     * Setting the language to be shown instead of the browser Accept-Languages
     * @return Enumeration with just the desired locale
     */
    @Override
    public Enumeration getLocales() {
        Vector locales = new Vector();
        locales.add(locale);
        return locales.elements();
    }
}
