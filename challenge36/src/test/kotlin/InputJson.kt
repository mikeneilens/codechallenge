val inputJson = """
    [
       {
          "id": "card1",
          "cardType": "carousel",
          "name": "Carousel"
       },
       {
          "id": "card2",
          "cardType": "banner",
          "name": "Banner"
       },
       {
          "id": "card3",
          "cardType": "departments",
          "name": "Old departments list",
          "filtering": {
             "groupId": "departmentGroup",
             "filters": [
                {
                   "filter": "osVersionEquals",
                   "value": "13.0.0"
                }
             ]
          }
       },
       {
          "id": "card4",
          "cardType": "recommendations",
          "name": "Recommendations1",
          "filtering": {
             "groupId": "recsGroup",
             "filters": [
                {
                   "filter": "osVersionGreaterThan",
                   "value": "14.9.0"
                }
             ]
          }
       },
       {
          "id": "card5",
          "cardType": "departments",
          "name": "New departments list default",
          "filtering": {
             "groupId": "departmentGroup",
             "filters": [
                {
                   "filter": "controlGroup"
                }
             ]
          }
       },
       {
          "id": "card6",
          "cardType": "departments",
          "name": "New departments list 15",
          "filtering": {
             "groupId": "departmentGroup",
             "filters": [
                {
                   "filter": "osVersionEquals",
                   "value": "15.0.0"
                }
             ]
          }
       },
       {
          "id": "card7",
          "cardType": "banner",
          "name": "Banner2",
          "filtering": {
             "filters": [
                {
                   "filter": "osVersionEquals",
                   "value": "15.0.0"
                }
             ]
          }
       },
       {
          "id": "card9",
          "cardType": "recommendations",
          "name": "Recommendations2",
          "filtering": {
             "groupId": "recsGroup",
             "filters": [
                {
                   "filter": "osVersionGreaterThan",
                   "value": "14.0.0"
                }
             ]
          }
       }
    ]
""".trimIndent()