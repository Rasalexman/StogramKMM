//
//  AvatarDescView.swift
//  Stogram
//
//  Created by Alexander on 03.05.2022.
//

import SwiftUI
import shared

struct AvatarDescView: View {
    
    var user: IUser
    var desc: String = ""
    var imageSize: CGFloat = Consts.POST_IMAGE_SIZE
    var nameSize: CGFloat = 16
    
    var isShowDesc: Bool {
        return !desc.isEmpty
    }
    
    var borderSize: CGFloat {
        return user.hasStory ? Consts.STORY_BORDER_SIZE : Consts.ZERO_SIZE
    }
    
    var body: some View {
        NavigationLink(destination: ProfileView(profileId: user.id)) {
            HStack {
                UserAvatarView(user: user, cWidth: imageSize, cHeight: imageSize, border: borderSize)
                
                VStack(alignment: .leading, spacing: 0) {
                    Text(user.name)
                        .font(.system(size: nameSize))
                        .bold()
                        .lineLimit(1)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    if isShowDesc {
                        Text(desc)
                            .font(.system(size: 12))
                            .lineLimit(1)
                    }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(EdgeInsets(top:8, leading: 8, bottom: 8, trailing: 8))
        }.buttonStyle(PlainButtonStyle())
    }
}

struct AvatarDescView_Previews: PreviewProvider {
    static var previews: some View {
        AvatarDescView(user: PostEntity().takePostUser()).previewLayout(PreviewLayout.sizeThatFits)
    }
}
