//
//  Modules.swift
//  Stogram
//
//  Created by Alexander on 18.04.2022.
//

import Foundation
import Sodi
import RealmSwift

let providersModule = sodiModule(moduleName: "providersModule") { sodi in
    sodi.bindSingle(to: Realm.self) {
        try! Realm()
    }
}

let ldsModule = sodiModule(moduleName: "ldsModule") { sodi in
    sodi.bindProvider(to: IUserLocalDataSource.self) { UserLocalDataSource() }
    sodi.bindProvider(to: IHomeLocalDataSource.self) { HomeLocalDataSource() }
    sodi.bindProvider(to: ISearchLocalDataSource.self) { SearchLocalDataSource() }
    sodi.bindProvider(to: IReactionsLocalDataSource.self) { ReactionsLocalDataSource() }
    sodi.bindProvider(to: ICommentsLocalDataSource.self) { CommentsLocalDataSource() }
}

let repositoryModule = sodiModule(moduleName: "repositoryModule") { sodi in
    sodi.bindSingle(to: IUserRepository.self) { UserRepository(lds: instance()) }
    sodi.bindSingle(to: IHomeRepository.self) { HomeReposotory(lds: instance()) }
    sodi.bindSingle(to: ISearchRepository.self) { SearchRepository(lds: instance()) }
    sodi.bindSingle(to: IReactionsRepository.self) { ReactionsRepository(lds: instance()) }
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
