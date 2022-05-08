//
//  IPostModel.swift
//  Stogram
//
//  Created by Alexander on 03.05.2022.
//

import Foundation

protocol IPostModel: IUserModel {
    var date: String {get set}
    var postId: String {get set}
    var text: String {get set}
    var isLiked: Bool {get set}
    var likesCount: String {get set}
}
