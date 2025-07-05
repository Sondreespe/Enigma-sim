package enigma;

public class Reflector {
    
    // konstant "wiring" som sender signalet tilbake gjennom rotorene. 
    // viktig element er at den er symmetrisk, slik at A blir til Z og Z blir til A. Dette er viktig for dekryptering
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
