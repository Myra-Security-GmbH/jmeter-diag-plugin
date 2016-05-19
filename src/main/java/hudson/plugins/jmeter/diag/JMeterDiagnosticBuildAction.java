package hudson.plugins.jmeter.diag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import hudson.model.Action;
import hudson.model.BallColor;
import hudson.model.ModelObject;
import hudson.model.Run;
import hudson.plugins.jmeter.diag.entity.HttpSample;
import hudson.plugins.jmeter.diag.entity.TestResults;
import hudson.plugins.jmeter.diag.parser.JMeterXmlParser;
import jenkins.model.Jenkins;
import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created by blorenz on 18.05.16.
 */
@SuppressWarnings("unused")
public class JMeterDiagnosticBuildAction implements Action, ModelObject {
    private String jtlFileContent;
    private Run<?, ?> run;

    @Override
    public String getIconFileName() {
        return "graph.gif";
    }

    @Override
    public String getDisplayName() {
        return Messages.JMeterDiag_DisplayName();
    }

    @Override
    public String getUrlName() {
        return "jmeter-diagnostic";
    }

    /**
     * Getter for run
     *
     * @return Current build run
     */
    public Run<?, ?> getRun() {
        return run;
    }

    /**
     * @param sampleId
     * @return
     * @throws IOException
     */
    public HttpSample getDetailSample(String sampleId) throws IOException {
        try {
            TestResults testResults = this.getTestResult();

            for (HttpSample sample : testResults.getHttpSamples()) {
                if (sample.getId().equals(sampleId)) {
                    return sample;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converts the given header string to a better readable string.
     *
     * @param header Header string to be parsed
     * @return HtmlString
     */
    public String parseHeader(String header) {
        return header.replaceAll("(?m)^([^:]+):(.+)", "<b>$1</b>:<span style='color:green'>$2</span>").replaceAll("\n", "<br />");
    }

    /**
     * Reformats a json string.
     *
     * @param json Json string
     * @return Formatted json string
     */
    public String parseJson(String json) {
        if (json.trim().substring(0, 1).equals("{")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            return gson.toJson(je)
                    .replaceAll("(?m)\"([^\"]+)\".*:(.+)", "\"<b>$1</b>\" : $2")
                    .replaceAll("\n", "<br />");
        }

        return json;
    }

    /**
     * Converts a simple boolean value true/false to the matching ball colors.
     *
     * @param successful True if the sample were successfull
     * @return BallColor instance of the used color
     */
    public BallColor convertSuccessFlag(boolean successful) {
        if (successful) {
            return BallColor.BLUE;
        }

        return BallColor.RED;
    }

    /**
     * Builds a link to a detail page of the httpSample.
     *
     * @param sample HttpSample to build a detail link to.
     * @return Link to detail view of the httpSample
     */
    public String buildLink(HttpSample sample) {
        return Jenkins.getInstance().getRootUrl() + this.run.getUrl() + this.getUrlName() + "/detail?sampleId=" + sample.getId();
    }

    /**
     * Returns the entity for the test result.
     *
     * @return TestResult entity parsed from the jtl file.
     */
    public TestResults getTestResult() throws ParserConfigurationException, SAXException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        JMeterXmlParser parser = new JMeterXmlParser();
        saxParser.parse(IOUtils.toInputStream(this.jtlFileContent), parser);

        return parser.getRoot();
    }

    /**
     * Constructor
     *
     * @param jtlFileContent File to parse
     */
    public JMeterDiagnosticBuildAction(String jtlFileContent, Run<?, ?> run) {
        this.jtlFileContent = jtlFileContent;
        this.run = run;
    }
}
