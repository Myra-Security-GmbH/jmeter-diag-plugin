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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by blorenz on 13.05.16.
 */
@SuppressWarnings("unused")
public class JMeterDiagnosticPublisher extends Recorder implements SimpleBuildStep {

    private String jtlFile;

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }
    }

    @DataBoundConstructor
    public JMeterDiagnosticPublisher(String jtlFile) {
        this.jtlFile = jtlFile;
    }

    @Override
    public void perform(
            @Nonnull Run<?, ?> run,
            @Nonnull FilePath filePath,
            @Nonnull Launcher launcher,
            @Nonnull TaskListener taskListener
    ) throws InterruptedException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath + "/" + this.jtlFile)));
        run.addAction(new JMeterDiagnosticBuildAction(content, run));
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }
}
