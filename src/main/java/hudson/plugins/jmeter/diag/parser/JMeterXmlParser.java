package hudson.plugins.jmeter.diag.parser;

import hudson.plugins.jmeter.diag.entity.AssertionResult;
import hudson.plugins.jmeter.diag.entity.HttpSample;
import hudson.plugins.jmeter.diag.entity.TestResults;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by blorenz on 13.05.16.
 */
public class JMeterXmlParser extends DefaultHandler {

    private TestResults root;
    private HttpSample currentSample;
    private AssertionResult currentAssertionResult;

    private String buffer;

    public TestResults getRoot() {
        return root;
    }

    private TestResults handleRootElement(Attributes attributes) {
        this.root = new TestResults();
        this.root.setVersion(attributes.getValue("version"));

        return this.root;
    }

    private HttpSample handleSample(Attributes attributes) {
        HttpSample sample = new HttpSample();
        sample.setName(attributes.getValue("lb"));

        // duration
        if (attributes.getValue("t") != null) {
            sample.setDuration(Long.parseLong(attributes.getValue("t")));
        } else if (attributes.getValue("time") != null) {
            sample.setDuration(Long.parseLong(attributes.getValue("time")));
        }

        // size
        if (attributes.getValue("by") != null) {
            sample.setSize(Long.parseLong(attributes.getValue("by")));
        }

        // date
        if (attributes.getValue("ts") != null) {
            sample.setDate(new Date(Long.parseLong(attributes.getValue("ts"))));
        } else if (attributes.getValue("timeStamp") != null) {
            sample.setDate(new Date(Long.parseLong(attributes.getValue("timeStamp"))));
        }

        // successfull
        if (attributes.getValue("s") != null) {
            sample.setSuccessful(Boolean.parseBoolean(attributes.getValue("s")));
        }

        // responseCode
        if (attributes.getValue("rc") != null) {
            sample.setResponseCode(Integer.parseInt(attributes.getValue("rc")));
        } else if (attributes.getValue("rs") != null) {
            sample.setResponseCode(Integer.parseInt(attributes.getValue("rs")));
        }

        // errorCount
        if (attributes.getValue("ec") != null) {
            sample.setErrorCount(Long.parseLong(attributes.getValue("ec")));
        }


        // it="0"
        // lt="750"
        // ct="119"
        // rm="Forbidden"
        // tn="Thread-Gruppe 1-1"
        // dt="text"
        // de="UTF-8"
        // sc="1"
        // ng="1"
        // na="1"
        // hn="85a083b46043"

        return sample;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "testResults":
                this.handleRootElement(attributes);
                break;

            case "httpSample":
                this.currentSample = this.handleSample(attributes);
                this.root.addSample(this.currentSample);
                break;

            case "assertionResult":
                this.currentAssertionResult = new AssertionResult();
                this.currentSample.addAssertionResult(this.currentAssertionResult);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "responseHeader":
                if (this.currentSample != null) {
                    this.currentSample.setResponseHeader(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "requestHeader":
                if (this.currentSample != null) {
                    this.currentSample.setRequestHeader(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "responseData":
                if (this.currentSample != null) {
                    this.currentSample.setResponseData(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "responseFile":
                if (this.currentSample != null) {
                    this.currentSample.setResponseFile(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "cookies":
                if (this.currentSample != null) {
                    this.currentSample.setCookies(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "method":
                if (this.currentSample != null) {
                    this.currentSample.setMethod(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "queryString":
                if (this.currentSample != null) {
                    this.currentSample.setQueryString(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "java.net.URL":
                if (this.currentSample != null) {
                    try {
                        this.currentSample.setUrl(new URL(this.buffer.trim()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    this.buffer = null;
                }
                break;

            case "httpSample":
                this.currentSample = null;
                break;

            case "assertionResult":
                this.currentAssertionResult = null;
                break;

            case "name":
                if (this.currentAssertionResult != null) {
                    this.currentAssertionResult.setName(this.buffer.trim());
                    this.buffer = null;
                }
                break;

            case "failure":
                if (this.currentAssertionResult != null) {
                    this.currentAssertionResult.setFailure(this.buffer.trim().equalsIgnoreCase("true"));
                    this.buffer = null;
                }
                break;

            case "error":
                if (this.currentAssertionResult != null) {
                    this.currentAssertionResult.setError(this.buffer.trim().equalsIgnoreCase("true"));
                    this.buffer = null;
                }
                break;

            case "failureMessage":
                if (this.currentAssertionResult != null) {
                    this.currentAssertionResult.setFailureMessage(this.buffer.trim());
                    this.buffer = null;
                }
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.buffer != null) {
            this.buffer += new String(ch, start, length);
        } else {
            this.buffer = new String(ch, start, length);
        }

        this.buffer = this.buffer.replaceAll("(?m)^[ ]*", "");
    }
}
