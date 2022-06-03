//
//  PhotosLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import shared

final class SearchLocalDataSource : ISearchLocalDataSource {
    
    private var lastQuery: String = ""
    private var searchPosts: [PostEntity] = []
    
    func searchPosts(query: String) -> [PostEntity] {
        if query != lastQuery {
            let lowerCasedQuery = query.lowercased()
            let randomPhotos = takeRandomPosts()
            let filtered = randomPhotos.filter {
                let user = $0.user ?? UserEntity.companion.createRandom(hasUserStory: true)
                return user.name.lowercased().contains(lowerCasedQuery) || user.id.lowercased().contains(lowerCasedQuery)
            }
            lastQuery = query
            searchPosts = filtered
            return filtered
        }
        return searchPosts
    }
    
    func takeRandomPosts() -> [PostEntity] {
        return PostEntity.companion.createRandomList()
    }
}

protocol ISearchLocalDataSource {
    func searchPosts(query: String) -> [PostEntity]
    func takeRandomPosts() -> [PostEntity]
}
