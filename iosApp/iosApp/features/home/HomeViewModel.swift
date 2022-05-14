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
    private let repository: IHomeRepository = instance()

    private var job: Closeable? = nil
    private let postsRepository = PostsRepository()
    private let realmDatabase = RealmDataBase()
    
    @Published var userPosts: [PostModel] = []
    @Published var userStories: [StoryModel] = []

    private var sharedPost = [PostEntity]()
    
    override init() {
        super.init()
        self.fetchHomeData()
    }
    
    private func fetchHomeData() {
        userPosts = self.repository.takeUserPosts()
        userStories = self.repository.takeUserStories()
    }

    func startObservingPosts() {
        self.job = postsRepository.allPostsAsCommonFlowable().watch { posts in
            self.sharedPost = posts as! [PostEntity]
            print("----> posts count \(posts?.count ?? 0)")
        }
        //let posts = realmDatabase.getAllPosts()
    }

    func stopObservingPost() {
        self.job?.close()
    }
}
