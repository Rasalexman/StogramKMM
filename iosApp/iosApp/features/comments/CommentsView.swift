//
//  CommentsView.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import SwiftUI
import Sodi

struct CommentsView: BaseView {
    
    @ObservedObject private var vm: CommentsViewModel = instance()
    
    var body: some View {
        LazyVStack(alignment: .trailing, spacing: 0) {
            ForEach(vm.postComments, id: \.id) { comment in
                CommentItemView(comment: comment)
                    .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
                Divider()
                    .frame(width: UIScreen.screenWidth-8, height: 2, alignment: .trailing)
            }
        }.listStyle(.plain)
    }
}

struct CommentsView_Previews: PreviewProvider {
    static var previews: some View {
        CommentsView()
    }
}
