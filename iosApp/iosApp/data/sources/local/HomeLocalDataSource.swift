//
//  HomeLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi
import shared

final class HomeLocalDataSource : IHomeLocalDataSource {
    
    private var allPosts: [PostEntity] = []
    
    func takeUserPosts() -> [PostEntity] {
//        if allPosts.isEmpty {
//            allPosts = createPosts()
//        }
        
        return PostEntity.companion.createRandomList()
    }
    
    func takeUserStories() -> [UserEntity] {
        return UserEntity.companion.createRandomList(hasUserStory: true)
    }
}

protocol IHomeLocalDataSource {
    func takeUserPosts() -> [PostEntity]
    func takeUserStories() -> [UserEntity]
}
