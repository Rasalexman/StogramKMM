//
//  HomeLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import RealmSwift
import Sodi

final class HomeLocalDataSource : IHomeLocalDataSource {
    
    private var localRealm: Realm = instance()
    
    private var allPosts: [PostModel] = []
    
//    init() {
//        try! localRealm.write {
//            localRealm.deleteAll()
//        }
//    }
    
    func takeUserPosts() -> [PostModel] {
//        if allPosts.isEmpty {
//            allPosts = createPosts()
//        }
        
        let allPostsResult = localRealm.objects(PostModel.self)
        var allPosts:[PostModel] = []
        if allPostsResult.isEmpty {
            allPosts = createPosts()
        } else {
            allPosts = Array(allPostsResult)
        }
        return allPosts
    }
    
    func takeUserStories() -> [StoryModel] {
        var createdStories:[StoryModel] = []
        let randomInt = Int.random(in: 10..<24)
        for _ in 0...randomInt {
            let localStory = StoryModel(userName: randomUserName(), userPhoto: randomPhoto())
            createdStories.append(localStory)
        }
        
        return createdStories
    }
    
    private func createPosts() -> [PostModel] {
        var createdPosts:[PostModel] = []
        let randomInt = Int.random(in: 10..<24)
        for rnd in 0...randomInt {
            try! localRealm.write {
                let localPost = PostModel(rnd)
                localRealm.add(localPost)
                createdPosts.append(localPost)
            }
        }
        
        return createdPosts
    }
}

protocol IHomeLocalDataSource {
    func takeUserPosts() -> [PostModel]
    func takeUserStories() -> [StoryModel]
}
