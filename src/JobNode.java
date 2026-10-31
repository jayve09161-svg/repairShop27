/**
 * Node of the singly linked list that backs the queue.
 */
public class JobNode {
    RepairJob data;
    JobNode next;

    public JobNode(RepairJob data) {
        this.data = data;
        this.next = null;
    }
}
