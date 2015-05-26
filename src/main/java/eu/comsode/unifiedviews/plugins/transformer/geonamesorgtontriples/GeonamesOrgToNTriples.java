package eu.comsode.unifiedviews.plugins.transformer.geonamesorgtontriples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URI;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.unifiedviews.dataunit.DataUnit;
import eu.unifiedviews.dataunit.DataUnitException;
import eu.unifiedviews.dataunit.files.FilesDataUnit;
import eu.unifiedviews.dataunit.files.WritableFilesDataUnit;
import eu.unifiedviews.dpu.DPU;
import eu.unifiedviews.dpu.DPUException;
import eu.unifiedviews.helpers.dataunit.files.FilesHelper;
import eu.unifiedviews.helpers.dataunit.virtualpath.VirtualPathHelpers;
import eu.unifiedviews.helpers.dpu.config.ConfigHistory;
import eu.unifiedviews.helpers.dpu.context.ContextUtils;
import eu.unifiedviews.helpers.dpu.exec.AbstractDpu;

/**
 * Main data processing unit class.
 */
@DPU.AsTransformer
public class GeonamesOrgToNTriples extends AbstractDpu<GeonamesOrgToNTriplesConfig_V1> {

    private static final Logger LOG = LoggerFactory.getLogger(GeonamesOrgToNTriples.class);

    @DataUnit.AsInput(name = "filesInput")
    public FilesDataUnit filesInput;

    @DataUnit.AsOutput(name = "filesOutput")
    public WritableFilesDataUnit filesOutput;

    public GeonamesOrgToNTriples() {
        super(GeonamesOrgToNTriplesVaadinDialog.class, ConfigHistory.noHistory(GeonamesOrgToNTriplesConfig_V1.class));
    }

    @Override
    protected void innerExecute() throws DPUException {
        OutputStreamWriter outputFileWriter = null;
        String outputSymbolicName = "t-geonames-output.ttl";
        try {
            File outputFile = new File(URI.create(filesOutput.addNewFile(outputSymbolicName)));
            VirtualPathHelpers.setVirtualPath(filesOutput, outputSymbolicName, outputSymbolicName);
            outputFileWriter = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
            RDFWriter outputWriter = Rio.createWriter(RDFFormat.TURTLE, outputFileWriter);
            long count = 0;
            for (FilesDataUnit.Entry entry : FilesHelper.getFiles(filesInput)) {
                BufferedReader sc = null;
                try {
                    sc = new BufferedReader(new FileReader(new File(URI.create(entry.getFileURIString()))));
                    String line;
                    RDFParser inputParser = new RDFXMLParserSilent();//Rio.createParser(RDFFormat.RDFXML);
                    boolean lineEven = false;
                    while ((line = sc.readLine()) != null) {
                        if (lineEven) {
                            inputParser.setRDFHandler(outputWriter);
                            inputParser.parse(new StringReader(line), outputFile.toURI().toString());
                            count++;
                        }
                        lineEven = !lineEven;
                        if ((count % 1000) == 0) {
                            LOG.info("Processed " + count + " files ");
                        }
                    }
                } catch (IOException | RDFParseException | RDFHandlerException ex) {
                    throw ContextUtils.dpuException(ctx, ex, "GeonamesOrgToNTriples.execute.exception");
                } finally {
                    if (sc != null) {
                        try {
                            sc.close();
                        } catch (IOException ex) {
                        }
                    }
                }
            }
            LOG.info("Processed " + count + " files ");
        } catch (IOException | DataUnitException ex) {
            throw ContextUtils.dpuException(ctx, ex, "GeonamesOrgToNTriples.execute.exception");
        } finally {
            if (outputFileWriter != null) {
                try {
                    outputFileWriter.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
