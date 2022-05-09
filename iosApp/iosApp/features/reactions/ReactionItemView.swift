//
//  ReactionItemView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI

struct ReactionItemView: View {
    
    var reaction: ReactionModel
    
    var body: some View {
        HStack(alignment: .center, spacing: 0) {
                        
            CircleImageView(photo: reaction.reactionUserPhoto)
                
            Text(reaction.fullDescription)
                .font(.system(size: 12))
                .lineLimit(5)
                .multilineTextAlignment(.leading)
                .padding(EdgeInsets(top:0, leading: 8, bottom: 0, trailing: 8))
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
            
            ZStack {
                ProfilePhotoView(postModel: reaction.postModel, cWidth: Consts.REACTION_IMAGE_SIZE, cHeight: Consts.REACTION_IMAGE_SIZE)
                    .frame(width: Consts.REACTION_IMAGE_SIZE, height: Consts.REACTION_IMAGE_SIZE, alignment:.center)
            }.frame(alignment: .trailing)
            
        }.padding(EdgeInsets(top:8, leading: 8, bottom: 8, trailing: 8))
            .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
    }
}

struct ReactionItemView_Previews: PreviewProvider {
    static var previews: some View {
        ReactionItemView(reaction: ReactionModel(
            reactionType: ReactionType.photoComment,
            reactionUserPhoto: randomPhoto(),
            comment: randomString(400)
        ))
    }
}
