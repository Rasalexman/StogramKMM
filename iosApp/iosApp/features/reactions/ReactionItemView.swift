//
//  ReactionItemView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import shared

struct ReactionItemView: View {
    
    @State var reaction: ReactionEntity
    
    private var user: IUser {
        return reaction.takeUserFrom()
    }
    
    private var postPhoto: URL {
        return reaction.takeReactionPost().takeFirstPhoto().toUrl()
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
            
            AvatarView(user: self.user)
            
            Text(reaction.createFullDescription())
                .font(.system(size: 12))
                .lineLimit(5)
                .multilineTextAlignment(.leading)
                .padding(EdgeInsets(top:0, leading: 8, bottom: 0, trailing: 8))
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
            
            ProfilePhotoView(photoUrl: postPhoto, hasMoreContent: false,
                             cWidth: Consts.REACTION_IMAGE_SIZE, cHeight: Consts.REACTION_IMAGE_SIZE)
            .frame(width: Consts.REACTION_IMAGE_SIZE, height: Consts.REACTION_IMAGE_SIZE, alignment:.center)
            
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .padding(EdgeInsets(top:8, leading: 8, bottom: 8, trailing: 8))
            .buttonStyle(PlainButtonStyle())
    }
}

struct ReactionItemView_Previews: PreviewProvider {
    static var previews: some View {
        ReactionItemView(reaction: ReactionEntity.companion.createRandom()).previewLayout(PreviewLayout.sizeThatFits)
    }
}
