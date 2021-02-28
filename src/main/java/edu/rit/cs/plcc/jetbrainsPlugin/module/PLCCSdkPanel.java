package edu.rit.cs.plcc.jetbrainsPlugin.module;

import com.intellij.openapi.projectRoots.Sdk;
import edu.rit.cs.plcc.jetbrainsPlugin.util.JdkToolchain;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class PLCCSdkPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox<String> plccLocation;
    private JComboBox<Sdk> jdkComboBox;
    private JLabel label;

    public PLCCSdkPanel(PLCCModuleWizardStep wizardStep) {
        super(new BorderLayout());
        new PLCCToolchain(plccLocation, this).populateToolchainComboBox();
        new JdkToolchain(jdkComboBox, wizardStep).populateToolchainComboBox();
        add(rootPanel, BorderLayout.CENTER);
    }

    public Optional<String> getPlccLocationEntry() {
        if (plccLocation.getSelectedIndex() == -1) {
            return Optional.empty();
        } else {
            return Optional.of((String) Objects.requireNonNull(plccLocation.getSelectedItem()));
        }
    }
}
