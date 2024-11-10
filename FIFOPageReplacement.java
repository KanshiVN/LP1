import java.util.Scanner;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter reference string length and number of frames: ");
        int n = scanner.nextInt(), f = scanner.nextInt();
        
        int[] rs = new int[n], frames = new int[f];
        for (int i = 0; i < f; frames[i++] = -1);

        System.out.print("Enter reference string: ");
        for (int i = 0; i < n; rs[i++] = scanner.nextInt());

        int pf = 0, count = 0;
        System.out.println("\nThe Page Replacement Process is: ");
        for (int i = 0; i < n; i++) {
            boolean pageFault = true;
            for (int j = 0; j < f; j++) if (frames[j] == rs[i]) { pageFault = false; break; }
            if (pageFault) {
                frames[count] = rs[i]; count = (count + 1) % f; pf++;
            }

            for (int j = 0; j < f; j++) System.out.print(frames[j] == -1 ? "\t-" : "\t" + frames[j]);
            if (pageFault) System.out.print("\tPF No. " + pf);
            System.out.println();
        }
        System.out.println("\nTotal Page Faults: " + pf);
        scanner.close();
    }
}
