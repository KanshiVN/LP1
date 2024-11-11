#include <bits/stdc++.h>

using namespace std;

// Function to simulate LRU Page Replacement
void lruPageReplacement(const vector<int>& pages, int capacity) {
    unordered_map<int, list<int>::iterator> pagePosition; // Map to store the position of each page in cache
    list<int> cache; // List to represent the cache (recently used pages at the front)
    int pageFaults = 0;

    for (int page : pages) {
        // If the page is already in the cache
        if (pagePosition.find(page) != pagePosition.end()) {
            // Move the page to the front (most recently used)
            cache.erase(pagePosition[page]);
            cache.push_front(page);
            pagePosition[page] = cache.begin();
        } else {
            // Page fault occurs
            pageFaults++;

            // If the cache is full, remove the least recently used page (back of the list)
            if (cache.size() == capacity) {
                int lruPage = cache.back();
                cache.pop_back();
                pagePosition.erase(lruPage);
            }

            // Insert the new page at the front (most recently used)
            cache.push_front(page);
            pagePosition[page] = cache.begin();
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

    lruPageReplacement(pages, capacity);

    return 0;
}
