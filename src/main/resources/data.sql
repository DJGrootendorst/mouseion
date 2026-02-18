-- Voeg testschilderijen toe, let op: dit is testdata.

-- PAINTINGS
INSERT INTO painting (id, title, year, image) VALUES (1,'Sterrennacht', 1889, 'sterrennacht.jpg');
INSERT INTO painting (id, title, year, image) VALUES (2,'Mona Lisa', 1503, 'monalisa.jpg');
INSERT INTO painting (id, title, year, image) VALUES (3,'De Nachtwacht', 1642, 'nachtwacht.jpg');

-- PAINTERS
INSERT INTO painter (id, name, birth_year, death_year)
VALUES (1, 'Leonardo da Vinci', 1452, 1519);

INSERT INTO painter (id, name, birth_year, death_year)
VALUES (2, 'Rembrandt van Rijn', 1606, 1669);

INSERT INTO painter (id, name, birth_year, death_year)
VALUES (3, 'Vincent van Gogh', 1853, 1890);

-- EDUCATION CONTENT
INSERT INTO education_content (id, learning_goal, question, answer, created_at)
VALUES (1, 'Je kunt uitleggen waarom de Mona Lisa een typisch voorbeeld is van kunst uit de renaissance.', 'Waarom is de Mona Lisa een typisch renaissance kunstwerk?', 'Je ziet een uiterst realistische weergave van het gezicht en de handen van Mona Lisa.', NOW());

INSERT INTO education_content (id, learning_goal, question, answer, created_at)
VALUES (2, 'Je kunt uitleggen waarom de Nachtwacht een typisch voorbeeld is van kunst uit de Gouden Eeuw', 'Waarom is de Nachtwacht een typisch kunstwerk uit de Gouden Eeuw?', 'In het schilderij de Nachtwacht zie je de burgerij en schutterij.', NOW());

INSERT INTO education_content (id, learning_goal, question, answer, created_at)
VALUES (3, 'Je kunt uitleggen waarom de Sterrennacht een typisch voorbeeld is van een kunstwerk uit de moderne tijd.', 'Waarom is de Sterrennacht een typisch modern kunstwerk?', 'Je ziet in het schilderij dat emotie belangrijker is dan realisme.', NOW());

-- HISTORICAL PERIODS
INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (1, 1, 'De tijd van jagers en boeren', -2500000, -3001);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (2, 2, 'De tijd van Grieken en Romeinen', -3000, 499);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (3, 3, 'De tijd van monniken en ridders', 500, 999);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (4, 4, 'De tijd van steden en staten', 1000, 1499);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (5, 5, 'De tijd van Ontdekkers en Hervormers', 1500, 1599);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (6, 6, 'De tijd van Pruiken en Revoluties', 1600, 1699);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (7, 7, 'De tijd van Ontdekkers en Hervormers', 1700, 1799);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (8, 8, 'De tijd van Burgers en Stoommachines', 1800, 1899);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (9, 9, 'De tijd van wereldoorlogen', 1900, 1949);

INSERT INTO historical_period (id, period_number, name, first_year, last_year)
VALUES (10, 10, 'De tijd van televisie en computer', 1950, 2026);


-- CHARACTERISTIC ASPECTS --

-- HISTORICAL PERIOD 1
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (1, 1, 'de levenswijze van jager-verzamelaars', 1);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (2, 2, 'het ontstaan van landbouw en landbouwsamenlevingen', 1);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (3, 3, 'het ontstaan van de eerste stedelijke gemeenschappen', 1);

-- HISTORICAL PERIOD 2
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (4, 4, 'de ontwikkeling van wetenschappelijk denken en het denken over burgerschap en politiek in de Griekse stadstaat', 2);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (5, 5, 'de groei van het Romeinse imperium waardoor de Grieks-Romeinse cultuur zich in Europa verspreidde', 2);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (6, 6, 'de klassieke vormentaal van de Grieks-Romeinse cultuur', 2);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (7, 7, 'de confrontatie tussen de Grieks-Romeinse cultuur en de Germaanse cultuur van Noordwest-Europa', 2);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (8, 8, 'de ontwikkeling van het jodendom en het christendom als de eerste monotheïstische godsdiensten', 2);

-- HISTORICAL PERIOD 3
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (9, 9, 'het ontstaan en de verspreiding van de islam', 3);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (10, 10, 'de vrijwel volledige vervanging in West-Europa van de agrarisch-urbane cultuur door een zelfvoorzienende agrarische cultuur, georganiseerd via hofstelsel en horigheid', 3);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (11, 11, 'het ontstaan van feodale verhoudingen in het bestuur', 3);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (12, 12, 'de verspreiding van christendom in geheel Europa', 3);

-- HISTORICAL PERIOD 4
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (13, 13, 'de opkomst van handel en ambacht die de basis legde voor het herleven van een agrarisch-urbane samenleving', 4);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (14, 14, 'de opkomst van de stedelijke burgerij en de toenemende zelfstandigheid van steden', 4);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (15, 15, 'het begin van staatsvorming en centralisatie', 4);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (16, 16, 'het conflict in de christelijke wereld over de vraag of de wereldlijke dan wel de geestelijke macht het primaat behoorde te hebben', 4);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (17, 17, 'de expansie van de christelijke wereld, onder andere in de vorm van kruistochten', 4);

-- HISTORICAL PERIOD 5
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (18, 18, 'het veranderende mens- en wereldbeeld van de renaissance en het begin van een nieuwe wetenschappelijke belangstelling', 5);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (19, 19, 'de hernieuwde oriëntatie op het erfgoed van de klassieke oudheid', 5);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (20, 20, 'het begin van de Europese expansie overzee', 5);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (21, 21, 'de protestantse reformatie die de splitsing van de christelijke kerk in West-Europa tot gevolg had', 5);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (22, 22, 'het conflict in de Nederlanden dat resulteerde in de stichting van een Nederlandse staat', 5);

-- HISTORICAL PERIOD 6
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (23, 23, 'wereldwijde handelscontacten, handelskapitalisme en het begin van een wereldeconomie', 6);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (24, 24, 'de bijzondere plaats in staatkundig opzicht en de bloei in economisch en cultureel opzicht van de Nederlandse Republiek', 6);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (25, 25, 'het streven van vorsten naar absolute macht', 6);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (26, 26, 'de wetenschappelijke revolutie', 6);

-- HISTORICAL PERIOD 7
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (27, 27, 'rationeel optimisme en verlicht denken werd toegepast op alle terreinen van de samenleving: godsdienst, politiek, economie en sociale verhoudingen', 7);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (28, 28, 'voortbestaan van het ancién regime met pogingen om het vorstelijk bestuur op eigentijdse verlichte wijze vorm te geven (verlicht absolutisme)', 7);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (29, 29, 'de democratische revoluties in westerse landen met als gevolg discussies over grondwetten, grondrechten en staatsburgerschap', 7);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (30, 30, 'uitbouw van de Europese overheersing, met name in de vorm van plantagekoloniën en de daarmee verbonden transatlantische slavenhandel, en de opkomst van het abolitionisme', 7);

-- HISTORICAL PERIOD 8
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (31, 31, 'de industriële revolutie legde in de westerse wereld de basis voor een industriële samenleving', 8);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (32, 32, 'de opkomst van politiek-maatschappelijke stromingen: liberalisme, nationalisme, socialisme, confessionalisme en feminisme', 8);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (33, 33, 'voortschrijdende democratisering, met deelname van steeds meer mannen en vrouwen aan het politieke proces', 8);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (34, 34, 'de opkomst van emancipatiebewegingen', 8);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (35, 35, 'discussies over de sociale kwestie', 8);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (36, 36, 'de moderne vorm van imperialisme die verband hield met de industrialisatie', 8);

-- HISTORICAL PERIOD 9
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (37, 37, 'het voeren van twee wereldoorlogen', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (38, 38, 'de crisis van het wereldkapitalisme', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (39, 39, 'het in de praktijk brengen van totalitaire ideologieën communisme en fascisme/nationaal-socialisme', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (40, 40, 'de rol van moderne propaganda- en communicatiemiddelen en vormen van massaorganisatie', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (41, 41, 'vormen van verzet tegen het West-Europese imperialisme', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (42, 42, 'verwoestingen op niet eerder vertoonde schaal door massavernietigingswapens en de betrokkenheid van de burgerbevolking bij oorlogvoering', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (43, 43, 'racisme en discriminatie die leidden tot genocide, in het bijzonder op de joden', 9);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (44, 44, 'de Duitse bezetting van Nederland', 9);

-- HISTORICAL PERIOD 10
INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (45, 45, 'de dekolonisatie maakte een eind aan de westerse hegemonie in de wereld', 10);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (46, 46, 'de verdeling van de wereld in twee ideologische blokken in de greep van een wapenwedloop en de daaruit voortvloeiende dreiging van een atoomoorlog', 10);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (47, 47, 'de toenemende westerse welvaart die vanaf de jaren zestig van de 20e eeuw aanleiding gaf tot ingrijpende sociaal-culturele veranderingsprocessen', 10);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (48, 48, 'de eenwording van Europa', 10);

INSERT INTO characteristic_aspect (id, number, description, historical_period_id)
VALUES (49, 49, 'de ontwikkeling van pluriforme en multiculturele samenlevingen', 10);