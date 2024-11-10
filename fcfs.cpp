#include <iostream>
using namespace std;

int main() {
    int n;
    cout << "Enter number of processes: ";
    cin >> n;

    int arrivalTime[n], burstTime[n], waitingTime = 0, turnaroundTime = 0, startTime = 0;

    // Input arrival and burst times
    for (int i = 0; i < n; i++) {
        cout << "Enter arrival time and burst time for process " << i + 1 << ": ";
        cin >> arrivalTime[i] >> burstTime[i];
    }

    cout << "\nID\tArrival\tBurst\tStart\tFinish\tWaiting\tTurnaround\n";
    float totalWaitingTime = 0, totalTurnaroundTime = 0;

    for (int i = 0; i < n; i++) {
        startTime = max(startTime, arrivalTime[i]);
        int finishTime = startTime + burstTime[i];
        waitingTime = startTime - arrivalTime[i];
        turnaroundTime = finishTime - arrivalTime[i];

        totalWaitingTime += waitingTime;
        totalTurnaroundTime += turnaroundTime;

        cout << i + 1 << "\t" << arrivalTime[i] << "\t" << burstTime[i] << "\t"
             << startTime << "\t" << finishTime << "\t" << waitingTime << "\t" << turnaroundTime << "\n";

        startTime = finishTime; // Move to the finish time of the current process
    }

    cout << "Average Waiting Time: " << totalWaitingTime / n << "\n";
    cout << "Average Turnaround Time: " << totalTurnaroundTime / n << "\n";

    return 0;
}
