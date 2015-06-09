package eu.comsode.unifiedviews.plugins.transformer.geonamesorgtordffile;

import org.openrdf.rio.RDFFormat;

/**
 */
public class GeonamesOrgToRdfFileConfig_V1 {

    private String serializationType = RDFFormat.TURTLE.getName();

    public String getSerializationType() {
        return serializationType;
    }

    public void setSerializationType(String serializationType) {
        this.serializationType = serializationType;
    }

}
