//
//  Utils.swift
//  iosApp
//
//  Created by Alexander on 08.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

var randomBool: Bool {
    return (Int.random(in: 0..<2)%2 == 0)
}

var randomCount: String {
    return "\(Int.random(in: 2..<99999))"
}

private let randomPhotoNames: [String] = [
    "random-photo1", "random-photo2", "random-photo3",
    "random-photo4", "random-photo5", "random-photo6",
    "random-photo7", "random-photo8", "random-photo9",
    "random-photo10", "ProfileImage", "PostImage"]

private let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

private let namesCombs: [String] = [
    "Ivan", "Disco", "Chillout",
    "Vocal", "Deep", "Mix", "Soundeo",
    "House", "Fabulous", "SoundCloud",
    "However", "Catalina", "Xcode",
    "Product", "Overview", "Public",
    "Session", "Disconnect", "Respond",
    "Request", "Dapp", "Kotlin",
    "Parabéns", "Really", "Belissimo", "Beautiful"
]
private let reactions: [ReactionType] = [ReactionType.photoLike, ReactionType.historyComment, ReactionType.likeOnComment, ReactionType.photoComment]

public func randomString(_ length: Int, _ justLowerCase: Bool = false) -> String {
    let randomLength = Int.random(in: 10..<length)
    
    var text: String = ""
    for _ in 1...randomLength {
        var decValue = 0  // ascii decimal value of a character
        var charType = 3  // default is lowercase
        if justLowerCase == false {
            // randomize the character type
            charType =  Int(arc4random_uniform(4))
        }
        switch charType {
        case 1:  // digit: random Int between 48 and 57
            decValue = Int(arc4random_uniform(10)) + 48
        case 2:  // uppercase letter
            decValue = Int(arc4random_uniform(26)) + 65
        case 3:  // lowercase letter
            decValue = Int(arc4random_uniform(26)) + 97
        default:  // space character
            decValue = 32
        }
        // get ASCII character from random decimal value
        let scalar: Unicode.Scalar? = Unicode.Scalar(decValue)
        var newString = ""
        newString.unicodeScalars.append(scalar!)
        let char = newString
        text = text + char
        // remove double spaces
        text = text.replacingOccurrences(of:"  ", with: " ")
    }
    return text
}

public func randomPhoto(_ ind: Int? = nil) -> String {
    var profilePhoto = "PostImage"
    if let inputIndex = ind {
        if(inputIndex%2 == 0) {
            let randIndex = Int.random(in: 0..<randomPhotoNames.count)
            profilePhoto = randomPhotoNames[randIndex]
        }
    } else {
        let randIndex = Int.random(in: 0..<randomPhotoNames.count)
        profilePhoto = randomPhotoNames[randIndex]
    }
    
    return profilePhoto
}

public func randomPhotos() -> [String] {
    var randomUserPhotos: Set<String> = Set()
    let randIndex = Int.random(in: 1..<6)
    for _ in 0..<randIndex {
        randomUserPhotos.insert(randomPhoto())
    }
    return randomUserPhotos.reversed()
}

public func randomUserName() -> String {
    let randNameIndex = Int.random(in: 0..<namesCombs.count)
    let userName = namesCombs[randNameIndex]
    let distincted = namesCombs.filter { $0 != userName }
    let randSurnameIndex = Int.random(in: 0..<distincted.count)
    let userSurname = distincted[randSurnameIndex]
    return "\(userName) \(userSurname)"
}

public func randomReaction() -> ReactionType {
    let randIndex = Int.random(in: 0..<reactions.count)
    return reactions[randIndex]
}

extension Int {
    func toCGFloat() -> CGFloat {
        return CGFloat(self)
    }
}

extension String {
    @available(iOS 15, *)
    func markdownToAttributed() -> AttributedString {
        do {
            return try AttributedString(markdown: self) /// convert to AttributedString
        } catch {
            return AttributedString("Error parsing markdown: \(error)")
        }
    }
}

extension UINavigationController {
  open override func viewWillLayoutSubviews() {
    navigationBar.topItem?.backButtonDisplayMode = .minimal
  }
}
