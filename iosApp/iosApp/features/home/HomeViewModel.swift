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

//    private var job: Closeable? = nil
//    private let postsRepository = PostsRepository()
    
    @Published var userPosts: [PostEntity] = []
    @Published var userStories: [UserEntity] = []

    //private var sharedPost = [PostEntity]()
    
    override init() {
        super.init()
        self.fetchHomeData()
    }
    
    private func fetchHomeData() {
        userPosts = PostEntity.companion.createRandomList()
        userStories = UserEntity.companion.createRandomList(hasUserStory: true)
    }

    func startObservingPosts() {
//        self.job = postsRepository.allPostsAsCommonFlowable().watch { posts in
//            self.sharedPost = posts as! [PostEntity]
//            print("----> posts count \(posts?.count ?? 0)")
//        }
        //let posts = realmDatabase.getAllPosts()
    }

    func stopObservingPost() {
//        self.job?.close()
    }
}
