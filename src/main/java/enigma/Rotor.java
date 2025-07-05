package enigma;

public interface Rotor {

    /**
     * Roterer opp rotoren angitt ved index.
     */
    public void stepUp(int rotorIndex);

    /**
     * Roterer ned rotoren angitt ved index.
     */
    public void stepDown(int rotorIndex);

    /**
     * Henter verdien til rotoren angitt ved index.
     */
    public void setRotorValue(int index, int positiom);

    /**
     * gets the value of the rotor with the given index.
     * index 0 is rotor 1, index 1 is rotor 2, index 2 is rotor 3.
     * @param indexRotor
     * @return
     */
    public int getRotorValue(int indexRotor);
}
