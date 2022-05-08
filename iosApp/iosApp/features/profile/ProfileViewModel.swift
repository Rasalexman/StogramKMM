//
//  ProfileViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi

final class ProfileViewModel : BaseViewModel {
    
    private let repository: IUserRepository = instance()
    
    @Published var postCount: String = "957"
    @Published var subsCount: String = "349"
    @Published var observCount: String = "453"
    
    @Published var userName: String = "Александр Минкин"
    @Published var userDesc: String = randomString(256)
    
    @Published var userPosts: [PostModel] = []
    
    func fetchProfileData(userId: String) {
        userPosts = repository.takeUserPosts(userId: userId)
    }
}
