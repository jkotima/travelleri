# Suorituskyky

Kaikki testit suoritettiin koneella, jossa prosessorina i5-8265U @ 1.60Ghz x 4, muistia 16gb, käyttöjärjestelmänä Ubuntu.

## Testaus satunnaisesti generoiduilla verkoilla 

Testaukset suoritettiin satunnaisesti generoiduilla yksisuuntaisilla verkoilla, joissa kaarien paino on satunnainen liukuluku välillä 0-100. 
Verkkoja generoitiin kymmenen per solmumäärä, joiden suoritusajoista otettiin keskiarvo.

Valmiit testit kaikille algoritmeille voi ajaa komennolla
```
./gradlew run --args "runPerformanceTest all" --console=plain

```

Yksittäisen testin voi myös ajaa. Esimerkiksi max 10 solmuisilla verkolla ajettava testi DynamicTSP:lle:

```
./gradlew run --args "runPerformanceTest dynamic 10" --console=plain

```
### Tulokset

| Solmujen lkm | NaiveTSP suoritusaika (ns) | BranchTSP suoritusaika | DynamicTSP suoritusaika (ns) | ApproxTSP suoritusaika (ns) |
|---|---|---|---|---|
| 2 | 2559 | 24670 | 11228 | 1348
| 3 | 2659 | 3607 | 8903 | 1347
| 4 | 7950 | 10376 | 26083 | 1269
| 5 | 44816 | 37653 | 61791  | 1798
| 6 | 418571 | 75313 | 124675  | 2114
| 7 | 11889404 | 284436 | 133470 | 3386
| 8 | 426653851 | 1664075 | 319795 | 3129
| 9 |  | 16299561 | 837232 | 3984
| 10 |  | 85784533 | 1892480 | 4824
| 11 |  | 348503966 | 4468774 | 4270
| 12 |  | 2685138728 | 10923793 | 1376

Huomataan, että DynamicTSP on huomattavasti nopein optimipolun tuottavista algoritmeista, mutta alle kuuden solmun verkoilla BranchTSP tai NaiveTSP ovat hieman nopeampia (eivät käytä niin raskasta tietorakennetta).

Approx TSP:lle ei ole solmun määrällä niinkään merkitystä suoritusaikaan. Toisaalta se ei myöskään palauta optimipolkua.


## ApproxTSP:n polun pituus vs. optimipolku

### Satunnaisverkot
| Solmujen lkm | ApproxTSP:n polun pituss % optimipolusta (keskimäärin) |
|---|---|

***kesken***