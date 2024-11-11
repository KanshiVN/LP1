#include <bits/stdc++.h>
#include <algorithm>

using namespace std;

// Function to simulate LFU Page Replacement
void lfuPageReplacement(const vector<int>& pages, int capacity) {
    unordered_map<int, int> pageFrequency; // Map to store page frequency
    unordered_map<int, list<int>::iterator> pagePosition; // Map to store page positions in the cache
    list<int> cache; // List to represent the cache
    int pageFaults = 0;

    for (int page : pages) {
        // If the page is already in the cache, increase its frequency
        if (pageFrequency.find(page) != pageFrequency.end()) {
            pageFrequency[page]++;
        } else {
            // If the cache is full, we need to replace a page
            if (cache.size() == capacity) {
                // Find the least frequently used page
                int lfuPage = *min_element(cache.begin(), cache.end(),
                    [&](int a, int b) {
                        return pageFrequency[a] < pageFrequency[b];
                    });

                // Remove the LFU page from cache and frequency map
                cache.erase(pagePosition[lfuPage]);
                pageFrequency.erase(lfuPage);
                pagePosition.erase(lfuPage);
            }

            // Add the new page to the cache and frequency map
            cache.push_back(page);
            pagePosition[page] = --cache.end();
            pageFrequency[page] = 1;
            pageFaults++; // Increment page fault count
        }

        // Display current state of the cache
        cout << "Cache: ";
        for (int p : cache) cout << p << " ";
        cout << "\n";
    }

    cout << "Total Page Faults: " << pageFaults << "\n";
}

int main() {
    int capacity;
    cout << "Enter cache capacity: ";
    cin >> capacity;

    int n;
    cout << "Enter number of pages: ";
    cin >> n;

    vector<int> pages(n);
    cout << "Enter the page sequence:\n";
    for (int i = 0; i < n; i++) {
        cin >> pages[i];
    }

    lfuPageReplacement(pages, capacity);

    return 0;
}
