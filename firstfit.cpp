#include <iostream>
#include <vector>

using namespace std;

int main() {
    int m, n;
    cout << "Enter number of memory blocks and processes: ";
    cin >> m >> n;

    vector<int> memory(m), process(n);
    
    cout << "Enter memory block sizes: ";
    for (int i = 0; i < m; i++) cin >> memory[i];
    
    cout << "Enter process sizes: ";
    for (int i = 0; i < n; i++) cin >> process[i];

    cout << "\nProcess No\tProcess Size\tBlock No\tBlock Size\n";
    
    for (int i = 0; i < n; i++) {
        bool allocated = false;
        for (int j = 0; j < m; j++) {
            if (memory[j] >= process[i]) {
                cout << i+1 << "\t\t" << process[i] << "\t\t" << j+1 << "\t\t" << memory[j] << endl;
                memory[j] -= process[i]; // Allocate memory
                allocated = true;
                break;
            }
        }
        if (!allocated) cout << i+1 << "\t\t" << process[i] << "\t\t" << "Not Allocated" << endl;
    }

    return 0;
}
