package enigma;

public class CryptoRotor {
    // møsntrenene til rotorene
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

    private final int[] wiring; // det aktive mønsteret, dette er for første passering gjennom rotorene
    private final int[] inverseWiring; // den inverse av mønsteret over, dette er for andre passering gjennom rotorene
    private int postition; // den gjeldene posisjonen til rotoren


    public CryptoRotor(int[] wiring, int startPostition) {
        this.wiring = wiring.clone(); // for å unngå permutasjon av originale mønsteret
        this.inverseWiring = new int[26]; // oppretter tomt int array for inversen
        this.postition = startPostition % 26; // unngår verdier utenfor 0-25

        for(int i = 0; i < 26; i++){
            inverseWiring[wiring[i]] = i; // fyller inn den inverse koblingen
        }
    }

    /**
     * Denne funksjonen gjelder for den første passeringen mellom rotorene. !VIKTIG ! gjelder bare for en enkel rotor
     * Koder en bokstav ved å sende den gjennom rotoren og kobler den opp til en annen bokstav ved hjelp av wiring
     * @param inputIndex
     * @return indeksen til den kodede bokstaven
     */
    public int firstRotorPassage(int inputIndex){
        int shifted = (inputIndex + postition) % 26; // 
        int mapped = wiring[shifted]; //
        return (mapped - postition + 26) % 26; // 
    }      

    /**
     * Denne funksjonen gjelder for den andre passeringen mellom rotorene. !VIKTIG ! gjelder bare for en enkel rotor
     * Koder en bokstav ved å sende den gjennom rotoren og kobler den opp til en annen bokstav ved hjelp av inverseWiring 
     * @param inputIndex
     * @return indeksen til den kodede bokstavem
     */
    public int secondRotorPassage(int inputIndex){
        int shifted = (inputIndex + postition) % 26; // 
        int mapped = inverseWiring[shifted]; // 
        return (mapped - postition + 26) % 26; // 
    }

    /**
     * Roterer rotorens posisjon en plass. Dette er for krypteringen og ikke GUI-en.
     */
    public void step(){
        postition = (postition + 1) % 26; // roterer rotoren en plass
    }

    /**
     * Henter den gjeldene posisjonenn til rotoren
     * @return indeksen til den gjeldene posisjonen, 0-25
     */
    public int getPosition() {
        return postition; // henter gjeldene posisjon
    }

    /**
     * Setter posisjonen til rotoren. Kan være den ikke trengs, men kanskje mtp reset osv
     * @param position
     */
    public void setPosition(int position) {
        this.postition = position % 26; // setter posisjonen, unngår verdier utenfor 0-25
    }
}
