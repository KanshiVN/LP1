import java.util.*;

class SymTab {
    int index;
    String name;
    int addr;

    SymTab(int i, String s, int a) {
        index = i;
        name = s;
        addr = a;
    }
}

class LitTab {
    int index;
    String name;
    int addr;

    LitTab(int i, String a, int addr) {
        index = i;
        name = a;
        this.addr = addr;
    }

    void setAddr(int a) {
        addr = a;
    }
}

class PoolTab {
    int pIndex;
    int lIndex;

    PoolTab(int i, int a) {
        pIndex = i;
        lIndex = a; 
    }
}

public class Pass1Assembler {
    public static void main(String[] args) {
        String[][] input = {
            {null, "START", "100", null},
            {null, "MOVER", "AREG", "A"},
            {"AGAIN", "ADD", "AREG", "='2"},
            {null, "ADD", "AREG", "B"},
            {"AGAIN", "ADD", "AREG", "='3"},
            {null, "LTORG", null, null},
            {"AGAIN2", "ADD", "AREG", "BREG"},
            {"AGAIN2", "ADD", "AREG", "CREG"},
            {"AGAIN", "ADD", "AREG", "='2"},
            {null, "DC", "B", "3"},
            {"LOOP", "DS", "A", "1"},
            {null, "END", null, null}
        };

        SymTab[] symTab = new SymTab[20];
        LitTab[] litTab = new LitTab[20];
        PoolTab[] poolTab = new PoolTab[20];

        int loc = Integer.parseInt(input[0][2]);
        int i = 1, sn = 0, ln = 0, Inc = 0, pn = 0;
        String m = input[1][1];

        while (m != null && !m.equals("END")) {
            if (check(m) == 1) {
                String op2 = input[i][3];
                if (comp(op2, symTab, sn) == 1) {
                    symTab[sn++] = new SymTab(sn, op2, 0);
                } else if (comp(op2, symTab, sn) == 2) {
                    litTab[ln++] = new LitTab(ln, op2, 0);
                }
                loc++;
            } else if (check(m) == 2 || check(m) == 3) {
                String op1 = input[i][0] != null ? input[i][0] : input[i][2];
                if (comp(op1, symTab, sn) != 99) {
                    symTab[sn++] = new SymTab(sn, op1, loc);
                }
                loc += Integer.parseInt(input[i][3] != null ? input[i][3] : "1");
            } else if (check(m) == 4) {
                poolTab[pn++] = new PoolTab(pn, ln);
                while (Inc < ln) {
                    litTab[Inc++].setAddr(loc++);
                }
            } else {
                System.out.println("Unknown instruction: " + m);
            }
            i++;
            m = input[i][1];
        }

        printTables(symTab, sn, litTab, ln, poolTab, pn);
    }

    static int check(String m) {
        switch (m) {
            case "MOVER": case "ADD": return 1;
            case "DS": return 2;
            case "DC": return 3;
            case "LTORG": return 4;
            default: return -1;
        }
    }

    static int comp(String m, SymTab[] s, int sn) {
        if (m == null) return 99;
        if (m.equals("AREG") || m.equals("BREG") || m.equals("CREG")) return 0;
        if (m.startsWith("=")) return 2;
        for (int i = 0; i < sn; i++) {
            if (m.equals(s[i].name)) return 1;
        }
        return 99;
    }

    static int comp(String m, LitTab[] l, int ln) {
        for (int i = 0; i < ln; i++) {
            if (m.equals(l[i].name)) return i;
        }
        return -1;
    }

    static void printTables(SymTab[] symTab, int sn, LitTab[] litTab, int ln, PoolTab[] poolTab, int pn) {
        System.out.println("Symbol Table\nIndex\tSymbol\tAddress");
        for (int i = 0; i < sn; i++) {
            System.out.println(symTab[i].index + "\t" + symTab[i].name + "\t" + symTab[i].addr);
        }

        System.out.println("\nLiteral Table\nIndex\tLiteral\tAddress");
        for (int i = 0; i < ln; i++) {
            System.out.println(litTab[i].index + "\t" + litTab[i].name + "\t" + litTab[i].addr);
        }

        System.out.println("\nPool Table\nPool Index\tLiteral Index");
        for (int i = 0; i < pn; i++) {
            System.out.println(poolTab[i].pIndex + "\t\t" + poolTab[i].lIndex);
        }
    }
}
