//
//  PostView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI

struct PostView: View {
    var post: PostModel
    var showHeader: Bool = true
    var showCommentsCount: Bool = true
    var showContent: Bool = true
    
    @State var hasLike: Bool = false
    
    var body: some View {
        
        VStack(alignment: .leading, spacing: 0) {
            
            if(showHeader) {
                AvatarDescView(user: post, userDesc: post.location)
            }
            
            if(showContent) {
                if(post.hasMoreContent) {
                    PostSliderView(images: post.allPhotos)
                } else {
                    ProfilePhotoView(postModel: post)
                }
            }
            
            HStack {
                
                LikesCountView(isLiked: $hasLike, count: self.post.likesCount) {
                    //post.isLiked = !post.isLiked
                    hasLike = !hasLike
                }
                
                if(showCommentsCount) {
                    CommentsCountView(post: self.post)
                }
                
                HStack {
                    Button(action: {
                        print("-----> HELLO SHARE")
                    }, label: {
                        Image(systemName: "paperplane")
                            .resizable()
                            .frame(width: 22, height: 22)
                    }).padding(4)
                        .buttonStyle(.borderless)
                    
                }.frame(minWidth: 0, maxWidth: .infinity, alignment: .trailing)
                
            }.padding(EdgeInsets(top:8, leading: 4, bottom: 4, trailing: 8))
                .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
            
            Text(post.text)
                .font(.system(size: 12))
                .lineLimit(5)
                .padding(EdgeInsets(top:2, leading: 8, bottom: 10, trailing: 8))
            
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading).onAppear {
            hasLike = post.isLiked
        }
        
    }
}

struct PostView_Previews: PreviewProvider {
    static var previews: some View {
        PostView(post: PostModel()).previewLayout(PreviewLayout.sizeThatFits).previewLayout(PreviewLayout.sizeThatFits)
    }
}
