# Suorituskyky

Kaikki testit suoritettiin koneella, jossa prosessorina i5-8265U @ 1.60Ghz x 4, muistia 16gb, käyttöjärjestelmänä Linux (Ubuntu).

## Testaus satunnaisesti generoiduilla verkoilla 

Testaukset suoritettiin satunnaisesti generoiduilla yksisuuntaisilla verkoilla, joissa kaarien paino on satunnainen liukuluku välillä 0-100. 
Algoritmit ajettiin useaan kertaa jokaista verkon kokoa kohti: ennen algoritmin ajoa generoitiin aina uusi satunnainen verkko. Ajojen lukumäärä riippui suoritusajasta: jos suoritusaika oli yli viisi sekuntia, ajettiin algoritmi vain 5 kertaa, jos yli 30 sekuntia, vain kerran. Verkkojen suoritusajoista otettiin keskiarvo.

Testit ajetaan komentoriviltä algoritmille seuraavasti

```
./gradlew run --args "runPerformanceTest [algoritmi] [aloitusverkkokoko] [maksimiverkkokoko] [toistot per verkkokoko]" --console=plain

Esim.

./gradlew run --args "runPerformanceTest naive 2 8 10" --console=plain
```

Jos ja kun Javan heap-space loppuu kesken (esim. ajettaessa ApproxTSP isoilla verkoilla), voi ohjelman pakata jariksi ja kekomuistivarausta kasvattaa (tässä 6 gigaa):
```
./gradlew jar
java -Xmx6g -jar ./build/libs/travelleri.jar runPerformanceTest dynamic 20 23 1
```

Jarista ajamalla ohjelma toimii myös hieman nopeammin.

### Tulokset

| Solmujen lkm | NaiveTSP suoritusaika (ns) | BranchTSP suoritusaika (ns) | DynamicTSP suoritusaika (ns) | ApproxTSP suoritusaika (ns) |
|---|---|---|---|---|
| 2  | 4777        | 37949       | 6618        | 1450 |
| 3  | 2493        | 3050        | 6511        | 1270 |
| 4  | 7927        | 10759       | 15654       | 1356 |
| 5  | 43447       | 36094       | 38547       | 2142 |
| 6  | 397618      | 63505       | 67374       | 2472 |
| 7  | 11213647    | 287946      | 84531       | 4021 |
| 8  | 404999348   | 1516081     | 211324      | 3670 |
| 9  | 26299607848 | 4358703     | 511233      | 4246 |
| 10 |             | 29974455    | 1296410     | 4289 |
| 11 |             | 329767642   | 3542664     | 3334 |
| 12 |             | 3579601372  | 8429117     | 1078 |
| 13 |             | 45634820622 | 19946926    | 1134 |



BranchTSP selviytyy kohtuullisessa ajassa 13 solmun verkoista, jonka jälkeen se hidastuu merkittävästi. DynamicTSP:ssä jokaisen lisäsolmun kohdalla suoritusaika n. kaksinkertaistuu. DynamicTSP toimi vielä 24 solmulla, mutta Javan kekomuistia joutui kasvattamaan jopa 13 gigaan.


| Solmujen lkm | NaiveTSP suoritusaika (s) | BranchTSP suoritusaika (s) | DynamicTSP suoritusaika (s) | ApproxTSP suoritusaika (s) |
|---|---|---|---|---|
| 10 |  | 0.029974  | 0.001296  | 0.000004 |
| 11 |  | 0.329768  | 0.003543  | 0.000003 |
| 12 |  | 3.579601  | 0.008429  | 0.000001 |
| 13 |  | 45.634821 | 0.019947  | 0.000001 |
| 14 |  |           | 0.047579  | 0.000001 |
| 15 |  |           | 0.131301  | 0.000001 |
| 16 |  |           | 0.342394  | 0.000002 |
| 17 |  |           | 0.918363  | 0.000002 |
| 18 |  |           | 2.081705  | 0.000008 |
| 19 |  |           | 5.222039  | 0.000003 |
| 20 |  |           | 12.325517 | 0.000003 |
| 21 |  |           | 31.407858 | 0.000003 |
| 22 |  |           | 80.750790 | 0.000003 |
| 23 |  |           | 187.242918 | 0.000003 |
| 24 |  |           | 332.882166 | 0.000003 |


Huomataan, että DynamicTSP on huomattavasti nopein optimipolun tuottavista algoritmeista. Alle 7 solmun verkoista kevyempää tietorakennetta käyttävät algoritmin ovat hieman nopeampia, mutta tuskin niin paljoa, että sillä olisi käytännön merkitystä.

ApproxTSP:lle ei ole solmujen määrällä niinkään merkitystä suoritusaikaan - toisaalta se ei myöskään palauta optimipolkua. Kuitenkin yli 20 solmun verkoissa ApproxTSP alkaa olla näistä algoritmeista ainut käyttökelpoinen algoritmi, varsinkin jos on kiire.

![Suorituskyky](./suorituskyky.jpg)

## Algoritmien muistin käyttö

***kesken***


## ApproxTSP:n polun pituus vs. optimipolku

### Satunnaisverkot
| Solmujen lkm | ApproxTSP:n polun pituus % optimipolusta (keskimäärin) |
|---|---|

***kesken***

