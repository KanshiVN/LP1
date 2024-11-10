#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Process {
    int id, burst_time, priority, waiting_time, turnaround_time, completion_time;
};

bool compare(Process a, Process b) {
    return a.priority < b.priority; // Higher priority (lower priority value)
}

int main() {
    int n;
    cout << "Enter number of processes: ";
    cin >> n;
    
    vector<Process> processes(n);
    for (int i = 0; i < n; i++) {
        processes[i].id = i + 1;
        cout << "Enter burst time and priority for process " << i + 1 << ": ";
        cin >> processes[i].burst_time >> processes[i].priority;
    }
    
    sort(processes.begin(), processes.end(), compare);
    
    int current_time = 0;
    for (int i = 0; i < n; i++) {
        processes[i].completion_time = current_time + processes[i].burst_time;
        processes[i].turnaround_time = processes[i].completion_time;
        processes[i].waiting_time = processes[i].turnaround_time - processes[i].burst_time;
        current_time = processes[i].completion_time;
    }
    
    cout << "\nProcess\tBurst Time\tPriority\tWaiting Time\tTurnaround Time\tCompletion Time\n";
    for (auto &p : processes)
        cout << p.id << "\t" << p.burst_time << "\t\t" << p.priority << "\t\t" 
             << p.waiting_time << "\t\t" << p.turnaround_time << "\t\t\t" << p.completion_time << endl;

    return 0;
}
