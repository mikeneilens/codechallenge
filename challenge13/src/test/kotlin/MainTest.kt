import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {

    private val peterJones = Shop("Peter Jones", "SW3", GeoLocation(51.492246,-0.159 ))
    private val oxfordStreet = Shop("Oxford Street", "W1", GeoLocation(51.524565, -0.112042))
    private val liverpool = Shop("Liverpool", "L1", GeoLocation(53.403799, -2.987648))
    private val headOffice = Shop("Victoria 171", "SW1E 5NN",GeoLocation(51.496466,-0.141499))

    private val peterJonesAsString = "Peter Jones,SW3,51.492246,-0.159"
    private val peterJonesAndLiverpoolAsString = "Peter Jones,SW3,51.492246,-0.159,Liverpool,L1,53.403799,-2.987648"
    private val peterJonesHeadOfficeAndLiverpoolAsString = "Peter Jones,SW3,51.492246,-0.159," +
            "Victoria 171,SW1E 5NN,51.496466,-0.141499," +
            "Liverpool,L1,53.403799,-2.987648"
    private val peterJonesAndReykjavikAsString = "Peter Jones,SW3,51.492246,-0.159,Reykjavik,L1,64.153,-21.895"

    @Test
    fun `Empty string converts to an empty list of shops`() {
        val emptyString = ""
        assertEquals(listOf<Shop>(), emptyString.toShops() )
    }

    @Test
    fun `string containing data for one shop converts to a list of one shop`() {
        val stringForOneShop = peterJonesAsString
        val shops =  stringForOneShop.toShops()
        assertEquals(1, shops.size )
        assertEquals(peterJones, shops[0] )
    }

    @Test
    fun `string containing an invalid number of elements returns empty list of shops`() {
        val stringForOneShop = "Peter Jones,SW3,51.492246"
        val shops =  stringForOneShop.toShops()
        assertEquals(listOf<Shop>(), shops )
    }

    @Test
    fun `string containing invalid data for lng and lat returns an empty list`() {
        val stringForOneShop = "Peter Jones,SW3,xxx,yyy"
        val shops =  stringForOneShop.toShops()
        assertEquals(listOf<Shop>(), shops )
    }

    @Test
    fun `string containing data for two shops converts to a list of two shops`() {
        val stringForOneShop = peterJonesAndLiverpoolAsString
        val shops =  stringForOneShop.toShops()
        assertEquals(2, shops.size )
        assertEquals(peterJones, shops[0] )
        assertEquals(liverpool, shops[1] )
    }

    @Test
    fun `distance between Liverpool and London should be 177`() {
        val londonLocation = oxfordStreet.geoLocation
        val liverpoolLocation = liverpool.geoLocation
        val distanceBetweenLiverpoolAndLondon = londonLocation.distanceTo(liverpoolLocation).toInt()

        assertEquals(177, distanceBetweenLiverpoolAndLondon)
    }

    @Test
    fun `distance between Peter Jones location and Oxford Street location should be 3 miles`() {
        val peterJonesLocation = peterJones.geoLocation
        val oxfordStreetLocation = oxfordStreet.geoLocation
        val distanceBetweenPeterJonesAndOxfordStreet = peterJonesLocation.distanceTo(oxfordStreetLocation).toInt()

        assertEquals(3, distanceBetweenPeterJonesAndOxfordStreet)
    }

    @Test
    fun `distance to same shop is zero `() {
        val distanceToSamePlace = peterJones.distanceTo(peterJones)
        assertEquals(0.0,distanceToSamePlace)
    }

    @Test
    fun `distance between Peter Jones shop and Oxford Street shop is roughly 3 miles `() {
        val distanceBetweenPeterJonesAndOxfordStreet = peterJones.distanceTo(oxfordStreet).toInt()
        assertEquals(3,distanceBetweenPeterJonesAndOxfordStreet)
    }

    @Test
    fun `Re-ordering an empty list of shops creates an empty list of shops`() {
        val emptyListOfShops = emptyList<Shop>()

        assertEquals(emptyList<Shop>(), emptyListOfShops.createRoute())
    }

    @Test
    fun `Re-ordering a list of one shop returns a list of one shop`() {
        val listOfOneShop = listOf(oxfordStreet)

        assertEquals(1, listOfOneShop.createRoute().size)
        assertEquals(oxfordStreet, listOfOneShop.createRoute().first())
    }

    @Test
    fun `Re-ordering a list of two shops returns a list of two shop`() {
        val listOfTwoShop = listOf(headOffice, oxfordStreet)
        val orderedShops = listOfTwoShop.createRoute()

        assertEquals(2, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(oxfordStreet, orderedShops[1])
        assertEquals(2, orderedShops[1].distanceFromLastShop.toInt())

    }

    @Test
    fun `Re-ordering a list of three shops in correct order returns a list of three shops`() {
        val listOfThreeShop = listOf(headOffice, peterJones, oxfordStreet)
        val orderedShops = listOfThreeShop.createRoute()

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(0, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
    }

    @Test
    fun `Re-ordering a list of three shops in incorrect order returns a list of three shops`() {
        val listOfThreeShop = listOf(headOffice, oxfordStreet, peterJones)
        val orderedShops = listOfThreeShop.createRoute()

        assertEquals(3, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(0, orderedShops[1].distanceFromLastShop.toInt())
        assertEquals(3, orderedShops[2].distanceFromLastShop.toInt())
    }

    @Test
    fun `Re-ordering a list of four shops in a random order returns shops in the correct order`() {
        val listOfFourShop = listOf(headOffice, liverpool, peterJones, oxfordStreet)

        val orderedShops = listOfFourShop.createRoute()

        assertEquals(4, orderedShops.size)
        assertEquals(headOffice, orderedShops[0])
        assertEquals(peterJones, orderedShops[1])
        assertEquals(oxfordStreet, orderedShops[2])
        assertEquals(liverpool, orderedShops[3])
    }

    @Test
    fun `closest shop is null if the list of new shops is empty `() {
        val listOfNewShops = emptyList<Shop>()
        val listOfAllShops = emptyList<Shop>()

        assertEquals(null, findClosestShop(listOfAllShops, listOfNewShops))
    }
    @Test
    fun `closest shop is null if the list of all shops is empty `() {
        val listOfNewShops = listOf(headOffice, liverpool, peterJones)
        val listOfAllShops = emptyList<Shop>()

        assertEquals(null, findClosestShop(listOfAllShops, listOfNewShops))
    }
    @Test
    fun `closest shop to peter jones is head office `() {
        val listOfNewShops = listOf(peterJones)
        val listOfAllShops = listOf(peterJones, liverpool, headOffice, oxfordStreet)

        assertEquals(headOffice, findClosestShop(listOfAllShops, listOfNewShops))
    }
    @Test
    fun `liverpool is the last shop on the new list of shops then closest shop is oxford street `() {
        val listOfNewShops = listOf(peterJones, liverpool)
        val listOfAllShops = listOf(peterJones, liverpool, headOffice, oxfordStreet)

        assertEquals(oxfordStreet, findClosestShop(listOfAllShops, listOfNewShops))
    }

    @Test
    fun `journey time is zero if there are no shops`() {
        assertEquals(0, calculateJourneyTime(""))
    }

    @Test
    fun `journey time is zero if there is one shop in the string`() {
        assertEquals(0, calculateJourneyTime(peterJonesAsString))
    }

    @Test
    fun `journey time is calculated correctly if there is two shops in the string that are reachable in the time allowed`() {
        assertEquals(21379, calculateJourneyTime(peterJonesAndLiverpoolAsString))
    }
    @Test
    fun `journey time is zero if there is two shops in the string that are not reachable in the time allowed`() {
        assertEquals(0, calculateJourneyTime(peterJonesAndReykjavikAsString))
    }
    @Test
    fun `journey time is calculated correctly if there is three shops in the string that are reachable in the same day`() {
        assertEquals(21509, calculateJourneyTime(peterJonesHeadOfficeAndLiverpoolAsString))
    }

    @Test
    fun `journey time is calculated correctly if there is three shops in the string that are not all reachable in the same day`() {
        assertEquals(71909, calculateJourneyTime(peterJonesHeadOfficeAndLiverpoolAsString, 21413))
    }

    @Test
    fun `journey time is calculated correctly if array of shops is empty`(){
        assertEquals(0, listOf<Shop>().calculateJourneyTime())
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains one shop`(){
        assertEquals(0, listOf(Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)).calculateJourneyTime())
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains two shops`(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),30.0) //30 miles will take 1 hour or 3600 seconds to reach

        assertEquals(3600, listOf(shop1, shop2).calculateJourneyTime())
    }
    @Test
    fun `journey time is calculated as zero if array of shops contains two shops with shop too far away to reach`(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),30.0)

        assertEquals(0, listOf(shop1, shop2).calculateJourneyTime(1800))
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains three shops all reachable in same day`(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),30.0)
        val shop3 = Shop("shop3","pc3",GeoLocation(1.0,2.0),30.0)

        assertEquals(7200, listOf(shop1, shop2, shop3).calculateJourneyTime())
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains three shops but not all reachable in same day`(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),30.0)
        val shop3 = Shop("shop3","pc3",GeoLocation(1.0,2.0),30.0)
        val timeAllowedEachDay = 3600
        val timeRequiredForBreak = 10000
        assertEquals(3600 + timeRequiredForBreak + 3600, listOf(shop1, shop2, shop3).calculateJourneyTime(timeAllowedEachDay,timeRequiredForBreak))
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains four shops that will take 3 days to get around `(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),30.0)
        val shop3 = Shop("shop3","pc3",GeoLocation(1.0,2.0),30.0)
        val shop4 = Shop("shop4","pc4",GeoLocation(1.0,2.0),30.0)
        val timeAllowedEachDay = 3600
        val timeRequiredForBreak = 10000
        assertEquals(3600 + timeRequiredForBreak + 3600 + timeRequiredForBreak + 3600, listOf(shop1, shop2, shop3, shop4).calculateJourneyTime(timeAllowedEachDay,timeRequiredForBreak))
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains many shops `(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),15.0)
        val shop3 = Shop("shop3","pc3",GeoLocation(1.0,2.0),30.0)//This will require time for a break before it
        val shop4 = Shop("shop4","pc4",GeoLocation(1.0,2.0),15.0)//This will require time for a break before it
        val shop5 = Shop("shop5","pc5",GeoLocation(1.0,2.0),15.0)
        val shop6 = Shop("shop6","pc6",GeoLocation(1.0,2.0),15.0)//This will require time for a break before it
        val timeAllowedEachDay = 3600
        val timeRequiredForBreak = 10000
        assertEquals(1800 + timeRequiredForBreak + 3600 + timeRequiredForBreak + 1800 + 1800 + timeRequiredForBreak + 1800, listOf(shop1, shop2, shop3, shop4, shop5, shop6).calculateJourneyTime(timeAllowedEachDay,timeRequiredForBreak))
    }
    @Test
    fun `journey time is calculated correctly if array of shops contains many shops with one in the middle unreachable `(){
        val shop1 = Shop("shop1","pc1",GeoLocation(1.0,2.0),0.0)
        val shop2 = Shop("shop2","pc2",GeoLocation(1.0,2.0),15.0)
        val shop3 = Shop("shop3","pc3",GeoLocation(1.0,2.0),30.0)//This will require time for a break before it
        val shop4 = Shop("shop4","pc4",GeoLocation(1.0,2.0),1500.0)
        val shop5 = Shop("shop5","pc5",GeoLocation(1.0,2.0),15.0)
        val shop6 = Shop("shop6","pc6",GeoLocation(1.0,2.0),15.0)
        val timeAllowedEachDay = 3600
        val timeRequiredForBreak = 10000
        assertEquals(1800 + timeRequiredForBreak + 3600 , listOf(shop1, shop2, shop3, shop4, shop5, shop6).calculateJourneyTime(timeAllowedEachDay,timeRequiredForBreak))
    }

    @Test
    fun `Test with the data from the challenge`() {
        val allShopData = "Abergavenny,NP7 9LL,-3.028245,51.818294,Abergavenny Petrol Station,NP7 9LL,-3.028245,51.818294,Abingdon,OX14 3HL,-1.279705,51.672083,Addlestone,KT15 2GL,-0.491506,51.370886,Admiral Park (Guernsey),GY1 2AL,,,Alcester,B49 5DA,-1.870244,52.216453,Alderley Edge,SK9 7JX,-2.236853,53.302691,Allington Park,ME16 0PX,0.497457,51.286182,Alton,GU34 2WT,-0.967194,51.152833,Altrincham,WA14 5ZL,-2.364468,53.405692,Amersham,HP6 5DR,-0.606307,51.67757,Ampthill,MK45 2LU,-0.493385,52.032506,Andover,SP10 1LY,-1.481229,51.208503,Ashbourne,DE6 1GD,-1.730621,53.016451,Ashford,TN23 3RT,0.856462,51.155224,Aylesbury,HP20 1AL,-0.807729,51.814595,Bagshot,GU19 5DH,-0.699842,51.355975,Bagshot Road,RG12 9SE,-0.747918,51.405228,Balham,SW12 9BN,-0.151792,51.444994,Banbury,OX16 2FW,-1.340086,52.065689,Banstead,SM7 2NB,-0.198596,51.322955,Barbican,EC1Y 8NX,-0.092363,51.521846,Barnet,EN5 5XY,-0.203261,51.655374,Barry,CF63 2PE,-3.239174,51.414386,BASINGSTOKE,RG21 4BF,-1.081862,51.268043,Bath,BA1 5AL,-2.358584,51.383386,Battersea,SW8 4LT,-0.148161,51.477121,BATTERSEA NINE ELMS,SW11 7DN,-0.134422,51.482165,Bayswater,W2 6ES,-0.187706,51.517146,Beaconsfield,HP9 2PW,-0.646524,51.61222,Beckenham,BR3 1SD,-0.023902,51.412377,Bedford,MK41 0GX,-0.420151,52.144362,Belgravia,SW1X 8GG,-0.1571,51.498997,Berkhamsted,HP4 1HS,-0.566618,51.761858,Biggin Hill,TN16 3BB,0.038489,51.308348,Billericay,CM12 9BY,0.416548,51.623979,Birchanger Services,CM23 5QZ,0.194809,51.871647,Bishops Stortford,CM23 2BA,0.159865,51.872978,Blaby,LE8 4DJ,-1.163808,52.574867,Bloomsbury,WC1N 1AF,-0.123529,51.524273,Brackley,NN13 6BE,-1.150962,52.026807,Bracknell,RG12 1RQ,-0.752195,51.418293,Brent Cross,NW4 3FQ,-0.223792,51.576416,Bridport,DT6 3QP,-2.759893,50.733657,Brighton,BN1 2LA,-0.151267,50.824953,Bromley,BR1 5AJ,0.021064,51.421089,Bromley South,BR2 9HD,0.018149,51.398999,Bromsgrove,B61 8RR,-2.064403,52.333078,Broxbourne,EN10 6PY,-0.02222,51.738505,Buckhurst Hill,IG9 5EP,0.044986,51.625447,Buckingham,MK18 1RS,-0.985353,52.000708,Burgess Hill,RH15 9NP,-0.131244,50.955078,Burgh Heath,KT20 6SU,-0.211888,51.271217,Burnt Common,GU23 7JY,-0.512846,51.282099,Bury St Edmunds,IP33 3DH,0.707987,52.243416,Buxton,SK17 6DF,-1.911921,53.259595,Byres Road,G12 8AU,-4.291378,55.877402,Caldicot,NP26 4BR,-2.754474,51.589495,Cambridge,CB2 9LT,0.111739,52.169486,Canary Wharf,E14 5EW,-0.016226,51.504577,Canary Wharf,E14 5EW,-0.016226,51.504577,Canterbury,CT1 1UL,1.086042,51.276294,Cardiff Gate Services,CF23 8RP,-3.130627,51.538673,Caterham,CR3 6LB,-0.077885,51.281935,Caversham,RG4 8AY,-0.973645,51.467252,Chandlers Ford,SO53 2LG,-1.373075,50.986891,Charnock Richard (Services),PR7 5LR,-2.692571,53.629996,Cheadle Hulme,SK8 7AE,-2.186789,53.37353,Cheam,SM3 8BD,-0.216378,51.358936,Cheltenham,GL50 3QW,-2.083824,51.901938,Cheltenham Petrol Station,GL50 3QW,-2.083824,51.901938,Chesham,HP5 1DR,-0.611018,51.707002,Chester,CH3 5AF,-2.879014,53.192461,Chichester,PO19 1RD,-0.785585,50.832334,Chippenham,SN15 3WL,-2.116938,51.458394,Chipping Sodbury,BS37 6AU,-2.396312,51.540449,Chiswick,W4 5TA,-0.262436,51.492629,Christchurch,BH23 1QD,-1.779396,50.736503,Cirencester,GL7 1SZ,-1.971251,51.713416,Clapham Common,SW4 0HY,-0.139259,51.462044,Clapham Junction,SW11 1PW,-0.167921,51.462774,Clerkenwell,EC1V 4DE,-0.102786,51.524254,Clifton,BS8 1QS,-2.608449,51.457131,Cobham,KT11 1AF,-0.413945,51.330107,Colchester,CO4 3EQ,0.921191,51.893701,Comely Bank,EH4 1AW,-3.219402,55.959311,Cookery School (Finchley Road),NW3 6NN,-0.179884,51.546563,Cookery School (Kings Cross),N1C 4BZ,-0.123462,51.536153,Cookery School (Salisbury),SP2 7TS,-1.801853,51.07606,Corley Services,CV7 8NR,-1.546728,52.471822,Coulsdon,CR5 2NB,-0.136553,51.322289,Coulsdon DFC,CR5 2HR,-0.133792,51.317101,Cowbridge,CF71 7FB,-3.448324,51.461382,Crewkerne,TA18 8DA,-2.79436,50.882896,Crouch End,N8 8DU,-0.124119,51.578992,Crowborough,TN6 1DL,0.161938,51.058455,Croydon,CR0 1LG,-0.096194,51.374536,Daventry,NN11 4DR,-1.161736,52.257225,Dibden,SO45 6AG,-1.398446,50.870132,Dorchester,DT1 1BN,-2.436197,50.714339,Dorking,RH4 2HQ,-0.33371,51.231297,Droitwich,WR9 8LB,-2.145602,52.268784,Ealing,W5 3HJ,-0.290845,51.516077,East Cowes,PO32 6SP,-1.288424,50.758164,East Grinstead,RH19 4YZ,-0.010925,51.123635,East Putney,SW15 2SP,-0.211879,51.459909,East Sheen,SW14 7JG,-0.269248,51.464367,Eastbourne,BN21 1HR,0.267739,50.772774,Edenbridge,TN8 5LN,0.064284,51.195339,Edgware Road,W2 2DS,-0.166246,51.517727,Egham,TW20 9HZ,-0.546661,51.431742,Eldon Square,NE1 7JD,-1.61528,54.975017,Ely,CB7 4QJ,0.268705,52.400894,Enfield,EN2 6SN,-0.083494,51.651256,Enfield Chase,EN2 7AS,-0.092482,51.653416,Epsom,KT18 5DB,-0.268622,51.332158,Esher,KT10 9QE,-0.366962,51.36812,Evesham,WR11 4BD,-1.950348,52.092061,Exeter,EX1 2ED,-3.514024,50.724387,Faringdon,SN7 7GQ,-1.575547,51.650249,Farnham,GU9 7HD,-0.802169,51.21551,Finchley,N12 8NR,-0.179779,51.611324,Finchley Central,N3 2LN,-0.194666,51.600227,Fitzroy Street,CB1 1EW,0.130594,52.206608,Fleet,GU51 3LA,-0.841504,51.280883,Fleet South Services,GU51 1AA,-0.855807,51.295546,Formby,L37 4AJ,-3.058383,53.555466,Four Oaks,B74 4AB,-1.831773,52.587639,Frimley,GU16 7JD,-0.744138,51.314419,Fulham,SW6 1LX,-0.199502,51.480312,Fulham Palace Road,W6 8QX,-0.219542,51.484188,Gerrards Cross,SL9 8ES,-0.551109,51.588217,Gillingham,SP8 4UA,-2.275914,51.035823,Gloucester Road,SW7 4SF,-0.183082,51.494519,Godalming,GU7 1HY,-0.60946,51.186416,Goldsworth Park,GU21 3LG,-0.590697,51.318796,Gordano Services,BS20 7XG,-2.707929,51.477507,Gosport,PO12 1SD,-1.135492,50.792626,Great Malvern,WR14 4WR,-2.328429,52.112595,Green Street Green,BR6 6BG,0.089996,51.354366,Greenwich,SE10 9DD,-0.014331,51.48217,Gretna Green (Services),DG16 5HQ,-3.084017,55.010746,Guildford,GU1 4AT,-0.573916,51.238307,Guildford Worplesdon Road,GU2 9UY,-0.594269,51.256982,Hailsham,BN27 1BE,0.260073,50.864077,Hall Green,B28 9EF,-1.842187,52.428899,Hampton,TW12 2HQ,-0.373641,51.415603,Harborne,B17 9PP,-1.94908,52.458591,Harpenden,AL5 2TJ,-0.357144,51.815685,Harrogate,HG1 1HD,-1.536241,53.989854,Harrow Weald,HA3 6HF,-0.339606,51.60534,Hartshead Moor (Services),HD6 4JX,-1.74646,53.714327,Haslemere,GU27 2AB,-0.710321,51.089048,Havant,PO9 1PR,-0.981213,50.851832,Hawkhurst,TN18 4JB,0.51149,51.046997,Haywards Heath,RH16 1DJ,-0.104735,51.005716,Hazlemere,HP15 7LG,-0.713128,51.651732,Headington,OX3 9HP,-1.211112,51.761394,Headington - London Road,OX3 7RD,-1.213994,51.759531,Heathfield,TN21 8DF,0.253409,50.968052,Helensburgh,G84 7LB,-4.704267,55.994132,Helensburgh Petrol Station,G84 7LB,-4.704267,55.994132,Henley,RG9 2BA,-0.905023,51.538566,Hereford,HR4 9HT,-2.718586,52.058733,Hersham,KT12 4HL,-0.3991,51.365218,Hexham,NE46 3PJ,-2.098948,54.972753,High Holborn,WC1V 7EX,-0.11878,51.517425,High Wycombe,HP11 1TJ,-0.760498,51.615115,Highbury Corner,N5 1RD,-0.103742,51.546706,Hitchin,SG5 1HF,-0.283031,51.950465,Holloway Road,N7 6PA,-0.115986,51.555805,Holsworthy,EX22 6BL,-4.353758,50.807192,Hopwood Park Petrol Filling Station (ser,B48 7AU,-1.945766,52.363718,Hopwood Services,B48 7AU,-1.945766,52.363718,Horley,RH6 7PZ,-0.162757,51.171298,Horley - Brighton Road,RH6 7JU,-0.163675,51.180569,Horsham New,RH12 1LP,-0.335675,51.063106,Horticulture Direct Services,RG12 8YA,-0.770445,51.411734,Hove,BN3 7PZ,-0.176393,50.843695,Hythe,CT21 5NH,1.088941,51.071992,Ipswich,IP3 9SQ,1.201301,52.034026,Ipswich (Corn Exchange),IP1 1AS,1.152467,52.057472,Islington,N1 0RW,-0.107122,51.533778,Jesmond,NE2 2AN,-1.604258,54.99166,JL Foodhall Bluewater,DA9 9SA,0.271536,51.440441,JL Foodhall Oxford Street,W1C 1DX,-0.145109,51.515057,John Barnes,NW3 6NN,-0.179884,51.546563,John Lewis Aberdeen,AB25 1BW,-2.100636,57.150079,John Lewis at home Ashford,TN25 4DT,0.860765,51.158204,John Lewis at home Basingstoke,RG21 4BF,-1.081862,51.268043,John Lewis at home Chester,CH1 4QG,-2.914358,53.196933,John Lewis at home Chichester,PO19 7YH,-0.757297,50.841461,John Lewis at home Croydon,CR0 4XJ,-0.118968,51.372081,John Lewis at home Horsham,RH12 1LP,-0.335675,51.063106,John Lewis at home Ipswich,IP3 9SQ,1.201301,52.034026,John Lewis at home Newbury,RG14 1AY,-1.323382,51.403953,John Lewis at home Poole,BH12 1DN,-1.920832,50.728924,John Lewis at home Swindon,SN5 8WA,-1.812896,51.554305,John Lewis at home Tamworth,B78 3HD,-1.705797,52.627129,John Lewis at home Tunbridge Wells,TN2 3UP,0.29556,51.159704,John Lewis Birmingham,B2 4AU,-1.899766,52.476917,John Lewis Bluewater,DA9 9SA,0.271536,51.440441,John Lewis Brent Cross,NW4 3FL,-0.223792,51.576416,John Lewis Cambridge,CB2 3DS,0.122693,52.203626,John Lewis Cardiff,CF10 1EG,-3.173921,51.477903,John Lewis Cheadle,SK8 3BZ,-2.214799,53.375181,John Lewis Chelmsford,CM1 1GD,0.475947,51.734631,John Lewis Cheltenham,GL50 1DQ,-2.072513,51.89994,John Lewis Cribbs Causeway,BS34 5QU,-2.597706,51.526226,John Lewis Exeter,EX4 6NN,-3.525637,50.726374,John Lewis Glasgow,G1 2GF,-4.251392,55.863851,John Lewis Heathrow Terminal 2,TW6 1EW,-0.449579,51.469777,John Lewis High Wycombe,HP12 4NW,-0.782847,51.611123,John Lewis Kingston,KT1 1TE,-0.306875,51.411564,John Lewis Leeds,LS2 7AR,-1.538422,53.798229,John Lewis Leicester,LE1 4SA,-1.138531,52.636951,John Lewis Liverpool,L1 8BJ,-2.987648,53.403799,John Lewis Milton Keynes,MK9 3EP,-0.752437,52.044848,John Lewis Newcastle,NE99 1AB,-1.615383,54.96746,John Lewis Norwich,NR1 3LX,1.295972,52.625197,John Lewis Nottingham,NG1 3QA,-1.147709,52.956,John Lewis Outlet Swindon,SN2 2DY,-1.798364,51.562463,John Lewis Oxford,OX1 1PB,-1.261618,51.749895,John Lewis Oxford Street,W1A 1EX,-0.112042,51.524565,John Lewis Peterborough,PE1 1NL,-0.245287,52.574709,John Lewis Reading,RG1 2BB,-0.972085,51.455316,John Lewis Sheffield,S1 4HP,-1.472154,53.379243,John Lewis Solihull,B91 3RA,-1.780718,52.413899,John Lewis Southampton,SO15 1QA,-1.40776,50.904423,John Lewis St Pancras,N1C 4QL,-0.126147,51.531428,John Lewis Stratford,E20 1EL,-0.00672,51.54373,John Lewis Trafford,M17 8JL,-2.345293,53.463529,John Lewis Watford,WD17 2TW,-0.392278,51.654612,John Lewis Welwyn,AL8 6TP,-0.206928,51.803239,John Lewis White City Westfield,W12 7FU,-0.222345,51.510392,John Lewis York,YO32 9GX,-1.045231,53.988583,John Lewis.com,SW1E 5NN,-0.141499,51.496466,Keele Services,ST5 5HG,-2.2909,52.992774,Kenilworth,CV8 1JP,-1.575817,52.34151,Kensington,W8 6SA,-0.19725,51.4992,Kensington Gardens,W2 3HJ,-0.183227,51.510789,Keynsham,BS31 1ST,-2.481869,51.420935,Kings Cross,N1C 4BZ,-0.123462,51.536153,Kings Cross Station,N1C 4AP,-0.123297,51.53238,Kings Road,SW3 5XP,-0.16699,51.488424,Kingshill,ME19 4QJ,0.40328,51.272298,Kingsthorpe,NN2 7BD,-0.901201,52.262586,Kingston,KT1 1TG,-0.304637,51.411811,Knight and Lee,PO5 3QE,-1.088277,50.785092,Knightsbridge,SW3 1JJ,-0.16475,51.499359,Knutsford,WA16 6BU,-2.375307,53.30407,Leatherhead,KT22 8DW,-0.329242,51.294524,Leicester Forest East Services,LE3 3GB,-1.205624,52.619143,Leigh On Sea,SS9 3JA,0.654995,51.548488,Leighton Buzzard,LU7 1DH,-0.663544,51.916754,Lewes,BN7 2LP,0.014422,50.874435,Lichfield,WS13 6RX,-1.842172,52.673624,Lincoln,LN2 4DS,-0.520806,53.251326,Lincoln Petrol Station,LN2 4DS,-0.520806,53.251326,Little Waitrose at John Lewis Watford,WD17 2TW,-0.392278,51.654612,Little Waitrose John Lewis Southampton,SO15 1QA,-1.40776,50.904423,Locks Heath,SO31 6DX,-1.27498,50.863576,London Gateway,NW7 3HB,-0.259362,51.624869,Longfield,DA3 7QA,0.300735,51.396371,Lutterworth,LE17 4NF,-1.199138,52.458761,Lymington New,SO41 9GF,-1.549581,50.757146,Maidenhead,SL6 8AF,-0.714723,51.523131,Malmesbury,SN16 9FS,-2.09269,51.58096,Market Harborough,LE16 8BD,-0.915974,52.475466,Marlborough,SN8 1AA,-1.729931,51.420942,Marlow,SL7 1DD,-0.775079,51.573352,Marylebone,W1U 4SD,-0.152077,51.519519,Meanwood,LS6 4RJ,-1.56782,53.828315,Melksham,SN12 6LP,-2.138789,51.375545,Membury Services,RG17 7TZ,-1.557179,51.483486,Menai Bridge,LL59 5EA,-4.166358,53.223915,Michaelwood Services,GL11 6DD,-2.420144,51.666641,Mill Hill,NW7 1GU,-0.209031,51.607255,Milngavie,G62 6HJ,-4.314874,55.934204,Mimms Services,EN6 3QQ,-0.221891,51.687531,Monmouth,NP25 3EQ,-2.718624,51.809944,Monument,EC4R 9AN,-0.086735,51.509974,Morningside,EH10 4AX,-3.209309,55.929444,MOUNTSORREL,LE12 7TZ,-1.151878,52.735693,Muswell Hill,N10 1DJ,-0.142442,51.591643,Nailsea,BS48 1AP,-2.758015,51.433521,New Malden,KT3 4HE,-0.256083,51.402339,Newark,NG24 1FF,-0.81216,53.079896,Newbury,RG14 1NB,-1.329292,51.407117,Newmarket,CB8 8NY,0.408142,52.246868,Newport,TF10 7DS,-2.36744,52.765419,Newport Pagnell,MK16 9EZ,-0.709055,52.082172,Newton Mearns,G77 6GW,-4.349241,55.783207,North Walsham,NR28 0NB,1.368243,52.827186,Northwich,CW9 5HD,-2.515085,53.258811,Northwood,HA6 2XW,-0.423317,51.611233,Norwich,NR4 6NU,1.251195,52.6079,Notting Hill Gate,W11 3QG,-0.199821,51.508796,Oadby,LE2 4LA,-1.081307,52.60021,Oakgrove,MK10 9SU,-0.714295,52.038648,Okehampton,EX20 1WL,-4.004084,50.740446,Old Brompton Road,SW7 3RD,-0.179164,51.491976,Otley,LS21 3AS,-1.696192,53.904926,Oundle,PE8 4NH,-0.465705,52.48414,Oxford Botley Road,OX2 0HH,-1.27938,51.75267,Oxford Services,OX33 1LJ ,-1.097191,51.738038,Oxted,RH8 0QE,-0.004629,51.258378,Paddock Wood,TN12 6EX,0.393733,51.179877,Parkstone,BH14 0AP,-1.939931,50.728747,Parsons Green,SW6 4TN,-0.201175,51.474412,Peartree,OX2 8JZ,-1.283438,51.795601,Peter Jones,SW1W 8EL,-0.159,51.492246,Peterborough,PE1 2BF,-0.249935,52.575479,Petersfield,GU32 3JA,-0.936701,51.004364,Petts Wood,BR5 1EA,0.075412,51.386631,Pimlico,SW1V 1QT,-0.139468,51.492549,Ponteland,NE20 9NH,-1.742564,55.049668,Pontprennau,CF23 8AN,-3.137916,51.530294,Portishead,BS20 7DE,-2.763612,51.485968,Portishead Petrol Station,BS20 7DE,-2.763612,51.485968,Poundbury,DT1 3BW,-2.46693,50.715299,Poynton,SK12 1RD,-2.119714,53.34895,Putney,SW15 1TW,-0.216481,51.463729,Ramsgate,CT11 9EJ,1.416949,51.332671,Raynes Park,SW20 0BS,-0.234012,51.408987,Reading,RG30 6WR,-1.019142,51.465726,Red Houses (Jersey),JE3 8LB,,,Richmond,TW9 1AE,-0.301404,51.462337,Rickmansworth,WD3 1FX,-0.472249,51.640703,Ringwood,BH24 1AT,-1.79327,50.847353,ROEHAMPTON,SW15 4LB,-0.238889,51.449446,Rohais (Guernsey),GY1 1FG,,,Romsey,SO51 8AS,-1.496818,50.989998,Ruislip,HA4 7DS,-0.423038,51.571358,Rushden,NN10 6AR,-0.619588,52.299379,Rushden Petrol Station,NN10 6AR,-0.619588,52.299379,Rustington,BN16 2NE,-0.504887,50.810181,Saffron Walden,CB10 1DT,0.241993,52.022721,Salisbury,SP2 7TS,-1.801853,51.07606,Salisbury Petrol Station,SP2 7TS,-1.801853,51.07606,Saltash,PL12 6LD,-4.23376,50.421424,Sandbach,CW11 4BE,-2.363342,53.142604,Sanderstead,CR2 9LE,-0.075478,51.332354,Sandhurst,GU47 0PU,-0.778368,51.341129,Saxmundham,IP17 1EP,1.493146,52.21333,Sceptre (Watford),WD24 7RU,-0.394888,51.680977,Sevenoaks,TN13 1JR,0.193398,51.269683,Sheffield,S11 8HY,-1.477041,53.372999,Sheffield Petrol Station,S11 8HY,-1.477041,53.372999,Sherborne,DT9 3PU,-2.516449,50.949349,Shrewsbury,SY1 1DP,-2.752666,52.708699,Sidcup,DA14 6EN,0.104958,51.42544,Sidmouth,EX10 9GA,-3.244277,50.700078,SKY (OSTERLEY),TW7 5QD,-0.329267,51.487377,Solihull,B91 3QG,-1.784027,52.413806,South Bank Tower,SE1 9LQ,-0.107208,51.507644,South Harrow,HA2 0EG,-0.352161,51.567669,South Harrow Petrol Station,HA2 0EG,-0.352161,51.567669,South Woodford,E18 2NA,0.022465,51.595316,Southampton New,SO17 2FX,-1.395022,50.923808,Southend,SS2 4DQ,0.724546,51.555868,Southend Petrol Station,SS2 4DQ,0.724546,51.555868,Southsea,PO5 2EJ,-1.087027,50.785882,St Albans,AL3 4JZ,-0.360512,51.746431,St Helier (Jersey),JE2 3GA,,,St Ives,PE27 5BW,-0.068892,52.322643,St Katharine Docks,E1W 1YY,-0.067249,51.507298,St Neots,PE19 2BH,-0.27195,52.228551,St Saviour (Jersey),JE2 7PN,,,Stamford,PE9 2PR,-0.485526,52.651649,Stevenage,SG1 3EH,-0.20852,51.911158,Stirling,FK7 7GX,-3.93347,56.113855,Storrington,RH20 4NQ,-0.451977,50.918173,Stourbridge,DY8 1HJ,-2.146537,52.455954,Stratford City,E20 1EH,-0.006639,51.543594,Stratford Upon Avon,CV37 8LU,-1.698599,52.179477,Stroud,GL5 2AP,-2.212701,51.741374,Sudbury,CO10 2SS,0.731648,52.036891,Sunningdale,SL5 0HD,-0.63364,51.391595,Surbiton,KT6 4QR,-0.304573,51.393717,Swaffham,PE37 7HT,0.683974,52.655715,Swaffham Petrol Station,PE37 7HT,0.683974,52.655715,Swindon,SN1 7BX,-1.807138,51.544828,Teignmouth,TQ14 8HR,-3.49516,50.546827,Temple Fortune,NW11 0QS,-0.199908,51.58306,Tenterden,TN30 6BW,0.688643,51.068343,Thame,OX9 3ZD,-0.977681,51.749019,Thatcham,RG19 3HN,-1.256786,51.402759,Tonbridge,TN9 1RG,0.276223,51.194257,Torquay,TQ1 3HL,-3.528001,50.476812,Towcester,NN12 6HZ,-0.989163,52.13021,Trinity Square,NG1 3EN,-1.148613,52.956249,Truro,TR1 1RH,-5.036674,50.272557,Tubs Hill,TN13 1DH,0.183644,51.276485,Twickenham,TW1 3RJ,-0.328809,51.448298,Twyford,RG10 9EH,-0.866374,51.477873,Uckfield,TN22 1PU,0.096163,50.969323,Upminster,RM14 3BT,0.252052,51.556179,UTTOXETER,ST14 8FA,-1.861803,52.89701,Vauxhall,SW8 1SJ,-0.124111,51.484859,Victoria Bressenden Place,SW1E 5DH,-0.142601,51.498149,Victoria Street,SW1E 6QP,-0.137377,51.497552,Waitrose Online Direct,RG12 8YA,-0.770445,51.411734,Walbrook,EC4N 8AF,-0.090177,51.512154,Wallingford,OX10 0EF,-1.125362,51.600722,Walton-le-Dale,PR5 4AW,-2.679119,53.74881,Wandsworth,SW18 4TF,-0.193594,51.454261,Wantage,OX12 8BD,-1.424919,51.588841,Warminster,BA12 9BR,-2.177745,51.205281,Warwick Services,CV35 0AA,-1.515881,52.219853,Waterlooville,PO7 7HS,-1.030421,50.879355,Waterside,UB7 0GB,-0.487355,51.537324,Wellington,TA21 8RD,-3.225686,50.979792,Wells,BA5 2PJ,-2.650273,51.209474,Welwyn Garden City,AL8 6AB,-0.204346,51.803595,West Byfleet,KT14 6NE,-0.506062,51.33871,West Ealing,W13 0NL,-0.321978,51.513117,West Hampstead,NW6 1RN,-0.192784,51.55151,West Kensington,W14 9PP,-0.205255,51.488602,Westbury Park,BS9 4HN,-2.612402,51.480589,Westfield London,W12 7GA,-0.223647,51.508055,Weston Super Mare,BS23 3UZ,-2.958139,51.341378,Weybridge,KT13 8BL,-0.456875,51.373618,Whetstone,N20 9HX,-0.175629,51.631259,Willerby,HU10 6EB,-0.455239,53.764038,Wilmslow,SK9 1AY,-2.229443,53.328422,Wimbledon,SW19 7JY,-0.202204,51.42478,Wimbledon Hill,SW19 7NS,-0.20752,51.421003,Wimborne,BH21 1AN,-1.98248,50.800216,Winchester,SO22 6EL,-1.336797,51.074249,Winchmore Hill,N21 2QP,-0.093169,51.635326,Windsor New,SL4 1TG,-0.610614,51.482208,Winton,BH9 2AD,-1.879047,50.740705,Witney,OX28 6AR,-1.483448,51.784817,Wokingham,RG40 1BB,-0.833588,51.411668,Wollaton,NG8 2DH,-1.229522,52.955965,Wolverhampton,WV2 4NJ,-2.132816,52.574227,Wolverhampton Petrol Station,WV2 4NJ,-2.132816,52.574227,Woodhall South,S26 7XR,-1.28137,53.315191,Woodley,RG5 3JW,-0.905502,51.451809,Wootton,NN4 6HP,-0.892305,52.210308,Worcester,WR5 2JG,-2.194486,52.182355,Worcester Park,KT4 8DX,-0.24356,51.378196,Worthing,BN11 1LL,-0.367748,50.813757,Wymondham,NR18 0SH,1.123725,52.575198,Wymondham Petrol Station,NR18 0SH,1.123725,52.575198,Yateley,GU46 6FR,-0.846275,51.338895,York,YO31 7UL,-1.069846,53.956725"

        assertEquals(1419721, calculateJourneyTime(allShopData))
    }
}