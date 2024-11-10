import java.util.Scanner;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of frames: ");
        int frames = Integer.parseInt(in.nextLine());
        System.out.print("Enter the length of the reference string: ");
        int ref_len = Integer.parseInt(in.nextLine());
        int[] reference = new int[ref_len];
        int[] buffer = new int[frames];
        boolean[] hit = new boolean[ref_len];
        int numFault = 0;

        for (int j = 0; j < frames; j++) buffer[j] = -1;

        System.out.println("Enter the reference string (hit Enter after each number): ");
        for (int i = 0; i < ref_len; i++) reference[i] = Integer.parseInt(in.nextLine());

        System.out.println();
        for (int i = 0; i < ref_len; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    hit[i] = true;
                    search = j;
                    break;
                }
            }

            if (search == -1) {
                int[] nextUse = new int[frames];
                for (int j = 0; j < frames; j++) nextUse[j] = -1;

                for (int j = i + 1; j < ref_len; j++) {
                    for (int k = 0; k < frames; k++) {
                        if (reference[j] == buffer[k] && nextUse[k] == -1) {
                            nextUse[k] = j;
                            break;
                        }
                    }
                }

                int replaceIndex = -1;
                for (int j = 0; j < frames; j++) {
                    if (nextUse[j] == -1) {
                        replaceIndex = j;
                        break;
                    }
                }

                if (replaceIndex == -1) {
                    int farthest = 0;
                    for (int j = 1; j < frames; j++) {
                        if (nextUse[j] > nextUse[farthest]) farthest = j;
                    }
                    replaceIndex = farthest;
                }

                buffer[replaceIndex] = reference[i];
                numFault++;
            }

            System.out.print("Reference: " + reference[i] + " | Memory: ");
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == -1) System.out.print("* ");
                else System.out.print(buffer[j] + " ");
            }
            System.out.println(hit[i] ? "Hit" : "Page Fault");
        }

        System.out.println("Total Number of Page Faults: " + numFault);
        in.close();
    }
}
