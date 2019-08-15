//
//  GBP.swift
//  challenge10
//
//  Created by Michael Neilens on 15/08/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import Foundation

struct GBP:Equatable {
    let value:Int
    init (_ value:Int) {
        if value >= 0 {self.value = value}
        else {self.value = -value}
    }
}
