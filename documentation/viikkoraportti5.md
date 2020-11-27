# Viikkoraportti 5
Tällä viikolla tuli aika intensiivisesti yritettyä saada dynamicTSP -algoritmia toimimaan taulukoinnin kanssa. Teinkin toimivan taulukoinnin dtsp-funktiolle, mutta huomasin, että taulukointia käytettäessä hukkuu myös tieto kuljetun reitin solmuista. Nykyisessä toteutuksessa kuljetun reitin solmujen kirjanpito säilyy funktion argumentissa - valitettavasti taulukointia käyttäessä funktio voi saada saman arvon "eri kautta kuljetulla rekursiolla", joten aikaisemmin "muistiin kirjatut" solmut ovat irrelevantteja ja niitä on turha taulukoida muistiin lyhintä reittiä varten.

Toisin sanoen kyseistä kaavaa käyttäen taulukoinnin kanssa on ainoastaan mahdollista ratkaista lyhyimmän TSP-reitin pituus, ei reitin solmuja. Aika paljon aikaa meni aikaa tämän toteamiseen.

Mutta ei hätä tämän näköinen! Nyt olisi tarkoitus tehdä algoritmi, jossa aluksi lasketaan (nopeaksi todetulla!) taulukoidulla dynaamisella algoritmilla lyhyimmän reitin pituus. Lyhyimmän reitin solmut lasketaan tämän jälkeen peruuttavalla haulla - tietoa lyhyimmän reitin pituudesta voi käyttää hyväksi branch-and-boundin ylärajana. 

Myös jäljellä olevien kulkemattomiin solmuihin yhteydessä oleviin kaarien summaa voisi kokeilla pitää yhtenä ylärajana. 

Taulukoinnissa lasketaan dtsp-funktion argumenteista start ja remaining hash-arvo, jonka perusteella haetaan tulos taulukosta. Törmäykset käsitellään linkitetyn listan tapaisesti.

Motivaationa olisi saada algoritmi toimimaan järkevässä ajassa vähintään 16 solmun verkossa.

Tällä viikolla siis saatu aikaan:

* Pään hakkaamista seinään
* BranchTSP aloitus (tähän siis tulossa taulukoituDynamic+branch-n-bound-haku)
* UI toimimaan branchillä ja ehkä turha ominaisuus säännöllisten verkkojen generoimiseen
* Vertaisarvionti


Ensi viikolla olisi siis tarkoitus:

* testaus verkoilla, jotka on jonkin muotoisia (approx-algoritmin vertailuja optimeihin tuloksiin, missä toimii, missä ei)
* grafiikkaa testidokumenttiin (suorituskyky)
* ominaisuus verkkojen tekemiseen katuosoitteiden avulla, aloitus/kartoitus
* yleinen parantelu

