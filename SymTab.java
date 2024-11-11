import java.io.*;
import java.util.*;

class SymTab {
    private static final int MAX = 100;
    private static List<String[]> symbolTable = new ArrayList<>();
    private static List<String[]> opcodeTable = new ArrayList<>();
    private static List<String[]> literalTable = new ArrayList<>();
    private static List<Integer> poolTable = new ArrayList<>();

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Error: Please provide an input file.");
            System.exit(1);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))) {
            processInputFile(bufferedReader);
            printTables();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void processInputFile(BufferedReader bufferedReader) throws IOException {
        String line;
        int lineCount = 0, LC = 0;

        System.out.println("___________________________________________________");
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split("\t");
            if (lineCount == 0) {
                LC = processFirstLine(tokens);
            } else {
                processLine(tokens, LC);
            }
            printLine(tokens);
            lineCount++;
            LC++;
        }
        System.out.println("___________________________________________________");
    }

    private static int processFirstLine(String[] tokens) {
        if (tokens.length >= 3) {
            try {
                return Integer.parseInt(tokens[2]);
            } catch (NumberFormatException e) {
                System.out.println("Warning: Invalid LC value in first line. Using default value 0.");
            }
        } else {
            System.out.println("Warning: First line doesn't have expected format. Using default LC value 0.");
        }
        return 0;
    }

    private static void processLine(String[] tokens, int LC) {
        if (tokens.length > 0 && !tokens[0].isEmpty()) {
            addToSymbolTable(tokens[0], LC);
        }
        if (tokens.length > 1) {
            if (tokens[1].equalsIgnoreCase("DS") || tokens[1].equalsIgnoreCase("DC")) {
                addToSymbolTable(tokens[0], LC);
            }
            addToOpcodeTable(tokens[1]);
        }
        if (tokens.length > 2 && tokens[2].charAt(0) == '=') {
            addToLiteralTable(tokens[2], LC);
        }
    }

    private static void addToSymbolTable(String symbol, int LC) {
        symbolTable.add(new String[]{symbol, Integer.toString(LC), "1"});
    }

    private static void addToOpcodeTable(String mnemonic) {
        String[] entry = new String[3];
        entry[0] = mnemonic;
        if (mnemonic.equalsIgnoreCase("START") || mnemonic.equalsIgnoreCase("END") ||
            mnemonic.equalsIgnoreCase("ORIGIN") || mnemonic.equalsIgnoreCase("EQU") ||
            mnemonic.equalsIgnoreCase("LTORG")) {
            entry[1] = "AD";
            entry[2] = "R11";
        } else if (mnemonic.equalsIgnoreCase("DS") || mnemonic.equalsIgnoreCase("DC")) {
            entry[1] = "DL";
            entry[2] = "R7";
        } else {
            entry[1] = "IS";
            entry[2] = "(04,1)";
        }
        opcodeTable.add(entry);
    }

    private static void addToLiteralTable(String literal, int LC) {
        literalTable.add(new String[]{literal, Integer.toString(LC)});
    }

    private static void printLine(String[] tokens) {
        for (String token : tokens) {
            System.out.print(token + "\t");
        }
        System.out.println();
    }

    private static void printTables() {
        printSymbolTable();
        printOpcodeTable();
        printLiteralTable();
        initializePoolTable();
        printPoolTable();
    }

    private static void printSymbolTable() {
        System.out.println("\n\n    SYMBOL TABLE        ");
        System.out.println("--------------------------");
        System.out.println("SYMBOL\tADDRESS\tLENGTH");
        System.out.println("--------------------------");
        for (String[] entry : symbolTable) {
            System.out.println(String.join("\t", entry));
        }
        System.out.println("--------------------------");
    }

    private static void printOpcodeTable() {
        System.out.println("\n\n    OPCODE TABLE        ");
        System.out.println("----------------------------");
        System.out.println("MNEMONIC\tCLASS\tINFO");
        System.out.println("----------------------------");
        for (String[] entry : opcodeTable) {
            System.out.println(String.join("\t\t", entry));
        }
        System.out.println("----------------------------");
    }

    private static void printLiteralTable() {
        System.out.println("\n\n   LITERAL TABLE        ");
        System.out.println("-----------------");
        System.out.println("LITERAL\tADDRESS");
        System.out.println("-----------------");
        for (String[] entry : literalTable) {
            System.out.println(String.join("\t", entry));
        }
        System.out.println("------------------");
    }

    private static void initializePoolTable() {
        for (int i = 0; i < literalTable.size() - 1; i++) {
            if (i == 0) {
                poolTable.add(i + 1);
            } else {
                int currentAddress = Integer.parseInt(literalTable.get(i)[1]);
                int nextAddress = Integer.parseInt(literalTable.get(i + 1)[1]);
                if (currentAddress < nextAddress - 1) {
                    poolTable.add(i + 2);
                }
            }
        }
    }

    private static void printPoolTable() {
        System.out.println("\n\n   POOL TABLE        ");
        System.out.println("-----------------");
        System.out.println("LITERAL NUMBER");
        System.out.println("-----------------");
        for (int entry : poolTable) {
            System.out.println(entry);
        }
        System.out.println("------------------");
    }
}
