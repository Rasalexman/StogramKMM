//
//  UserRepository.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

final class UserRepository : IUserRepository {
    private let localDataSource: IUserLocalDataSource
    init(lds: IUserLocalDataSource) {
        localDataSource = lds
    }
    
    func takeUserPosts(userId: String) -> [PostModel] {
        return localDataSource.takeUserPosts(userId: userId)
    }
}

protocol IUserRepository {
    func takeUserPosts(userId: String) -> [PostModel]
}
