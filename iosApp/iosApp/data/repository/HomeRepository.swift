//
//  HomeRepository.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

final class HomeReposotory : IHomeRepository {
    
    private let localDataSource: IHomeLocalDataSource
    init(lds: IHomeLocalDataSource) {
        localDataSource = lds
    }
    
    func takeUserPosts() -> [PostModel] {
        return localDataSource.takeUserPosts()
    }
    
    func takeUserStories() -> [StoryModel] {
        return localDataSource.takeUserStories()
    }
    
}

protocol IHomeRepository {
    func takeUserPosts() -> [PostModel]
    func takeUserStories() -> [StoryModel]
}
