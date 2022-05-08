//
//  StoryModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import RealmSwift

struct StoryModel: Identifiable {
    let id = UUID()
    var userId: String = UUID().uuidString
    var userName: String = Consts.USER_NAME
    var userPhoto: String = Consts.USER_PHOTO
}
