//
//  HttpRequestor.swift
//  challenge20
//
//  Created by Michael Neilens on 10/02/2020.
//  Copyright © 2020 Michael Neilens. All rights reserved.
//

import Foundation

struct Request:Equatable {
    let refId:String
    let command:String
    let repeats:Int
}
struct Move:Equatable {
    let request:Request
    let turns:Array<Turn>
}


func makeHttpRequest(forRequest request:Request,completion: @escaping (String)->()) {
    let urlString = "https://challenge20.appspot.com/?command=\(request.command)&referenceid=\(request.refId)&repeat=\(request.repeats)"
    guard let url = URL(string: urlString) else {
        print("urlString: " + urlString )
        return
    }
    
    let session = URLSession(configuration: URLSessionConfiguration.default)
    

    let dataTask = session.dataTask(with: url){
        (data:Data?, response:URLResponse?, error:Error?) in
        print("task complete")
        if let data = data {
            let body = String(decoding: data, as: UTF8.self)
            completion(body)
        }
    }
    dataTask.resume()
}

