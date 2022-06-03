//
//  UserLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import shared

final class UserLocalDataSource : IUserLocalDataSource {
    
    private var userPosts: [PostEntity] = []
    
    func takeUserPosts(userId: String) -> [PostEntity] {
        if userPosts.isEmpty {
            userPosts = PostEntity.companion.createRandomList()
        }
        return userPosts
    }
}

protocol IUserLocalDataSource {
    func takeUserPosts(userId: String) -> [PostEntity]
}
