//
//  SlotView.swift
//  connect4
//
//  Created by Michael Neilens on 28/05/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import UIKit

class SlotView:UIView {
    private(set) var col = 0 
    let tokenMap = [".":"EmptySlot","r":"RedSlot","R":"RedSlot","y":"YellowSlot","Y":"YellowSlot"]
    
    
    init(col:Col, token:Token, frame:CGRect) {
        self.col = col
        super.init(frame: frame)

        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: self.frame.height))
        imageView.image = UIImage(named: tokenMap[token] ?? "EmptySlot")
        self.addSubview(imageView)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func addImage(token:Token) {
        let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: self.frame.height))
        imageView.image = UIImage(named: tokenMap[token] ?? "EmptySlot")
        self.addSubview(imageView)

    }
}
