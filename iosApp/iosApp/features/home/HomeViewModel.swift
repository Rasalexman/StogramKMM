//
//  HomeViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi

class HomeViewModel : BaseViewModel {
    private let repository: IHomeRepository = instance()
    
    @Published var userPosts: [PostModel] = []
    @Published var userStories: [StoryModel] = []
    
    override init() {
        super.init()
        self.fetchHomeData()
    }
    
    private func fetchHomeData() {
        userPosts = self.repository.takeUserPosts()
        userStories = self.repository.takeUserStories()
    }
}
