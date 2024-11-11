import java.util.*;

class Assembler {
    // Pass 1
    ArrayList<ArrayList<String>> ic = null;
    ArrayList<ArrayList<String>> sym = null;
    ArrayList<ArrayList<String>> lit = null;
    ArrayList<ArrayList<String>> pol = null;

    // Assign Location Counter (LC) to the Assembly code
    public String[][] assignLC(String[][] asm) {
        int start_value = 0;
        for (int i = 0; i < asm.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (asm[i][j].equals("START")) {
                    start_value = Integer.parseInt(asm[i][j + 1]);
                }
            }
            if (i == 0) {
                continue;
            }
            // start_value = start_value + 1;
            asm[i][0] = String.valueOf(start_value);
        }
        return asm;
    }

    // Print the Assembly code with LC
    public void printCode(String[][] asm) {
        for (int i = 0; i < asm.length; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print("	" + asm[i][j]);
            }
            System.out.println();
        }
    }

    // Generate Intermediate Code (IC), Symbol Table (SYM), Literal Table (LIT), and Pool Table (POOL)
    public void generateOutput(String[][] asm, String[][] mot) {
        ic = new ArrayList<>(asm.length);
        sym = new ArrayList<>(asm.length);
        lit = new ArrayList<>(asm.length);
        pol = new ArrayList<>(asm.length);

        // Initialize the tables
        for (int i = 0; i < asm.length; i++) {
            ic.add(new ArrayList<>());
            sym.add(new ArrayList<>());
            lit.add(new ArrayList<>());
            pol.add(new ArrayList<>());
        }

        int sym_index = 0;
        int lit_index = 0;
        int pol_index = 0;

        for (int i = 0; i < asm.length; i++) {
            String lcStr = asm[i][0];
            ic.get(i).add(lcStr);
            for (int j = 0; j < 4; j++) {
                for (int m = 0; m < mot.length; m++) {
                    if (asm[i][j].equals(mot[m][0])) {
                        String icStr = mot[m][1] + "," + mot[m][2];
                        ic.get(i).add(icStr);
                    }
                }
                if (asm[i][j].equals(mot[15][0]) || asm[i][j].equals(mot[12][0])) {
                    String polStr = pol_index + "," + asm[i][j] + "," + asm[i][0];
                    pol.get(i).add(polStr);
                }
                if (asm[i][j].matches("[a-zA-Z]+")) {
                    String symStr = sym_index + "," + asm[i][j] + "," + asm[i][0];
                    sym.get(i).add(symStr);
                    sym_index++;
                    String icStr = "S," + asm[i][j];
                    ic.get(i).add(icStr);
                }
                if (asm[i][j].matches("[0-9]+") && !asm[i][j].equals(asm[i][0])) {
                    String litStr = lit_index + "," + asm[i][j] + "," + asm[i][0];
                    lit.get(i).add(litStr);
                    lit_index++;
                    String icStr = "C," + asm[i][j];
                    ic.get(i).add(icStr);
                }
            }
        }
    }

    // Print the Final Output: IC, SYM, LIT, POOL
    public void printFinalOutput() {
        System.out.println("IC");
        for (ArrayList<String> str : ic) {
            for (String st : str) {
                System.out.print("	" + st);
            }
            System.out.println();
        }

        System.out.println("SYM");
        for (ArrayList<String> str : sym) {
            for (String st : str) {
                System.out.print("	" + st);
            }
            System.out.println();
        }

        System.out.println("LIT");
        for (ArrayList<String> str : lit) {
            for (String st : str) {
                System.out.print("	" + st);
            }
            System.out.println();
        }

        System.out.println("POOL");
        for (ArrayList<String> str : pol) {
            for (String st : str) {
                System.out.print("	" + st);
            }
            System.out.println();
        }
    }

    // Pass 2
    ArrayList<ArrayList<String>> machine_code = null;

    // Generate Machine Code from Intermediate Code
	public void generateMachineCode() {
		machine_code = new ArrayList<>(ic.size());
	
		// Initialize the machine code table
		for (int i = 0; i < ic.size(); i++) {
			machine_code.add(new ArrayList<>());
		}
	
		// Generate the machine code
		for (int i = 0; i < ic.size(); i++) {
			if (!ic.get(i).contains("AD")) { // Skip Address Directive (AD) type instructions
				for (int j = 0; j < ic.get(i).size(); j++) {
					String icElement = ic.get(i).get(j);
					
					if (icElement.contains("IS")) {
						String[] str = icElement.split(",");
						machine_code.get(i).add(str[1]);
					}
					if (icElement.contains("RG")) {
						String[] str = icElement.split(",");
						machine_code.get(i).add(str[1]);
					}
					if (icElement.contains("DL")) {
						String[] str = icElement.split(",");
						machine_code.get(i).add(str[1]);
					}
					if (icElement.contains("C")) {
						String[] str = icElement.split(",");
						machine_code.get(i).add(str[1]);
	
						// Check if the literal table (lit) has elements before accessing it
						if (i < lit.size() && !lit.get(i).isEmpty()) {
							String[] addr = lit.get(i).get(0).split(",");
							machine_code.get(i).add(addr[2]); // Add the address of the literal
						}
					}
				}
			}
		}
	}
	
	
    // Print the Machine Code
    public void printMachineCode() {
        System.out.println("MACHINE CODE");
        for (ArrayList<String> str : machine_code) {
            for (String st : str) {
                System.out.print(" " + st);
            }
            System.out.println();
        }
    }
}

public class Pass2 {
    public static void main(String[] args) {
        String asm_code[][] = {
            {"", "", "START", "200", ""},
            {"", "", "MOVER", "AREG", ""},
            {"", "", "MOVEM", "BREG", ""},
            {"", "X", "DS", "1", ""},
            {"", "Y", "DS", "2", ""},
            {"", "", "STOP", "", ""},
            {"", "", "END", "", ""}
        };

        String mot[][] = {
            {"STOP", "IS", "00"},
            {"ADD", "IS", "01"},
            {"SUB", "IS", "02"},
            {"MULT", "IS", "03"},
            {"MOVER", "IS", "04"},
            {"MOVEM", "IS", "05"},
            {"COMP", "IS", "06"},
            {"BC", "IS", "07"},
            {"DIV", "IS", "08"},
            {"READ", "IS", "09"},
            {"PRINT", "IS", "10"},
            {"START", "AD", "01"},
            {"END", "AD", "02"},
            {"ORIGIN", "AD", "03"},
            {"EQU", "AD", "04"},
            {"LTORG", "AD", "05"},
            {"DS", "DL", "01"},
            {"DC", "DL", "02"},
            {"AREG", "RG", "01"},
            {"BREG", "RG", "02"},
            {"CREG", "RG", "03"}
        };

        Assembler assembler = new Assembler();
        String[][] asm_with_lc;
        asm_with_lc = assembler.assignLC(asm_code);
        assembler.printCode(asm_with_lc);
        assembler.generateOutput(asm_with_lc, mot);
        assembler.printFinalOutput();
        assembler.generateMachineCode();
        assembler.printMachineCode();
    }
}
