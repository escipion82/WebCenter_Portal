package es.merchan.sample.portal.util;

import java.util.Locale;

import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;

import oracle.webcenter.framework.application.WCApplicationContextFactory;
import oracle.webcenter.framework.application.context.WebCenterApplicationContext;

/**
 * Locale Util to be used for writing/reading cookies
 * @author Daniel Merchan Garcia
 */
public final class CustomLocaleUtils {
    
    /**
     * Name of the cookie storing the user preference
     */
    public static final String LANG_COOKIE = "merchan_lang";
    
    /**
     * Services class. Can not be instantiated
     */
    private CustomLocaleUtils() {
        super();
    }
    
    
    /**
     * Set a new preferred locale
     * @param lang
     */
    public static void setCookieLang(Locale lang) {
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        // Get the context-root because is the same as the cookie-path in weblogic.xml
        ServletContext appContext = (ServletContext)ADFContext.getCurrent().getEnvironment().getContext();
        // Write the cookie in the cookie path /MyApp
        Cookie langCookie = new Cookie(LANG_COOKIE,lang.getLanguage());
        //langCookie.setPath(appContext.getContextPath());
        langCookie.setPath("/");
        response.addCookie(langCookie);
    }
}
