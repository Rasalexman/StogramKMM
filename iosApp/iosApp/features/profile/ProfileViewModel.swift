//
//  ProfileViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi
import shared

final class ProfileViewModel : BaseViewModel {
    
    @Published var selectedUser: IUser = UserEntity.companion.createRandomDetailed(hasUserStory: true)
    
    @Published var userPosts: [PostEntity] = []
    
    func fetchProfileData(userId: String) {
        userPosts = PostEntity.companion.createRandomList()
    }
}
