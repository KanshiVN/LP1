#include <iostream>
#include <queue>
#include <vector>

using namespace std;

struct Process {
    int id, burst_time, remaining_time, waiting_time, turnaround_time;
};

int main() {
    int n, time_quantum, current_time = 0;
    cout << "Enter number of processes and time quantum: ";
    cin >> n >> time_quantum;

    vector<Process> processes(n);
    queue<int> q;
    for (int i = 0; i < n; i++) {
        processes[i].id = i + 1;
        cout << "Enter burst time for process " << i + 1 << ": ";
        cin >> processes[i].burst_time;
        processes[i].remaining_time = processes[i].burst_time;
        q.push(i);
    }

    while (!q.empty()) {
        int i = q.front(); q.pop();
        int time_to_run = min(processes[i].remaining_time, time_quantum);
        current_time += time_to_run;
        processes[i].remaining_time -= time_to_run;
        
        if (processes[i].remaining_time > 0)
            q.push(i);
        else {
            processes[i].waiting_time = current_time - processes[i].burst_time;
            processes[i].turnaround_time = current_time;
        }
    }

    cout << "\nProcess\tBurst Time\tWaiting Time\tTurnaround Time\n";
    for (auto &p : processes)
        cout << p.id << "\t" << p.burst_time << "\t\t" << p.waiting_time << "\t\t" << p.turnaround_time << endl;

    return 0;
}
