const noPubs = "{\"Pubs\":[]}";
const singlePub = "{\"Pubs\":[{" +
        "  \"Name\": \"Cask and Glass\"," +
        "  \"Address\": \"39 Palace Street Victoria London SW1E 5HN\"," +
        "  \"Town\": \"London\"," +
        "  \"PostCode\": \"SW1E 5HN\"," +
        "  \"RegularBeers\": [" +
        "    \"Shepherd Neame Master Brew\"," +
        "    \"Shepherd Neame Spitfire\"" +
        "  ]," +
        "  \"GuestBeers\": [" +
        "    \"Shepherd Neame --seasonal--\"," +
        "    \"Shepherd Neame --varies--\"," +
        "    \"Shepherd Neame Whitstable Bay Pale Ale\"" +
        "  ]," +
        "  \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15938&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "  \"Id\": \"15938\"," +
        "  \"Branch\": \"WLD\"," +
        "  \"CreateTS\": \"2019-05-16 19:31:39\"" +
        "}]}";
const singlePubWithNoBeer = "{\"Pubs\":[{" +
        "  \"Name\": \"Cask and Glass\"," +
        "  \"Address\": \"39 Palace Street Victoria London SW1E 5HN\"," +
        "  \"Town\": \"London\"," +
        "  \"PostCode\": \"SW1E 5HN\"," +
        "  \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15938&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "  \"Id\": \"15938\"," +
        "  \"Branch\": \"WLD\"," +
        "  \"CreateTS\": \"2019-05-16 19:31:39\"" +
        "}]}";
const singlePubMissingFields = "{\"Pubs\":[{" +
        "  \"Address\": \"39 Palace Street Victoria London SW1E 5HN\"," +
        "  \"Town\": \"London\"," +
        "  \"PostCode\": \"SW1E 5HN\"," +
        "  \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15938&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "  \"Id\": \"15938\"," +
        "  \"CreateTS\": \"2019-05-16 19:31:39\"" +
        "}]}";
const manyPubs = "{" +
        "  \"Pubs\": [" +
        "    {" +
        "      \"Name\": \"Phoenix\"," +
        "      \"Address\": \"14 Palace Street Victoria London SW1E 5JA\"," +
        "      \"Town\": \"London\"," +
        "      \"RegularBeers\": [" +
        "        \"Young#039;s Bitter\"" +
        "      ]," +
        "      \"GuestBeers\": [" +
        "        \"Sharp#039;s Doom Bar\"," +
        "        \"Twickenham --varies--\"," +
        "        \"Young#039;s --varies--\"" +
        "      ]," +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16185&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "      \"Id\": \"16185\"," +
        "      \"Branch\": \"WLD\"," +
        "      \"CreateTS\": \"2019-05-16 19:31:20\"" +
        "    }," +
        "    {" +
        "      \"Name\": \"Windsor Castle\"," +
        "      \"Address\": \"23 Francis Street Westminster London SW1P 1DN\"," +
        "      \"Town\": \"London\"," +
        "      \"RegularBeers\": [" +
        "        \"Samuel Smith Old Brewery Bitter\"" +
        "      ]," +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16187&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "      \"Id\": \"16187\"," +
        "      \"Branch\": \"WLD\"," +
        "      \"CreateTS\": \"2019-05-16 19:31:20\"" +
        "    }," +
        "    {" +
        "      \"Name\": \"Sports Bar and Grill Victoria\"," +
        "      \"Address\": \"Unit 59 1st floor Victoria Main Line Station Terminus Place Victoria London SW1V 1JU\"," +
        "      \"Town\": \"London\"," +
        "      \"RegularBeers\": [" +
        "        \"Brakspear Bitter\"," +
        "        \"Marston#039;s EPA\"" +
        "      ]," +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16196&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "      \"Id\": \"16196\"," +
        "      \"Branch\": \"WLD\"," +
        "      \"CreateTS\": \"2019-05-16 19:31:20\"" +
        "    }," +
        "    {" +
        "      \"Name\": \"Willow Walk\"," +
        "      \"Address\": \"25 Wilton Road Victoria London SW1V 1LW\"," +
        "      \"Town\": \"London\"," +
        "      \"RegularBeers\": [" +
        "        \"Fuller#039;s London Pride\"," +
        "        \"Greene King Abbot\"," +
        "        \"Greene King IPA\"" +
        "      ]," +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15951&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "      \"Id\": \"15951\"," +
        "      \"Branch\": \"WLD\"," +
        "      \"CreateTS\": \"2019-05-16 19:31:21\"" +
        "    }," +
        "    {" +
        "      \"Name\": \"Buckingham Arms\"," +
        "      \"Address\": \"62 Petty France Westminster London SW1H 9EU\"," +
        "      \"Town\": \"London\"," +
        "      \"RegularBeers\": [" +
        "        \"Young#039;s --seasonal--\"," +
        "        \"Young#039;s Bitter\"," +
        "        \"Young#039;s London Gold\"," +
        "        \"Young#039;s Special\"" +
        "      ]," +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15905&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "      \"Id\": \"15905\"," +
        "      \"Branch\": \"WLD\"," +
        "      \"CreateTS\": \"2019-05-16 19:31:21\"" +
        "    }," +
        "    {" +
        "      \"Name\": \"Shakespeare\"," +
        "      \"Address\": \"99 Buckingham Palace Road Victoria London SW1W 0RP\"," +
        "      \"Town\": \"London\"," +
        "      \"RegularBeers\": [" +
        "        \"Greene King IPA\"," +
        "        \"Greene King IPA Reserve\"" +
        "      ]," +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16066&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\"," +
        "      \"Id\": \"16066\"," +
        "      \"Branch\": \"WLD\"," +
        "      \"CreateTS\": \"2019-05-16 19:31:21\"" +
        "    }" +
        "  ]" +
        "}";
const invalidJson = "This is some invalid json!!";
const jsonNotPubs = "{\"Shops\":[]}";

module.exports = { noPubs, singlePub, singlePubWithNoBeer, singlePubMissingFields, manyPubs, invalidJson, jsonNotPubs };
