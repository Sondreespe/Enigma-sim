# Enigma-sim
Dette prosjektet er en simulering av den klassiske Enigma-maskinen brukt under 2. verdenskrig. 
Apllikasjonen er laget i Java med JavaFx for  GUI, og etterligner krypteringsprossesen med rotorer, plugboard og reflektor.

---------------------------------------------------------------------------------------------------------------------------------
## Hva er en Enigma-maskin
- En enigma masking er et kryptering instrument som kan kryptere bokstaver og dermed også setninger. Den ble brukt av Tyskland i 2.verdenskrig til å sende konfidensiale meldinger over morse til sine allierte. Maskinen hadde en enkel funksjonalitet og mekanikk, samt ca **1.5 x 10^20** mulige kombinasjoner/innstillinger.

## Oppbygning
maskinen er bygd opp av tre deler. **Rotorene med reflektor**, **GUI-tastaturet med lyseffekt** og **plugboardet**. Under skal jeg forklare grunndigere mekanikken bak dette

### Plugboard:
- Nederst i vinduet har vi plugboardet. Her kan vi "koble" sammen 10 par med bokstaver. Det vil for seg selv si at hvis vi kobler E og T sammen vil vi få en T hvis vi trykker på E, og vice versa. 

### Rotorene:
- Vi har tre rotorer som viser alle bokstavene i det engelske alfabetet (A-Z). Ved å trykke opp og ned kan du stille inn rotorene til ønskelig posisjon. Hver rotor har en egen sekvens som kobler en gitt bokstav til en anne. F.eks bytter alltid rotor 1 (den lengst til venstre) A med B. Innebygd i mekanikken til rotorene så har vi en roteringsfunksjon. For hvert tastetrykk roterer rotoren lengst til høyre ett hakk. Hvis den rotoren gjør en hel omdreining så roterer neste rotor ett hakk. Dette gjelder også for siste rotoren. 

### Reflektoren:
- Etter at bokstaven har passert gjennom alle tre rotorene sender reflektoren signalet tilbake. Dette signalet skal være symetrisk og kosntant slik det er mulig å dekryptere bokstaven. 

### Flow chart av prosessen:
 Trykket bokstav -> plugboard -> rotor høyre -> rotor midten -> rotor venstre -> reflektor -> rotor venstre -> rotor midt -> rotor øyre -> lyser opp.



## Hvordan ta den i bruk:
- **Start** apllikasjonen ved å gå til filen src/Java/enigma/Main.java bla helt nederst til "main"-funskjonen og trykk **run**.
- **Husk eller skriv ned** rotor posisjonene og koblingene du har gjort på plugboardet.
- **Trykk på en bokstav** på tastaturet til pc´en din.
- **Den kryperte bokstaven** vil lyse opp på tastaturet på skjermen din.
- **For å dekryptere bokstaven** må du stille inne maskinen med samme koblinger og samme rotor-posisjoner som da du først startet.
Så må du skrive inn de krypterte bokstavene. Da vil den originale inputen lyse opp. 
- **Trykk på reset-knappen** om du ønsker å fjerne alle koblinger og sette alle rotor-posisjoer til 0.



