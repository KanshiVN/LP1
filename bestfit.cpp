#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int m, n;
    cout << "Enter number of memory blocks and processes: ";
    cin >> m >> n;

    vector<int> blocks(m), processes(n);
    cout << "Enter memory block sizes: ";
    for (int i = 0; i < m; i++) cin >> blocks[i];

    cout << "Enter process sizes: ";
    for (int i = 0; i < n; i++) cin >> processes[i];

    cout << "\nProcess No\tProcess Size\tBlock No\tBlock Size\n";
    for (int i = 0; i < n; i++) {
        int best_idx = -1;
        for (int j = 0; j < m; j++) {
            if (blocks[j] >= processes[i] && (best_idx == -1 || blocks[j] < blocks[best_idx]))
                best_idx = j;
        }
        if (best_idx != -1) {
            cout << i + 1 << "\t\t" << processes[i] << "\t\t" << best_idx + 1 << "\t\t" << blocks[best_idx] << endl;
            blocks[best_idx] -= processes[i];
        } else {
            cout << i + 1 << "\t\t" << processes[i] << "\t\t" << "Not Allocated" << endl;
        }
    }

    return 0;
}
