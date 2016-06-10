package hudson.plugins.jmeter.diag.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blorenz on 18.05.16.
 */
public class TestResults {

    private List<HttpSample> httpSamples;
    private String version;

    public void addSample(HttpSample sample) {
        this.httpSamples.add(sample);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return this.httpSamples.isEmpty();
    }

    /**
     * Constructor
     */
    public TestResults() {
        this.httpSamples = new ArrayList<>();
    }

    /**
     * Returns the httpSamples list.
     *
     * @return List of httpSamples.
     */
    public List<HttpSample> getHttpSamples() {
        return httpSamples;
    }

    @Override
    public String toString() {
        return "\nTestResults{" +
                "httpSamples=" + httpSamples +
                ", version='" + version + '\'' +
                '}';
    }
}
