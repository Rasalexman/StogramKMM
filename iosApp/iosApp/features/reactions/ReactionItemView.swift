//
//  ReactionItemView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import shared

struct ReactionItemView: View {
    
    var reaction: ReactionEntity
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
                        
            UserAvatarView(user: reaction.takeUserFrom())
                
            Text(reaction.createFullDescription())
                .font(.system(size: 12))
                .lineLimit(5)
                .multilineTextAlignment(.leading)
                .padding(EdgeInsets(top:0, leading: 8, bottom: 0, trailing: 8))
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
            
            ZStack {
                ProfilePhotoView(post: reaction.takeReactionPost(), cWidth: Consts.REACTION_IMAGE_SIZE, cHeight: Consts.REACTION_IMAGE_SIZE)
                    .frame(width: Consts.REACTION_IMAGE_SIZE, height: Consts.REACTION_IMAGE_SIZE, alignment:.center)
            }.frame(alignment: .trailing)
            
        }.padding(EdgeInsets(top:8, leading: 8, bottom: 8, trailing: 8))
            .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
    }
}

struct ReactionItemView_Previews: PreviewProvider {
    static var previews: some View {
        ReactionItemView(reaction: ReactionEntity.companion.createRandom())
    }
}
