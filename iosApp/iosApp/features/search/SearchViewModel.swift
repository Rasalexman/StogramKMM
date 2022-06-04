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
    
    @Published var searchResult: [PostEntity] = []
    
    func searchPosts(query: String) -> [PostEntity] {
        repository.onQueryChanged(query: query)
        return searchResult
    }
    
    func showPostDetails() {
        onNavigateState(navTag: NavTag.DETAILS.rawValue)
    }
    
    func start() {
        addObserver(repository.takeSearchedPostsCommonFlow().watch { posts in
            if let defaultPosts = posts as? [PostEntity] {
                self.searchResult = defaultPosts
            }
        })
    }
}
