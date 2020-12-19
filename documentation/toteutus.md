# Toteutus

## NaiveTSP

Algoritmin naivissa toteutuksessa generoidaan aluksi kaikki mahdolliset etenimisjärjestykset solmuissa niin, että polku alkaa solmusta 0 ja kulkee kaikkien solmujen kautta. Tämän jälkeen näiden permutaatioiden mukaiset polut käydään läpi, laskien samalla ko. polusta syntyvä matka. Lyhin löydetty polku ja sen permutaatio tallennetaan oliomuuttujiin.

Aikavaativuus on luokkaa O(n!), joten algoritmi on todella hidas.

## DynamicTSP

Dynaamisessa toteutuksessa hyödynnetään lyhimmän polun palauttavaa rekursiivista esitystä
```
dtsp(start, {remaining}) = min(graph[start][k] + dtsp(k, {remaining}\{k})),
jossa k ∈ {remaining}.
```

Esimerkiksi, jos verkossa on solmut {0, 1, 2}, toimii rekursio seuraavalla tavalla laskiessaan lyhimmän polun pituuden (dtsp(0, {1,2}) = lyhinPolku):
```

dtsp(0, {1,2}) = min( graph[0][1] + dtsp(1, {2}),
                      graph[0][2] + dtsp(2, {1})
                    )
---
dtsp(1, {2}) = min( graph[1][2] + dtsp(2, {}) )
dtsp(2, {1}) = min( graph[2][1] + dtsp(1, {}) )
---
dtsp(2, {}) = 0
dtsp(1, {}) = 0
---

(graph[x][y] on etäisyys solmujen x ja y välillä)
(min() palauttaa pienimmän arvon)

```

Tulokset (ja edeltävät solmut) tallennetaan *DtspMemo*-luokan avulla muistiin, joten samoja laskutoimituksia ei tarvitse laskea useampaan kertaan. Käytännössä siis joka kerta, kun kutsutaan dtsp-metodia jollain parametrillä, tarkastetaan ensin, onko kyseisille parametreille saatu laskettua jo arvo. Jos on, palautetaan vastaus muistista.

Kun rekursio on käyty läpi, käydään lasketut dtsp-tulokset läpi "takaperin" edeltävien solmujen mukaisesti (updateShortestPath-metodissa), joka tallentaa lyhyimmän polun talteen shortestPath-muuttujaan.

Toteutus on samankaltainen, kuin [Held-Karpin algoritmissa](https://en.wikipedia.org/wiki/Held%E2%80%93Karp_algorithm), jonka aikavaativuus on O(n<sup>2</sup>2<sup>n</sup>).

## DtspMemo

Tämä tietorakenne on toteutettu DynamicTSP:n dtsp-metodin laskemien tulosten tallentamiseen ja niiden hakemiseen. Tulosten lisäksi jokaiseen start- ja remaining -pariin merkataan edeltävä solmu.

Tallentaessa dtsp-tulosta start- ja remaining -parille lasketaan hash-arvo, jonka perusteella tulostaulukon indeksi on haettavissa nopeasti. Hash-taulukon ja tulostaulukon koko lasketaan konstruktoriargumentissa annetun solmumäärän perusteella. Isoilla verkoilla käytetään suurinta mahdollista taulukon kokoa, jossa hash-arvon laskennassa ei vielä esiintynyt ongelmia (ylivuoto).

Törmäyksien hallinta on toteutettu linkittämällä, eli jokaisessa tulos-oliossa on osoitin (next) seuraavaan saman hash-arvon saaneeseen alkioon.


## ApproxTSP

Solmuissa eteneminen on toteutettu rekursiivisesti. Rekursio etenee aina lähimpään vierailemattomaan solmuun pitämällä samalla kirjaa kokonaismatkasta. Kun kaikki solmut on käyty läpi, tallennetaan tulos shortestPath-muuttujaan.

Reitti tallennetaan muistiin käyttämällä apuna *NodeList*-tietorakennetta, joka mm. toteuttaa add-metodin.

Laskettu polku ei siis missään nimessa kaikissa tapauksissa ole optimipolku, mutta algoritmi on nopea: jos n on solmujen lukumäärä, aikavaativuus on vain O(n²) (käytössä solmut läpi käyvä rekursio, jonka sisällä yksi jäljellä olevat solmut läpi käyvä silmukka).

Algoritmi toimii samalla tavalla kuin [nearest neighbor algorithm.](https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm)

## BranchTSP

Algoritmi perustuu peruuttavaan hakuun ja sitä nopeuttavaan branch-and-bound -tekniikkan.

Algoritmi käy järjestelmällisesti läpi kaikki yhdistelmät (O(n!)). Kuteinkin, sen hetkisen läpikäytävän polun pituus on kasvanut yli lyhyimmän siihen mennessä löydetyn polun, lopetetaan kyseisen haaran läpikäynti. Tämä (branch-and-bound) nopeuttaa laskentaa huomattavasti.

## NodeList

Yksinkertainen tietorakenne solmujulistojen tallentamiseen (käytössä ApproxTSP:ssä sekä BranchTSP:ssä). Tärkeimpänä metodit add (lisää solmun listan viimeiseksi) ja getLast (viimeisen solmun palautus). getPath palauttaa listan kokonaislukutaulukkona.

## UI

Sovelluksen käyttöliittymä on komentorivipohjainen. Ominaisuuksia on ajettavissa valikon kautta ja joitakin ominaisuuksia komentoriviargumentteja hyödyntäen suoraan.

Käyttöliittymään on toteutettu:
* verkkojen avaaminen tiedostosta
* verkkojen luominen ja tallenttaminen tiedostoon
    * verkkojen luonti käsin
    * satunnaisverkkojen automaattinen luominen
* algoritmien suoritus
    * algoritmien suoritusajan mittaus
    * lyhimmän polun, polun pituuden ja suoritusajan tulostus
* osoitekoordinaattejen perusteella suoritettava reitin laskenta
  * koordinaattien perusteella haetaan ajomatkojen etäisyysmatriisi ulkoisesta OSRM-rajapinnasta
  * etäisyysmatriisille ajetaan dynamicTSP ja tuloksesta ja koordinaateista generoidaan Google Maps-reittilinkki.
* suorituskykytesti satunnaisverkoilla
* testi ApproxTSP-algoritmin polun pituudelle suhteessa optimipolkuun

Käyttöliittymä on selkeyttä tavoitellen palasteltu useampaan tiedostoon /ui alle.

## I/O

UI:n tiedoston tallennus- ja avausominaisuutta varten on toteutettu yksinkertainen luokka, jolla voi tallentaa kaksiulotteisen liukulukutaulukon csv-tyyliseksi tiedostoksi ja avata csv-tiedoston kaksiulotteiseksi taulukoksi.

OsmrFetch hakee etäisyysmatriisin koordinaattien perusteella [OSRM](http://project-osrm.org/):n avoimesta rajapinnasta ja palauttaa sen kaksiulotteisena liukulukutaulukkona.
