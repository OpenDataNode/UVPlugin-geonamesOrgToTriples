package eu.comsode.unifiedviews.plugins.transformer.geonamesorgtordffile;

import eu.unifiedviews.dpu.config.DPUConfigException;
import eu.unifiedviews.helpers.dpu.vaadin.dialog.AbstractDialog;

/**
 * Vaadin configuration dialog for FilesMerger.
 */
public class GeonamesOrgToRdfFileVaadinDialog extends AbstractDialog<GeonamesOrgToRdfFileConfig_V1> {

    public GeonamesOrgToRdfFileVaadinDialog() {
        super(GeonamesOrgToRdfFile.class);
    }

    @Override
    public void setConfiguration(GeonamesOrgToRdfFileConfig_V1 c) throws DPUConfigException {

    }

    @Override
    public GeonamesOrgToRdfFileConfig_V1 getConfiguration() throws DPUConfigException {
        final GeonamesOrgToRdfFileConfig_V1 c = new GeonamesOrgToRdfFileConfig_V1();

        return c;
    }

    @Override
    public void buildDialogLayout() {
    }

}
