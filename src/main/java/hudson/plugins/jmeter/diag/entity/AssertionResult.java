package hudson.plugins.jmeter.diag.entity;

/**
 * Created by blorenz on 13.05.16.
 */
public class AssertionResult {

    /**
     * Name of the test assertion.
     */
    private String name;

    /**
     * Did the assert fail?
     */
    private boolean failure;

    /**
     * Were there an error executing the assertion.
     */
    private boolean error;

    /**
     * Message why the assertion was a failure.
     */
    private String failureMessage;

    public String getName() {
        return name;
    }

    public AssertionResult setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isFailure() {
        return failure;
    }

    public AssertionResult setFailure(boolean failure) {
        this.failure = failure;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public AssertionResult setError(boolean error) {
        this.error = error;
        return this;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public AssertionResult setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        return this;
    }

    @Override
    public String toString() {
        return "\nAssertionResult{" +
                "name='" + name + '\'' +
                ", failure=" + failure +
                ", error=" + error +
                ", failureMessage='" + failureMessage + '\'' +
                '}';
    }
}
