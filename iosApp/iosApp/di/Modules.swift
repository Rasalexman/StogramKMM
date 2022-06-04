//
//  Modules.swift
//  Stogram
//
//  Created by Alexander on 18.04.2022.
//

import Foundation
import Sodi
import shared

let databaseModule = sodiModule(moduleName: "databaseModule") { sodi in
    sodi.bindProvider(to: RealmDataBase.self) { RealmDataBase() }
}

let ldsModule = sodiModule(moduleName: "ldsModule") { sodi in
    sodi.bindProvider(to: IUserStoriesLocalDataSource.self) { UserStoriesLocalDataSource(database: instance()) }
    sodi.bindProvider(to: IPostsLocalDataSource.self) { PostsLocalDataSource(database: instance()) }
    sodi.bindProvider(to: IReactionsLocalDataSource.self) { ReactionsLocalDataSource(database: instance()) }
    sodi.bindProvider(to: ICommentsLocalDataSource.self) { CommentsLocalDataSource() }
}

let repositoryModule = sodiModule(moduleName: "repositoryModule") { sodi in
    sodi.bindSingle(to: IUserStoriesRepository.self) { UserStoriesRepository(localDataSource: instance()) }
    sodi.bindSingle(to: IPostsRepository.self) { PostsRepository(localDataSource: instance()) }
    sodi.bindSingle(to: ISearchRepository.self) { SearchRepository() }
    sodi.bindSingle(to: IReactionsRepository.self) { ReactionsRepository(localDataSource: instance()) }
    sodi.bindSingle(to: ICommentsRepository.self) { CommentsRepository(lds: instance()) }
}

let viewModelModule = sodiModule(moduleName: "viewModelModule") { sodi in
    sodi.bindSingle { ProfileViewModel() }
    sodi.bindSingle { HomeViewModel() }
    sodi.bindSingle { MainViewModel() }
    sodi.bindSingle { SearchViewModel() }
    sodi.bindSingle { ReactionsViewModel() }
    sodi.bindSingle { PostDetailsViewModel() }
    sodi.bindSingle { CommentsViewModel() }
}
