//
//  PostDetailsView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi
import shared

struct PostDetailsView: BaseView {
    
    @ObservedObject private var vm: PostDetailsViewModel = instance()
    @State var selectedPost: PostEntity
    var showContent: Bool = true
    var showHeader: Bool = false
    var showCommentsCount: Bool = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            ScrollView(.vertical, showsIndicators: false) {
                LazyVStack(alignment: .leading, spacing: 0) {
                    Divider()
                    
                    PostView(
                        post: self.selectedPost,
                        showHeader: self.showHeader,
                        showCommentsCount: self.showCommentsCount,
                        showContent: self.showContent
                    )
                    
                    Divider()
                    
                    Text("Комментарии:")
                        .font(.system(size: 20))
                        .multilineTextAlignment(.leading)
                        .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
                        .padding([.top, .leading], 8)
                    
                    CommentsView(postId: selectedPost.id)
                }
            }
            Divider()
            EnterBarView(enteredText: $vm.enteredText)
        }
    }
    
    static func getNavLink(onSelection: Binding<String?>, selectedPost: PostEntity) -> some View {
        return NavigationLink(
            destination: PostDetailsView(selectedPost: selectedPost)
        ) { EmptyView() }
    }
}

struct PostDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        PostDetailsView(selectedPost: PostEntity.companion.createRandom(defaultUser: nil))
    }
}
