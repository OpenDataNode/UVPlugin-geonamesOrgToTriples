package eu.comsode.unifiedviews.plugins.transformer.geonamesorgtordffile;

import org.openrdf.rio.RDFFormat;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;

import eu.unifiedviews.dpu.config.DPUConfigException;
import eu.unifiedviews.helpers.dpu.vaadin.dialog.AbstractDialog;

/**
 * Vaadin configuration dialog for FilesMerger.
 */
public class GeonamesOrgToRdfFileVaadinDialog extends AbstractDialog<GeonamesOrgToRdfFileConfig_V1> {

    private NativeSelect serializationTypeSelect;

    public GeonamesOrgToRdfFileVaadinDialog() {
        super(GeonamesOrgToRdfFile.class);
    }

    @Override
    public void setConfiguration(GeonamesOrgToRdfFileConfig_V1 c) throws DPUConfigException {
        serializationTypeSelect.setValue(RDFFormat.valueOf(c.getSerializationType()));
    }

    @Override
    public GeonamesOrgToRdfFileConfig_V1 getConfiguration() throws DPUConfigException {
        final GeonamesOrgToRdfFileConfig_V1 c = new GeonamesOrgToRdfFileConfig_V1();
        final RDFFormat format = (RDFFormat) serializationTypeSelect.getValue();

        c.setSerializationType(format.getName());
        return c;
    }

    @Override
    public void buildDialogLayout() {
        FormLayout mainLayout = new FormLayout();

        setWidth("100%");
        setHeight("100%");

        serializationTypeSelect = new NativeSelect(ctx.tr("combo.serializationType"));
        for (RDFFormat item : RDFFormat.values()) {
            serializationTypeSelect.addItem(item);
            serializationTypeSelect.setItemCaption(item, item.getName());
        }
        serializationTypeSelect.setNullSelectionAllowed(false);
        serializationTypeSelect.setImmediate(true);
        mainLayout.addComponent(serializationTypeSelect);
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        setCompositionRoot(mainLayout);
    }

}
