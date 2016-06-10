package hudson.plugins.jmeter.diag;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import jenkins.tasks.SimpleBuildStep;
import org.apache.commons.io.IOUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by blorenz on 13.05.16.
 */
@SuppressWarnings("unused")
public class JMeterDiagnosticPublisher extends Recorder implements SimpleBuildStep {
    private boolean failOnEmpty;
    private String jtlFile;

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }
    }

    @DataBoundConstructor
    public JMeterDiagnosticPublisher(String jtlFile, boolean failOnEmpty) {
        this.jtlFile = jtlFile;
        this.failOnEmpty = failOnEmpty;
    }

    @Override
    public void perform(
            @Nonnull Run<?, ?> run,
            @Nonnull FilePath filePath,
            @Nonnull Launcher launcher,
            @Nonnull TaskListener taskListener
    ) throws InterruptedException, IOException {
        FilePath fp = new FilePath(launcher.getChannel(), filePath + "/" + this.jtlFile);
        String content = IOUtils.toString(fp.read());
        run.addAction(new JMeterDiagnosticBuildAction(content, this.failOnEmpty, run));
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }
}
