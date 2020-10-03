public class NewPatientQueue {
    private Patient[] array;
    //private PHashtable table;
    private PHashtable table;

    /*TO BE COMPLETED IN PART 1*/

    private int queueSize;
    private int tableSize;
    private int length;

    //constructor: set variables
    //capacity = initial capacity of array
    public NewPatientQueue(int capacity) {
        array = new Patient[capacity];
        table = new PHashtable(capacity);
        queueSize = 0;
        tableSize = 0;
        length = capacity;
    }

    //insert Patient p into queue
    //return the final index at which the patient is stored
    //return -1 if the patient could not be inserted
    public int insert(Patient p) {
        int position = 0;
        Patient tempPatient;
        int parentPosition;
        if (size() == array.length) {
            return -1;
        } else if (size() == 0) {
            queueSize++;
            p.setPosInQueue(position);
            array[0] = p;
            table.put(p);
            return position;
        } else { // insert p at end of heap and swim up
            position = size();
            queueSize++;
            array[position] = p;
            parentPosition = (position - 1) / 2;
            if (array[parentPosition].compareTo(p) >= 0) {
                p.setPosInQueue(position);
            }
            else {
                while (array[parentPosition].compareTo(p) < 0) {
                    tempPatient = array[parentPosition];
                    array[parentPosition] = p;
                    p.setPosInQueue(parentPosition);
                    array[position] = tempPatient;
                    tempPatient.setPosInQueue(position);
                    position = parentPosition;
                    parentPosition = (position - 1) / 2;
                }
            }

        }
        table.put(p);
        return position;
    }

    //remove and return the patient with the highest urgency level
    //if there are multiple patients with the same urgency level,
    //return the one who arrived first
    public Patient delMax() {
        Patient maxPatient;
        Patient tempPatient;
        int position = 0;
        int leftChildPosition;
        int rightChildPosition;
        boolean sinkDownComplete = false;
        if (size() == 0) {
            return null;
        } else {
            maxPatient = array[0];
            tempPatient = array[size() - 1];
            array[0] = tempPatient;
            array[size() - 1] = null;
            queueSize--;
            leftChildPosition = 2 * position + 1;
            rightChildPosition = 2 * position + 2;
            while ((leftChildPosition < size()) && (sinkDownComplete == false)) {
                if ((array[position].compareTo(array[leftChildPosition]) >= 0) &&
                    ((rightChildPosition >= size()) || (array[position].compareTo(array[rightChildPosition]) >= 0))) {
                    array[position].setPosInQueue(position);
                    sinkDownComplete = true;
                } else {
                    if ((rightChildPosition >= size()) ||
                        (array[leftChildPosition].compareTo(array[rightChildPosition]) > 0)) {
                        // parent is smaller than one of the children and left child is larger than right child
                        if (array[leftChildPosition].compareTo(tempPatient) > 0) {
                            array[leftChildPosition].setPosInQueue(position);
                            array[position] = array[leftChildPosition];
                            array[leftChildPosition] = tempPatient;
                            position = leftChildPosition;
                            array[position].setPosInQueue(position);
                            leftChildPosition = 2 * position + 1;
                            rightChildPosition = 2 * position + 2;
                        }
                    } else {
                        array[rightChildPosition].setPosInQueue(position);
                        array[position] = array[rightChildPosition];
                        array[rightChildPosition] = tempPatient;
                        position = rightChildPosition;
                        array[position].setPosInQueue(position);
                        leftChildPosition = 2 * position + 1;
                        rightChildPosition = 2 * position + 2;
                    }
                }
            }
            if (size() == 1) {
                tempPatient.setPosInQueue(0);
            }
        }
        maxPatient.setPosInQueue(-1);
        table.remove(maxPatient.name());
        return maxPatient;
    }

    //return but do not remove the first patient in the queue
    public Patient getMax() {
        return array[0];
    }

    //return the number of patients currently in the queue
    public int size() {
        return queueSize;
    }

    //return true if the queue is empty; false else
    public boolean isEmpty() {
        boolean check;
        if (size() == 0) {
            check = true;
        }
        else {
            check = false;
        }
        return check;
    }

    //used for testing underlying data structure
    public Patient[] getArray() {
        return array;
    }

    /*TO BE COMPLETED IN PART 2*/

    //remove and return the Patient with
    //name s from the queue
    //return null if the Patient isn't in the queue
    public Patient remove(String s) {
        Patient patient = null;
        int hashKey = s.hashCode() % table.getSize();
        int count = 0;
        boolean found = false;

        Patient removedPatient;
        Patient tempPatient;
        int position;
        int parentPosition;
        int leftChildPosition;
        int rightChildPosition;
        boolean sinkDownComplete = false;

        if (hashKey < 0) {
            hashKey = hashKey + table.getSize();
        }
        if (table.getArray()[hashKey] == null) {
            return null;
        } else {
            while ((count < table.getArray()[hashKey].size()) && (found == false)) {
                patient = table.getArray()[hashKey].get(count);
                if (s.equals(patient.name())) {

                    table.getArray()[hashKey].remove(count);
                    found = true;
                    tableSize--;
                } else {
                    count++;
                }
            }
        }

        position = patient.posInQueue();
        removedPatient = array[position];
        tempPatient = array[size() - 1];
        array[position] = tempPatient;
        array[position].setPosInQueue(position);
        array[size() - 1] = null;
        queueSize--;
        if (position == size()) {
            position--;
        }
        parentPosition = (position - 1) / 2;
        leftChildPosition = 2 * position + 1;
        rightChildPosition = 2 * position + 2;

        if (size() == 0) {
            removedPatient.setPosInQueue(-1);
            return removedPatient;
        }

        if (array[position].compareTo(array[parentPosition]) > 0) { // swim up
            while (array[parentPosition].compareTo(array[position]) < 0) {
                tempPatient = array[parentPosition];
                array[parentPosition] = array[position];
                array[position].setPosInQueue(parentPosition);
                array[position] = tempPatient;
                tempPatient.setPosInQueue(position);
                position = parentPosition;
                parentPosition = (position - 1) / 2;
            }
        } else { // sink down
            while ((leftChildPosition < size()) && (sinkDownComplete == false)) {
                if ((array[position].compareTo(array[leftChildPosition]) >= 0) &&
                    ((rightChildPosition >= size()) || (array[position].compareTo(array[rightChildPosition]) >= 0))) {
                    array[position].setPosInQueue(position);
                    sinkDownComplete = true;
                } else {
                    if ((rightChildPosition >= size()) ||
                        (array[leftChildPosition].compareTo(array[rightChildPosition]) > 0)) {
                        // parent is smaller than one of the children and left child is larger than right child
                        if (array[leftChildPosition].compareTo(tempPatient) > 0) {
                            array[leftChildPosition].setPosInQueue(position);
                            array[position] = array[leftChildPosition];
                            array[leftChildPosition] = tempPatient;
                            position = leftChildPosition;
                            array[position].setPosInQueue(position);
                            leftChildPosition = 2 * position + 1;
                            rightChildPosition = 2 * position + 2;
                        } else if ((size() - leftChildPosition) < (2 * position + 1)) {
                            leftChildPosition = size();
                        }
                    } else {
                        array[rightChildPosition].setPosInQueue(position);
                        array[position] = array[rightChildPosition];
                        array[rightChildPosition] = tempPatient;
                        position = rightChildPosition;
                        array[position].setPosInQueue(position);
                        leftChildPosition = 2 * position + 1;
                        rightChildPosition = 2 * position + 2;
                    }
                }
                array[position].setPosInQueue(position);
            }
        }
        removedPatient.setPosInQueue(-1);
        return removedPatient;
    }

    //update the emergency level of the Patient
    //with name s to urgency
    public void update(String s, int urgency) {
        int position;
        int oldUrgency = table.get(s).urgency();
        Patient tempPatient;
        int leftChildPosition;
        int rightChildPosition;
        boolean sinkDownComplete = false;
        int parentPosition;
        position = table.get(s).posInQueue();
        table.get(s).setUrgency(urgency);

        if (oldUrgency > urgency) { // sink down
            tempPatient = array[position];
            leftChildPosition = 2 * position + 1;
            rightChildPosition = 2 * position + 2;
            while ((leftChildPosition < size()) && (sinkDownComplete == false)) {
                if ((array[position].compareTo(array[leftChildPosition]) > 0) &&
                    ((rightChildPosition >= size()) || (array[position].compareTo(array[rightChildPosition]) > 0))) {
                    array[position].setPosInQueue(position);
                    sinkDownComplete = true;
                } else {
                    if ((rightChildPosition >= size()) ||
                        (array[leftChildPosition].compareTo(array[rightChildPosition]) > 0)) {
                        // parent is smaller than one of the children and left child is larger than right child
                        if (array[leftChildPosition].compareTo(tempPatient) > 0) {
                            array[leftChildPosition].setPosInQueue(position);
                            array[position] = array[leftChildPosition];
                            array[leftChildPosition] = tempPatient;
                            position = leftChildPosition;
                            array[position].setPosInQueue(position);
                            leftChildPosition = 2 * position + 1;
                            rightChildPosition = 2 * position + 2;
                        }
                    } else {
                        array[rightChildPosition].setPosInQueue(position);
                        array[position] = array[rightChildPosition];
                        array[rightChildPosition] = tempPatient;
                        position = rightChildPosition;
                        array[position].setPosInQueue(position);
                        leftChildPosition = 2 * position + 1;
                        rightChildPosition = 2 * position + 2;
                    }
                }
                array[position].setPosInQueue(position);
            }
        } else if (oldUrgency < urgency) { //swim up
            parentPosition = (position - 1) / 2;
            while (array[parentPosition].compareTo(array[position]) < 0) {
                tempPatient = array[parentPosition];
                array[parentPosition] = array[position];
                tempPatient.setPosInQueue(position);
                array[position] = tempPatient;
                position = parentPosition;
                parentPosition = (position - 1) / 2;
            }
            array[position].setPosInQueue(position);
        }
    }

    public int getLength() {
        return length;
    }
    public PHashtable getTable() {
        return table;
    }
}