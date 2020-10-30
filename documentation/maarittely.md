# Määrittelydokumentti


Tarkoitus on toteuttaa kauppamatkustajan ongelmaan (travelling salesperson problem, TSP)
ratkaisuja kolmella eri algoritmilla sekä toteuttaa näille suorituskykyvertailua erikokoisilla verkoilla. 

Kauppamatkustajan ongelmassa ratkaistaan usean eri solmun kautta kulkeva reitti niin,
että kuljetaan jokaisen verkon solmun kautta. Yhtenä käytännön sovelluksena on 
esimerkiksi jakeluauton reititys usean osoitteen kautta niin, että ajettu matka on
mahdollisimman pieni. Kauppamatkustajan ongelma on tunnettu NP-ongelma, eli sen aikavaatimus on polynominen.

Työ liittyy tietojenkäsittelytieteiden kandiohjelmaan ja se toteutetaan Javalla. Dokumentaatiokieli on suomi.


### Sovellukseen on tarkoitus toteuttaa:
* "Naivi algoritmi", jossa kokeillaan kaikki mahdolliset permutaatiot, joista lyhyin reitti valitaan
	* Tavoitteena aikavaativuusluokka **O(n!)**

* Algoritmi, jossa hyödynnetään dynaamista ohjelmointia
	* Tavoitteena aikavaativuusluokka **O(n²2<sup>n</sup>)**

* Minimivirittynyttä puuta (MST, Kruskal tai Prim) hyödyntävä approksimaatioalgoritmi
	* Tavoitteena aikavaativuusluokka **O(N²)**

Ohjelma saa syötteenä painotetun verkon. Verkon voi syöttää matriisina käsin tai lukea CSV-tiedostosta. Ohjelma palauttaa lyhimmän reitin solmujärjestyksen ja reitin kokonaispituuden.
Reitti päättyy samaan solmuun kuin lähtösolmu (closed loop).

Lisäksi, jos aikaa on riittävästi, työkalu verkkojen luomiseen katuosotteiden perusteella, käyttäen jotakin karttarajapintaa (esim. GraphHopper) osoitteiden välisten välimatkojen selvittämiseen.

Lähteet:

[https://en.wikipedia.org/wiki/Travelling_salesman_problem]

[https://www.thecrazyprogrammer.com/2017/05/travelling-salesman-problem.html]

[https://www.tutorialspoint.com/design_and_analysis_of_algorithms/design_and_analysis_of_algorithms_travelling_salesman_problem.htm]

[https://www.geeksforgeeks.org/travelling-salesman-problem-set-2-approximate-using-mst/?ref=rp]
