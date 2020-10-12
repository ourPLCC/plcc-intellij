package edu.rit.cs.plcc.jetbrainsPlugin.module;

import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PLCCSdkPanel extends JPanel {
    private JPanel rootPanel;
    private JComboBox comboBox1;

    public PLCCSdkPanel() {
        super(new BorderLayout());
        new PLCCToolchain(comboBox1, this).populateToolchainComboBox();
        add(rootPanel, BorderLayout.CENTER);
    }

    public Optional<String> getSdk() {
        if (comboBox1.getSelectedIndex() == -1) {
            return Optional.empty();
        } else {
            return Optional.of((String) Objects.requireNonNull(comboBox1.getSelectedItem()));
        }
    }
}
