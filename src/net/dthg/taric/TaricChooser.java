package net.dthg.taric;

import org.mozilla.javascript.NativeArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;

public class TaricChooser extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dateEntryField;
    private JButton selectFileButton;
    private JLabel filenameDisplay;
    private JProgressBar progressBar1;
    private JButton retrieveButton;
    private JComboBox sectionCombo;
    private JComboBox chapterCombo;
    private File outputFile;

    public TaricChooser() {
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                int ret = chooser.showSaveDialog(contentPane);
                if (JFileChooser.APPROVE_OPTION == ret) {
                    outputFile = chooser.getSelectedFile();
                    filenameDisplay.setText(outputFile.getPath());
                    buttonOK.setEnabled(true);
                }
            }
        });
        dateEntryField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getID());
            }
        });
        retrieveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRetrieve();
            }
        });
        sectionCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectSection();
            }
        });
    }

    private void onSelectSection() {
        Object o = sectionCombo.getSelectedItem();
        chapterCombo.removeAllItems();
        if (null != o) {
            Section s = (Section) o;
            for (Iterator i = s.getChapters().iterator(); i.hasNext();) {
                Chapter chapter = (Chapter) i.next();
                chapterCombo.addItem(chapter);
            }
        }
        pack();
    }

    private void onRetrieve() {
        progressBar1.setEnabled(true);
        progressBar1.setValue(0);
        final String date = dateEntryField.getText();
        SwingWorker<Boolean, Object> worker = new SwingWorker<Boolean, Object>() {
            public Boolean doInBackground() {
                SectionsFetch fetch = new SectionsFetch(date);
                String data = fetch.fetch();
                updateStatus(33);
                SectionTransformer transformer = new SectionTransformer();
                JSReader reader = new JSReader();
                Object na = reader.interpretAndFetch(data, "sectiontree");
                updateStatus(66);
                java.util.List<Section> list = transformer.transform((NativeArray) na);
                sectionCombo.removeAllItems();
                for (Iterator i = list.iterator(); i.hasNext();) {
                    Section section = (Section) i.next();
                    sectionCombo.addItem(section);
                }
                return true;
            }

            public void done() {
                pack();
                progressBar1.setValue(0);
                progressBar1.setEnabled(false);
            }
        };
        worker.execute();
    }


    private void onOK() {
        progressBar1.setEnabled(true);
        runTaric();
    }

    void updateStatus(final int i) {
        Runnable doSetProgressBarValue = new Runnable() {
            public void run() {
                progressBar1.setValue(i);
            }
        };
        SwingUtilities.invokeLater(doSetProgressBarValue);
    }

    private void runTaric() {
        final String date = dateEntryField.getText();
        final int chapter = ((Chapter) chapterCombo.getSelectedItem()).getCode();
        SwingWorker<Boolean, Object> worker = new SwingWorker<Boolean, Object>() {
            public Boolean doInBackground() {
                ChapterFetch fetch = new ChapterFetch(date, chapter);
                String data = fetch.fetch();
                updateStatus(33);
                ChapterTransformer transformer = new ChapterTransformer();
                JSReader reader = new JSReader();
                Object na = reader.interpretAndFetch(data, "chaptertree");
                updateStatus(66);
                try {
                    transformer.transform(new PrintStream(outputFile, "UTF-8"), (NativeArray) na);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateStatus(100);
                return true;
            }

            protected void done() {
                dispose();
            }
        };
        worker.execute();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        String[] sections = new String[21];
        for (int i = 1; i <= 21; ++i) {
            sections[i - 1] = Integer.toString(i);
        }
        dateEntryField = new JTextField(Taric.getDefaultDate(), 8);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        buttonOK = new JButton();
        buttonOK.setEnabled(false);
        buttonOK.setText("Run");
        panel2.add(buttonOK);
        buttonCancel = new JButton();
        buttonCancel.setText("Exit");
        panel2.add(buttonCancel);
        progressBar1 = new JProgressBar();
        progressBar1.setEnabled(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(progressBar1, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Date");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Output file");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label2, gbc);
        filenameDisplay = new JLabel();
        filenameDisplay.setText("(no file selected)");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(filenameDisplay, gbc);
        selectFileButton = new JButton();
        selectFileButton.setText("Select file...");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(selectFileButton, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(dateEntryField, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer3, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Section");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Chapter");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label4, gbc);
        retrieveButton = new JButton();
        retrieveButton.setText("Retrieve");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(retrieveButton, gbc);
        sectionCombo = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(sectionCombo, gbc);
        chapterCombo = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(chapterCombo, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("(YYYYmmdd)");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label5, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        contentPane.add(spacer8, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        contentPane.add(spacer10, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
