#include <bits/stdc++.h>

using namespace std;
void FIFO_PageReplacement(vector<int> pages, int frameSize) {
    unordered_set<int> pageSet;  // To store the pages in memory
    queue<int> pageQueue;  // To maintain the order of pages
    int pageFaults = 0;  // Counter for page faults

    // Iterate over all pages
    for (int i = 0; i < pages.size(); i++) {
        // If the page is not in memory
        if (pageSet.find(pages[i]) == pageSet.end()) {
            // If the memory is full, remove the page that has been in memory the longest (FIFO)
            if (pageSet.size() == frameSize) {
                int oldestPage = pageQueue.front();
                pageQueue.pop();
                pageSet.erase(oldestPage);
            }

            // Add the new page to memory
            pageSet.insert(pages[i]);
            pageQueue.push(pages[i]);
            pageFaults++;  // Increment page faults
        }

        cout << "After processing page " << pages[i] << ": ";
        // Print the current pages in memory
        queue<int> tempQueue = pageQueue;  // Make a temporary copy of the queue
        while (!tempQueue.empty()) {
            cout << tempQueue.front() << " ";
            tempQueue.pop();
        }
        cout << endl;
    }

    cout << "\nTotal page faults: " << pageFaults << endl;
}

int main() {
    // Example: Pages to be accessed
    vector<int> pages = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 3};
    int frameSize = 3;  // Number of frames in memory

    FIFO_PageReplacement(pages, frameSize);

    return 0;
}
