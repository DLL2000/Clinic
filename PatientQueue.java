public class PatientQueue {
    private Patient[] array;

    private int queueSize;
    //constructor: set variables
    //capacity = initial capacity of array
    public PatientQueue(int capacity) {
        array = new Patient[capacity];
        queueSize = 0;
    }

    //insert Patient p into queue
    //return the final index at which the patient is stored
    //return -1 if the patient could not be inserted
    public int insert(Patient p) {
        int position = 0;
        Patient tempPatient;
        int parentPosition = 0;
        if (size() == array.length) {
            return -1;
        } else if (size() == 0) {
            queueSize++;
            array[0] = p;
            p.setPosInQueue(position);
            return position;
        } else { // insert p at end of heap and swim up
            position = size();
            queueSize++;
            array[position] = p;
            parentPosition = (position - 1) / 2;
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
            }
        }
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
}