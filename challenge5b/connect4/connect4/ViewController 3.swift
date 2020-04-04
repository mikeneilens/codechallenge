//
//  ViewController.swift
//  connect4
//
//  Created by Michael Neilens on 28/05/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var statusLabel: UILabel!
    
    var grid = [""]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        resetGame()
    }

    func resetGame() {
        grid = [".......",
                ".......",
                ".......",
                ".......",
                ".......",
                "......."]
        statusLabel.text = "Touch a column to play"
        refreshViews()
        
        if Int.random(in: 0..<2) == 1 {
            computerPlays()
        }
    }
    
    func refreshViews() {
        removeViews()
        for row in 0..<grid.count {
            for col in 0..<grid[0].count {
                createView(row: row, col: col, token: grid[row][col])
            }
        }
    }
    
    func createView(row:Int, col:Int, token:Token)  {
        let width = self.view.frame.width / 7.0
        let height = width
        let y = 100 + CGFloat(row) * height
        let x = CGFloat(col) * width
        
        let slotView = SlotView(col:col, token:token, frame:CGRect(x: x, y: y, width: width, height: height) )
        self.view.addSubview(slotView)
        
        let gestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(ViewController.slotTapped(gestureRecognizer:)))
        gestureRecognizer.delegate = self
        slotView.addGestureRecognizer(gestureRecognizer)
    }
    
    func removeViews() {
        self.view.subviews.forEach{if $0 is SlotView {$0.removeFromSuperview()}}
    }
    
    @objc func slotTapped(gestureRecognizer: UIGestureRecognizer) {
        if playerHasWonOrDrawn() {
            resetGame()
            return
        }

        if let slotView = gestureRecognizer.view as? SlotView {
            playAddsToken(col: slotView.col)
        }
    }
    
    func playAddsToken(col:Col) {
        let playersToken =  lastTokenPlayed(grid: grid).0 == "r" ? "y" : "r"
        
        if let newGrid = grid.drop(playersToken, intoColumn:col) {
            grid = newGrid
            refreshViews()
            refreshStatus()
            computerPlays()
        }
    }
    
    func computerPlays() {
        if playerHasWonOrDrawn() {return}

        grid = addToken(grid:grid)
        refreshViews()
        refreshStatus()
    }
    
    func refreshStatus() {
        let result = getGridStatus(grid: grid)
        statusLabel.text = result
    }
    
    func playerHasWonOrDrawn() -> Bool {
        guard let labelText = statusLabel.text else {return false}
        
        if labelText.contains("win") || labelText.contains("draw") {
            return true
        } else {
            return false
        }
    }
}

