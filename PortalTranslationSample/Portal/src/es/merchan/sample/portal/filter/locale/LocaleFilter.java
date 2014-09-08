package es.merchan.sample.portal.filter.locale;

import es.merchan.sample.portal.util.CustomLocaleUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter that sets the language based on the user preferences from a given cookie
 * In case of not given the cookie then it will take the default browser locale
 * @author Daniel Merchan Garcia
 * @version 1.0
 */
public final class LocaleFilter implements Filter {
    
    /**
     * Filter Configuration
     */
    private FilterConfig _filterConfig = null;

    /**
     * Initializing the filter
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    /**
     * Destroy the filter
     */
    public void destroy() {
        _filterConfig = null;
    }

    /**
     * Check the Cookie and overwrite the default language given by the browser
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException,
                                                   ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String preferredLocale = this.getPreferredLocaleFromCookies(req);
        LocaleRequestWrapper localeReqWrapp = new LocaleRequestWrapper(req,preferredLocale);
        chain.doFilter(localeReqWrapp, response);
    }

    /**
     * Get the preferredLocale from the cookies
     * @param req
     * @return String format locale from the configured cookie
     */
    private String getPreferredLocaleFromCookies(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        boolean found = false;
        int i = 0;
        Cookie cookie = null;
        String prefLang = null;
        if (cookies != null) {
            while (i < cookies.length && !found) {
                cookie = cookies[i];;
                if (cookie.getName().equalsIgnoreCase(CustomLocaleUtils.LANG_COOKIE)) {
                    prefLang = cookie.getValue();
                    found = true;
                }
                ++i;
            }
        }
        return prefLang;
    }
}
