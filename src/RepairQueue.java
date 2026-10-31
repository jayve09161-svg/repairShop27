/**
 * Manually coded Queue (FIFO) using a singly linked list.
 * No java.util / Collections used.
 *
 * front -> oldest job (next to be repaired)
 * rear  -> newest job
 */
public class RepairQueue {
    private JobNode front;
    private JobNode rear;
    private int size;

    public RepairQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    public boolean isEmpty() { return front == null; }
    public int size()        { return size; }

    /** Add a job at the rear. O(1) */
    public void enqueue(RepairJob job) {
        JobNode node = new JobNode(job);
        if (isEmpty()) {
            front = node;
            rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    /** Remove and return the job at the front. Returns null if empty. O(1) */
    public RepairJob dequeue() {
        if (isEmpty()) {
            return null;
        }
        RepairJob job = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return job;
    }

    /** Look at the front job without removing it. */
    public RepairJob peek() {
        return isEmpty() ? null : front.data;
    }

    /**
     * Manual LINEAR SEARCH by job ID (case-insensitive).
     * Walks the stored nodes one by one. Returns the job or null. O(n)
     */
    public RepairJob searchById(String id) {
        JobNode current = front;
        while (current != null) {
            if (sameText(current.data.getJobId(), id)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /** Position in the queue (1 = next to be processed), or -1 if not found. */
    public int positionOf(String id) {
        JobNode current = front;
        int position = 1;
        while (current != null) {
            if (sameText(current.data.getJobId(), id)) {
                return position;
            }
            current = current.next;
            position++;
        }
        return -1;
    }

    /** Pending jobs as text, one per line, in processing order. */
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        JobNode current = front;
        int n = 1;
        while (current != null) {
            sb.append(n).append(". ").append(current.data).append("\n");
            current = current.next;
            n++;
        }
        return sb.toString();
    }

    /** Manual case-insensitive comparison (no built-in search helpers). */
    private boolean sameText(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            if (Character.toUpperCase(a.charAt(i)) != Character.toUpperCase(b.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
