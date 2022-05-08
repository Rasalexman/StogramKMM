//
//  SearchViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi

final class SearchViewModel : BaseViewModel {
    private let repository: ISearchRepository = instance()
    
    var randomPosts: [PostModel] = []
    
    override init() {
        super.init()
        randomPosts = takeRandomPosts()
    }
    
    func takeRandomPosts() -> [PostModel] {
        return randomPosts.isEmpty ? repository.takeRandomPosts() : randomPosts
    }
    
    func searchPosts(query: String) -> [PostModel] {
        return repository.searchPosts(query: query.lowercased())
    }
    
    func showPostDetails() {
        onNavigateState(navTag: NavTag.DETAILS.rawValue)
    }
}
