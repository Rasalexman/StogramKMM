//
//  PhotosRepository.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

final class SearchRepository : ISearchRepository {
    private let localDataSource: ISearchLocalDataSource
    init(lds: ISearchLocalDataSource) {
        localDataSource = lds
    }
    
    func searchPosts(query: String) -> [PostModel] {
        return localDataSource.searchPosts(query: query)
    }
    
    func takeRandomPosts() -> [PostModel] {
        return localDataSource.takeRandomPosts()
    }
}

protocol ISearchRepository {
    func searchPosts(query: String) -> [PostModel]
    func takeRandomPosts() -> [PostModel]
}
