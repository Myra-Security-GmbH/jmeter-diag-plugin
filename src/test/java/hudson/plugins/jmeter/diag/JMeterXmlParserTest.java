package hudson.plugins.jmeter.diag;

import hudson.plugins.jmeter.diag.entity.HttpSample;
import hudson.plugins.jmeter.diag.entity.TestResults;
import hudson.plugins.jmeter.diag.parser.JMeterXmlParser;

import static org.junit.Assert.*;

import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URL;

/**
 * Created by blorenz on 13.05.16.
 */
public class JMeterXmlParserTest extends JenkinsRule {

    @Test
    public void parse() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File inputFile = new File(classLoader.getResource("testfile.jtl").getFile());
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            JMeterXmlParser parser = new JMeterXmlParser();
            saxParser.parse(inputFile, parser);

            TestResults testResults = parser.getRoot();
            assertEquals("1.2", testResults.getVersion());
            assertEquals(15, testResults.getHttpSamples().size());

            for (int i = 0; i < testResults.getHttpSamples().size(); i++) {
                HttpSample sample = testResults.getHttpSamples().get(i);

                assertTrue(sample.getUrl() instanceof URL);

                switch (i) {
                    case 0:
                        assertEquals("", sample.getCookies());
                        assertEquals("", sample.getResponseFile());
                        assertEquals("", sample.getResponseData());
                        assertEquals(750L, sample.getDuration());
                        assertEquals(559L, sample.getSize());
                        assertEquals("Fri May 13 14:34:52 CEST 2016", sample.getDate().toString());
                        assertFalse(sample.isSuccessful());
                        assertEquals(403, sample.getResponseCode());
                        assertEquals(1, sample.getErrorCount());

                        assertEquals("{ \"fqdn\" : \"cdn.afterspring.org\", \"resource\" : \"*.jpg\", \"recursive\" : true }", sample.getQueryString());
                        assertEquals("PUT", sample.getMethod());
                        assertEquals("https://myracloud-web.local/en/rapi/cacheClear/afterspring.org", sample.getUrl().toString());

                        assertEquals("Connection: keep-alive\n" +
                                        "Authorization: MYRA 32bba84827cf56ebb661b5a5214d969a:tX0eTctlmnBSLTQPasfWJoIH59XvqYT7MH9sJRwHyVU93xiZtny88gVSLIw1B66YUo5VKf31CDUzOxuGl1zIAQ==\n" +
                                        "Content-Type: application/json\n" +
                                        "Date: 2016-05-13T14:34:52+02:00\n" +
                                        "Content-Length: 76\n" +
                                        "Host: myracloud-web.local\n" +
                                        "User-Agent: Apache-HttpClient/4.2.6 (java 1.5)",
                                sample.getRequestHeader()
                        );

                        assertEquals("HTTP/1.1 403 Forbidden\n" +
                                        "Server: nginx/1.6.2\n" +
                                        "Content-Type: text/html; charset=UTF-8\n" +
                                        "Transfer-Encoding: chunked\n" +
                                        "Connection: keep-alive\n" +
                                        "Cache-Control: no-cache\n" +
                                        "Date: Fri, 13 May 2016 12:34:53 GMT\n" +
                                        "X-Content-Type-Options: nosniff\n" +
                                        "X-Frame-Options: DENY\n" +
                                        "Content-Security-Policy: default-src 'self'; script-src 'self' 'nonce-9Ji6rNn4srbaeAZXdlOETymQtSqu1ZK2PNNe8AaPdaY'; object-src 'none'; style-src 'self' 'unsafe-inline'; img-src 'self' myracloud.com *.googleapis.com data:; frame-src 'none'; report-uri /csp/report\n" +
                                        "X-XSS-Protection: 1; mode=block",
                                sample.getResponseHeader()
                        );
                        break;

                    case 1:
                        assertTrue(sample.isSuccessful());
                        break;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
