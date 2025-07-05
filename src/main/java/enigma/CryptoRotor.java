package enigma;

public class CryptoRotor {
    private final int[] wiring; // rotorens faste koblingsmønster
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

    public void step(){
        postition = (postition + 1) % 26; // roterer rotoren en plass
    }

    public int  getPosition() {
        return postition; // henter gjeldene posisjon
    }

    public void setPosition(int position) {
        this.postition = position % 26; // setter posisjonen, unngår verdier utenfor 0-25
    }
}
