//
//  PhotosRepository.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import shared

final class SearchRepository : ISearchRepository {
    private let localDataSource: ISearchLocalDataSource
    init(lds: ISearchLocalDataSource) {
        localDataSource = lds
    }
    
    func searchPosts(query: String) -> [PostEntity] {
        return localDataSource.searchPosts(query: query)
    }
    
    func takeRandomPosts() -> [PostEntity] {
        return localDataSource.takeRandomPosts()
    }
}

protocol ISearchRepository {
    func searchPosts(query: String) -> [PostEntity]
    func takeRandomPosts() -> [PostEntity]
}
