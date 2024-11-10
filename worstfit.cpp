#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int n, m;
    cout << "Enter number of blocks and processes: ";
    cin >> n >> m;

    vector<int> blocks(n), processes(m);
    cout << "Enter block sizes: ";
    for (int &b : blocks) cin >> b;
    cout << "Enter process sizes: ";
    for (int &p : processes) cin >> p;

    cout << "\nP.No\tP.Size\tB.No\tB.Size\n";
    for (int i = 0; i < m; i++) {
        int idx = -1;
        for (int j = 0; j < n; j++) 
            if (blocks[j] >= processes[i] && (idx == -1 || blocks[j] > blocks[idx])) idx = j;
        if (idx != -1) {
            cout << i + 1 << "\t" << processes[i] << "\t" << idx + 1 << "\t" << blocks[idx] << endl;
            blocks[idx] -= processes[i];
        } else cout << i + 1 << "\t" << processes[i] << "\tNot allocated\n";
    }

    return 0;
}
