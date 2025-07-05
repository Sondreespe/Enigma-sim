package enigma;

public class CryptoRotor implements Rotor {
    // Mønstrene til rotorene
    public static final int[] ROTOR_I = {
        4, 10, 12, 5, 11, 6, 3, 16, 21, 25,
        13, 19, 14, 22, 24, 7, 23, 20, 18, 15,
        0, 8, 1, 17, 2, 9
    };

    public static final int[] ROTOR_II = {
        0, 9, 3, 10, 18, 8, 17, 20, 23, 1,
        11, 7, 22, 19, 12, 2, 16, 6, 25, 13,
        15, 24, 5, 21, 14, 4
    };

    public static final int[] ROTOR_III = {
        1, 3, 5, 7, 9, 11, 2, 15, 17, 19,
        23, 21, 25, 13, 24, 4, 8, 22, 6, 0,
        10, 12, 20, 18, 16, 14
    };

    private final int[] wiring;        // aktiv wiring
    private final int[] inverseWiring; // invers wiring
    private int position;              // rotorens posisjon 0–25

    public CryptoRotor(int[] wiring, int startPosition) {
        this.wiring = wiring.clone();
        this.inverseWiring = new int[26];
        this.position = startPosition % 26;

        for (int i = 0; i < 26; i++) {
            inverseWiring[wiring[i]] = i;
        }
    }

    /**
     * Første passering (tastatur → reflektor)
     */
    public int firstRotorPassage(int inputIndex) {
        int shifted = (inputIndex + position) % 26;
        int mapped = wiring[shifted];
        return (mapped - position + 26) % 26;
    }

    /**
     * Andre passering (reflektor → tastatur)
     */
    public int secondRotorPassage(int inputIndex) {
        int shifted = (inputIndex + position) % 26;
        int mapped = inverseWiring[shifted];
        return (mapped - position + 26) % 26;
    }

    @Override
    public void stepUp() {
        position = (position + 1) % 26;
    }

    @Override
    public void stepDown() {
        position = (position - 1 + 26) % 26;
    }

    @Override
    public void setPos(int pos) {
        position = pos % 26;
    }

    @Override
    public int getPos() {
        return position;
    }

    @Override
    public void reset() {
        setPos(0);
    }
}