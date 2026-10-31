/**
 * Record class: one computer repair job.
 */
public class RepairJob {
    private final String jobId;
    private final String customerName;
    private final String contactNumber;
    private final String deviceType;
    private final String issue;

    public RepairJob(String jobId, String customerName, String contactNumber,
                     String deviceType, String issue) {
        this.jobId = jobId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.deviceType = deviceType;
        this.issue = issue;
    }

    public String getJobId()         { return jobId; }
    public String getCustomerName()  { return customerName; }
    public String getContactNumber() { return contactNumber; }
    public String getDeviceType()    { return deviceType; }
    public String getIssue()         { return issue; }

    @Override
    public String toString() {
        return jobId + " | " + customerName + " | " + contactNumber
                + " | " + deviceType + " | " + issue;
    }
}
