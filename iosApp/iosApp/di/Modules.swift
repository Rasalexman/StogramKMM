//
//  Modules.swift
//  Stogram
//
//  Created by Alexander on 18.04.2022.
//

import Foundation
import Sodi
import shared

let databaseModule = sodiModule(moduleName: ModulesNames.DATABASE) { sodi in
    sodi.bindSingle(to: RealmDataBase.self) { RealmDataBase() }
}

let ldsModule = sodiModule(moduleName: ModulesNames.LDS) { sodi in
    sodi.bindProvider(to: IUserLocalDataSource.self) { UserLocalDataSource(database: instance()) }
    sodi.bindProvider(to: IUserStoriesLocalDataSource.self) { UserStoriesLocalDataSource(database: instance()) }
    sodi.bindProvider(to: IPostsLocalDataSource.self) { PostsLocalDataSource(database: instance()) }
    sodi.bindProvider(to: IReactionsLocalDataSource.self) { ReactionsLocalDataSource(database: instance()) }
    sodi.bindProvider(to: ICommentsLocalDataSource.self) { CommentsLocalDataSource(database: instance()) }
}

let repositoryModule = sodiModule(moduleName: ModulesNames.REPOSITORY) { sodi in
    sodi.bindSingle(to: IUserRepository.self) { UserRepository(localDataSource: instance()) }
    sodi.bindSingle(to: IUserStoriesRepository.self) { UserStoriesRepository(localDataSource: instance()) }
    sodi.bindSingle(to: IPostsRepository.self) { PostsRepository(localDataSource: instance()) }
    sodi.bindSingle(to: ISearchRepository.self) { SearchRepository() }
    sodi.bindSingle(to: IReactionsRepository.self) { ReactionsRepository(localDataSource: instance()) }
    sodi.bindSingle(to: ICommentsRepository.self) { CommentsRepository(localDataSource: instance()) }
}

let viewModelModule = sodiModule(moduleName: ModulesNames.VIEWMODELS) { sodi in
    sodi.bindSingle { ProfileViewModel() }
    sodi.bindSingle { HomeViewModel() }
    sodi.bindSingle { MainViewModel() }
    sodi.bindSingle { SearchViewModel() }
    sodi.bindSingle { ReactionsViewModel() }
    sodi.bindSingle { PostDetailsViewModel() }
    sodi.bindSingle { CommentsViewModel() }
    sodi.bindSingle { CreatePostViewModel() }
}
