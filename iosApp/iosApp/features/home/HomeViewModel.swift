//
//  HomeViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi
import shared

class HomeViewModel : BaseViewModel {

    private let postsRepository: IPostsRepository = instance()
    private let userStoriesRepository: IUserStoriesRepository = instance()
    
    @Published var userPosts: [PostEntity] = []
    @Published var userStories: [UserEntity] = []
    
    override init() {
        super.init()
        //fetchHomeData()
    }
    
    private func fetchHomeData() {
        userPosts = PostEntity.companion.createRandomList()
        userStories = UserEntity.companion.createRandomList(hasUserStory: true)
    }

    func start() {
        addObserver(postsRepository.allPostsAsCommonFlowable().flatMapCFlow(flatBlock: { posts in
            self.userStoriesRepository.getStoriesAsCommonFlow().mapCFlow(mapBlock: { stories in
                if let currentPosts = posts as? [PostEntity] {
                    if let currentStories = stories as? [UserEntity] {
                        return HomeState(posts: currentPosts, stories: currentStories)
                    }
                }
                return HomeState.EMPTY
            })
        }).watch { resultModel in
            if let currentModel = resultModel as? HomeState {
                self.userPosts = currentModel.posts
                self.userStories = currentModel.stories
            }
        })
    }
}
