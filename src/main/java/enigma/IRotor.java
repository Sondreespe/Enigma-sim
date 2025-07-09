package enigma;

public interface IRotor {

    /**
     * Roterer rotoren et hakk opp.
     */
    public void stepUp();

    /**
     * Roterer rotoren et hakk ned.
     */
    public void stepDown();

    /**
     * Setter posisjonen til rotoren.
     */
    public void setPos(int pos);

    /**
     * Henter posisjonen til rotoren.
     * @return the index of the rotor's position (0-25)
     */
    public int getPos();

    /***
     * Nullstiller rotoren til posisjon 0.
     */
    public void reset();
}
