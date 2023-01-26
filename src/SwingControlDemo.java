import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;


public class SwingControlDemo implements ActionListener {
    private JFrame mainFrame;
    private JPanel NorthPanel;
    private JPanel SouthPanel;
    private JPanel SuperNorthPanel;
    private JLabel headerLabel;
    private JLabel textLabel1;
    private JLabel textLabel2;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel statusControlPanel;
    private JTextArea outputArea;
    private JPanel textPanel1;
    private JPanel textPanel2;
    private JScrollPane scroll;
//    private JPanel hold= new JPanel();
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextField ta;
    private JTextField tb;
    private int WIDTH=800;
    private int HEIGHT=700;
    String blankArea = "\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n";



    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();
    }

    private String HtmlRead(String url , String keyword) {
        try {
            System.out.println();
            System.out.print("hello \n");
            URL newURL = new URL(url);

            ArrayList<String> rawOutputList = new ArrayList<>();

            String output = new String("");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(newURL.openStream())
            );
            String line;
            Pattern pat = Pattern.compile("a href=");
            Pattern pat2 = Pattern.compile(keyword);
            Matcher lineMatcher;
            Matcher lineMatcher2;
            String subline;
            while ( (line = reader.readLine()) != null ) {
                lineMatcher = pat.matcher(line);
                if (lineMatcher.find() == true) {
                    subline = line.substring(line.indexOf("a href=\"") + 8);
                    subline = subline.substring(0, subline.indexOf("\""));

                    lineMatcher2 = pat2.matcher(subline);
                    if (lineMatcher2.find() == true) {
                        System.out.println(subline);
                        output = output + subline + "\n";
                    }
                }

            }
            reader.close();
            return output;
        } catch(Exception ex) {
            System.out.println(ex);
        }

        return url;
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        NorthPanel = new JPanel();
        SouthPanel = new JPanel();
        NorthPanel.setLayout(new BorderLayout());
        SuperNorthPanel = new JPanel();

        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);

        textPanel1 = new JPanel();
        textPanel1.setLayout(new BorderLayout());
        textPanel2 = new JPanel();
        textPanel2.setLayout(new BorderLayout());
        SouthPanel = new JPanel();
        SouthPanel.setLayout(new GridLayout(1,1));
        SuperNorthPanel = new JPanel();
        SuperNorthPanel.setLayout(new GridLayout(2, 1));

        /* change layout to: url and search term boxes on the top left,
        submit and cancel buttons on top right, results at the bottom */

        ta = new JTextField("https://www.milton.edu/"); /* create new text area */
        ta.setBounds(50, 5, WIDTH-100, HEIGHT-25);

        textPanel1.add(ta, BorderLayout.CENTER);

        tb = new JTextField("careers"); /* create new text area */
        tb.setBounds(50, 35, WIDTH-100, HEIGHT-25);
        textPanel2.add(tb, BorderLayout.CENTER);

        outputArea = new JTextArea(blankArea); /* create new text area */
       // outputArea.setBounds(50, 100, WIDTH-100, HEIGHT-25);
      //  outputArea.setBounds(0, 0, WIDTH, HEIGHT);
       // SouthPanel.setBounds(0,0,1000,1000);
        outputArea.setWrapStyleWord(true);
        outputArea.setLineWrap(true);
        scroll = new JScrollPane(outputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        outputArea.add(scroll, BorderLayout.EAST);
        SouthPanel.add(scroll);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("status", JLabel.CENTER);
        textLabel1 = new JLabel("URL");
        textLabel2 = new JLabel("Keyword");

        statusLabel.setSize(350, 100);
        textLabel1.setSize(350, 25);
        textLabel2.setSize(350, 25);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        statusControlPanel = new JPanel();
        statusControlPanel.add(controlPanel, BorderLayout.NORTH);
        statusControlPanel.add(statusLabel, BorderLayout.SOUTH);
        textPanel1.add(textLabel1, BorderLayout.NORTH);
        textPanel2.add(textLabel2, BorderLayout.NORTH);
        SuperNorthPanel.add(textPanel1);
        SuperNorthPanel.add(textPanel2);
        NorthPanel.add(SuperNorthPanel,BorderLayout.NORTH);
        NorthPanel.add(statusControlPanel,BorderLayout.CENTER);
        NorthPanel.add(SouthPanel,BorderLayout.SOUTH);
        mainFrame.add(NorthPanel,BorderLayout.NORTH);


        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);
        //mainFrame.add(headerLabel);
        mainFrame.setVisible(true);
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Submit")) {
                statusLabel.setText("Submit Button clicked."); /* run code here */
                outputArea.setText(HtmlRead(ta.getText(), tb.getText()) + blankArea);
                outputArea.scrollRectToVisible(outputArea.getVisibleRect());

                ta.setText("https://www.milton.edu/");
                tb.setText("");
            } else {
                statusLabel.setText("Cancel Button clicked."); /* delete all other code */
                outputArea.setText(blankArea);
            }


        }
    }
}