//
//  PostModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import RealmSwift

final class PostModel: Object, Identifiable, IPostModel {
    let id = UUID()
    @Persisted public var postId: String = UUID().uuidString
    @Persisted public var userId: String = UUID().uuidString
    @Persisted public var userName: String = Consts.USER_NAME
    @Persisted public var userPhoto: String = Consts.USER_PHOTO
    @Persisted public var location: String = "Saint-Petersburg"
    @Persisted public var text: String = "some test text"
    @Persisted public var likesCount: String = "10"
    @Persisted public var commentsCount: String = "10"
    @Persisted public var isLiked: Bool = false
    @Persisted public var hasStory: Bool = false
    @Persisted public var date: String = "03.05.2022 21:55"
    @Persisted public var photos:List<RealmString> = List<RealmString>()
    
    
    private var alreadyFetched:[PhotoModel] = []
    
    var allPhotos: [PhotoModel]  {
        get {
            if !photos.isEmpty && alreadyFetched.isEmpty {
                alreadyFetched = photos.map { PhotoModel(userName: self.userName, photo: $0.photo) }
            }
            return alreadyFetched
        }
        set {
            alreadyFetched = newValue
            photos.removeAll()
            for item in newValue {
                photos.append(RealmString(inputPhoto: item.photo))
            }
        }
    }
    
    private var singlePhoto: String = ""
    
    var hasMoreContent: Bool {
        return allPhotos.count > 1
    }
    
    var isUser: Bool {
        return userId == Consts.USER_ID
    }
    
    override static func ignoredProperties() -> [String] {
        return ["allPhotos", "singlePhoto", "hasMoreContent", "alreadyFetched"]
    }
    
    
    convenience init(_ index: Int = 2) {
        self.init()
        userName = randomUserName()
        userPhoto = randomPhoto()
        text = randomString(256)
        likesCount = randomCount
        commentsCount = randomCount
        hasStory = randomBool
        isLiked = randomBool
        setupLocation()
        setupPhotos()
    }
    
    public func takeSinglePhoto() -> String {
        if singlePhoto.isEmpty {
            singlePhoto = allPhotos.first?.photo ?? userPhoto
        }
        return singlePhoto
    }
    
    private func setupLocation() {
        let isHideLocation = Int.random(in: 0..<2)%2 == 0
        if isHideLocation {
            location = ""
        }
    }
    
    private func setupPhotos() {
        if(photos.isEmpty) {
            let rndPhotos = randomPhotos()
            allPhotos = rndPhotos.map {
                PhotoModel(userName: self.userName, photo: $0)
            }
        }
    }
}
