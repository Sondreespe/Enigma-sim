package enigma;

public class Reflector {
    
    // konstant "wiring" som sender signalet tilbake gjennom rotorene. 
    // f.eks det kommer ut verdi 2 ut av rotorene, da vil reflektoren sende det tilbake som 20.
    private int[] wiring = {
        24, 17, 20, 7, 16, 18, 11, 3,
        15, 23, 13, 6, 14, 10, 12, 8, 
        4, 1, 5, 25, 2, 22, 21, 9, 0, 19
    };

    /**
     * Reflekterer signalet som kommer ut av rotorene tilbake gjennom rotorerene med en ny verdi.
     * @param inputIndex
     * @return
     */
    public int reflect(int inputIndex){
        return wiring[inputIndex];
    }
}
