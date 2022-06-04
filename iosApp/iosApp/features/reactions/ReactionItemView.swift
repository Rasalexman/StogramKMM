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
                     
            UserAvatarView(photoUrl: user.photo.toUrl()).background(
                NavigationLink(destination: ProfileView(profileId: user.id)) { EmptyView() }
            )
                
            Text(reaction.createFullDescription())
                .font(.system(size: 12))
                .lineLimit(5)
                .multilineTextAlignment(.leading)
                .padding(EdgeInsets(top:0, leading: 8, bottom: 0, trailing: 8))
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
            
            ZStack {
                ProfilePhotoView(photoUrl: postPhoto, hasMoreContent: false,
                                 cWidth: Consts.REACTION_IMAGE_SIZE, cHeight: Consts.REACTION_IMAGE_SIZE)
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
