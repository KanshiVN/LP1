// Generate Machine Code from Intermediate Code
    public void generateMachineCode() {
        machine_code = new ArrayList<>(ic.size());

        // Initialize the machine code table
        for (int i = 0; i < ic.size(); i++) {
            machine_code.add(new ArrayList<>());
        }

        // Generate the machine code
        for (int i = 0; i < ic.size(); i++) {
            if (!ic.get(i).contains("AD")) {
                for (int j = 0; j < ic.get(i).size(); j++) {
                    if (ic.get(i).get(j).contains("IS")) {
                        String[] str = ic.get(i).get(j).split(",");
                        machine_code.get(i).add(str[1]);
                    }
                    if (ic.get(i).get(j).contains("RG")) {
                        String[] str = ic.get(i).get(j).split(",");
                        machine_code.get(i).add(str[1]);
                    }
                    if (ic.get(i).get(j).contains("DL")) {
                        String[] str = ic.get(i).get(j).split(",");
                        machine_code.get(i).add(str[1]);
                    }
                    if (ic.get(i).get(j).contains("C")) {
                        String[] str = ic.get(i).get(j).split(",");
                        machine_code.get(i).add(str[1]);
                        // If there's a literal, append its address
                        if (lit.size() > i && !lit.get(i).isEmpty()) {
                            String[] addr = lit.get(i).get(0).split(",");
                            machine_code.get(i).add(addr[2]);
                        }
                    }
                }
            }
        }
    }