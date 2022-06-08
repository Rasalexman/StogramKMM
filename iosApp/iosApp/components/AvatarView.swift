//
//  AvatarView.swift
//  iosApp
//
//  Created by Alexander on 08.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AvatarView: View {
    
    @State var user: IUser
    @State private var isActive: Bool = false
    
    private var photoUrl: URL {
        return user.photo.toUrl()
    }
    
    private var userId: String {
        return user.id
    }
    
    var body: some View {
        
        Button(action: {
            self.isActive = true
        }, label: {
            UserAvatarView(photoUrl: photoUrl)
        })
        
        NavigationLink(
            destination: ProfileView(profileId: userId),
            isActive: $isActive) { EmptyView() }
            .hidden().frame(width: 0)
            .opacity(0)
            .buttonStyle(PlainButtonStyle())
        
    }
}

struct AvatarView_Previews: PreviewProvider {
    static var previews: some View {
        AvatarView(user: UserEntity.companion.createRandom(hasUserStory: false))
    }
}
