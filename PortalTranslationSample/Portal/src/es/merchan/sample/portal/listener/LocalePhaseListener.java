package es.merchan.sample.portal.listener;

import es.merchan.sample.portal.bean.locale.LocaleHandler;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

import oracle.adf.share.ADFContext;

import oracle.webcenter.portalframework.sitestructure.ResourceNotFoundException;
import oracle.webcenter.portalframework.sitestructure.SiteStructure;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;
import oracle.webcenter.portalframework.sitestructure.SiteStructureUtils;

public class LocalePhaseListener implements PagePhaseListener {
    public LocalePhaseListener() {
        super();
    }

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
        if (Lifecycle.PREPARE_MODEL_ID == pagePhaseEvent.getPhaseId()) {
            LocaleHandler localeHandler = (LocaleHandler)ADFContext.getCurrent().getSessionScope().get("localeHandler");
            if (localeHandler != null && localeHandler.isChanged()) {
                SiteStructureUtils.invalidateDefaultNavigationModelCache();
                localeHandler.setChanged(false);
            } else if (localeHandler == null) {
                SiteStructureUtils.invalidateDefaultNavigationModelCache();
            }
        }
    }
}
