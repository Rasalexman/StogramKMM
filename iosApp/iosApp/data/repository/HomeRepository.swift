//
//  HomeRepository.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import shared

final class HomeReposotory : IHomeRepository {
    
    private let localDataSource: IHomeLocalDataSource
    init(lds: IHomeLocalDataSource) {
        localDataSource = lds
    }
    
    func takeUserPosts() -> [PostEntity] {
        return localDataSource.takeUserPosts()
    }
    
    func takeUserStories() -> [UserEntity] {
        return localDataSource.takeUserStories()
    }
    
}

protocol IHomeRepository {
    func takeUserPosts() -> [PostEntity]
    func takeUserStories() -> [UserEntity]
}
