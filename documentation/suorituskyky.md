# Suorituskyky

## Testaus satunnaisesti generoiduilla verkoilla 

Testaukset suoritettiin satunnaisesti UI:sta generoiduilla yksisuuntaisilla verkoilla. Kaarien paino on tällöin satunnainen liukuluku välillä 0-100. Testeissä käytetyt verkot löytyvä projektin hakemistosta /tests.

Algoritmit voi suorittaa valitulle testiverkolle esim. komennoilla (linux):

```
./gradlew run --args "runNaive ./tests/5.csv" --console=plain

./gradlew run --args "runDynamic ./tests/5.csv" --console=plain

```

Testit toistettiin 5 kertaa samalla tietokoneella ja tuloksista otettiin keskiarvo.

| Solmujen lkm | NaiveTSP suoritusaika (ns) | DynamicTSP suoritusaika (ns) | ApproxTSP suoritusaika (ns) | ApproxTSP-polun pituus / optimipolun pituus |
|---|---|---|---|---|
| 2 | 10736 | 9610 | 4353 | 41/41  (100%)|
| 3 | 14279 | 13925 | 4154 | 119/119 (100%) |
| 4 | 19372 | 16916 | 4607 |  267/263 (102%) |
| 5 | 89852 | 38133 | 6187  | 212/212 (100%) |
| 6 | 561786 | 324764 | 5745  | 232/232 (100%) |
| 7 | 20245724 | 787857 | 6653 |  245/197 (124%) |
| 8 | 557931171 | 2019373 | 6366 | 230/194 (119%) |
| 9 | 29097261622 | 16299561 | 8422 | 284/249 (114%) |
| 10 |  | 85784533 | 8943 | 244/198 (123%) |
| 11 |  | 348503966 | 9239 |  281/252 (112% )|
| 12 |  | 2685138728 | 10153 |  207/165 (125%) |

