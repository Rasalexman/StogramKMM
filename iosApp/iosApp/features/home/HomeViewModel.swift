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

    private var job: Closeable? = nil
    private var storiesJob: Closeable? = nil
    
    private let postsRepository: IPostsRepository = instance()
    private let userStoriesRepository: IUserStoriesRepository = instance()
    
    @Published var userPosts: [PostEntity] = []
    @Published var userStories: [UserEntity] = []
    
    override init() {
        super.init()
        fetchHomeData()
    }
    
    private func fetchHomeData() {
        userPosts = PostEntity.companion.createRandomList()
        userStories = UserEntity.companion.createRandomList(hasUserStory: true)
    }

    func startObservingPosts() {
//        stopObservingPost()
//        self.job = postsRepository.allPostsAsCommonFlowable().watch { result in
//            if let posts = result as? [PostEntity] {
//                self.userPosts = posts
//                print("----> posts count \(posts.count)")
//            }
//        }
//        self.storiesJob = userStoriesRepository.getStoriesAsCommonFlow().watch { result in
//            if let stories = result as? [UserEntity] {
//                self.userStories = stories
//                print("----> stories count \(stories.count)")
//            }
//        }
    }

    func stopObservingPost() {
        self.job?.close()
        self.job = nil
        self.storiesJob?.close()
        self.storiesJob = nil
    }
}
