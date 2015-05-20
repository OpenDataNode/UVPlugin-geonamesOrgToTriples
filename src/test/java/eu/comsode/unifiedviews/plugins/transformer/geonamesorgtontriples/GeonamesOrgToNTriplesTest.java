package eu.comsode.unifiedviews.plugins.transformer.geonamesorgtontriples;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import cz.cuni.mff.xrg.odcs.dpu.test.TestEnvironment;
import eu.unifiedviews.dataunit.files.FilesDataUnit;
import eu.unifiedviews.dataunit.files.WritableFilesDataUnit;
import eu.unifiedviews.helpers.dataunit.files.FilesHelper;
import eu.unifiedviews.helpers.dpu.test.config.ConfigurationBuilder;

public class GeonamesOrgToNTriplesTest {
    @Test
    public void testSmallFile() throws Exception {
        GeonamesOrgToNTriplesConfig_V1 config = new GeonamesOrgToNTriplesConfig_V1();

        // Prepare DPU.
        GeonamesOrgToNTriples dpu = new GeonamesOrgToNTriples();
        dpu.configure((new ConfigurationBuilder()).setDpuConfiguration(config).toString());

        // Prepare test environment.
        TestEnvironment environment = new TestEnvironment();

        // Prepare data unit.
        WritableFilesDataUnit filesOutput = environment.createFilesOutput("filesOutput");
        WritableFilesDataUnit filesInput = environment.createFilesInput("filesInput");
        InputStream inputFileIS = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("example-geonames-rdf.txt");
        InputStream outputFileIS = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("example-geonames-rdf-output.ttl");
        String outputFileArray = IOUtils.toString(outputFileIS);
        
        
        File tempFile = File.createTempFile("____", "fdsa");
        FileUtils.copyInputStreamToFile(inputFileIS, tempFile);
        try {
            filesInput.addExistingFile("example-geonames-rdf.txt", URI.create(tempFile.toURI().toASCIIString()).toString());

            // Run.
            environment.run(dpu);

            // Get file iterator.
            Set<FilesDataUnit.Entry> outputEntries = FilesHelper.getFiles(filesOutput);

            // Iterate over files.
            for (FilesDataUnit.Entry entry : outputEntries) {
                String outputContent = FileUtils.readFileToString(new File(new URI(entry.getFileURIString())));
                Assert.assertEquals(outputFileArray, outputContent);
            }
        } finally {
            // Release resources.
            environment.release();
        }
    }
}
