//
//  Utility.swift
//  Filtering
//
//  Created by Michael Neilens on 09/04/2022.
//

import Foundation

struct FailedToConvertJsonToData: Error {}

func parseJson<T:Decodable>(_ input: String) throws -> [T] {
    guard let data = input.data(using: .utf8) else {
        throw FailedToConvertJsonToData()
    }
    return try JSONDecoder().decode([T].self, from: data)
}

