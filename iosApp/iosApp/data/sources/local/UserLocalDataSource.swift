//
//  UserLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

final class UserLocalDataSource : IUserLocalDataSource {
    
    private var userPosts: [PostModel] = []
    
    func takeUserPosts(userId: String) -> [PostModel] {
        if userPosts.isEmpty {
            userPosts = createUserPosts(userId: userId)
        }
        return userPosts
    }
    
    private func createUserPosts(userId: String) -> [PostModel] {
        var createdPosts:[PostModel] = []
        let randomInt = Int.random(in: 20..<48)
        for rnd in 0...randomInt {
            let userPost = PostModel(rnd)
            userPost.userId = userId
            createdPosts.append(userPost)
        }
        return createdPosts
    }
}

protocol IUserLocalDataSource {
    func takeUserPosts(userId: String) -> [PostModel]
}
