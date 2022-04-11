//
//  Dto.swift
//  Filtering
//
//  Created by Michael Neilens on 09/04/2022.
//

import Foundation

struct Dto {
    struct FilterRange: Decodable{
        let min: String
        let max: String
    }

    struct Filter: Decodable{
        let filter: String
        let value: String?
        let range: FilterRange?
        
    }
    struct Filtering: Decodable {
        let groupId: String?
        let filters: Array<Filter>?
    }

    struct Card: Decodable {
           let id: String
           let cardType: String
           let name: String
           let filtering: Filtering?
    }

}

