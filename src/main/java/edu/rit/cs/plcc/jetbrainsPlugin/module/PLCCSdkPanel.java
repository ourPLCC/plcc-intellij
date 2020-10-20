package edu.rit.cs.plcc.jetbrainsPlugin.module;

import edu.rit.cs.plcc.jetbrainsPlugin.util.JdkToolchain;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PLCCSdkPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox plccLocation;
    private JComboBox jdkComboBox;

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
