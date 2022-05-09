//
//  IUserModel.swift
//  Stogram
//
//  Created by Alexander on 03.05.2022.
//

import Foundation

protocol IUserModel {
    var userId: String {get set}
    var userName: String {get set}
    var userPhoto: String {get set}
    var hasStory: Bool {get set}
}
