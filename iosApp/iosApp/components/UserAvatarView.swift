//
//  CircleImageView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import URLImage
import shared

struct UserAvatarView: View {
    
    var user: IUser
    var cWidth: CGFloat = Consts.DEFAULT_IMAGE_SIZE
    var cHeight: CGFloat = Consts.DEFAULT_IMAGE_SIZE
    var border: CGFloat = Consts.ZERO_SIZE
    var shadow: CGFloat = Consts.ZERO_SIZE
    var bColor: Color = Color.red
    
    private var imageUrl: URL {
        return URL(string: user.photo)!
    }
    
    var body: some View {
        URLImage(imageUrl) { image in
            image
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: cWidth, height: cHeight)
                .clipShape(Circle())
                .shadow(radius: shadow)
                .overlay(Circle().stroke(bColor, lineWidth: border))
        }
    }
}

struct UserAvatarView_Previews: PreviewProvider {
    static var previews: some View {
        UserAvatarView(user: UserEntity.companion.createRandom(hasUserStory: true))
    }
}
