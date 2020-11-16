# Suorituskyky

Testaukset suoritettiin satunnaisesti UI:sta generoiduilla yksisuuntaisilla verkoilla. Kaarien paino on tällöin satunnainen liukuluku välillä 0-100. Testeissä käytetyt verkot löytyvä projektin hakemistosta /tests.

Algoritmit voi suorittaa valitulle testiverkolle esim. komennoilla (linux):

```
./gradlew run --args "runNaive ./tests/5.csv" --console=plain

./gradlew run --args " runDynamic ./tests/5.csv" --console=plain

```

Testit toistettiin 5 kertaa samalla tietokoneella ja tuloksista otettiin keskiarvo.

| Solmujen lkm | NaiveTSP suoritusaika | DynamicTSP suoritusaika | ApproxTSP suoritusaika | ApproxTSP-polun pituus (% todellisesta)
|---|---|---|---|---|
| 2 | 10736 | 9610 |   |   |
| 3 | 14279 | 13925 |   |   |
| 4 | 19372 | 16916 |  |   |
| 5 | 89852 | 38133 |   |   |
| 6 | 561786 | 324764 |   |   |
| 7 | 20245724 | 787857 |   |   |
| 8 | 557931171 | 2019373 |   |   |
| 9 | 29097261622 | 16299561 |   |   |