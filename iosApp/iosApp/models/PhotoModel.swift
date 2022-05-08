//
//  PhotoModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

struct PhotoModel: Identifiable, Hashable {
    let id = UUID()
    var userId: String = UUID().uuidString
    var userName: String = Consts.USER_NAME
    var photo: String = Consts.USER_PHOTO
    
    public static let EMPTY = PhotoModel(userId: "", userName: "", photo: "")
}
