#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Process {
    int id, burst_time, waiting_time, turnaround_time, completion_time;
};

bool compare(Process a, Process b) {
    return a.burst_time < b.burst_time; // Sort by burst time
}

int main() {
    int n;
    cout << "Enter number of processes: ";
    cin >> n;

    vector<Process> processes(n);

    // Input burst times
    for (int i = 0; i < n; i++) {
        processes[i].id = i + 1;
        cout << "Enter burst time for process " << i + 1 << ": ";
        cin >> processes[i].burst_time;
    }

    // Sort processes by burst time
    sort(processes.begin(), processes.end(), compare);

    int current_time = 0;
    // Calculate completion time, waiting time, and turnaround time
    for (int i = 0; i < n; i++) {
        processes[i].completion_time = current_time + processes[i].burst_time;
        processes[i].turnaround_time = processes[i].completion_time;
        processes[i].waiting_time = processes[i].turnaround_time - processes[i].burst_time;
        current_time = processes[i].completion_time;
    }

    // Output the results
    cout << "\nProcess\tBurst Time\tWaiting Time\tTurnaround Time\tCompletion Time\n";
    for (int i = 0; i < n; i++) {
        cout << processes[i].id << "\t" 
             << processes[i].burst_time << "\t\t"
             << processes[i].waiting_time << "\t\t"
             << processes[i].turnaround_time << "\t\t\t"
             << processes[i].completion_time << endl;
    }

    return 0;
}
