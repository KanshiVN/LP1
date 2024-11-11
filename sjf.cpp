#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>

using namespace std;

struct Process {
    int id, burst_time, remaining_time, waiting_time, turnaround_time, completion_time, arrival_time;
};

bool compareArrival(Process a, Process b) {
    return a.arrival_time < b.arrival_time; // Sort by arrival time
}

bool compareRemainingTime(Process a, Process b) {
    return a.remaining_time < b.remaining_time; // Sort by remaining time for SRTF
}

int main() {
    int n;
    cout << "Enter number of processes: ";
    cin >> n;

    vector<Process> processes(n);

    // Input burst times and arrival times
    for (int i = 0; i < n; i++) {
        processes[i].id = i + 1;
        cout << "Enter arrival time for process " << i + 1 << ": ";
        cin >> processes[i].arrival_time;
        cout << "Enter burst time for process " << i + 1 << ": ";
        cin >> processes[i].burst_time;
        processes[i].remaining_time = processes[i].burst_time; // Initially, remaining time equals burst time
    }

    // Sort processes by arrival time
    sort(processes.begin(), processes.end(), compareArrival);

    int current_time = 0;
    int completed = 0;
    int last_completed_time = 0;
    vector<bool> is_completed(n, false);

    // We use a while loop to handle processes in the ready queue
    while (completed < n) {
        // Find the process with the smallest remaining time that has arrived
        Process* current_process = nullptr;

        for (int i = 0; i < n; i++) {
            if (!is_completed[i] && processes[i].arrival_time <= current_time) {
                if (current_process == nullptr || processes[i].remaining_time < current_process->remaining_time) {
                    current_process = &processes[i];
                }
            }
        }

        if (current_process != nullptr) {
            // Execute the selected process for 1 unit of time (preemption)
            current_process->remaining_time--;
            current_time++;

            // If the process has finished execution
            if (current_process->remaining_time == 0) {
                current_process->completion_time = current_time;
                current_process->turnaround_time = current_process->completion_time - current_process->arrival_time;
                current_process->waiting_time = current_process->turnaround_time - current_process->burst_time;

                is_completed[current_process->id - 1] = true;
                completed++;
            }
        } else {
            // If no process is ready to execute, just increment time
            current_time++;
        }
    }

    // Output the results
    cout << "\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time\tCompletion Time\n";
    for (int i = 0; i < n; i++) {
        cout << processes[i].id << "\t" 
             << processes[i].arrival_time << "\t\t"
             << processes[i].burst_time << "\t\t"
             << processes[i].waiting_time << "\t\t"
             << processes[i].turnaround_time << "\t\t\t"
             << processes[i].completion_time << endl;
    }

    return 0;
}

