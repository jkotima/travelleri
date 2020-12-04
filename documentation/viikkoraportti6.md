# Viikkoraportti 6
Sain DynamicTSP:n toimimaan taulukoinnin kanssa, joten suunnitelmat meni vähän uusiksi viimeviikon ajatuksista. Ratkaisu löytyi tutustumalla hieman tarkemmin Held-Karpin algoritmiin ja sen pseudokoodiin.

Omassa toteutuksessa edeltävät solmut tallennetaan nyt DtspMemoon muun tuloksen yhteyteen, josta lyhimmän polun voi sitten dtsp:n ajamisen jälkeen hakea updateShortestPath-metodilla.

DtspMemlla tallennetaan dtsp:n tulokset hash-tietorakenteen avulla.

Alkuperäinen suunnitelma nopeimmaksi algoritmiksi reitin löytämiseen oli käyttää peruuttavaa hakua, joka hyödyntäisi branch-and-bound-ylärajana dtsp:n laskemaa lyhyintä polkua. Tämä ei ollutkaan kovin nopea algoritmi ja koska sain reitin taulukoinnin toimimaan DynamicTSP:hen, hylkäsin tämän idean.

Koska tällainen peruuttavan haun ratkaisu tuli koodailtua, niin päätin sen kuitenkin jättää projektiin omaksi algoritmikseen - kuitenkin sellaisena, joka toimii aivan itsenäisesti, ilman dtsp:tä. Branch-and-bound ylärajana toimii sen hetkinen löydetyn lyhyimmän polun pituus.

DtspMemon lisäksi uutena tietorakenteena projektiin tulla tupsahti NodeList, jota BranchTSP ja ApproxTSP nyt käyttävät taulukon kopionnin sijasta. Tämä selkeyttää ainakin koodia, suorituskykyyn tällä uudistuksella ei ollut hirveästi vaikutusta.

Testejä tuli myös tehtyä ja niiden dokumentointia. Ui:sta voi nyt suorittaa suoraan suorituskykytestisarjoja. Dokumentointia tuli muutenkin päiviteltyä sekä vertaisarviointi annettua.

Ensi viikolla olisi ainakin tarkoitus:

* ApproxTSP:n polun käyttökelpoisuuteen liittyviä testejä
* ominaisuus verkkojen tekemiseen katuosoitteiden avulla, aloitus/kartoitus
* yleinen parantelu

### Käytetyt tunnit
n. 5+10+8+10=33h yhteensä





