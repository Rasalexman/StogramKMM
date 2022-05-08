//
//  CommentsCountView.swift
//  Stogram
//
//  Created by Alexander on 03.05.2022.
//

import SwiftUI

struct CommentsCountView: View {
    var post: PostModel
    
    @State private var isActive: Bool = false
    
    var body: some View {
        NavigationLink(
            destination: PostDetailsView(selectedPost: post, showContent: false, showHeader: true, showCommentsCount: false),
            isActive: $isActive
        ) {
            Button(action: {
                print("-----> HELLO COMMENT")
                self.isActive = true
            }, label: {
                
                Image(systemName: "text.bubble.fill")
                    .resizable()
                    .frame(width: 22, height: 22)
                
                Text(post.commentsCount)
                    .font(.system(size: 12))
                    .padding(EdgeInsets(top:4, leading: 0, bottom: 4, trailing: 0))
                
            }).padding(EdgeInsets(top:4, leading: 4, bottom: 4, trailing: 8))
                .buttonStyle(.borderless)
        }.buttonStyle(PlainButtonStyle())
    }
}

struct CommentsCountView_Previews: PreviewProvider {
    static var previews: some View {
        CommentsCountView(post: PostModel())
    }
}
