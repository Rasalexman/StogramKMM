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
    
    var body: some View {
        ZStack {
            URLImage(user.photo.toUrl()) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: cWidth, height: cHeight, alignment: Alignment.center)
                    .clipShape(Circle())
                    //.shadow(radius: shadow)
                    .overlay(Circle().stroke(bColor, lineWidth: border))
            }
        }.frame(width: cWidth, height: cHeight)
    }
}

struct UserAvatarView_Previews: PreviewProvider {
    static var previews: some View {
        UserAvatarView(user: UserEntity.companion.createRandom(hasUserStory: true)).previewLayout(PreviewLayout.sizeThatFits)
    }
}
