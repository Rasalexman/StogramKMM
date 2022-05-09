//
//  PhotosLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

final class SearchLocalDataSource : ISearchLocalDataSource {
    
    private var lastQuery: String = ""
    private var searchPosts: [PostModel] = []
    
    func searchPosts(query: String) -> [PostModel] {
        if query != lastQuery {
            let lowerCasedQuery = query.lowercased()
            let randomPhotos = takeRandomPosts()
            let filtered = randomPhotos.filter {
                $0.userName.lowercased().contains(lowerCasedQuery) || $0.userId.lowercased().contains(lowerCasedQuery)
            }
            lastQuery = query
            searchPosts = filtered
            return filtered
        }
        return searchPosts
    }
    
    func takeRandomPosts() -> [PostModel] {
        var createdPosts:[PostModel] = []
        let randomInt = Int.random(in: 20..<48)
        for rnd in 0...randomInt {
            createdPosts.append(PostModel(rnd))
        }
        return createdPosts
    }
}

protocol ISearchLocalDataSource {
    func searchPosts(query: String) -> [PostModel]
    func takeRandomPosts() -> [PostModel]
}
