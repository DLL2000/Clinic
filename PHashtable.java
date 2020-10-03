import java.util.ArrayList;
public class PHashtable {
    private ArrayList[] table;

    private int tableSize;

    private int size;
    //set the table size to the first 
    //prime number p >= capacity
    public PHashtable (int capacity) {
        int p = capacity;
        if (isPrime(p) == true) {
            table = new ArrayList[p];
            size = p;
        } else {
            table = new ArrayList[getNextPrime(p)];
            size = getNextPrime(p);
        }
        tableSize = 0;
    }

    //return the Patient with the given name 
    //or null if the Patient is not in the table
    public Patient get(String name) {
        Patient patient = null;
        int hashKey = name.hashCode() % table.length;

        int count = 0;
        boolean found = false;
        if (hashKey < 0) {
            hashKey = hashKey + table.length;
        }
        if (table[hashKey] == null) {
            return null;
        } else {
            while ((count < table[hashKey].size()) && (found == false)) {
                patient = (Patient)table[hashKey].get(count);
                if (name.equals(patient.name())) {
                    found = true;
                } else {
                    count++;
                }
            }
        }
        return patient;
    }

    //put Patient p into the table
    public void put(Patient p) {
        int hashKey = p.name().hashCode() % table.length;
        int index = 0;
        boolean found = false;
        if (hashKey < 0) {
            hashKey = hashKey + table.length;
        }
        if (table[hashKey] != null) { //there is a patient at the hashkey location, make sure patient is not in chain
            while ((index < table[hashKey].size()) && (found == false)) {
                if (p.name().equals(table[hashKey])) {
                    found = true;
                } else {
                    index++;
                }
            }
            if (found == false) {
                table[hashKey].add(p);
                tableSize++;
            }
        } else {
            table[hashKey] = new ArrayList<Patient>();
            table[hashKey].add(p);
            tableSize++;
        }
    }

    //remove and returnthe Patient with the given name
    //from the table
    //return null if Patient doesn't exist
    public Patient remove(String name) {
        Patient patient = null;
        int hashKey = name.hashCode() % table.length;

        int count = 0;
        boolean found = false;
        if (hashKey < 0) {
            hashKey = hashKey + table.length;
        }
        if (table[hashKey] == null) {
            return null;
        } else {
            while ((count < table[hashKey].size()) && (found == false)) {
                patient = (Patient)table[hashKey].get(count);
                if (name.equals(patient.name())) {

                    table[hashKey].remove(count);
                    found = true;
                    tableSize--;
                } else {
                    count++;
                }
            }
        }
        return patient;
    }

    //return the number of Patients in the table
    public int size() {
        return tableSize;
    }

    //returns the underlying structure for testing
    public ArrayList<Patient>[] getArray() {
        return table;
    }

    //get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;
        int rem = num % 6;
        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }
        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }
        return num;
    }


    //determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if(num % 2 == 0){
            return false;
        }

        int x = 3;
        for(int i = x; i < num; i+=2){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

    int getSize() {
        return size;
    }
}