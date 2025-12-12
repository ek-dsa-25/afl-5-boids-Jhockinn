Jeg har valgt at implementere **RandomBehavior** som en ny strategi.

**Hvorfor RandomBehavior?**
- Relativt simpel at implementere
- Giver en tydelig visuel forskel sammenlignet med flok-adfærd
- Kræver ikke yderligere objekter på kortet (som mad eller forhindringer ville)

**Hvordan virker RandomBehavior?**
- Boids med denne adfærd ændrer retning tilfældigt med små vinkler
- Drejer mellem -15° og +15° per frame
- Bruger trigonometri til at rotere hastighedsvektoren
- Ignorerer fuldstændigt naboer og bevæger sig uafhængigt

**Implementering**:
- Oprettet `RandomBehavior.java` som implementerer `BehaviorStrategy`
- Tilføjet ny `BoidType.RANDOM` med gul farve
- Standard boids er hvide, random boids er gule - dette gør det nemt at se forskellen i simulationen