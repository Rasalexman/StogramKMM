//
//  SearchViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi
import shared

final class SearchViewModel : BaseViewModel {
    private let repository: ISearchRepository = instance()
    
    var randomPosts: [PostEntity] = []
    
    override init() {
        super.init()
        randomPosts = PostEntity.companion.createRandomList()
    }
    
    func takeRandomPosts() -> [PostEntity] {
        return randomPosts.isEmpty ? repository.takeRandomPosts() : randomPosts
    }
    
    func searchPosts(query: String) -> [PostEntity] {
        return repository.searchPosts(query: query.lowercased())
    }
    
    func showPostDetails() {
        onNavigateState(navTag: NavTag.DETAILS.rawValue)
    }
}
