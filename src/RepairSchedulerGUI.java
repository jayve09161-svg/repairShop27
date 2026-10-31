import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Computer Repair Job Scheduler - Swing GUI (single JFrame).
 */
public class RepairSchedulerGUI extends JFrame {

    private final RepairQueue queue = new RepairQueue();
    private int nextJobNumber = 1;

    private final JTextField nameField = new JTextField();
    private final JTextField contactField = new JTextField();
    private final JTextField deviceField = new JTextField();
    private final JTextField issueField = new JTextField();
    private final JTextField searchField = new JTextField();

    private final JTextArea pendingArea = new JTextArea();
    private final JLabel nextLabel = new JLabel();
    private final JLabel statusLabel = new JLabel(" ");

    public RepairSchedulerGUI() {
        super("Computer Repair Job Scheduler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.WEST);
        add(buildPendingPanel(), BorderLayout.CENTER);
        add(buildStatusPanel(), BorderLayout.SOUTH);

        refreshDisplay();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Register Repair Job"));
        panel.setPreferredSize(new java.awt.Dimension(280, 0));

        panel.add(new JLabel("Customer name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact number (digits only):"));
        panel.add(contactField);
        panel.add(new JLabel("Device (e.g. Laptop, Desktop):"));
        panel.add(deviceField);
        panel.add(new JLabel("Issue description:"));
        panel.add(issueField);

        JButton registerBtn = new JButton("Register Job");
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { registerJob(); }
        });
        panel.add(registerBtn);

        JButton processBtn = new JButton("Process Next Repair");
        processBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { processNext(); }
        });
        panel.add(processBtn);

        panel.add(new JLabel("Search by Job ID (e.g. JOB-001):"));
        panel.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { searchJob(); }
        });
        panel.add(searchBtn);

        return panel;
    }

    private JPanel buildPendingPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Pending Jobs (front of queue first)"));
        pendingArea.setEditable(false);
        pendingArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        nextLabel.setFont(nextLabel.getFont().deriveFont(Font.BOLD));
        panel.add(nextLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(pendingArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.add(statusLabel, BorderLayout.CENTER);
        return panel;
    }

    // ---------------- Operations ----------------

    private void registerJob() {
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String device = deviceField.getText().trim();
        String issue = issueField.getText().trim();

        if (name.length() == 0 || device.length() == 0 || issue.length() == 0) {
            showError("Name, device, and issue are required.");
            return;
        }
        if (!isValidContact(contact)) {
            showError("Contact number must be 7 to 11 digits (numbers only).");
            return;
        }

        String id = makeJobId(nextJobNumber);
        nextJobNumber++;
        queue.enqueue(new RepairJob(id, name, contact, device, issue));

        nameField.setText("");
        contactField.setText("");
        deviceField.setText("");
        issueField.setText("");

        showOk("Registered " + id + " for " + name + ". Position in queue: " + queue.size());
        refreshDisplay();
    }

    private void processNext() {
        RepairJob job = queue.dequeue();
        if (job == null) {
            showError("No pending jobs to process.");
            return;
        }
        refreshDisplay();
        showOk("Processing " + job.getJobId() + " (" + job.getCustomerName() + ")");
        JOptionPane.showMessageDialog(this,
                "Now repairing:\n\nJob ID: " + job.getJobId()
                        + "\nCustomer: " + job.getCustomerName()
                        + "\nContact: " + job.getContactNumber()
                        + "\nDevice: " + job.getDeviceType()
                        + "\nIssue: " + job.getIssue(),
                "Process Next Repair", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchJob() {
        String id = searchField.getText().trim();
        if (id.length() == 0) {
            showError("Enter a Job ID to search.");
            return;
        }
        RepairJob found = queue.searchById(id);          // searches the stored records
        if (found == null) {
            showError("NOT FOUND: no pending job with ID " + id
                    + " (it may already be processed).");
        } else {
            int pos = queue.positionOf(found.getJobId());
            showOk("FOUND: " + found + "  | queue position: " + pos);
        }
    }

    // ---------------- Helpers ----------------

    private void refreshDisplay() {
        pendingArea.setText(queue.toDisplayString());
        RepairJob next = queue.peek();
        if (next == null) {
            nextLabel.setText("Next repair: none  |  Pending: 0");
        } else {
            nextLabel.setText("Next repair: " + next.getJobId() + " (" + next.getCustomerName()
                    + ")  |  Pending: " + queue.size());
        }
    }

    private boolean isValidContact(String s) {
        if (s.length() < 7 || s.length() > 11) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String makeJobId(int n) {
        String num = "" + n;
        while (num.length() < 3) {
            num = "0" + num;
        }
        return "JOB-" + num;
    }

    private void showOk(String msg) {
        statusLabel.setForeground(new Color(0, 120, 0));
        statusLabel.setText(" " + msg);
    }

    private void showError(String msg) {
        statusLabel.setForeground(Color.RED);
        statusLabel.setText(" " + msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { new RepairSchedulerGUI().setVisible(true); }
        });
    }
}
