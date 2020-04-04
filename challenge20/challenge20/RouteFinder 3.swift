//
//  RouteFinder.swift
//  challenge20
//
//  Created by Michael Neilens on 10/02/2020.
//  Copyright © 2020 Michael Neilens. All rights reserved.
//

import Foundation

func getRefId(response:String)-> String {
    if let refid = response.split(separator:",").first {
        return String(refid)
    } else {
        return ""
    }
}
func getPlaces(response:String)->Array<Place> {
    return response.split(separator:",").dropFirst().map{String($0)}
}

typealias Place = String
typealias Turn = String

func getMoves(places:Array<Place>, refId:String) -> Array<Move> {
    return places
        .enumerated()
        .map{index, place in
            let turns = getTurns(place:place)
            return (Move(request:Request(refId: refId, command: "M", repeats: index + 1), turns:turns))
            }
        .filter{$0.turns.count > 0}
}

func getTurns(place:Place)->Array<Turn> {
    switch (place) {
    case "OL": return ["L"]
    case "OR": return ["R"]
    case "OLR":return ["L","R"]
    default:return []
    }
}

func containsExit(response:String) -> Bool {
    return getPlaces(response: response)
        .flatMap{getTurns(place:$0)}
        .contains("W")
}

func routeFinder(requestsMade:Array<Request> = [], response:String ) {
    if containsExit(response:response) {
        return
    }
    let newRefid = getRefId(response: response)

    if requestsMade.contains(where: {(request:Request) in request.refId == newRefid }) {
        return
    }
    
    let places = getPlaces(response: response)
    let moves = getMoves(places:places, refId: newRefid)
    
    moves.forEach { move in
        let completion = {(response:String) in
            let refid = getRefId(response: response)
            move.turns.forEach{ turn in
                let turnRequest = Request(refId: refid, command: turn, repeats: 1)
                let completion = {(response:String) in
                    turnCompletionTemplate(requestsMade: requestsMade + [turnRequest], response: response)
                }
                makeHttpRequest(forRequest:turnRequest, completion: completion)
            }
        }
        let moveRequest = move.request
        makeHttpRequest(forRequest: moveRequest, completion: completion)
        
    }
}

func moveCompletionTemplate() {
    
}


func turnCompletionTemplate(requestsMade:Array<Request>, response:String) {
    routeFinder(requestsMade: requestsMade, response: response)
}
