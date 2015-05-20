package eu.comsode.unifiedviews.plugins.transformer.geonamesorgtontriples;

import com.vaadin.ui.VerticalLayout;

import eu.unifiedviews.dpu.config.DPUConfigException;
import eu.unifiedviews.helpers.dpu.vaadin.dialog.AbstractDialog;

/**
 * Vaadin configuration dialog for FilesMerger.
 */
public class GeonamesOrgToNTriplesVaadinDialog extends AbstractDialog<GeonamesOrgToNTriplesConfig_V1> {

    public GeonamesOrgToNTriplesVaadinDialog() {
        super(GeonamesOrgToNTriples.class);
    }

    @Override
    public void setConfiguration(GeonamesOrgToNTriplesConfig_V1 c) throws DPUConfigException {

    }

    @Override
    public GeonamesOrgToNTriplesConfig_V1 getConfiguration() throws DPUConfigException {
        final GeonamesOrgToNTriplesConfig_V1 c = new GeonamesOrgToNTriplesConfig_V1();

        return c;
    }

    @Override
    public void buildDialogLayout() {
        VerticalLayout layout = new  VerticalLayout();
        setCompositionRoot(layout);
    }

}
