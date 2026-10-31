# Computer Repair Job Scheduler
IT20 – Data Structures and Algorithms | Midterm Mini-Project | S.Y. 2026–2027

## Description
A Java Swing desktop tool for a computer repair shop. Jobs are handled
first-come, first-served using a **manually coded Queue (singly linked list)**.
No java.util / Java Collections are used.

## Features
1. Register repair job (auto-generated ID: JOB-001, JOB-002, ...) with input validation
2. Display pending jobs in processing order
3. Process next repair (queue `dequeue`)
4. Search by Job ID (manual linear search over stored nodes; shows FOUND / NOT FOUND and queue position)

## Data Structure
| Class | Purpose |
|-------|---------|
| RepairJob | Record (ID, customer, contact, device, issue) |
| JobNode | Linked-list node |
| RepairQueue | enqueue, dequeue, peek, isEmpty, size, searchById, positionOf |
| RepairSchedulerGUI | Swing UI (1 JFrame) |

Complexities: enqueue O(1), dequeue O(1), peek O(1), search O(n).

## Setup
Requires JDK 8+.
```
cd src
javac *.java
java RepairSchedulerGUI
```
(Or open the folder in NetBeans / IntelliJ / Eclipse and run RepairSchedulerGUI.)

## Team Roles
- Member 1: <name> – RepairJob, JobNode, RepairQueue (structure + search), unit tests
- Member 2: <name> – RepairSchedulerGUI, validation, README, integration

## Repository
<GitHub link>
