Törp bányász app

Hozz létre egy alkalmazást, amivel törpöket, a bányászfelszereléseiket és mágikus rúnákat lehet kezelni! Számon tudjuk tartani, hogy melyik törpnek mennyi a termelékenység értéket, és hogy milyen tárgyai vannak. A tárgyak javítják a törp termelékenységét. A mágus törpök minden tárgyra mágikus rúnákat tudnak rajzolni, amelyekkel további bónusz adódik az adott tárgyhoz.

Három rétegű Spring Boot webalkalmazást készíts!

Az alkalmazásba beérkező HTTP kéréseket és azok tartalmát (akár az url részeként, akár a kérés törzsében érkeznek) logold ki INFO szinten! A log üzenetre formai követelmény nincs, az elvárás annyi, hogy légy következetes!

Third party eszközöket (modelmapper, lombok) használni lehet, de nem pontlevonás, ha anélkül oldjátok meg a feladatot.

Minden hibakezelést egy GlobalExceptionHandler nevű osztályban végezz el!

## Domain osztályok létrehozása 
#### Dwarf
- Szükséges fieldek: id (Long, szerver adja ki sorban), name (String, unique), productivity (int), items (List\<Item>)
- Állítsd be megfelelően a kapcsolatokat!

#### Item
- Szükséges fieldek: id (Long, szerver adja ki sorban), name (String), bonus (int), value (int), dwarf (Dwarf), runes (List\<Rune>)
- Állítsd be megfelelően a kapcsolatot!

#### Rune
- Szükséges fieldek: id (Long, szerver adja ki sorban), name (String), bonus (int), banned (boolean), item (Item)
- Állítsd be megfelelően a kapcsolatot!

#### POST /api/dwarves

DTO-ban a következő információk érkeznek:
- név (String, nem lehet null, üres String vagy csak whitespace karakter)
- termelékenység (Integer, nem lehet null)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet, valamint 400-as hibakódot!

Ha a megadott névvel már létezik törpe az adatbázisban, akkor küldj vissza ugyanúgy 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

Sikeres mentés esetén küldd vissza az elmentett törp id, név és termelékenység értékeit, valamint 201-es kódot!

#### POST /api/items

DTO-ban a következő információk érkeznek:
- név (String, nem lehet null, üres String vagy csak whitespace karakter)
- bónusz (Integer, nem lehet null)
- érték (Integer, nem lehet null)
- törp id (Integer, nem lehet null)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet, valamint 400-as hibakódot!

Ha a megadott id-nak megfelelő törp nem található a rendszerben, akkor küldj vissza ugyanúgy 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

Sikeres mentés esetén küldd vissza az elmentett tárgy id-ját, nevét, bónuszát, értékét és a törpéjének a nevét, valamint 201-es státuszkódot!

#### PUT /api/dwarves/{id}

A kérés hatására az alkalmazás megkeresi a megadott id-hoz tartozó törpöt, és növeli az összes tárgyának a bónusz értékét eggyel.

Sikeres módosítás esetén az alkalmazás küldje vissza a törp adatait (id, név, termelékenység, tárgyak listája) valamint egy 202-es státuszkódot!

Ha a megadott id-nak megfelelő törp nem található a rendszerben, akkor küldj vissza 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

#### PUT /api/items/{itemId}/dwarf/{dwarfId}

A kérés hatására az alkalmazás megkeresi az adott itemId-hoz tartozó tágyat, készít belőle egy másolatot, és elmenti az adott dwarfId-val rendelkező törpéhez. Ez lehet ugyanaz a törp is, mint az eredeti tárgy tulajdonosa. Az újonnan kovácsolt tárgyra egy mágus törp rárajzolja ugyanazokat a mágikus rúnákat is.

Sikeres létrehozás esetén az alkalmazás küldje vissza az új tárgy id-ját, nevét, bónuszát, értékét, a törpéjének a nevét valamint egy 201-es státuszkódot!

Ha a megadott itemId-nak megfelelő tárgy nem található a rendszerben, akkor küldj vissza 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

Ha a megadott dwarfId-nak megfelelő törp nem található a rendszerben, akkor küldj vissza 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

#### GET /api/dwarves/days

A kérés hatására az alkalmazás visszaad egy számot (egészre kerekítve felfele), amely megmutatja, hogy a jelenlegi törpök a jelenlegi tárgyaikkal hány nap alatt tudnak kitermelni egy millió egység aranyat.

Egy törpe termelékenysége azt jelzi, hogy egy nap alatt hány egység aranyat bányászik ki. Ehhez az értékhez hozzáadódnak a tárgyainak bónusz értékei. A tárgyak bónuszát tovább növelik a rajta lévő mágikus rúnák.

Amennyiben a bányában egy törpe sem dolgozik, akkor küldj vissza 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

Ezek alapján számold ki a szükséges napszámot, majd azt add vissza a válaszban 200-as státuszkóddal!

#### GET /api/dwarves/best

A kérés hatására az alkalmazás visszaadja annak a törpének a nevét, akinek
- a legnagyobb a termelékenység értéke (bónuszok nélkül!),
- és a tárgyai között van olyan, amelynek az ára az összes létező tárgy átlagos áránál kisebb.

Több ilyen törp is lehet!

A neveket egy JPQL lekérdezés adja vissza (!), ezek kerülnek a válaszba 200-as státuszkóddal.

#### DELETE /api/runes

Időről időre a mágus törpök felülvizsgálják a rúnákat, és amelyeket kockázatosnak találják, azokat betiltják, és leszedik az összes tárgyról.

A kérés hatására az alkalmazás megkeresi az összes rúnát, melynek a neve megegyezik a kérésben kapott Stringgel, majd logikai törlést hajt végre. (Tiltottnak jelzi a rúnát, valamint törli a rúna és a tárgy közötti kapcsolatot.)

Amennyiben a megadott névvel nem található rúna a rendszerben, akkor küldj vissza 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

Sikeres törlés esetén a 200-as státuszkód mellett egy szöveget kell visszaadni, amely tartalmazza a törölt rúnák darabszámát is. Például ha 2 rúna lett bannolva akkor a következő szöveget kell visszaadni: "Banned 2 rune(s)!"

#### GET /api/runes

A kérés hatására az alkalmazás visszaadja az azonos nevű rúnák átlagos bónusz értékeit.

A JPQL lekérdezés feltételei:
- egy dto-val tér vissza, amely tartalmazza az egyedi rúna neveket, és a hozzájuk tartozó átlagos bónuszokat
- csak azokat a rúnákat vizsgálja, amit nincsenek tiltva, és amik tárgyhoz vannak kötve
- az eredményeknek rúna név szerint csökkenő sorrendben kell lenniük

Amennyiben a lekérdezés nem ad vissza eredményt, akkor küldj vissza 400-as hibakódot és hibaüzenetet! Ehhez használj saját kivételt!

A válaszban a dto lista mellett adjunk vissza 200-as státuszkódot!
