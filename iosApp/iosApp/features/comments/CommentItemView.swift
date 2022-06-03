//
//  CommentItemView.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import SwiftUI
import shared

struct CommentItemView: View {
    
    var comment: CommentEntity
    @State var isLiked: Bool = false
    
    private var formattedDate: String {
        let fromDateFormatter = DateFormatter()
        fromDateFormatter.locale = Locale(identifier: "en_US_POSIX")
        fromDateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
        fromDateFormatter.timeZone = TimeZone(secondsFromGMT: 0)
        let dt = fromDateFormatter.date(from: comment.date)!
        
        let toDateFormatter = DateFormatter()
        toDateFormatter.locale = Locale(identifier: "en_US_POSIX")
        toDateFormatter.dateFormat = "dd.MM.yyyy HH:mm"
        toDateFormatter.timeZone = TimeZone(secondsFromGMT: 0)
        
        return toDateFormatter.string(from: dt)
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            
            AvatarDescView(
                user: comment.user,
                desc: formattedDate,
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
        CommentItemView(comment: CommentEntity.companion.createRandom())
            .previewLayout(PreviewLayout.sizeThatFits)
    }
}
