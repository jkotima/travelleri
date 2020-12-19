# Käyttöohje
*(Nämä ohjeet on tehty Linuxille)*

## Tekstipohjaisen käyttöliittymän ajaminen 
```
./gradlew run --console=plain
```

## Uuden verkon luominen tiedostoon
Valitse valikosta "1. luo uusi verkko ja tallenna se tiedostoon"

Tiedostot voi tehdä myös tavanomaisilla tekstieditoreilla: pilkku erottaa etäisyysmatriisin sarakkeen, rivinvaihto rivin.

## Algoritmin ajaminen tiedostossa olevalle verkolle
### Käyttöliittymästä
Valitse valikosta "2. avaa verkko tiedostosta ja aja algoritmi"

### Komentoriviltä
```
NaiveTSP:

./gradlew run --args "runNaive [tiedosto]" --console=plain

BranchTSP:

./gradlew run --args "runBranch [tiedosto]" --console=plain

DynamicTSP:

./gradlew run --args "runDynamic [tiedosto]" --console=plain

ApproxTSP:

./gradlew run --args "runApprox [tiedosto]" --console=plain

Esim.

./gradlew run --args "runNaive ./examples/graph.csv" --console=plain


```
## Osoitekoordinaattejen perusteella suoritettava TSP-reitin laskenta

### Käyttöliittymän GoogleMaps-työkalu
1. Valitse "3. pasteta koordinaatteja GoogleMapsista ja aja dynaaminen algoritmi"
2. Jätä sovellus taustalle ja mene selaimellasi GoogleMapsiin
3. Valitse hiiren oikealla näppäimellä haluamastasi paikasta kartalla (ensimmäinen valinta on lähtöpaikka)
4. Valitse avautuneesta valikosta koordinaatti (kopioituu leikepöydälle, josta travelleri sen nappaa)
5. Kun olet valinnut kaikki haluamasi paikat kartalla, paina sovelluksessa mitä tahansa näppäintä
6. Avaa travelleriin ilmestynyt linkki, joka avaa lasketun TSP-reitin GoogleMapsiin

### Tiedostosta komentoriviltä
Koordinaattitiedostoissa koordinaatit ovat samaa muotoa kuin Google Mapsista kopioidessa, ja ne ovat erotettu toisistaan rivinvaihdolla (ks. ./examples/coords.txt). 


```
./gradlew run --args "runFromCoordinates [tiedosto] [naive/branch/dynamic/approx]" --console=plain

Esim.
./gradlew run --args "runFromCoordinates ./examples/coords.txt approx" --console=plain

Tai

./gradlew run --args "runFromCoordinates ./examples/coords.txt" --console=plain

(ilman algoritmivalintaa suoritetaan dynamic)

```

Avaa travelleriin ilmestynyt linkki, joka avaa lasketun TSP-reitin GoogleMapsiin. 

## Suorituskykytestit
### Default-satunnaisverkkotesti käyttöliittymästä
Valitse "4. aja default suorituskykytesti satunnaisverkoille"

### Satunnaisverkkotesti komentoriviltä
```
./gradlew run --args "runPerformanceTest [naive/branch/dynamic/approx] [aloitusVerkkokoko] [lopetusVerkkokoko] [toistotPerVerkkokoko]" --console=plain

Esim.
./gradlew run --args "runPerformanceTest naive 2 6 10" --console=plain
```
### Testi ApproxTSP:n polun pituudelle suhteessa optimipolkuun
Valitse "5. aja testi ApproxTSP:n polun pituudelle suhteessa optimipolkuun"

### Testi ApproxTSP:n polun pituudelle suhteessa optimipolkuun, komentoriviltä
```
./gradlew run --args "runApproxPathTest [aloitusVerkkokoko] [lopetusVerkkokoko] [toistotPerVerkkokoko]" --console=plain

Esim.

./gradlew run --args "runApproxPathTest 10 10 10" --console=plain

```

## Gradleen liittyvät komennot
JUnit-testien ajaminen ja Jacoco-raportin generointi (.build/reports/tests ja .build/reports/jacoco):
```
./gradlew test jacocoTestReport
```

CheckStylen ajaminen (build/reports/checkstyle):
```
./gradlew check
```

Javadocin generoiminen (build/docs/javadoc):
```
./gradlew javadoc
```

Buildaaminen:
```
./gradlew build
```

Buildin (tai ./gradlew jar) jälkeen travellerin voi myös ajaa:
```
java -jar ./build/libs/travelleri.jar [+mahdolliset argumentit]
```



