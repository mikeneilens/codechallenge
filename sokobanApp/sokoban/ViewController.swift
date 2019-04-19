//
//  ViewController.swift
//  sokoban
//
//  Created by Michael Neilens on 14/04/2019.
//  Copyright © 2019 Michael Neilens. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var gameArray = GameArray()
    @IBOutlet weak var mainView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        gameArray = puzzleText
    }

    override func viewDidAppear(_ animated: Bool) {
        refreshMainView()
    }
    
    var minWidthOfTile:Double {
        let puzzleWidth = Double(puzzleText[0].count)
        let mainViewWidth = Double(mainView.frame.width)
        return mainViewWidth / puzzleWidth
    }
    var minHeightOfTile:Double {
        let puzzleHeight = Double(puzzleText.count)
        let mainViewHieght = Double(mainView.frame.height)
        return mainViewHieght / puzzleHeight
    }
    var sizeOfTile:Double {
        return (minWidthOfTile < minHeightOfTile) ? minWidthOfTile : minHeightOfTile
    }
    var margin:Double {
        let puzzleWidth = Double(puzzleText[0].count)
        let mainViewWidth = Double(mainView.frame.width)
        return (mainViewWidth - puzzleWidth * sizeOfTile) / 2
    }
    
    func addTilesToMainView(usingGameArray gameArray:GameArray) {
        for (row, string) in gameArray.enumerated() {
            for (column, character ) in string.enumerated() {
                let tileLabel = createTile(atPosition: Position(row, column), usingText: String(character))
                mainView.addSubview(tileLabel)
            }
        }
    }
    
    func refreshMainView(){
        mainView.subviews.forEach { $0.removeFromSuperview() }
        addTilesToMainView(usingGameArray: gameArray)
        if puzzleIsSolved(gameArray) {
            let winningTile = createPuzzleCompleteMessage()
            mainView.addSubview(winningTile)
        }
    }
    
    func createTile(atPosition position:Position, usingText text:String) -> UIView {
        let x = Double(position.column) * sizeOfTile + margin
        let y = Double(position.row) * sizeOfTile
        let cgRect = CGRect(x: x, y: y, width: sizeOfTile, height: sizeOfTile)
        let uiImageView = UIImageView(frame: cgRect)
        let mapTile = MapTileCreator.create(using:text)
        if mapTile.image != "" { uiImageView.image = UIImage(named: mapTile.image ) }
        uiImageView.backgroundColor = mapTile.backgroundColor
        return uiImageView
    }
    
    func createPuzzleCompleteMessage() -> UIView {
        let cgRect = CGRect(x: 0, y: mainView.frame.height/2 - 20 , width: mainView.frame.width, height: 40)
        let label = UILabel(frame: cgRect)
        label.backgroundColor = UIColor.white
        label.text = "Puzzle solved"
        label.textAlignment = NSTextAlignment.center
        label.adjustsFontSizeToFitWidth = true
        return label
    }

    @IBAction func upPressed(_ sender: UIButton) {
        gameArray = processSokobanMove(gameArray, "U")
        refreshMainView()
    }
    @IBAction func downPressed(_ sender: UIButton) {
        gameArray = processSokobanMove(gameArray, "D")
        refreshMainView()
    }
    @IBAction func leftPressd(_ sender: UIButton) {
        gameArray = processSokobanMove(gameArray, "L")
        refreshMainView()
    }
    @IBAction func rightPressed(_ sender: UIButton) {
        gameArray = processSokobanMove(gameArray, "R")
        refreshMainView()
    }
    @IBAction func resetPressed(_ sender: UIButton) {
        gameArray = puzzleText
        refreshMainView()
        
    }


}

