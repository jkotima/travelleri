# Suorituskyky

Kaikki testit suoritettiin koneella, jossa prosessorina i5-8265U x 4, muistia 16gb, käyttöjärjestelmänä Linux (Ubuntu).

## Testaus satunnaisesti generoiduilla verkoilla 

Testaukset suoritettiin satunnaisesti generoiduilla yksisuuntaisilla verkoilla, joissa kaarien paino on satunnainen liukuluku välillä 0-100. 
Algoritmit ajettiin useaan kertaa jokaista verkon kokoa kohti, niin että ennen algoritmin ajoa generoitiin aina uusi satunnainen verkko. Ajojen lukumäärä riippui suoritusajasta: pienille verkoille algoritmi ajettiin 10 kertaa. Jos suoritusaika oli yli viisi sekuntia, ajettiin 5 kertaa, jos yli 30 sekuntia, vain kerran. Verkkojen suoritusajoista otettiin keskiarvo.

Satunnaisen verkon suorituskykytestit ajetaan algoritmille seuraavasti:

```
./gradlew run --args "runPerformanceTest [algoritmi] [aloitusverkkokoko] [maksimiverkkokoko] [toistot per verkkokoko]" --console=plain

Esimerkiksi

./gradlew run --args "runPerformanceTest naive 2 8 10" --console=plain

...ajaa suorituskykytestin NaiveTSP-algoritmille 2...8 solmuisilla verkoilla, toistaen testin 10 kertaa per verkkokoko.
```

Jos ja kun Javan heap-space loppuu kesken (esim. ajettaessa DynamicTSP isoilla verkoilla), voi ohjelman pakata jariksi ja kekomuistivarausta kasvattaa (tässä 6 gigaa):
```
./gradlew jar
java -Xmx6g -jar ./build/libs/travelleri.jar runPerformanceTest dynamic 20 23 1
```

Jarista ajamalla ohjelma saattaa toimia myös hieman nopeammin.

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


Huomataan, että DynamicTSP on huomattavasti nopein optimipolun tuottavista algoritmeista. Alle 7 solmun verkoista NaiveTSP ja BranchTSP ovat hieman nopeampia, mutta eivät niin paljon, että erolla liene käytännön merkitystä missään käyttötapauksessa.

BranchTSP selviytyy kohtuullisessa ajassa vielä 12 solmun verkoista, jonka jälkeen se hidastuu merkittävästi. NaiveTSP ei taas toimi järkevässä ajassa enää 8 solmun jälkeen.

DynamicTSP:ssä jokaisen lisäsolmun kohdalla suoritusaika noin kaksinkertaistuu. DynamicTSP toimi vielä ainakin 24 solmulla, mutta alkaa olla jo aika hidas.

ApproxTSP:lle ei ole solmujen määrällä niinkään merkitystä suoritusaikaan - toisaalta se ei myöskään palauta optimipolkua. Kuitenkin yli 20 solmun verkoissa ApproxTSP alkaa olla näistä algoritmeista ainut käyttökelpoinen algoritmi, jos tulos halutaan ns. reaaliajassa.

![Suorituskyky](./suorituskyky.jpg)

## Muistin käyttö

Muistin käytön mittaaminen suorituksen aikana osoittautui hieman ongelmalliseksi johtuen Javan roskienkerääjän arvaamattomasta toiminnasta.
Algoritmien muistinkäytöstä sai kuitenkin jonkinlaisen kuvan tarkkalemalla käyttöjärjestelmätasolla, kuinka paljon Java-prosessi vei enintään käyttömuistia suorituksen aikana.
Esimerkiksi testikoneella, jossa on Ubuntu 18.04, sai prosessin muistin käytön maksimin näkyviin (tässä suoritetaan BranchTSP 10 solmun verkolla)

```
/usr/bin/time -v java -jar ./build/libs/travelleri.jar runPerformanceTest branch 10 10 1 |& grep Maximum

```

Tässä käytetyt testit voi ajaa myös bash skriptistä

```
./memorytest.sh
```
### Java-prosessin muistin käyttö, kb
| Solmujen lkm | NaiveTSP | BranchTSP | DynamicTSP | ApproxTSP |
|--------------|----------|-----------|------------|-----------|
| 2            | 45896    | 46064     | 47164      | 46156     |
| 3            | 46208    | 46296     | 46872      | 46264     |
| 4            | 45904    | 46316     | 47360      | 46068     |
| 5            | 46048    | 46548     | 46812      | 46172     |
| 6            | 46440    | 46448     | 46864      | 46264     |
| 7            | 61016    | 46880     | 47208      | 45948     |
| 8            | 281080   | 47468     | 47032      | 46240     |
| 9            | 934776   | 54124     | 47172      | 46184     |
| 10           |          | 94516     | 47288      | 45928     |
| 11           |          | 252240    | 51024      | 46444     |
| 12           |          | 605464    | 53304      | 46148     |
| 13           |          | 631908    | 59968      | 45920     |
| 14           |          |           | 72852      | 46476     |
| 15           |          |           | 1322956    | 45856     |
| 16           |          |           | 1373452    | 46136     |
| 17           |          |           | 1455788    | 46104     |
| 18           |          |           | 1507904    | 46060     |
| 19           |          |           | 2079440    | 46516     |
| 20           |          |           | 2736608    | 46216     |
| 21           |          |           | 3374672    | 46252     |
| 22           |          |           | 3881528    | 46136     |
| 23           |          |           | 6661624    | 46012     |
| 24           |          |           | 12530180   | 46728     |

DynamicTSP vie taulukoinnin käytön vuoksi paljon muistia varsinkin isoilla verkoilla. Kuitenkin DynamicTSP:n muistivaativuus näyttäisi olevan samaa luokkaa muiden algoritmien kanssa myös pienemmillä verkoilla. Kuutta solmua suuremmilla verkoilla NaiveTSP ja BranchTSP jäävät taakse nopeutensa lisäksi muistin käytössä.

DynamicTSP:llä 24 solmun kohdalla tuli jo testikoneen käyttömuistin (16gb) rajat vastaan. Käyttömuistin loppumisesta johtuvasta hidastumisesta ja tästä seuraavasta suoritusajan kasvusta johtuen voidaan tätä pitää rajana verkon koolle tällä algoritmilla tällä laitteistolla.

ApproxTSP:llä ei ole suurempia muistivaatimuksia.

### Java-prosessin muistin käyttö, Gb

![Muistin käyttö](./muisti.jpg)

Syy DynamicTSP:n muistin käytön harppaukselle yli 14 solmulla johtuu siitä, että 14 suuremmille verkoille DtspMemo:ssa varataan mahdollisimman suuri hash-taulukko, joka vielä toimii. Tällöin saadaan vähemmän törmäyksiä, mikä on hyväksi suorituskyvylle. Pienemmillä verkoilla algoritmi toimi nopeammin pienemmällä, solmumäärään suhteutetulla muistivarauksella.

## ApproxTSP:n polun pituus vs. optimipolku
ApproxTSP on toteutukseltaan [nearest neighbor](https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm) -algoritmi.
[Rosenkrantz, Stearns, Lewis (1977, 48)](https://pdfs.semanticscholar.org/2081/25449f697c46d02a98eceb18b8c4622384c5.pdf) mukaan nearest neighbor -approksimaatioalgoritmin polun pituus suhteessa optimipolkuun tulisi olla huonoimmassa tapauksessa

__NN / OPT ≤ (0.5)*(round(log<sub>2</sub>*n)+1))__,

missä NN on approksimaatioalgoritmin tuottaman polun pituus, OPT optimaalisen polun pituus, n verkon solmujen lukumäärä ja round() lähin kokonaisluku.
 
Ajetaan testi satunnaisverkoilla ApproxTSP:lle sekä DynamicTSP:lle (optimipolku), verkon solmumäärän ollessa 10...15. Tuloksesta lasketaan suhdeluku NN / OPT. Testi toistetaan 10 eri satunnaisverkolla jokaista solmumäärää kohden. 

Testi on ajettavissa sovelluksen valikosta valitsemalla 5.

Saadaan tulokseksi

| n  | NN / OPT (keskimäärin) | NN / OPT (maksimi) | (0.5)*(round(log<sub>2</sub>*n)+1)) |
|----|------------------------|--------------------|----------------------------|
| 2  | 1                      | 1                  | 1.5                        |
| 3  | 1                      | 1                  | 2                          |
| 4  | 1                      | 1                  | 2                          |
| 5  | 1.09                   | 1.85               | 2                          |
| 6  | 1.21                   | 1.56               | 2.5                        |
| 7  | 1.16                   | 1.73               | 2.5                        |
| 8  | 1.20                   | 1.41               | 2.5                        |
| 9  | 1.10                   | 1.30               | 2.5                        |
| 10 | 1.34                   | 1.84               | 2.5                        |
| 11 | 1.25                   | 1.69               | 2.5                        |
| 12 | 1.38                   | 1.96               | 3                          |
| 13 | 1.38                   | 2.02               | 3                          |
| 14 | 1.40                   | 1.84               | 3                          |
| 15 | 1.52                   | 1.76               | 3                          |
| 16 | 1.38                   | 1.87               | 3                          |
| 17 | 1.60                   | 2.02               | 3                          |
| 18 | 1.54                   | 2.00               | 3                          |
| 19 | 1.51                   | 1.80               | 3                          |
| 20 | 1.49                   | 1.82               | 3                          |
| 21 | 1.77                   | 2.39               | 3                          |
| 22 | 1.43                   | 1.78               | 3                          |

Huomataan, että testin aikana ApproxTSP:n tuottama polku oli pahimmassa tapauksessa 2.39 kertaa pitempi kuin optimipolku.
Kaikkien testien keskiarvo polun pituudelle oli noin 1.32 kertainen optimipolkuun nähden.
Kaikissa testitapauksissa NN / OPT oli pienempi kuin edellämainitusta kaavasta laskettu suhdeluku.

## Aikavaativuudet
### NaiveTSP

Ennen työn toteutusta kirjoitetussa määrittelydokumentissa mainitaan, että "naivin" algoritmin tulisi toimia O(n!) -ajassa. Koska solmut käydään läpi n permutaatioden määrän verran on operaatioiden määrä noin n!*n. Testituloksistakin on nähtävissä, että laskentaan kului hyvin paljon aikaa. Lopputuloksen voi siis arvioida toimivan O(n!) -ajassa.

### BranchTSP

BranchTSP:tä ei alunperin työhön ollut tarkoitus edes toteuttaa, eikä sille täten oltu määritelty mitään tavoitetta aikavaativuudelle. BranchTSP toimii jossain määrin samalla tavalla kuin NaiveTSP: molemmissa algoritmeissa luodaan kaikki reittipermutaatiot. Kuitenkin turhien hakujen "karsinnan" (engl. pruning) ansiosta BranchTSP suorituu paljon nopeammin.

Vaikuttaakin siltä, että suoritusaikaan vaikuttaa hyvin paljon se, minkälainen verkko sattu olemaan kyseessä. Suoritusajan kannalta merkkittävää on se, kuinka aikaisessa vaiheessa löydetään lyhyitä polkuja, joiden avulla karsitaan turhaa laskentaa. Esimerkiksi erilaisilla 10 solmun satunnaisverkoilla suoritusajat heittelevät aika paljon:

```
6763609
5130998
2592843
4532574
1132718
14198121
3887314
862463
1925427
2026987
2653997
786648
1625786
3260071
3988073
2380648
1955900
1336525
6962879
697073
```
Branch-and-bound algoritmien aikavaativuutta on [haastellista arvioida](https://rjlipton.wordpress.com/2012/12/19/branch-and-bound-why-does-it-work/). Teoriassa pahimmassa tapauksessa, jossa kaikki polut löydetään pituusjärjestyksessä pisimmät polut ensin, olisi aikavaativuusluokka O(n!) (vrt. NaiveTSP). Tämä on kuitenkin epätodennäköistä ja käytännössä algoritmi toimii paljon tätä nopeammin.

### DynamicTSP

DynamicTSP:n aikavaativuuden tavoitteena oli O(n<sup>2</sup>2<sup>n</sup>). Kyseinen aikavaativuus vastaa Held-Karp -algoritmin aikavaativuutta, jonka [pseudokoodista](https://www.researchgate.net/figure/Pseudocode-de-Bellman-Held-Karp_fig1_336923519) onkin katsottu mallia DynamicTSP:tä koodatessa.

DynamicTSP:n yksinkertaistettu pseudokoodi (todellisuudessa dtspResults ja dtspPredecessors tallennetaan samaan oliotietorakenteeseen):
```
1 dtspResults, dtspPredecessors = []
2 
3 function dtsp(start, {remaining})
4    if remaining is empty
5       return graph[start][0]
6
7    if (dtspResults[start, {remaining}] != NULL)
8       return dtspResults[start, {remaining}]
9
10   for k in remaining
11      x = graph[start][k] + dtsp(k, {remaining}\{k})
12      if (x < min) 
13          min = x
14          predecessor = k
15   dtspResults[start, {remaining}] = min
16   dtspPredecessors[start, {remaining}] = predecessor
17
18   return min
19
20 minPathLength = dtsp(0, {0...n}\{0})
21
22 updateShortestPath({0...n}\{0})
```

DynamicTSP (kuten Held-Karp) jakautuu kahteen vaiheeseeen:

1. minPathLength laskeminen
  - rivit 11 ja 12 toistetaan (n-1)(n-2)2<sup>n-3</sup>+(n-1) kertaa [(ks. Held-Karp, Wikipedia)](https://en.wikipedia.org/wiki/Held%E2%80%93Karp_algorithm)
    - n * n * 2<sup>n</sup> = O(n<sup>2</sup>2<sup>n</sup>)

2. updateShortestPath
  - Haetaan optimireitti dtspPredecessorsseista
    - käydään takaperin läpi dtspPredecessors, ks. ohjelmakoodi
  - Operaatioita tulee yhteensä ((n)(n - 1)) / 2 - 1 [(ks. Held-Karp, Wikipedia)](https://en.wikipedia.org/wiki/Held%E2%80%93Karp_algorithm)
    - n*n = O(n<sup>2</sup>)

Käytännössä nopeuteen vaikuttaa paljon taulukoinnin toteutus (tulosten tallennuksen ja haun tehokkuus).
Testien perusteella algoritmi vaikuttaisi toimivan eksponentiaalisessa ajassa eikä ole syytä epäillä, etteikö aikavaativuus olisi em. luokkaa.

### ApproxTSP

Määrittelydokumentissa oli tarkoitus toteuttaa  O(n<sup>2</sup>) approksimaatioalgoritmi, joka käyttää hyväksi jotain MST-puun algoritmia.

Approksimaatioalgoritmi tuli toteutettua lähinnä intuitiolla ja minimivirittyneiden puiden algoritmeista se muistuttaa lähinnä Primin algoritmia ainoastaan etenemistavaltaan, ei niinkään muuten. Myöhemmin selvisi, että algoritmi oli täysin samanlainen kuin wikipediassa kuvattu [Nearest neighbor algorithm](https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm), jonka aikaluokaksi on ilmoitettu O(n<sup>2</sup>).

Algoritmissa on solmut läpi käyvä rekursio, jonka sisällä yksi jäljellä olevat solmut läpi käyvä silmukka, eli operaatioita tulee noin n*n. Aikavaativuus on siis  O(n<sup>2</sup>). Tämä käy ilmi myös testauksessa: algoritmi toimii hyvin tehokkaasti.

# Lopputulos

Toteutetuista algoritmeista käyttökelpoisin optimipolun ratkaisemiseen on ehdottamasti DynamicTSP, eikä NaiveTSP ja BranchTSP algoritmien käyttöön löydy sinänsä perusteluja. 

DynamicTSP alkaa kuitenkin selkeästi hidastumaan 20 solmua suuremmilla verkoilla. Ennen kaikkea ongelmaksi vaikuttaa muodostuvan muistin suuri käyttö: jos muistia olisi käytettävissä enemmän, voisi algoritmi olla suorituskyvyllisesti toimiva suuremmillakin verkoilla esim. yön yli ajettuna.

Jos tarvitaan nopeaa algoritmia, joka antaa jonkinlaisen arvion lyhyimmästä reitistä, voisi ApproxTSP tai jokin muu approksimaatioalgoritmi olla varteenotettava vaihtoehto. Tämän algoritmin voi ajaa ongelmitta hyvinkin paljon suuremmille verkoille.



