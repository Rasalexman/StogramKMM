//
//  CommentItemView.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import SwiftUI

struct CommentItemView: View {
    
    var comment: CommentModel
    @State var isLiked: Bool = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            
            AvatarDescView(
                user: comment,
                userDesc: comment.date,
                imageSize: Consts.COMMENT_IMAGE_SIZE,
                nameSize: 12
            )
            
            Text(comment.text)
                .font(.system(size: 12))
                .multilineTextAlignment(.leading)
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
                .padding([.leading], 8)
            
            HStack {
                
                LikesCountView(isLiked: $isLiked, count: comment.likesCount, paddingStart: Consts.ZERO_SIZE) {
                    isLiked = !isLiked
                }
                
                HStack {
                    Button(action: {
                        print("-----> HELLO SHARE")
                    }, label: {
                        Image(systemName: "arrowshape.turn.up.forward.fill")
                            .resizable()
                            .frame(width: 26, height: 22)
                    }).padding(4)
                        .buttonStyle(.borderless)
                }.frame(minWidth: 0, maxWidth: .infinity, alignment: .trailing)
                    
            }.frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 8)
                .onAppear {
                    isLiked = comment.isLiked
                }
        }.padding(EdgeInsets(top:0, leading: 0, bottom: 4, trailing: 4))
            .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
    }
}

struct CommentItemView_Previews: PreviewProvider {
    static var previews: some View {
        CommentItemView(comment: CommentModel())
            .previewLayout(PreviewLayout.sizeThatFits)
    }
}
