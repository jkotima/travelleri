# Toteutus

## NaiveTSP

Algoritmin naivissa toteutuksessa generoidaan aluksi kaikki mahdolliset etenimisjärjestykset solmuissa niin, että polku alkaa solmusta 0 ja kulkee kaikkien solmujen kautta. Tämän jälkeen näiden permutaatioiden mukaiset polut käydään läpi, laskien samalla ko. polusta syntyvä matka. Lyhin löydetty polku ja sen permutaatio tallennetaan oliomuuttujiin.

Kun n on solmujen lukuäärä, permutaatioden generointi vie aikaa O(n!) (permutaatioiden lukumäärä) ja reittien läpikäyminen O(n!*n). Aikavaativuus on siis luokkaa O(n!), joten algoritmi on todella hidas.

Turhaa työtä ja muistin käyttöä lisää myös permutaatioiden tallentaminen aluksi muistiin. 

## DynamicTSP

Dynaamisessa toteutuksessa hyödynnetää lyhimmän polun palauttavaa rekursiivista esitystä
```
dtsp(start, {remaining}) = min(graph[start][k] + dtsp(k, {remaining}-{k})),
jossa k ∈ {remaining}.
```

Esimerkiksi, jos verkossa on solmut {0,1,2}, toimii rekursio seuraavalla tavalla palauttaen lyhimmän TSP-polun:
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


(graph[x][y] on etäisyys solmujen x ja y välillä)
(min() palauttaa pienimmän arvon)
```
Toteutetussa algoritmissa rekursiivisen metodin parametreina on lisäksi sen hetkinen polku ja polun pituus, jotka tallennetaan oliomuuttujiksi rekursion päätyttyä, mikäli löydetty polku on lyhin.

Yhden dtsp-metodin laskeminen vie O(n). 
Rekursio (O(n)) toistetaan {remaining} osajoukkojen verran, joka vie aikaa O(2<sup>n</sup>). Aikavaativuus on siis O(n²2<sup>n</sup>).


## I/O

## UI


