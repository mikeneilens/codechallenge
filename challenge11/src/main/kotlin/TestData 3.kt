val noPubs = "{\"Pubs\":[]}"
val singlePub = "{\"Pubs\":[{\n" +
        "  \"Name\": \"Cask and Glass\",\n" +
        "  \"Address\": \"39 Palace Street Victoria London SW1E 5HN\",\n" +
        "  \"Town\": \"London\",\n" +
        "  \"PostCode\": \"SW1E 5HN\",\n" +
        "  \"RegularBeers\": [\n" +
        "    \"Shepherd Neame Master Brew\",\n" +
        "    \"Shepherd Neame Spitfire\"\n" +
        "  ],\n" +
        "  \"GuestBeers\": [\n" +
        "    \"Shepherd Neame --seasonal--\",\n" +
        "    \"Shepherd Neame --varies--\",\n" +
        "    \"Shepherd Neame Whitstable Bay Pale Ale\"\n" +
        "  ],\n" +
        "  \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15938&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "  \"Id\": \"15938\",\n" +
        "  \"Branch\": \"WLD\",\n" +
        "  \"CreateTS\": \"2019-05-16 19:31:39\",\n" +
        "}]}"
val singlePubWithNoBeer = "{\"Pubs\":[{\n" +
        "  \"Name\": \"Cask and Glass\",\n" +
        "  \"Address\": \"39 Palace Street Victoria London SW1E 5HN\",\n" +
        "  \"Town\": \"London\",\n" +
        "  \"PostCode\": \"SW1E 5HN\",\n" +
        "  \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15938&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "  \"Id\": \"15938\",\n" +
        "  \"Branch\": \"WLD\",\n" +
        "  \"CreateTS\": \"2019-05-16 19:31:39\",\n" +
        "}]}"
val manyPubs = "{\n" +
        "  \"Pubs\": [\n" +
        "    {\n" +
        "      \"Name\": \"Phoenix\",\n" +
        "      \"Address\": \"14 Palace Street Victoria London SW1E 5JA\",\n" +
        "      \"Town\": \"London\",\n" +
        "      \"RegularBeers\": [\n" +
        "        \"Young#039;s Bitter\"\n" +
        "      ],\n" +
        "      \"GuestBeers\": [\n" +
        "        \"Sharp#039;s Doom Bar\",\n" +
        "        \"Twickenham --varies--\",\n" +
        "        \"Young#039;s --varies--\"\n" +
        "      ],\n" +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16185&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "      \"Id\": \"16185\",\n" +
        "      \"Branch\": \"WLD\",\n" +
        "      \"CreateTS\": \"2019-05-16 19:31:20\",\n" +
        "    },\n" +
        "    {\n" +
        "      \"Name\": \"Windsor Castle\",\n" +
        "      \"Address\": \"23 Francis Street Westminster London SW1P 1DN\",\n" +
        "      \"Town\": \"London\",\n" +
        "      \"RegularBeers\": [\n" +
        "        \"Samuel Smith Old Brewery Bitter\"\n" +
        "      ],\n" +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16187&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "      \"Id\": \"16187\",\n" +
        "      \"Branch\": \"WLD\",\n" +
        "      \"CreateTS\": \"2019-05-16 19:31:20\",\n" +
        "    },\n" +
        "    {\n" +
        "      \"Name\": \"Sports Bar and Grill Victoria\",\n" +
        "      \"Address\": \"Unit 59 1st floor Victoria Main Line Station Terminus Place Victoria London SW1V 1JU\",\n" +
        "      \"Town\": \"London\",\n" +
        "      \"RegularBeers\": [\n" +
        "        \"Brakspear Bitter\",\n" +
        "        \"Marston#039;s EPA\"\n" +
        "      ],\n" +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16196&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "      \"Id\": \"16196\",\n" +
        "      \"Branch\": \"WLD\",\n" +
        "      \"CreateTS\": \"2019-05-16 19:31:20\",\n" +
        "    },\n" +
        "    {\n" +
        "      \"Name\": \"Willow Walk\",\n" +
        "      \"Address\": \"25 Wilton Road Victoria London SW1V 1LW\",\n" +
        "      \"Town\": \"London\",\n" +
        "      \"RegularBeers\": [\n" +
        "        \"Fuller#039;s London Pride\",\n" +
        "        \"Greene King Abbot\",\n" +
        "        \"Greene King IPA\"\n" +
        "      ],\n" +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15951&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "      \"Id\": \"15951\",\n" +
        "      \"Branch\": \"WLD\",\n" +
        "      \"CreateTS\": \"2019-05-16 19:31:21\",\n" +
        "    },\n" +
        "    {\n" +
        "      \"Name\": \"Buckingham Arms\",\n" +
        "      \"Address\": \"62 Petty France Westminster London SW1H 9EU\",\n" +
        "      \"Town\": \"London\",\n" +
        "      \"RegularBeers\": [\n" +
        "        \"Young#039;s --seasonal--\",\n" +
        "        \"Young#039;s Bitter\",\n" +
        "        \"Young#039;s London Gold\",\n" +
        "        \"Young#039;s Special\"\n" +
        "      ],\n" +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=15905&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "      \"Id\": \"15905\",\n" +
        "      \"Branch\": \"WLD\",\n" +
        "      \"CreateTS\": \"2019-05-16 19:31:21\",\n" +
        "    },\n" +
        "    {\n" +
        "      \"Name\": \"Shakespeare\",\n" +
        "      \"Address\": \"99 Buckingham Palace Road Victoria London SW1W 0RP\",\n" +
        "      \"Town\": \"London\",\n" +
        "      \"RegularBeers\": [\n" +
        "        \"Greene King IPA\",\n" +
        "        \"Greene King IPA Reserve\"\n" +
        "      ],\n" +
        "      \"PubService\": \"https://pubcrawlapi.appspot.com/pub/?v=1&id=16066&branch=WLD&uId=mike&pubs=no&realAle=yes&memberDiscount=no&town=London\",\n" +
        "      \"Id\": \"16066\",\n" +
        "      \"Branch\": \"WLD\",\n" +
        "      \"CreateTS\": \"2019-05-16 19:31:21\",\n" +
        "    }\n" +
        "  ]\n" +
        "}"
