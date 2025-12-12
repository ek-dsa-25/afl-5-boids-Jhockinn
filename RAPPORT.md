Jeg har valgt at implementere **RandomBehavior** som en ny strategi.

**Hvordan virker RandomBehavior?**
- Boids med denne adfærd ændrer retning tilfældigt med små vinkler
- Drejer mellem -15° og +15° per frame
- Bruger trigonometri til at rotere hastighedsvektoren
- Ignorerer fuldstændigt naboer og bevæger sig uafhængigt
