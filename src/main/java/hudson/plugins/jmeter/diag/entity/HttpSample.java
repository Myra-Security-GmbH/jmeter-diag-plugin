package hudson.plugins.jmeter.diag.entity;

import hudson.model.Messages;
import hudson.plugins.jmeter.diag.util.EntityUtil;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by blorenz on 13.05.16.
 */
public class HttpSample {
    private String name;
    private List<AssertionResult> assertionResults;

    private String id;
    private long duration;
    private long size = 0;
    private boolean successful;
    private Date date;
    private int responseCode;
    private long errorCount;

    private String requestHeader;
    private String responseHeader;
    private String responseData;
    private String responseFile;
    private String cookies;
    private String method;
    private String queryString;
    private URL url;

    /**
     * Constructor
     */
    public HttpSample() {
        this.assertionResults = new ArrayList<>();
    }

    /**
     * Getter for id
     *
     * @return Id of the httpSample
     */
    public String getId() {
        if (this.id == null) {
            this.id = EntityUtil.buildHash(this);
        }

        return this.id;
    }

    /**
     * Getter for errorCount
     *
     * @return Error count of the httpSample
     * Default 0 or 1 unless multiple samples are aggregated
     */
    public long getErrorCount() {
        return errorCount;
    }

    /**
     * Setter for errorCount
     *
     * @param errorCount Error count of the httpSample
     */
    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * Getter for responseCode
     *
     * @return HTTP response code of the httpSample
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Setter for responseCode
     *
     * @param responseCode HTTP response code of the httpSample
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Getter for successful
     *
     * @return True if httpSample were successful
     */
    public boolean isSuccessful() {
        return this.successful;
    }

    /**
     * Setter for successful
     *
     * @param successful True if httpSample were successful
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * Getter for date
     *
     * @return Startdate of the httpSample
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for date
     *
     * @param date Startdate of the httpSample
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for size
     *
     * @return Size of the httpSample
     */
    public long getSize() {
        return size;
    }

    /**
     * Setter for size
     *
     * @param size Size of the httpSample
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Getter for duration
     *
     * @return Duration of the httpSample
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Setter for duration
     *
     * @param duration Duration of the httpSample
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * Getter for name
     *
     * @return Returns the name of the httpSample from the lb attribute.
     */
    public String getName() {
        return name;
    }


    /**
     * Setter for name
     *
     * @param name Name of the httpsSample
     * @return self
     */
    public HttpSample setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Getter for requestHeader
     *
     * @return The full raw requestHeader.
     */
    public String getRequestHeader() {
        return requestHeader;
    }

    /**
     * Setter for requestHeader
     *
     * @param requestHeader Raw requestHeader for the httpSample
     * @return self
     */
    public HttpSample setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    /**
     * Getter for responseHeader.
     *
     * @return Returns raw response header
     */
    public String getResponseHeader() {
        return responseHeader;
    }

    /**
     * Setter for responseHeader
     *
     * @param responseHeader Raw responseHeader for the httpSample
     * @return self
     */
    public HttpSample setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    /**
     * Getter for responseData
     *
     * @return Raw responseData for the httpSample.
     */
    public String getResponseData() {
        return responseData;
    }

    /**
     * Setter for responseData.
     *
     * @param responseData Raw data returned for a response in a httpSample.
     * @return self
     */
    public HttpSample setResponseData(String responseData) {
        this.responseData = responseData;
        return this;
    }

    /**
     * Getter for responseFile.
     *
     * @return Filename of the response
     */
    public String getResponseFile() {
        return responseFile;
    }

    /**
     * Setter for responseFile.
     *
     * @param responseFile File that contains the response.
     * @return self
     */
    public HttpSample setResponseFile(String responseFile) {
        this.responseFile = responseFile;
        return this;
    }

    /**
     * Getter for cookies
     *
     * @return String representation of cookies
     */
    public String getCookies() {
        return cookies;
    }

    /**
     * Setter for cookies
     *
     * @param cookies Raw representation of cookies
     * @return self
     */
    public HttpSample setCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    /**
     * Getter for method
     *
     * @return Method for httpSample
     */
    public String getMethod() {
        return method;
    }

    /**
     * Setter for method
     *
     * @param method Method for the http request
     * @return self
     */
    public HttpSample setMethod(String method) {
        this.method = method;
        return this;
    }

    /**
     * Getter for queryString.
     *
     * @return QueryString of the httpSample
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * Setter for queryString.
     *
     * @param queryString QueryString of the httpSample
     * @return self
     */
    public HttpSample setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    /**
     * Getter for url
     *
     * @return Url of the httpSample
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Setter for url
     *
     * @param url Url of the httpSample
     * @return self
     */
    public HttpSample setUrl(URL url) {
        this.url = url;
        return this;
    }

    /**
     * Add a new assertionResult to the sample.
     *
     * @param assertionResult AssertResult to append
     */
    public void addAssertionResult(AssertionResult assertionResult) {
        this.assertionResults.add(assertionResult);
    }

    /**
     * Getter for assertionResults
     *
     * @return List of assertionResults
     */
    public List<AssertionResult> getAssertionResults() {
        return assertionResults;
    }

    @Override
    public String toString() {
        return "\nHttpSample{" +
                "name='" + name + '\'' +
                ", assertionResults=" + assertionResults +
                ", requestHeader='" + requestHeader + '\'' +
                ", responseHeader='" + responseHeader + '\'' +
                ", responseData='" + responseData + '\'' +
                ", responseFile='" + responseFile + '\'' +
                ", cookies='" + cookies + '\'' +
                ", method='" + method + '\'' +
                ", queryString='" + queryString + '\'' +
                ", url=" + url +
                '}';
    }
}
